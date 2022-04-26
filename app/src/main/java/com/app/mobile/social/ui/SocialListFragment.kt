package com.app.mobile.social.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mobile.social.BaseBindingFragment
import com.app.mobile.social.R
import com.app.mobile.social.databinding.FragmentSocialListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SocialListFragment : BaseBindingFragment<FragmentSocialListBinding>() {
    override val layoutRes: Int
        get() = R.layout.fragment_social_list


    @Inject
    lateinit var listAdapter: SocialListAdapter

    val socialViewModel: SocialViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.socialVm = socialViewModel
        setupView()
        subscribeUi()
    }

    private fun setupView() {
        viewDataBinding.socialListRecycler.apply {
            val linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (socialViewModel.dataLoading.value == false &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == listAdapter.currentList.size - 1) {
                        socialViewModel.loadSocials()
                    }
                }
            })
        }
    }


    private fun subscribeUi() {
        socialViewModel.items.observe(requireActivity()) {
            Log.i("hsik","socialViewModel.items = $it")
            listAdapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SocialListFragment()
    }
}