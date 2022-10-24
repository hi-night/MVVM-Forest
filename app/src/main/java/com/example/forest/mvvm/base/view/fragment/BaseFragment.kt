package com.example.forest.mvvm.base.view.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.forest.mvvm.base.view.IView

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), IView {

}
