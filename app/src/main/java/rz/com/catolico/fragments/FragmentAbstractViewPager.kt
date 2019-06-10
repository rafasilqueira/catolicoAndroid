package rz.com.catolico.fragments

import rz.com.catolico.interfaces.IBaseFragmentActivty

abstract class FragmentAbstractViewPager<T,A:IBaseFragmentActivty>(initialView : Int) : FragmentAbstractAdapter<T,A>(initialView){
     abstract fun setupViewPager()
}