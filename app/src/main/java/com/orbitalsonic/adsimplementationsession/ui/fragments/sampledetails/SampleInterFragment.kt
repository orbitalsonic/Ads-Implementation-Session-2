package com.orbitalsonic.adsimplementationsession.ui.fragments.sampledetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orbitalsonic.adsimplementationsession.R
import com.orbitalsonic.adsimplementationsession.adsconfig.AdmobInterstitialAds
import com.orbitalsonic.adsimplementationsession.adsconfig.FbInterstitialAds
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.FbInterstitialOnCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.InterstitialOnLoadCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.InterstitialOnShowCallBack
import com.orbitalsonic.adsimplementationsession.databinding.FragmentSampleInterBinding
import com.orbitalsonic.adsimplementationsession.helpers.listeners.OnClickListeners.setSafeOnClickListener
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants
import com.orbitalsonic.adsimplementationsession.ui.fragments.BaseFragment

class SampleInterFragment : BaseFragment<FragmentSampleInterBinding>() {

    private lateinit var admobInterstitialAds: AdmobInterstitialAds
    private lateinit var fbInterstitialAds: FbInterstitialAds

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState, R.layout.fragment_sample_inter)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true

            binding.btnShow.setSafeOnClickListener {
                showInterstitialAd()
            }

            adsConfig()
        }
    }

    private fun adsConfig(){
        activity?.let { mActivity ->
            admobInterstitialAds = AdmobInterstitialAds(mActivity)
            fbInterstitialAds = FbInterstitialAds(mActivity)
            loadInterstitialAd()
        }

    }

    private fun loadInterstitialAd(){
        activity?.let { mActivity ->
            when (RemoteConfigConstants.priorityInterstitial) {
                1 -> {
                    Log.d(GeneralUtils.AD_TAG, "Call FB Interstitial")
                    fbInterstitialAds.loadInterstitialAd(
                        mActivity.resources.getString(R.string.fb_interstitial_ids),
                        RemoteConfigConstants.interstitialActive,
                        false,
                        GeneralUtils.isInternetConnected(mActivity),
                        object : FbInterstitialOnCallBack {
                            override fun onInterstitialDisplayed() {}

                            override fun onInterstitialDismissed() {}

                            override fun onError() {}

                            override fun onAdLoaded() {}

                            override fun onAdClicked() {}

                            override fun onLoggingImpression() {}

                        })
                }
                2 -> {
                    Log.d(GeneralUtils.AD_TAG, "Call Admob Interstitial")
                    admobInterstitialAds.loadInterstitialAd(
                        mActivity.resources.getString(R.string.admob_interstitial_ids),
                        RemoteConfigConstants.interstitialActive,
                        false,
                        GeneralUtils.isInternetConnected(mActivity),
                        object : InterstitialOnLoadCallBack {
                            override fun onAdFailedToLoad(adError: String) {
                            }

                            override fun onAdLoaded() {
                            }

                        })
                }
            }
        }
    }

    private fun showInterstitialAd(){
        activity?.let { mActivity ->
            when (RemoteConfigConstants.priorityInterstitial) {
                1 -> {
                    fbInterstitialAds.showInterstitialAds()
                }
                2 -> {
                    admobInterstitialAds.showInterstitialAd(object :
                        InterstitialOnShowCallBack {
                        override fun onAdDismissedFullScreenContent() {
                        }

                        override fun onAdFailedToShowFullScreenContent() {
                        }

                        override fun onAdShowedFullScreenContent() {
                        }

                        override fun onAdImpression() {
                        }

                    })
                }
            }
        }
    }


}