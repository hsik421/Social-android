package com.app.mobile.social.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val replyAdapter by lazy { ReplyListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(SOCIAL_PARAM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        param?.let {
            viewDataBinding.item = it
            viewDataBinding.socialVm = socialViewModel
        }

        setupView()
        subscribeUI()
    }

    private fun setupView(){
        viewDataBinding.recycler.apply {
            adapter = replyAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun subscribeUI(){
        param?.let { social ->
            socialViewModel.items.observe(viewLifecycleOwner){ originList->
                val temp = originList.find { find-> find.idx == social.idx } as? SocialModel.Social
                replyAdapter.submitList(temp?.replies?.toMutableList())
                viewDataBinding.edit.text = null
                viewDataBinding.invalidateAll()
            }
        }
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