package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.adapter.ViewHolder.VHOracao
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Usuario
import rz.com.catolico.fragments.FragmentAbstractAdapter
import rz.com.catolico.fragments.FragmentOracao
import rz.com.catolico.interfaces.IFavoriteOracao

class AdapterOracao(context: Context,private val mItems: MutableList<Oracao>) : AdapterAbstract<Oracao>(context, mItems), IFavoriteOracao {

    private var fragmentAbstractAdapter: FragmentAbstractAdapter<Oracao, *>? = null
    private var onClickListener: View.OnClickListener? = null

    constructor(context: Context, mItems: MutableList<Oracao>, onClickListener: View.OnClickListener) : this(context, mItems) {
        this.onClickListener = onClickListener
    }

    constructor(context: Context, mItems: MutableList<Oracao>, fragmentAbstractAdapter: FragmentAbstractAdapter<Oracao, *>) : this(context, mItems) {
        this.fragmentAbstractAdapter = fragmentAbstractAdapter
    }

     fun syncronize() {
         getUser()?.let {
             syncronizeFavorites(mItems, it.oracoes)
         }
    }

    init {
        syncronize()
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_oracao, parent, false)
        if (onClickListener != null) {
            view.setOnClickListener(onClickListener)
        }
        return VHOracao(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, genericType: Oracao) {
        val view: VHOracao
        if (holder is VHOracao) {
            view = holder
            view.txtOracao.text = genericType.name
            view.txtCategoria.text = genericType.categoriaOracao.name

            setupIcon(view, genericType)

            view.favoriteButton?.setOnClickListener {
                usuario?.let { onUpdateFavorite(genericType, it) }
            }

            view.setOnClickListener(View.OnClickListener {
                fragmentAbstractAdapter?.let {
                    if (fragmentAbstractAdapter is FragmentOracao) {
                        (fragmentAbstractAdapter as FragmentOracao).selectedAdapter = this@AdapterOracao
                        (fragmentAbstractAdapter as FragmentOracao).onItemClick(genericType)
                        return@let
                    }
                }
            })
        }
    }

    override fun onSucessUpdateFavorite(type: Oracao, user: Usuario) {
        super.onSucessUpdateFavorite(type, user)
        notifyDataSetChanged()
    }

    /*override fun onSucessUpdateFavorite(type: Oracao) {
        notifyDataSetChanged()
    }*/

    private fun setupIcon(view: VHOracao, oracao: Oracao) {
        setupFavoriteIcon(view, oracao)
        if (usuario == null) {
            view.favoriteButton!!.visibility = View.GONE
            view.dividerLine.visibility = View.GONE
        }
    }

    private fun setupFavoriteIcon(view: VHOracao, oracao: Oracao) {
        if (oracao.favorite) {
            view.favoriteButton?.setImageResource(R.drawable.ic_favorite_star_selected)
        } else {
            view.favoriteButton?.setImageResource(R.drawable.ic_favorite_star_unselected)
        }
    }
}