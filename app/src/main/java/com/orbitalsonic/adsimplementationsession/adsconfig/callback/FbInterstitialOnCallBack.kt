package com.orbitalsonic.adsimplementationsession.adsconfig.callback

interface FbInterstitialOnCallBack {
    fun onInterstitialDisplayed()
    fun onInterstitialDismissed()
    fun onError()
    fun onAdLoaded()
    fun onAdClicked()
    fun onLoggingImpression()
}