import com.android.build.gradle.internal.tasks.FinalizeBundleTask
import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.techgv.composeexprimentals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.techgv.composeexprimentals"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {

        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        outputs.all {
            this as com.android.build.gradle.internal.api.ApkVariantOutputImpl

            val buildName = buildType.name
            val versionCode = defaultConfig.versionCode
            val date = SimpleDateFormat("dd-MM-yyyy").format(Date())
            val baseFileName =
                "AppName" + "_" + buildName + "_" + date + "_" + "V" + versionName + "." + versionCode

            outputFileName = "$baseFileName.apk"

            // Get final bundle task name for this variant
            val bundleFinalizeTaskName = StringBuilder("sign").run {
                // Add each flavor dimension for this variant here
                productFlavors.forEach {
                    append(it.name.capitalizeAsciiOnly())
                }
                // Add build type of this variant
                append(buildType.name.capitalizeAsciiOnly())
                append("Bundle")
                toString()
            }

            tasks.named(bundleFinalizeTaskName, FinalizeBundleTask::class.java) {
                val file = finalBundleFile.asFile.get()
                val name = "$baseFileName.aab"
                val finalFile = File(file.parentFile, name)
                finalBundleFile.set(finalFile)
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}