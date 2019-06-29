package rz.com.catolico.fragments

import rz.com.catolico.activiy.ActivityBaseFragment

abstract class FragmentAbstractViewPager<T, A : ActivityBaseFragment> : FragmentAbstractAdapter<T, A>() {
    abstract fun setupViewPager()
}