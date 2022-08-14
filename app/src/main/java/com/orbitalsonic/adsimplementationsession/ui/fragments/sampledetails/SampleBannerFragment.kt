package com.orbitalsonic.adsimplementationsession.ui.fragments.sampledetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orbitalsonic.adsimplementationsession.R
import com.orbitalsonic.adsimplementationsession.adsconfig.AdmobBannerAds
import com.orbitalsonic.adsimplementationsession.adsconfig.FbBannerAds
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.BannerCallBack
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.FbBannerCallBack
import com.orbitalsonic.adsimplementationsession.databinding.FragmentSampleBannerBinding
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.AD_TAG
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.isInternetConnected
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.smallBannerActive
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.prioritySmallBanner
import com.orbitalsonic.adsimplementationsession.ui.fragments.BaseFragment

class SampleBannerFragment : BaseFragment<FragmentSampleBannerBinding>() {

    private lateinit var fbBannerAds: FbBannerAds
    private lateinit var admobBannerAds: AdmobBannerAds

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState, R.layout.fragment_sample_banner)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            adsConfig()
        }
    }

    private fun adsConfig() {
        activity?.let { mActivity ->
            fbBannerAds = FbBannerAds(mActivity)
            admobBannerAds = AdmobBannerAds(mActivity)

            when(prioritySmallBanner){
                1 ->{
                    Log.d(AD_TAG, "Call FB Banner")
                    fbBannerAds.loadBannerAds(binding.adsContainerLayout,
                        binding.adsPlaceHolder,
                        binding.loadingLayout,
                        getString(R.string.fb_small_banner_ids),
                        smallBannerActive,
                        false,
                        isInternetConnected(mActivity),
                        object : FbBannerCallBack {
                            override fun onError(adError: String) { }

                            override fun onAdLoaded() {}

                            override fun onAdClicked() {}

                            override fun onLoggingImpression() {}

                        })
                }
                2 ->{
                    Log.d(AD_TAG, "Call Admob Banner")
                    admobBannerAds.loadBannerAds(binding.adsContainerLayout,
                        binding.adsPlaceHolder,
                        binding.loadingLayout,
                        getString(R.string.admob_small_banner_ids),
                        smallBannerActive,
                        false,
                        isInternetConnected(mActivity),
                        object : BannerCallBack {
                            override fun onAdFailedToLoad(adError: String) {
                            }

                            override fun onAdLoaded() {
                            }

                            override fun onAdImpression() {
                            }

                        })
                }
                else ->{
                    binding.adsContainerLayout.visibility = View.GONE
                }
            }
        }

    }


}