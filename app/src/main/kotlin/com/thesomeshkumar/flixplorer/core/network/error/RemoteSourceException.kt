package com.thesomeshkumar.flixplorer.core.network.error

sealed class RemoteSourceException(val messageResource: Any?) : RuntimeException() {
    class Connection(messageResource: Int) : RemoteSourceException(messageResource)
    class Unexpected(messageResource: Int) : RemoteSourceException(messageResource)
    class Timeout(messageResource: Int) : RemoteSourceException(messageResource)
    class Client(messageResource: Int) : RemoteSourceException(messageResource)
    class Server(messageResource: Any?) : RemoteSourceException(messageResource)
}
