package com.orbitalsonic.adsimplementationsession.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orbitalsonic.adsimplementationsession.R
import com.orbitalsonic.adsimplementationsession.databinding.FragmentHomeBinding
import com.orbitalsonic.adsimplementationsession.helpers.listeners.OnClickListeners.setSafeOnClickListener
import com.orbitalsonic.adsimplementationsession.helpers.utils.ExtensionFunctions.findNavControllerSafely
import com.orbitalsonic.adsimplementationsession.ui.fragments.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState, R.layout.fragment_home)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            onClickMethods()
        }
    }

    private fun onClickMethods() {
        binding.btnSimpleBanner.setSafeOnClickListener {
            if (findNavControllerSafely()?.currentDestination?.id == R.id.homeFragment){
                val action = HomeFragmentDirections.actionHomeFragmentToSampleBannerFragment()
                findNavControllerSafely()?.navigate(action)
            }
        }
        binding.btnNativeBanner.setSafeOnClickListener {
            if (findNavControllerSafely()?.currentDestination?.id == R.id.homeFragment){
                val action = HomeFragmentDirections.actionHomeFragmentToSampleNativeFragment()
                findNavControllerSafely()?.navigate(action)
            }
        }
        binding.btnSimpleInter.setSafeOnClickListener {
            if (findNavControllerSafely()?.currentDestination?.id == R.id.homeFragment){
                val action = HomeFragmentDirections.actionHomeFragmentToSampleInterFragment()
                findNavControllerSafely()?.navigate(action)

            }
        }
        binding.btnPreloadInter.setSafeOnClickListener {
            if (findNavControllerSafely()?.currentDestination?.id == R.id.homeFragment){
                val action = HomeFragmentDirections.actionHomeFragmentToSamplePreloadInterFragment()
                findNavControllerSafely()?.navigate(action)

            }
        }

    }

}