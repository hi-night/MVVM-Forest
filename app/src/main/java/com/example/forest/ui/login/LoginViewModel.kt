package com.example.forest.ui.login

import androidx.lifecycle.viewModelScope
import com.example.forest.base.isFailure
import com.example.forest.base.isSuccess
import com.example.forest.mvvm.base.viewmodel.BaseViewModel
import com.example.forest.mvvm.ext.setState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    /**
     * UI 状态 必须有值
     */
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    /**
     * 事件
     */
    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> = _uiEvent.asSharedFlow()

    fun dispatch(action: UIAction) {
        when (action) {
            is UIAction.UpdateUserName -> {
                _uiState.value = _uiState.value.copy(userName = action.userName)
            }
            is UIAction.UpdatePassword -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }
            is UIAction.Login -> {
                login()
            }
        }
    }

    /**
     * 网络请求 使用flow
     */
    private fun login() {
        viewModelScope.launch {
            val userName = _uiState.value.userName
            val password = _uiState.value.password

            if (userName.isNullOrEmpty() || password.isNullOrEmpty()) {
                _uiEvent.emit(UIEvent.ShowToast("请输入用户名或密码！"))
                return@launch
            }

            flow {
                emit(loginRepository.login(userName, password))
            }
                .onStart {
                    _uiEvent.emit(UIEvent.IsShowDialog(true))
                }
                .onEach { result ->
                    result.isSuccess {
                        // code == 0 的情况
                        _uiState.setState { copy(isLoginSuccess = true) }
                    }
                    result.isFailure {
                        // code 不等于0 的情况
                        _uiEvent.emit(UIEvent.ShowToast(it.message))
                    }
                }
                .catch {
                    // 处理异常及崩溃处理
                    _uiEvent.emit(UIEvent.ShowToast(it.message))
                }
                .onCompletion {
                    // 完成时调用
                    _uiEvent.emit(UIEvent.IsShowDialog(false))
                }
                .collect()
        }
    }

    /**
     * 网络请求 方式2 未处理异常
     */
    private fun login2() {
        viewModelScope.launch {
            val userName = _uiState.value.userName
            val password = _uiState.value.password

            if (userName.isNullOrEmpty() || password.isNullOrEmpty()) {
                _uiEvent.emit(UIEvent.ShowToast("请输入用户名或密码！"))
                return@launch
            }

            _uiEvent.emit(UIEvent.IsShowDialog(true))
            val result = loginRepository.login(userName, password)
            result.isSuccess {
                result.isSuccess {
                    // code == 0 的情况
                    _uiEvent.emit(UIEvent.ShowToast(it?.nickname))
                    _uiState.setState { copy(isLoginSuccess = true) }
                }
                result.isFailure {
                    // code 不等于0 的情况
                    _uiEvent.emit(UIEvent.ShowToast(it.message))
                }
            }
            result.isFailure { }
            _uiEvent.emit(UIEvent.IsShowDialog(false))
        }
    }


    sealed class UIEvent {
        data class IsShowDialog(val isShow: Boolean) : UIEvent()

        data class ShowToast(var message: String?) : UIEvent()
    }

    sealed class UIAction {
        data class UpdateUserName(var userName: String?) : UIAction()
        data class UpdatePassword(var password: String?) : UIAction()
        object Login : UIAction()
    }

    data class UIState(
        val userName: String? = null,
        val password: String? = null,
        val isLoginSuccess: Boolean = false
    )
}