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

// ☝️ on top here your other content of build.gradle.kts file

tasks.register("copyGitHooks", Copy::class.java) {
    description = "Copies the git hooks from /git-hooks to the .git folder."
    group = "git hooks"
    from("$rootDir/scripts/pre-commit")
    into("$rootDir/.git/hooks/")
}
tasks.register("installGitHooks", Exec::class.java) {
    description = "Installs the pre-commit git hooks from /git-hooks."
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("Git hook installed successfully.")
    }
}

afterEvaluate {
    tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
}
true // Needed to make the Suppress annotation work for the plugins block
