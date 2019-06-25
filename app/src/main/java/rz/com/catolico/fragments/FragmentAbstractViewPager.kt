package rz.com.catolico.fragments

import rz.com.catolico.activiy.ActivityBaseFragment

abstract class FragmentAbstractViewPager<T, A : ActivityBaseFragment>(initialView: Int) : FragmentAbstractAdapter<T, A>(initialView) {
    abstract fun setupViewPager()
}