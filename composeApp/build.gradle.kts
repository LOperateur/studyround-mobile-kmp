import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.moko.multiplatform.resources)
//    alias(libs.plugins.google.services)
//    alias(libs.plugins.sqlDelight)
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
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.kamel.image)
            implementation(libs.kermit)
            implementation(libs.material3.window.size.multiplatform)
            implementation(libs.multiplatform.settings)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            api(libs.moko.resources.compose)
            implementation(libs.moko.resources.test)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)

//            implementation("org.jetbrains.skiko:skiko:0.7.90")

//            implementation(libs.sqlDelight.coroutine)
//            implementation(libs.sqlDelight.runtime)
//            implementation(libs.sqlDelight.primitive.adapters)
        }

        androidMain.dependencies {
            api(libs.androidx.appcompat)
            api(libs.androidx.core.ktx)
            api(libs.koin.android)

            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.googleid)
            implementation(project.dependencies.platform(libs.firebase.bom))

            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.security.crypto)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
//            implementation(libs.sqlDelight.driver.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
//            implementation(libs.sqlDelight.driver.native)
        }
    }

    @Suppress("OPT_IN_USAGE")
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.studyround.app"
    disableStaticFrameworkWarning = true
}

android {
    val proguardAndroid = "proguard-android-optimize.txt"
    val proguardConsumerRules = "proguard-rules.pro"
    val releaseUrl = "\"https://backend.studyround.com\""
    val stagingUrl = "\"https://staging-backend.studyround.com\""

    val googleKeyProps = loadProperties("$rootDir/secrets/google-key.properties")
    val googleClientServerId = googleKeyProps.getProperty("googleServerClientId")

    namespace = "com.studyround.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true

            resValue("string", "app_name", "@string/app_name_dev")
            buildConfigField("String", "BASE_API_URL", stagingUrl)
            buildConfigField("String", "GOOGLE_SERVER_CLIENT_ID", googleClientServerId)
        }

        create("staging") {
            initWith(getByName("debug"))
            matchingFallbacks.add("debug") // For moko resources
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

            resValue("string", "app_name", "@string/app_name_release")
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


    // Temporary fix for moko resources with Kotlin 1.9.0+
    // Expected object 'MR' has no actual declaration in module... for JVM
    // https://github.com/icerockdev/moko-resources/issues/510
    // https://github.com/icerockdev/moko-resources/issues/531
    sourceSets {
        getByName("main").java.srcDirs("build/generated/moko/androidMain/src")
    }
}
