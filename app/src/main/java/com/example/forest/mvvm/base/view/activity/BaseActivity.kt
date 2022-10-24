package com.example.forest.mvvm.base.view.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.forest.mvvm.base.view.IView

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId), IView {

}
