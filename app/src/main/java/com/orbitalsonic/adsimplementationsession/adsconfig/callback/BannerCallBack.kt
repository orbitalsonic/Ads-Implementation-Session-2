package com.orbitalsonic.adsimplementationsession.adsconfig.callback

interface BannerCallBack {
    fun onAdFailedToLoad(adError:String)
    fun onAdLoaded()
    fun onAdImpression()
}