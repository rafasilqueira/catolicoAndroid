package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tupinamba.model.bean.Santo
import com.squareup.picasso.Picasso
import rz.com.catolico.R
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao
import rz.com.catolico.utils.SantoUtils.Companion.getDaysToDate

class AdapterSanto(context: Context, mItems: List<Santo>) : GenericAdapter<Santo>(context, mItems) {

    private var fragmentAbstract: FragmentAbstract<Santo>? = null

    constructor(context: Context, fragmentAbstract: FragmentAbstract<Santo>, mItems: List<Santo>) : this(context, mItems) {
        this.fragmentAbstract = fragmentAbstract
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_santos, parent, false)
        return VHSanto(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, santo: Santo) {
        val view: VHSanto
        holder.setIsRecyclable(false)
        if (holder is VHSanto) {
            view = holder
            view.txtSantoNome.text = santo.nome
            view.txtComemoracao.text = formatterComemoracao.format(santo.comemoracao)
            view.txtDiaData.text = getDaysToDate(context, santo.diasData)
            view.txtDescricao.text = santo.descricao

            setupIcons(view, santo)

            if (santo.imgurl != null && !santo.imgurl.equals("")) {
                Picasso.with(context).load(santo.imgurl).into(view.imgSanto)
            }

            view.setOnClickListener(View.OnClickListener {
                view.txtDescricao.visibility = if (view.txtDescricao.visibility == View.GONE) View.VISIBLE else View.GONE
            })

            view.prayButton.setOnClickListener {

            }

            view.shareButton.setOnClickListener {

            }

            view.favoriteButton.setOnClickListener {

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
            view.divideLineOne.visibility = View.GONE
            view.prayButton.visibility = View.GONE
            view.txtPrayQty.visibility = View.GONE
        } else {
            view.txtPrayQty.text = getOracoesQty(santo)
        }
    }

    private fun setupFavoriteIcon(view: VHSanto) {
        if (!isUserLogged) {
            view.favoriteButton.visibility = View.GONE
            view.dividerLineTwo.visibility = View.GONE
        }
    }

    private fun setupSantoDia(view: VHSanto, santo: Santo) {
        if (santo.diasData == 0) {
            view.txtIsSantoDia.visibility = View.VISIBLE
        }
    }

    private fun getOracoesQty(santo: Santo): String {
        return " %02d ".format((santo.oracoes?.size ?: 0).toString())
    }

}
