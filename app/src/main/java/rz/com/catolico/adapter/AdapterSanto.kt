package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.adapter.ViewHolder.VHSanto
import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.fragments.FragmentSanto
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao
import rz.com.catolico.utils.SantoUtils.Companion.getDaysToDate
import rz.com.catolico.utils.ToastMisc

class AdapterSanto(context: Context, mItems: MutableList<Santo>) : AdapterAbstract<Santo>(context, mItems), IFavorite<Santo> {

    private var fragmentAbstract: FragmentAbstract<Santo>? = null

    init {
        if (usuario != null && usuario?.santos?.isNotEmpty()!!) {
            super.syncronizeFavorites(mItems, usuario?.santos!!)
        }
    }

    constructor(context: Context, fragmentAbstract: FragmentAbstract<Santo>, mItems: MutableList<Santo>) : this(context, mItems) {
        this.fragmentAbstract = fragmentAbstract
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_santo, parent, false)
        return VHSanto(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, santo: Santo) {
        val view: VHSanto
        holder.setIsRecyclable(false)
        if (holder is VHSanto) {
            view = holder
            view.txtSantoNome.text = santo.nome
            view.txtComemoracao.text = formatterComemoracao.format(santo.comemoracao)
            view.txtDiaData.text = getDaysToDate(context, santo.diasData!!)
            view.txtDescricao.text = santo.descricao
            if (santo.favorite) {
                view.favoriteButton?.setImageResource(R.drawable.ic_favorite_star_selected)
            }

            setupIcons(view, santo)

            if (santo.imgurl != null && !santo.imgurl.equals("")) {
                Picasso.with(context).load(santo.imgurl).into(view.imgSanto)
            }

            view.setOnClickListener(View.OnClickListener {
                view.txtDescricao.visibility = if (view.txtDescricao.visibility == View.GONE) View.VISIBLE else View.GONE
            })

            view.prayButton.setOnClickListener {
                (fragmentAbstract as FragmentSanto).showDialogSayntPrays(santo)
            }

            view.shareButton?.setOnClickListener {

            }

            view.favoriteButton?.setOnClickListener {
                if (usuario != null) {
                    val userClone = usuario?.clone() as Usuario

                    if (santo.favorite) {
                        userClone.removeSanto(santo)
                    } else {
                        userClone.addSanto(santo)
                    }

                    val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
                    call.enqueue(object : Callback<Boolean> {

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            santo.favorite = !santo.favorite
                            notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            t.printStackTrace()
                            ToastMisc.generalError(this@AdapterSanto.context)
                        }
                    })
                }
            }
        }
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
        if (santo.diasData == 0) {
            view.txtIsSantoDia.visibility = View.VISIBLE
        }
    }

    private fun getOracoesQty(santo: Santo): String {
        return "%02d".format((santo.oracoes.size))
    }

}
