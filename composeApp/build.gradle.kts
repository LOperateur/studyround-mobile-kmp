import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    // alias(libs.plugins.google.services)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            isStatic = true
            binaryOption("bundleId", "com.operator.studyround")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            api(libs.image.loader)
            implementation(libs.kamel.image)
            implementation(libs.kermit)
            implementation(libs.material3.window.size.multiplatform)
            implementation(libs.multiplatform.settings)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.material.navigation)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.paging.common)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.alert.banner)
        }

        androidMain.dependencies {
            api(libs.androidx.appcompat)
            api(libs.androidx.core.ktx)
            implementation(libs.koin.android)

            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.googleid)
//            implementation(project.dependencies.platform(libs.firebase.bom))

            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.security.crypto)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        all {
            languageSettings.optIn("org.koin.core.annotation.KoinExperimentalAPI")
        }
    }

    @Suppress("OPT_IN_USAGE")
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    val proguardAndroid = "proguard-android-optimize.txt"
    val proguardConsumerRules = "proguard-rules.pro"
    val releaseUrl = "\"https://backend.studyround.com\""
    val stagingUrl = "\"https://backend.studyround.com\""

    val googleKeyProps = loadProperties("$rootDir/secrets/google-key.properties")
    val googleClientServerId = googleKeyProps.getProperty("googleServerClientId")

    namespace = "com.studyround.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/composeResources")

    defaultConfig {
        applicationId = "com.operator.u_learn"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true

            manifestPlaceholders["app_name"] = "@string/app_name_dev"
            buildConfigField("String", "BASE_API_URL", stagingUrl)
            buildConfigField("String", "GOOGLE_SERVER_CLIENT_ID", googleClientServerId)
        }

        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".staging"
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile(proguardAndroid),
                proguardConsumerRules
            )

            manifestPlaceholders["app_name"] = "@string/app_name_release"
            buildConfigField("String", "BASE_API_URL", releaseUrl)
            buildConfigField("String", "GOOGLE_SERVER_CLIENT_ID", googleClientServerId)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
