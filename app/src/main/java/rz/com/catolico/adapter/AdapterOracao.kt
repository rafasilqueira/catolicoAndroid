package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.CallBack.CallBackDialog
import rz.com.catolico.R
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Usuario
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.ToastMisc

class AdapterOracao(context: Context, mItems: MutableList<Oracao>) : AdapterAbstract<Oracao>(context, mItems), IFavorite<Oracao> {

    private var fragmentAbstract: FragmentAbstract<Oracao>? = null

    init {
        if (usuario != null && usuario?.oracoes?.isNotEmpty()!!) {
            super.syncronizeFavorites(mItems, usuario?.oracoes!!)
        }
    }

    constructor(context: Context, fragmentAbstract: FragmentAbstract<Oracao>, mItems: MutableList<Oracao>) : this(context, mItems) {
        this.fragmentAbstract = fragmentAbstract
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_oracao, parent, false)
        return VHOracao(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, oracao: Oracao) {
        val view: VHOracao
        if (holder is VHOracao) {
            view = holder
            view.txtOracao?.text = oracao.nome
            view.txtCategoria?.text = oracao.categoriaOracao?.nome

            if (oracao.favorite) {
                view.favoriteButton?.setImageResource(R.drawable.ic_favorite_heart_selected)
            }



            view.favoriteButton?.setOnClickListener {
                if (usuario != null) {
                    val userClone = usuario?.clone() as Usuario

                    if (oracao.favorite) {
                        userClone.removeOracao(oracao)
                    } else {
                        userClone.addOracao(oracao)
                    }

                    val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
                    call.enqueue(object : CallBackDialog<Boolean>(context) {

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            super.onResponse(call, response)
                            oracao.favorite = !oracao.favorite
                            ToastMisc.sucess(this@AdapterOracao.context)
                            notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            super.onFailure(call, t)
                            ToastMisc.generalError(this@AdapterOracao.context)
                        }

                    })
                }
            }
        }

    }
}