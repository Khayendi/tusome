package com.code.tusome.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.code.tusome.models.FragObject

class AuthAdapter(private val list: List<FragObject>, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position].fragment

    override fun getPageTitle(position: Int): CharSequence = list[position].title
}