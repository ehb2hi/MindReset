package app.hablyra.monetization

import android.app.Activity
import android.app.Application
import android.util.Log
import app.hablyra.mobile.R
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AndroidMonetizationManager(
    private val application: Application
) {
    private val consentInformation = UserMessagingPlatform.getConsentInformation(application)
    private val consentRequested = AtomicBoolean(false)
    private val adsInitializationStarted = AtomicBoolean(false)
    private val backgroundScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _adsReady = MutableStateFlow(false)
    val adsReady = _adsReady.asStateFlow()

    private val _canRequestAds = MutableStateFlow(false)
    val canRequestAds = _canRequestAds.asStateFlow()

    private val _privacyOptionsRequired = MutableStateFlow(false)
    val privacyOptionsRequired = _privacyOptionsRequired.asStateFlow()

    fun gatherConsent(activity: Activity) {
        if (!consentRequested.compareAndSet(false, true)) return

        if (application.resources.getBoolean(R.bool.ump_reset_on_launch)) {
            consentInformation.reset()
        }

        consentInformation.requestConsentInfoUpdate(
            activity,
            buildRequestParameters(activity),
            {
                updatePrivacyOptionsRequirement()
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    if (formError != null) {
                        Log.w(TAG, "UMP form unavailable: ${formError.errorCode} ${formError.message}")
                    }
                    updatePrivacyOptionsRequirement()
                    initializeAdsIfAllowed()
                }
            },
            { requestError ->
                Log.w(TAG, "UMP update failed: ${requestError.errorCode} ${requestError.message}")
                updatePrivacyOptionsRequirement()
                initializeAdsIfAllowed()
            }
        )
    }

    fun showPrivacyOptions(activity: Activity) {
        if (!_privacyOptionsRequired.value) return

        UserMessagingPlatform.showPrivacyOptionsForm(activity) { formError ->
            if (formError != null) {
                Log.w(TAG, "UMP privacy options unavailable: ${formError.errorCode} ${formError.message}")
            }
            updatePrivacyOptionsRequirement()
            initializeAdsIfAllowed()
        }
    }

    private fun buildRequestParameters(activity: Activity): ConsentRequestParameters {
        val geography = application.getString(R.string.ump_debug_geography)
        val testDeviceId = application.getString(R.string.ump_test_device_id)
        if (geography == DEBUG_DISABLED && testDeviceId.isBlank()) {
            return ConsentRequestParameters.Builder().build()
        }

        val debugBuilder = ConsentDebugSettings.Builder(activity)
        if (testDeviceId.isNotBlank()) {
            debugBuilder.addTestDeviceHashedId(testDeviceId)
        }
        when (geography.lowercase()) {
            DEBUG_EEA -> debugBuilder.setDebugGeography(
                ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA
            )
            DEBUG_NOT_EEA -> debugBuilder.setDebugGeography(
                ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_OTHER
            )
        }

        return ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugBuilder.build())
            .build()
    }

    private fun updatePrivacyOptionsRequirement() {
        _privacyOptionsRequired.value =
            consentInformation.privacyOptionsRequirementStatus ==
                ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
    }

    private fun initializeAdsIfAllowed() {
        val canRequestAds = consentInformation.canRequestAds()
        _canRequestAds.value = canRequestAds
        if (!canRequestAds) return
        if (!adsInitializationStarted.compareAndSet(false, true)) return

        backgroundScope.launch {
            try {
                MobileAds.initialize(application) {
                    _adsReady.value = true
                }
            } catch (exception: RuntimeException) {
                Log.w(TAG, "Google Mobile Ads initialization failed", exception)
            }
        }
    }

    private companion object {
        const val TAG = "HablyraMonetization"
        const val DEBUG_DISABLED = "disabled"
        const val DEBUG_EEA = "eea"
        const val DEBUG_NOT_EEA = "not_eea"
    }
}
