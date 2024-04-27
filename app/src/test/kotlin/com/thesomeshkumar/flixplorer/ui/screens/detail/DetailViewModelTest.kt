package com.thesomeshkumar.flixplorer.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.thesomeshkumar.flixplorer.data.common.RemoteSourceException
import com.thesomeshkumar.flixplorer.data.model.MovieDetailsDTO
import com.thesomeshkumar.flixplorer.data.model.mapToUI
import com.thesomeshkumar.flixplorer.data.repository.FlixplorerRepository
import com.thesomeshkumar.flixplorer.ui.models.DetailUI
import com.thesomeshkumar.flixplorer.ui.navigation.MainScreenRoutes
import com.thesomeshkumar.flixplorer.util.MainDispatcherRule
import com.thesomeshkumar.flixplorer.util.parseJson
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import java.io.IOException
import java.net.SocketTimeoutException

class DetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK(relaxed = true)
    private lateinit var repository: FlixplorerRepository

    private lateinit var viewModel: DetailViewModel
    private var mediaType: String = "movie"
    private var mediaId: Int = 1

    @Test
    fun `when view model initialized correctly expect Loading ui state`() = runTest {
        createViewModel()
        expectThat(viewModel.uiState.value).isA<DetailUiState.Loading>()
        verify(exactly = 1) { repository.getDetails(mediaType, mediaId) }
    }

    @Test
    fun `when view model initialized with wrong mediaType expect IllegalArgumentException exception`() =
        runTest {
            expectCatching { createViewModel(mediaType = null) }.isFailure()
                .isA<IllegalArgumentException>()

            verify(exactly = 0) { repository.getDetails(any(), any()) }
        }

    @Test
    fun `when server returns movie details expect Success ui state`() = runTest {
        val response = parseJson<MovieDetailsDTO, DetailUI>("movies_detail.json") { movieResponse ->
            movieResponse.mapToUI()
        }

        every { repository.getDetails(any(), any()) } returns flowOf(response)
        createViewModel()
        viewModel.uiState.test {
            expectThat(awaitItem()).isA<DetailUiState.Success>()
                .get { details.title }
                .isEqualTo("Expendables")
        }
    }

    @Test
    fun `when server returns unknown error expect Unexpected error ui state`() = runTest {
        every { repository.getDetails(any(), any()) } returns flow { throw Exception() }
        createViewModel()
        viewModel.uiState.test {
            expectThat(awaitItem()).isA<DetailUiState.Error>()
                .get {
                    this.remoteSourceException
                }
                .isA<RemoteSourceException.Unexpected>()
        }
    }

    @Test
    fun `when server returns unknown error expect Connection error ui state`() = runTest {
        every { repository.getDetails(any(), any()) } returns flow { throw IOException() }
        createViewModel()
        viewModel.uiState.test {
            expectThat(awaitItem()).isA<DetailUiState.Error>()
                .get {
                    this.remoteSourceException
                }
                .isA<RemoteSourceException.Connection>()
        }
    }

    @Test
    fun `when server returns unknown error expect Timeout error ui state`() = runTest {
        every {
            repository.getDetails(any(), any())
        } returns flow { throw SocketTimeoutException() }
        createViewModel()
        viewModel.uiState.test {
            expectThat(awaitItem()).isA<DetailUiState.Error>()
                .get {
                    this.remoteSourceException
                }
                .isA<RemoteSourceException.Timeout>()
        }
    }

    @Test
    fun `when server returns unknown error expect Client error ui state`() = runTest {
        every {
            repository.getDetails(any(), any())
        } returns flow {
            throw HttpException(Response.error<Nothing>(400, "".toResponseBody(null)))
        }
        createViewModel()
        viewModel.uiState.test {
            expectThat(awaitItem()).isA<DetailUiState.Error>()
                .get {
                    this.remoteSourceException
                }
                .isA<RemoteSourceException.Client>()
        }
    }

    @Test
    fun `when server returns unknown error expect Server error ui state`() = runTest {
        every {
            repository.getDetails(any(), any())
        } returns flow {
            throw HttpException(Response.error<Nothing>(500, "".toResponseBody(null)))
        }
        createViewModel()
        viewModel.uiState.test {
            expectThat(awaitItem()).isA<DetailUiState.Error>()
                .get {
                    this.remoteSourceException
                }
                .isA<RemoteSourceException.Server>()
        }
    }

    private fun createViewModel(mediaType: String? = this.mediaType, mediaId: Int = this.mediaId) {
        val savedState = SavedStateHandle(
            mapOf(
                MainScreenRoutes.ARG_MEDIA_TYPE to mediaType,
                MainScreenRoutes.ARG_MEDIA_ID to mediaId.toString()
            )
        )
        viewModel = DetailViewModel(savedState, repository)
    }
}
