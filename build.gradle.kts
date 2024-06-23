// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
}

val detektFormatting = libs.detekt.formatting

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config.setFrom(rootProject.files("config/detekt/detekt.yml"))
    }

    dependencies {
        detektPlugins(detektFormatting)
    }
}

tasks.register("installGitHooks") {
    val gitHooksDir = File(rootDir, ".git/hooks")
    val preCommitFile = File(gitHooksDir, "pre-commit")
    val preCommitScript = File(rootDir, "scripts/pre-commit.sh")

    preCommitScript.copyTo(preCommitFile, true)
    preCommitFile.setExecutable(true)
}

tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")

true // Needed to make the Suppress annotation work for the plugins block
