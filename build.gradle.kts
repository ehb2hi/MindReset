plugins {
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.serialization)
    alias(libs.plugins.cashapp.sqldelight)
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms)
    alias(libs.plugins.google.firebaseCrashlytics)
}

kotlin {
    jvmToolchain(21)

    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "KMPLib"
            freeCompilerArgs = listOf("-Xbinary=bundleId=$baseName")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.jetbrains.kotlinDatetime)
            implementation(libs.jetbrains.kotlinCoroutinesCore)
            implementation(libs.jetbrains.navigationCompose)
            implementation(libs.jetbrains.serializationJson)
            implementation(libs.cashapp.sqldelightCoroutinesExtensions)
            implementation(libs.cashapp.sqldelightPrimitiveAdapters)
            implementation(libs.epicarchitect.epicCalendarCompose)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlinTest)
        }
        androidMain.dependencies {
            implementation(libs.android.coreKtx)
            implementation(libs.android.activityCompose)
            implementation(libs.cashapp.sqldelightAndroidDriver)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
            implementation(libs.google.mobileAds)
            implementation(libs.google.ump)
        }
        iosMain.dependencies {
            implementation(libs.cashapp.sqldelightNativeDriver)
        }
    }
}

val releaseAdMobAppId = providers
    .gradleProperty("HABLYRA_ADMOB_APP_ID")
    .orElse(providers.environmentVariable("HABLYRA_ADMOB_APP_ID"))
val releaseAdMobBannerId = providers
    .gradleProperty("HABLYRA_ADMOB_BANNER_ID")
    .orElse(providers.environmentVariable("HABLYRA_ADMOB_BANNER_ID"))
val releaseVersionName = providers
    .gradleProperty("HABLYRA_VERSION_NAME")
    .orElse(providers.environmentVariable("HABLYRA_VERSION_NAME"))
    .orElse("4.3.0")
val releaseVersionCode = providers
    .gradleProperty("HABLYRA_VERSION_CODE")
    .orElse(providers.environmentVariable("HABLYRA_VERSION_CODE"))
    .map(String::toInt)
    .orElse(85)
val releaseKeystorePath = providers.environmentVariable("HABLYRA_UPLOAD_KEYSTORE_PATH")
val releaseKeyAlias = providers.environmentVariable("HABLYRA_UPLOAD_KEY_ALIAS")
val releaseKeyPassword = providers.environmentVariable("HABLYRA_UPLOAD_KEY_PASSWORD")
val releaseStorePassword = providers.environmentVariable("HABLYRA_UPLOAD_STORE_PASSWORD")
val releaseSigningConfigured = listOf(
    releaseKeystorePath,
    releaseKeyAlias,
    releaseKeyPassword,
    releaseStorePassword
).all { !it.orNull.isNullOrBlank() }

val validateReleaseAdMobConfiguration = tasks.register("validateReleaseAdMobConfiguration") {
    group = "verification"
    description = "Validates production AdMob identifiers before a release build."

    doLast {
        val appId = releaseAdMobAppId.orNull.orEmpty()
        val bannerId = releaseAdMobBannerId.orNull.orEmpty()
        val missingValues = buildList {
            if (appId.isBlank()) add("HABLYRA_ADMOB_APP_ID")
            if (bannerId.isBlank()) add("HABLYRA_ADMOB_BANNER_ID")
        }
        if (missingValues.isNotEmpty()) {
            throw GradleException(
                "Missing release AdMob configuration: " + missingValues.joinToString() +
                    ". Supply each value as a Gradle property (-P) or environment variable."
            )
        }
        if (!appId.matches(Regex("ca-app-pub-[0-9]+~[0-9]+"))) {
            throw GradleException("HABLYRA_ADMOB_APP_ID must be an AdMob app ID (ca-app-pub-...~...).")
        }
        if (!bannerId.matches(Regex("ca-app-pub-[0-9]+/[0-9]+"))) {
            throw GradleException(
                "HABLYRA_ADMOB_BANNER_ID must be a standard AdMob banner unit ID " +
                    "(ca-app-pub-.../...), not an app ID."
            )
        }
    }
}

val validateReleaseSigningConfiguration = tasks.register("validateReleaseSigningConfiguration") {
    group = "verification"
    description = "Validates upload-key configuration before a release build."

    doLast {
        val signingValues = mapOf(
            "HABLYRA_UPLOAD_KEYSTORE_PATH" to releaseKeystorePath.orNull,
            "HABLYRA_UPLOAD_KEY_ALIAS" to releaseKeyAlias.orNull,
            "HABLYRA_UPLOAD_KEY_PASSWORD" to releaseKeyPassword.orNull,
            "HABLYRA_UPLOAD_STORE_PASSWORD" to releaseStorePassword.orNull
        )
        val missingValues = signingValues.filterValues { it.isNullOrBlank() }.keys
        if (missingValues.isNotEmpty()) {
            throw GradleException(
                "Missing release signing configuration: " + missingValues.joinToString() +
                    ". Release artifacts must be signed with the Hablyra upload key."
            )
        }

        val keystoreFile = file(requireNotNull(releaseKeystorePath.orNull))
        if (!keystoreFile.isFile) {
            throw GradleException(
                "HABLYRA_UPLOAD_KEYSTORE_PATH does not point to a readable keystore file."
            )
        }
    }
}

tasks.matching { it.name == "preReleaseBuild" }.configureEach {
    dependsOn(validateReleaseAdMobConfiguration, validateReleaseSigningConfiguration)
}

android {
    namespace = "app.hablyra.mobile"
    bundle.storeArchive.enable = true
    compileSdk = 35

    defaultConfig {
        applicationId = "app.hablyra.mobile"
        resourceConfigurations += setOf("en", "ru")
        minSdk = 26
        targetSdk = 35
        versionCode = releaseVersionCode.get()
        versionName = releaseVersionName.get()
        base.archivesName.set(releaseVersionName.map { "hablyra-$it" })
    }

    signingConfigs {
        create("release") {
            if (releaseSigningConfigured) {
                storeFile = file(requireNotNull(releaseKeystorePath.orNull))
                storePassword = releaseStorePassword.get()
                keyAlias = releaseKeyAlias.get()
                keyPassword = releaseKeyPassword.get()
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            manifestPlaceholders["ADMOB_APP_ID"] = releaseAdMobAppId.orElse("").get()
            resValue(
                "string",
                "admob_banner_ad_unit_id",
                releaseAdMobBannerId.orElse("").get()
            )
            resValue("string", "ump_debug_geography", "disabled")
            resValue("string", "ump_test_device_id", "")
            resValue("bool", "ump_reset_on_launch", "false")
            if (releaseSigningConfigured) {
                signingConfig = signingConfigs.getByName("release")
            }
        }

        debug {
            applicationIdSuffix = ".debug"
            manifestPlaceholders["ADMOB_APP_ID"] = "ca-app-pub-3940256099942544~3347511713"
            resValue("string", "admob_banner_ad_unit_id", "ca-app-pub-3940256099942544/6300978111")
            resValue(
                "string",
                "ump_debug_geography",
                providers.gradleProperty("HABLYRA_UMP_DEBUG_GEOGRAPHY").orElse("disabled").get()
            )
            resValue(
                "string",
                "ump_test_device_id",
                providers.gradleProperty("HABLYRA_UMP_TEST_DEVICE_ID").orElse("").get()
            )
            resValue(
                "bool",
                "ump_reset_on_launch",
                providers.gradleProperty("HABLYRA_UMP_RESET").orElse("false").get()
            )
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("app.hablyra.database")
        }
    }
}