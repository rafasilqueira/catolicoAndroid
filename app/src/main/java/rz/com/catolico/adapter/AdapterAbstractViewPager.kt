package rz.com.catolico.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*
import android.text.method.TextKeyListener.clear
import android.support.v4.view.PagerAdapter





abstract class AdapterAbstractViewPager<T>(var manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    abstract fun addAllFrags(mItems: MutableList<T>)

    protected  fun addFrag(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun clear() {

        val transaction = manager.beginTransaction()
        mFragmentList.forEach { transaction.remove(it) }
        mFragmentList.clear()
        transaction.commitAllowingStateLoss()
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L + position
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


}
