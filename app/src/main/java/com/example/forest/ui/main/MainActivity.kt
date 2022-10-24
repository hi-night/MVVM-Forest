package com.example.forest.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.forest.R
import com.example.forest.databinding.ActivityMainBinding
import com.example.forest.mvvm.base.adapter.ViewPagerAdapter
import com.example.forest.mvvm.base.view.activity.BaseActivity
import com.example.forest.ui.main.hoem.HomeFragment
import com.example.forest.ui.main.profile.ProfileFragment
import com.example.forest.ui.main.repos.ReposFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            listOf(HomeFragment(), ReposFragment(), ProfileFragment())
        )
        binding.viewPager.offscreenPageLimit = 2

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                for (index in 0..position) {
                    if (binding.navigation.visibility == View.VISIBLE)
                        binding.navigation.menu.getItem(index).isChecked = position == index
                }
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })

        binding.navigation.setOnItemSelectedListener { menuItem ->
            binding.viewPager.currentItem = getNavigationSelect(menuItem)
            true
        }
    }

    private fun getNavigationSelect(menuItem: MenuItem): Int {
        return when (menuItem.itemId) {
            R.id.nav_home -> 0
            R.id.nav_repos -> 1
            R.id.nav_profile -> 2
            else -> 0
        }
    }

    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                startActivity(Intent(activity, MainActivity::class.java))
                finish()
            }
    }
}