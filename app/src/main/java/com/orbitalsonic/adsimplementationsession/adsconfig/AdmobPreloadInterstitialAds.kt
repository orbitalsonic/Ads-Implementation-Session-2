package com.orbitalsonic.adsimplementationsession.adsconfig

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.InterstitialOnLoadCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.InterstitialOnShowCallBack
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.AD_TAG

object AdmobPreloadInterstitialAds {
    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest: AdRequest = AdRequest.Builder().build()

    private var isLoadingAd = false

    fun loadInterstitialAd(
        mActivity: Activity,
        admobInterstitialIds: String,
        isRemoteConfigActive: Boolean,
        isAppPurchased: Boolean,
        isInternetConnected:Boolean,
        interstitialOnLoadCallBack: InterstitialOnLoadCallBack
    ) {

        if (isInternetConnected && !isAppPurchased && isRemoteConfigActive) {
            if (mInterstitialAd == null && !isLoadingAd) {
                isLoadingAd = true
                InterstitialAd.load(
                    mActivity,
                    admobInterstitialIds,
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.e(AD_TAG, "admob Interstitial onAdFailedToLoad: ${adError.message}")
                            isLoadingAd = false
                            mInterstitialAd = null
                            interstitialOnLoadCallBack.onAdFailedToLoad(adError.toString())
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            Log.i(AD_TAG, "admob Interstitial onAdLoaded")
                            isLoadingAd = false
                            mInterstitialAd = interstitialAd
                            interstitialOnLoadCallBack.onAdLoaded()

                        }
                    })
            }
        }
    }

    fun showInterstitialAd( mActivity: Activity, interstitialOnShowCallBack: InterstitialOnShowCallBack) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.i(AD_TAG, "admob Interstitial onAdDismissedFullScreenContent")
                    interstitialOnShowCallBack.onAdDismissedFullScreenContent()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(AD_TAG, "admob Interstitial onAdFailedToShowFullScreenContent")
                    interstitialOnShowCallBack.onAdFailedToShowFullScreenContent()
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.i(AD_TAG, "admob Interstitial onAdShowedFullScreenContent")
                    interstitialOnShowCallBack.onAdShowedFullScreenContent()
                    mInterstitialAd = null
                }

                override fun onAdImpression() {
                    Log.i(AD_TAG, "admob Interstitial onAdImpression")
                    interstitialOnShowCallBack.onAdImpression()
                }
            }
            mInterstitialAd?.show(mActivity)
        }
    }

    fun showAndLoadInterstitialAd( mActivity: Activity,admobInterstitialIds: String, interstitialOnShowCallBack: InterstitialOnShowCallBack) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.i(AD_TAG, "admob Interstitial onAdDismissedFullScreenContent")
                    interstitialOnShowCallBack.onAdDismissedFullScreenContent()
                    loadAgainInterstitialAd(mActivity,admobInterstitialIds)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(AD_TAG, "admob Interstitial onAdFailedToShowFullScreenContent: ${adError.message}")
                    interstitialOnShowCallBack.onAdFailedToShowFullScreenContent()
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.i(AD_TAG, "admob Interstitial onAdShowedFullScreenContent")
                    interstitialOnShowCallBack.onAdShowedFullScreenContent()
                    mInterstitialAd = null
                }

                override fun onAdImpression() {
                    Log.i(AD_TAG, "admob Interstitial onAdImpression")
                    interstitialOnShowCallBack.onAdImpression()
                }
            }
            mInterstitialAd?.show(mActivity)
        }
    }

    private fun loadAgainInterstitialAd(
        mActivity: Activity,
        admobInterstitialIds: String
    ) {
        if (mInterstitialAd == null && !isLoadingAd) {
            isLoadingAd = true
            InterstitialAd.load(
                mActivity,
                admobInterstitialIds,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.e(AD_TAG, "admob Interstitial onAdFailedToLoad: $adError")
                        isLoadingAd = false
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.i(AD_TAG, "admob Interstitial onAdLoaded")
                        isLoadingAd = false
                        mInterstitialAd = interstitialAd

                    }
                })
        }
    }

    fun isInterstitialLoaded(): Boolean {
        return mInterstitialAd != null
    }

    fun dismissInterstitialLoaded() {
        mInterstitialAd = null
    }

}