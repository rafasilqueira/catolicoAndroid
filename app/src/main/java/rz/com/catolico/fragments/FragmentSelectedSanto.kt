package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterAbstractViewPager
import rz.com.catolico.bean.Santo
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.interfaces.IFavoriteSanto
import rz.com.catolico.interfaces.ISelectableContent
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao
import java.io.Serializable

class FragmentSelectedSanto : FragmentAbstract<ActivityCatolicoMain>(), Serializable, IFavoriteSanto, ISelectableContent {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var imgSanto: AppCompatImageView? = null
    private lateinit var santo: Santo
    private lateinit var celebrationDay: String

    companion object {
        fun instance(santo: Santo): FragmentSelectedSanto {
            val fragmentselectedSanto = FragmentSelectedSanto()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentselectedSanto.arguments = bundle
            return fragmentselectedSanto
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getSerialiableArgumentExtra("santo")?.let {
            santo = it as Santo
            celebrationDay = formatterComemoracao.format(santo.comemoracao)
            return@onAttach
        }

        throw CatolicoException("param santo is invalid")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_santo, container, false) as ViewGroup
        getParentActivity().isFavorite(santo.favorite)
        imgSanto = view.findViewById<AppCompatImageView>(R.id.imgSanto)
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        if (santo.imgurl != null && !santo.imgurl.equals("")) Picasso.with(context).load(santo.imgurl).into(imgSanto)
        setupViewPager(santo)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disableAllIcons()
        getParentActivity().showIconsSelectedContent()
    }

    private fun setupViewPager(santo: Santo) {
        val viewPagerAdapter = AdapterAbstractViewPager(childFragmentManager)
        tabLayout?.tabMode = TabLayout.MODE_FIXED
        viewPagerAdapter.addFrag(FragmentSantoHistory.instance(santo), getString(R.string.historia))
        viewPagerAdapter.addFrag(FragmentSantoRelated.instance(santo, this), getString(R.string.related))
        tabLayout?.setupWithViewPager(viewPager)
        viewPager?.adapter = viewPagerAdapter
        getParentActivity().setActionBarTitle(celebrationDay)
    }

    override fun onShareListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFavoriteListener() {
        getUser()?.let { onUpdateFavorite(santo, it) }
    }


    override fun onSucessUpdateFavorite(type: Santo) {
        getParentActivity().isFavorite(type.favorite)
    }

}
