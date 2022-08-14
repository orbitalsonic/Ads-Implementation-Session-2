package com.orbitalsonic.adsimplementationsession

import android.app.Application
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds

class MainApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        AudienceNetworkAds.initialize(this)
        AdSettings.addTestDevice("8ccdb88a-7d5d-4623-9751-a5a14b493b3d")
    }

}