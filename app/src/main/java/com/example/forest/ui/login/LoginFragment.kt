package com.example.forest.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.forest.R
import com.example.forest.databinding.FragmentLoginBinding
import com.example.forest.mvvm.base.view.fragment.BaseFragment
import com.example.forest.mvvm.ext.observeState
import com.example.forest.mvvm.ext.toast
import com.example.forest.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()

    // 使用代理方式处理 viewBinding 模板代码
    private val viewBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initUIState()
        initUIEvent()
    }

    private fun initView() {
        viewBinding.userName.addTextChangedListener {
            viewModel.dispatch(LoginViewModel.UIAction.UpdateUserName(it.toString()))
        }

        viewBinding.password.addTextChangedListener {
            viewModel.dispatch(LoginViewModel.UIAction.UpdatePassword(it.toString()))
        }

        viewBinding.login.setOnClickListener {
            viewModel.dispatch(LoginViewModel.UIAction.Login)
        }
    }

    private fun initUIState() {
        viewModel.uiState.also { state ->
            state.observeState(this, LoginViewModel.UIState::userName) {
                viewBinding.userName.setText(it)
                viewBinding.userName.setSelection(it?.length ?: 0)
            }

            state.observeState(this, LoginViewModel.UIState::password) {
                viewBinding.password.setText(it)
                viewBinding.password.setSelection(it?.length ?: 0)
            }

            state.observeState(this, LoginViewModel.UIState::isLoginSuccess) {
                if (it) {
                    activity?.apply {
                        MainActivity.launch(this)
                    }
                }
            }
        }
    }

    private fun initUIEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    when (it) {
                        is LoginViewModel.UIEvent.ShowToast ->
                            activity?.toast(it.message ?: "")
                        is LoginViewModel.UIEvent.IsShowDialog ->
                            viewBinding.progressBar.isVisible = it.isShow
                    }
                }
            }
        }
    }
}