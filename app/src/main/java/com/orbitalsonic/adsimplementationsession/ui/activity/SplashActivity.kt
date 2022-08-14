package com.orbitalsonic.adsimplementationsession.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.orbitalsonic.adsimplementationsession.R
import com.orbitalsonic.adsimplementationsession.adsconfig.AdmobBannerAds
import com.orbitalsonic.adsimplementationsession.adsconfig.FbBannerAds
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.BannerCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.FbNativeCallBack
import com.orbitalsonic.adsimplementationsession.databinding.ActivitySplashBinding
import com.orbitalsonic.adsimplementationsession.helpers.listeners.OnClickListeners.setSafeOnClickListener
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.isInternetConnected
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.IS_INTERSTITIAL_ACTIVE
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.IS_MEDIUM_NATIVE_ACTIVE
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.IS_SMALL_BANNER_ACTIVE
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.IS_SMALL_NATIVE_ACTIVE
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.PRIORITY_INTERSTITIAL
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.PRIORITY_MEDIUM_NATIVE
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.PRIORITY_SMALL_BANNER
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.PRIORITY_SMALL_NATIVE
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.interstitialActive
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.mediumNativeActive
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.priorityInterstitial
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.priorityMediumNative
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.prioritySmallBanner
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.prioritySmallNative
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.smallBannerActive
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.smallNativeActive


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var fbBannerAds: FbBannerAds
    private lateinit var admobBannerAds: AdmobBannerAds

    private val mHandler = Handler(Looper.getMainLooper())
    private val adsRunner = Runnable { checkAdvertisement() }

    private var isNativeLoadedOrFailed = false
    private var mCounter: Int = 0

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.btnNext.setSafeOnClickListener {
            intentMethod()
            binding.btnNext.isEnabled = false
        }


        if (isInternetConnected(this)) {
            initRemoteConfig()
        } else {
            binding.adsContainerLayout.visibility = View.GONE
            isNativeLoadedOrFailed = true
        }

        initAds()

    }

    private fun initAds(){
        fbBannerAds = FbBannerAds(this)
        admobBannerAds = AdmobBannerAds(this)
    }

    private fun loadAds() {
        when(priorityMediumNative){
            1 -> {
                fbBannerAds.loadNativeMedium(binding.adsContainerLayout,
                    binding.fbPlaceHolder,
                    binding.loadingLayout,
                    getString(R.string.fb_medium_native_ids),
                    mediumNativeActive,
                    false,
                    isInternetConnected(this),
                    object : FbNativeCallBack {
                        override fun onError(adError: String) {
                            isNativeLoadedOrFailed = true
                        }

                        override fun onAdLoaded() {
                            isNativeLoadedOrFailed = true
                        }

                        override fun onAdClicked() {}

                        override fun onLoggingImpression() {
                        }

                        override fun onMediaDownloaded() {}

                    })
            }

            2 -> {
                admobBannerAds.loadNativeAds(binding.adsContainerLayout,
                    binding.admobPlaceHolder,
                    binding.loadingLayout,
                    getString(R.string.admob_medium_native_ids),
                    mediumNativeActive,
                    false,
                    2,
                    isInternetConnected(this),
                    object : BannerCallBack {

                        override fun onAdFailedToLoad(adError: String) {
                            isNativeLoadedOrFailed = true
                        }

                        override fun onAdLoaded() {
                            isNativeLoadedOrFailed = true
                        }

                        override fun onAdImpression() {
                        }

                    })
            }else -> {
                isNativeLoadedOrFailed = true
                binding.adsContainerLayout.visibility = View.GONE
            }
        }


    }

    private fun intentMethod() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    private fun initRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 2
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        fetchRemoteValues()
    }

    private fun fetchRemoteValues() {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            updateRemoteValues()
        }

    }

    private fun checkAdvertisement() {

        if (mCounter < 12) {
            try {
                mCounter++
                if (isNativeLoadedOrFailed) {
                    binding.btnNext.visibility = View.VISIBLE
                    binding.loadingProgress.visibility = View.GONE
                }

            } catch (e: Exception) {

            }

            mHandler.removeCallbacks { adsRunner }
            mHandler.postDelayed(
                adsRunner,
                (1000)
            )
        } else {
            binding.btnNext.visibility = View.VISIBLE
            binding.loadingProgress.visibility = View.GONE
        }

    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(adsRunner)
    }

    override fun onResume() {
        super.onResume()
        mHandler.post(adsRunner)
    }

    private fun updateRemoteValues() {

        interstitialActive = remoteConfig[IS_INTERSTITIAL_ACTIVE].asBoolean()
        mediumNativeActive = remoteConfig[IS_MEDIUM_NATIVE_ACTIVE].asBoolean()
        smallNativeActive = remoteConfig[IS_SMALL_NATIVE_ACTIVE].asBoolean()
        smallBannerActive = remoteConfig[IS_SMALL_BANNER_ACTIVE].asBoolean()

        priorityInterstitial = remoteConfig[PRIORITY_INTERSTITIAL].asLong().toInt()
        priorityMediumNative = remoteConfig[PRIORITY_MEDIUM_NATIVE].asLong().toInt()
        prioritySmallNative = remoteConfig[PRIORITY_SMALL_NATIVE].asLong().toInt()
        prioritySmallBanner = remoteConfig[PRIORITY_SMALL_BANNER].asLong().toInt()

        loadAds()
        mCounter = 0

        if (!mediumNativeActive) {
            isNativeLoadedOrFailed = true
            binding.adsContainerLayout.visibility = View.GONE
        }

    }

}