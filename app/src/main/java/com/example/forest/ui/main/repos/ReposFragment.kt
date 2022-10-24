package com.example.forest.ui.main.repos

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.forest.R
import com.example.forest.databinding.FragmentReposBinding
import com.example.forest.mvvm.base.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReposFragment: BaseFragment(R.layout.fragment_repos) {
    private val binding by viewBinding(FragmentReposBinding::bind)
}