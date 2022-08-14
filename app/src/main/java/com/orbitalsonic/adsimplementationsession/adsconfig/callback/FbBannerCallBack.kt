package com.orbitalsonic.adsimplementationsession.adsconfig.callback

interface FbBannerCallBack {
    fun onError(adError:String)
    fun onAdLoaded()
    fun onAdClicked()
    fun onLoggingImpression()
}