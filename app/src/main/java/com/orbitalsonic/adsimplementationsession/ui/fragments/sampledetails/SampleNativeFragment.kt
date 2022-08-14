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
import com.orbitalsonic.adsimplementationsession.adsconfig.callback.FbNativeCallBack
import com.orbitalsonic.adsimplementationsession.databinding.FragmentSampleNativeBinding
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.AD_TAG
import com.orbitalsonic.adsimplementationsession.helpers.utils.GeneralUtils.isInternetConnected
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.smallNativeActive
import com.orbitalsonic.adsimplementationsession.helpers.utils.RemoteConfigConstants.prioritySmallNative
import com.orbitalsonic.adsimplementationsession.ui.fragments.BaseFragment

class SampleNativeFragment : BaseFragment<FragmentSampleNativeBinding>() {

    private lateinit var fbBannerAds: FbBannerAds
    private lateinit var admobBannerAds: AdmobBannerAds

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState, R.layout.fragment_sample_native)
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

            when(prioritySmallNative){
                1 ->{
                    Log.d(AD_TAG, "Call FB Native")
                    binding.admobNativePlaceHolder.visibility = View.GONE
                    binding.fbNativePlaceHolder.visibility = View.VISIBLE
                    fbBannerAds.loadNativeSmallAd(binding.adsContainerLayout,
                        binding.fbNativePlaceHolder,
                        binding.loadingLayout,
                        getString(R.string.fb_medium_native_ids),
                        smallNativeActive,
                        false,
                        isInternetConnected(mActivity),
                        object : FbNativeCallBack {
                            override fun onError(adError: String) { }

                            override fun onAdLoaded() {}

                            override fun onAdClicked() {}

                            override fun onLoggingImpression() {}
                            override fun onMediaDownloaded() {}

                        })
                }
                2 ->{
                    Log.d(AD_TAG, "Call Admob Native")
                    binding.admobNativePlaceHolder.visibility = View.VISIBLE
                    binding.fbNativePlaceHolder.visibility = View.GONE
                    admobBannerAds.loadNativeAds(binding.adsContainerLayout,
                        binding.admobNativePlaceHolder,
                        binding.loadingLayout,
                        getString(R.string.admob_medium_native_ids),
                        smallNativeActive,
                        false,
                        1,
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