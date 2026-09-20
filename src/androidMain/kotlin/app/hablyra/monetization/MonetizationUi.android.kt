package app.hablyra.monetization

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import app.hablyra.environment.LocalAppEnvironment
import app.hablyra.mobile.R
import app.hablyra.monetizationManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
actual fun DashboardAdBanner(modifier: Modifier) {
    val manager = runCatching { monetizationManager }.getOrNull() ?: return
    val adsReady by manager.adsReady.collectAsState()
    val canRequestAds by manager.canRequestAds.collectAsState()
    if (!adsReady || !canRequestAds) return

    val context = LocalContext.current
    val lifecycleOwner = context.findActivity() as? LifecycleOwner
    val label = LocalAppEnvironment.current.resources.strings.advertisementLabel()
    val adUnitId = remember(context) { context.getString(R.string.admob_banner_ad_unit_id) }
    if (adUnitId.isBlank()) return

    BoxWithConstraints(modifier = modifier) {
        val widthDp = maxWidth.value.toInt()
        if (widthDp <= 0) return@BoxWithConstraints

        val adSize = remember(widthDp) {
            AdSize.getLargeAnchoredAdaptiveBannerAdSize(context, widthDp)
        }
        var isLoaded by remember(widthDp) { mutableStateOf(false) }
        val adView = remember(widthDp, adUnitId) {
            AdView(context).apply {
                this.adUnitId = adUnitId
                setAdSize(adSize)
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        isLoaded = true
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        isLoaded = false
                        Log.w(TAG, "Banner load failed: ${error.code} ${error.domain}")
                    }
                }
            }
        }

        LaunchedEffect(adView) {
            adView.loadAd(AdRequest.Builder().build())
        }
        DisposableEffect(adView, lifecycleOwner) {
            val observer = object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) = adView.resume()
                override fun onPause(owner: LifecycleOwner) = adView.pause()
            }
            lifecycleOwner?.lifecycle?.addObserver(observer)
            onDispose {
                lifecycleOwner?.lifecycle?.removeObserver(observer)
                adView.destroy()
            }
        }

        AndroidView(
            modifier = if (isLoaded) {
                Modifier
                    .fillMaxWidth()
                    .height(adSize.height.dp)
                    .semantics { contentDescription = label }
            } else {
                Modifier.size(0.dp)
            },
            factory = { adView }
        )
    }
}

@Composable
actual fun rememberPrivacyOptionsState(): PrivacyOptionsState {
    val manager = runCatching { monetizationManager }.getOrNull()
        ?: return remember { PrivacyOptionsState(isRequired = false, show = {}) }
    val isRequired by manager.privacyOptionsRequired.collectAsState()
    val activity = LocalContext.current.findActivity()
    return remember(isRequired, activity) {
        PrivacyOptionsState(
            isRequired = isRequired && activity != null,
            show = {
                if (activity != null) {
                    manager.showPrivacyOptions(activity)
                }
            }
        )
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

private const val TAG = "HablyraBanner"
