package com.orbitalsonic.adsimplementationsession.ui.fragments.sampledetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orbitalsonic.adsimplementationsession.R
import com.orbitalsonic.adsimplementationsession.adsconfig.AdmobPreloadInterstitialAds
import com.orbitalsonic.adsimplementationsession.adsconfig.FbPreloadInterstitialAds
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.FbInterstitialOnCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.InterstitialOnLoadCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.InterstitialOnShowCallBack
import com.orbitalsonic.adsimplementationsession.databinding.FragmentSamplePreloadInterBinding
import com.orbitalsonic.adsimplementationsession.helpers.listeners.OnClickListeners.setSafeOnClickListener
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.AD_TAG
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.isInternetConnected
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.priorityInterstitial
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.interstitialActive
import com.orbitalsonic.adsimplementationsession.ui.fragments.BaseFragment

class SamplePreloadInterFragment : BaseFragment<FragmentSamplePreloadInterBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState, R.layout.fragment_sample_preload_inter)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            binding.btnShow.setSafeOnClickListener {
                showInterstitialAd()
            }

            loadInterstitialAd()
        }
    }

    private fun loadInterstitialAd(){
        activity?.let { mActivity ->
            when (priorityInterstitial) {
                1 -> {
                    Log.d(AD_TAG, "Call FB Interstitial")
                    FbPreloadInterstitialAds.loadInterstitialAd(mActivity,
                        mActivity.resources.getString(R.string.fb_interstitial_ids),
                        interstitialActive,
                        false,
                        isInternetConnected(mActivity),
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
                    Log.d(AD_TAG, "Call Admob Interstitial")
                    AdmobPreloadInterstitialAds.loadInterstitialAd(mActivity,
                        mActivity.resources.getString(R.string.admob_interstitial_ids),
                        interstitialActive,
                        false,
                        isInternetConnected(mActivity),
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
            when (priorityInterstitial) {
                1 -> {
                    FbPreloadInterstitialAds.showInterstitialAds()
                }
                2 -> {
                    AdmobPreloadInterstitialAds.showInterstitialAd(mActivity,object :
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