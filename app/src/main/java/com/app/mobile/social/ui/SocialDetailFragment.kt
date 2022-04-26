package com.app.mobile.social.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.app.mobile.social.BaseBindingFragment
import com.app.mobile.social.R
import com.app.mobile.social.data.SocialModel
import com.app.mobile.social.databinding.FragmentSocialDetailBinding

private const val SOCIAL_PARAM = "SOCIAL_PARAM"

class SocialDetailFragment : BaseBindingFragment<FragmentSocialDetailBinding>() {
    override val layoutRes: Int
        get() = R.layout.fragment_social_detail
    private var param: SocialModel.Social? = null

    private val socialViewModel: SocialViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
        arguments?.let {
            param = it.getParcelable(SOCIAL_PARAM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.socialVm = socialViewModel
    }

    companion object {
        @JvmStatic
        fun newInstance(param: SocialModel.Social) =
            SocialDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SOCIAL_PARAM, param)
                }
            }
    }
}