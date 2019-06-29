package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.adapter.ViewHolder.VHOracao
import rz.com.catolico.adapter.ViewHolder.VHOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Usuario
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.fragments.FragmentOracao
import rz.com.catolico.fragments.FragmentSelectedOracao
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.Constantes.Companion.SELECTED_ORACAO_FRAGMENT_TAG

class AdapterOracao(context: Context, mItems: MutableList<Oracao>) : AdapterAbstract<Oracao>(context, mItems), IFavorite<Oracao> {

    private var fragmentAbstract: FragmentAbstract<*>? = null
    private var parentView: VHOracaoCategory? = null

    constructor(context: Context, fragmentAbstract: FragmentAbstract<*>, mItems: MutableList<Oracao>, parentVH: VHOracaoCategory) : this(context, mItems) {
        this.fragmentAbstract = fragmentAbstract
        this.parentView = parentVH
    }

    init {
        if (usuario != null && usuario?.oracoes?.isNotEmpty()!!) {
            syncronizeFavorites(mItems, usuario?.oracoes!!)
        }
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_oracao, parent, false)
        return VHOracao(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, oracao: Oracao) {
        val view: VHOracao
        if (holder is VHOracao) {
            view = holder
            view.txtOracao.text = oracao.name
            view.txtCategoria.text = oracao.categoriaOracao?.name

            setupIcon(view, oracao)

            view.favoriteButton?.setOnClickListener {
                if (usuario != null) {
                    val userClone = usuario?.clone() as Usuario

                    if (oracao.favorite) {
                        userClone.removeOracao(oracao)
                    } else {
                        userClone.addOracao(oracao)
                    }

                    val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
                    call.enqueue(object : Callback<Boolean> {

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            oracao.favorite = !oracao.favorite
                            if (oracao.favorite) {
                                usuario!!.addOracao(oracao.clone() as Oracao)
                            } else {
                                usuario!!.removeOracao(oracao)
                            }
                            notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })
                }
            }

            view.setOnClickListener(View.OnClickListener {
                fragmentAbstract?.let {
                    it.swapFragment(FragmentSelectedOracao.instance(oracao), SELECTED_ORACAO_FRAGMENT_TAG)
                    when (fragmentAbstract) {
                        is FragmentOracao -> (it as FragmentOracao).selectedAdapter = this@AdapterOracao
                    }
                }
            })

        }
    }

    private fun setupIcon(view: VHOracao, oracao: Oracao) {
        setupFavoriteIcon(view, oracao)
        if (usuario == null) {
            view.favoriteButton!!.visibility = View.GONE
            view.dividerLine!!.visibility = View.GONE
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