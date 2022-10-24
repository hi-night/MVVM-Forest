package com.example.forest.ui.main.hoem


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.forest.R
import com.example.forest.databinding.FragmentHomeBinding
import com.example.forest.ui.main.hoem.FooterAdapter
import com.example.forest.mvvm.base.view.fragment.BaseFragment
import com.example.forest.mvvm.ext.observe
import com.scwang.smart.refresh.header.ClassicsHeader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private val homeAdapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter.withLoadStateFooter(
                footer = FooterAdapter(homeAdapter)
            )
        }

        with(binding.swipeRefresh) {
            setOnRefreshListener {
                homeAdapter.refresh()
            }
            // 设置刷新样式
            setRefreshHeader(ClassicsHeader(activity))
            // 关闭下拉加载
            setEnableLoadMore(false)

            observe(homeAdapter.loadStateFlow.asLiveData()) { loadStates ->
                finishRefresh(loadStates.refresh is LoadState.Loading)
            }
        }

        observe(viewModel.articleList) {
            homeAdapter.submitData(lifecycle, it)
        }
    }
}