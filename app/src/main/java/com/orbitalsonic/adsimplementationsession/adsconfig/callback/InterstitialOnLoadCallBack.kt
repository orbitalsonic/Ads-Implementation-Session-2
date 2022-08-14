package com.orbitalsonic.adsimplementationsession.adsconfig.callback

interface InterstitialOnLoadCallBack {
    fun onAdFailedToLoad(adError:String)
    fun onAdLoaded()
}