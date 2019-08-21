package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.ViewHolder.VHSanto
import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario
import rz.com.catolico.fragments.FragmentAbstractAdapter
import rz.com.catolico.fragments.FragmentSelectedSanto
import rz.com.catolico.interfaces.IFavoriteSanto
import rz.com.catolico.utils.Constantes.Companion.SELECTED_SANTO_FRAGMENT_TAG
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao

class AdapterSanto(context: Context, val mItems: MutableList<Santo>) : AdapterAbstract<Santo>(context, mItems), IFavoriteSanto {

    private var fragmentAbstractAdapter: FragmentAbstractAdapter<Santo, ActivityCatolicoMain>? = null

    init {
        syncronize()
    }

    fun syncronize() {
        getUser()?.let { super.syncronizeFavorites(mItems, it.santos) }
    }

    constructor(context: Context, fragmentAbstractAdapter: FragmentAbstractAdapter<Santo, ActivityCatolicoMain>, mItems: MutableList<Santo>) : this(context, mItems) {
        this.fragmentAbstractAdapter = fragmentAbstractAdapter
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_santo, parent, false)
        return VHSanto(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, genericType: Santo) {
        val view: VHSanto
        holder.setIsRecyclable(false)
        if (holder is VHSanto) {
            view = holder
            view.txtSantoNome.text = genericType.name
            view.txtComemoracao.text = formatterComemoracao.format(genericType.comemoracao)
            if (genericType.favorite) {
                view.favoriteButton?.setImageResource(R.drawable.ic_favorite_star_selected)
            }

            setupIcons(view, genericType)

            if (genericType.imgurl != null && !genericType.imgurl.equals("")) {
                Picasso.with(context).load(genericType.imgurl).into(view.imgSanto)
            }

            view.setOnClickListener(View.OnClickListener {
                fragmentAbstractAdapter?.onItemClick(genericType)
                //showSelectedSanto(genericType)
            })

            view.prayButton.setOnClickListener {
                showSelectedSanto(genericType)
            }

            view.shareButton?.setOnClickListener {

            }

            view.favoriteButton?.setOnClickListener {
                usuario?.let { onUpdateFavorite(genericType, it) }
            }
        }
    }

    override fun onSucessUpdateFavorite(type: Santo, user: Usuario) {
        super.onSucessUpdateFavorite(type,user)
        notifyDataSetChanged()
    }

    private fun showSelectedSanto(santo: Santo) {
        fragmentAbstractAdapter?.swapFragment(FragmentSelectedSanto.instance(santo), SELECTED_SANTO_FRAGMENT_TAG)
    }

    private fun setupIcons(view: VHSanto, santo: Santo) {
        setupFavoriteIcon(view)
        setupPrayIcon(view, santo)
        setupSantoDia(view, santo)
    }

    private fun setupPrayIcon(view: VHSanto, santo: Santo) {
        if (santo.oracoes.isEmpty()) {
            view.dividerLineTwo.visibility = View.GONE
            view.prayButton.visibility = View.GONE
            view.txtPrayQty.visibility = View.GONE
        } else {
            view.txtPrayQty.text = getOracoesQty(santo)
            view.divideLineOne.visibility = View.VISIBLE
        }
    }

    private fun setupFavoriteIcon(view: VHSanto) {
        if (usuario == null) {
            view.favoriteButton?.visibility = View.GONE
            view.dividerLineTwo.visibility = View.GONE
            view.divideLineOne.visibility = View.GONE
        }
    }

    private fun setupSantoDia(view: VHSanto, santo: Santo) {
        view.txtAsterisco.visibility = if (santo.diasData == 0) View.VISIBLE else View.GONE
    }

    private fun getOracoesQty(santo: Santo): String {
        return "%02d".format((santo.oracoes.size))
    }

}
