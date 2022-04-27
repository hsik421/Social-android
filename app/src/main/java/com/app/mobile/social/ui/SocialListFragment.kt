package com.app.mobile.social.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mobile.social.BaseBindingFragment
import com.app.mobile.social.R
import com.app.mobile.social.databinding.FragmentSocialListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialListFragment : BaseBindingFragment<FragmentSocialListBinding>() {
    override val layoutRes: Int
        get() = R.layout.fragment_social_list


    lateinit var listAdapter: SocialListAdapter

    val socialViewModel: SocialViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.socialVm = socialViewModel
        setupView()
        subscribeUi()
    }

    private fun setupView() {

        viewDataBinding.socialListRecycler.apply {
            itemAnimator = null
            listAdapter = SocialListAdapter(socialViewModel)
            val linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            if (!listAdapter.hasObservers()) {
                listAdapter.setHasStableIds(true)
            }
            adapter = listAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (socialViewModel.dataLoading.value == false &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == listAdapter.currentList.size - 1
                    ) {
                        socialViewModel.loadSocials()
                    }
                }
            })
        }

    }


    private fun subscribeUi() {
        socialViewModel.items.observe(requireActivity()) {
            listAdapter.submitList(it)
        }
        socialViewModel.itemEvent.observe(viewLifecycleOwner) {

            requireActivity().supportFragmentManager.commit {
                replace(R.id.content_frame,SocialDetailFragment.newInstance(it))
                addToBackStack(null)
            }
        }
        socialViewModel.browserEvent.observe(viewLifecycleOwner) {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it?:return@observe)))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SocialListFragment()
    }
}