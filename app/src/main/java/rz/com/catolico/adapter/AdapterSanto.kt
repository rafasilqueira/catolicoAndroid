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

            if (santo.diasData == 0) {
                view.imgStar.visibility = View.VISIBLE
            }

            if (santo.imgurl != null && !santo.imgurl.equals("")) {
                Picasso.with(context)
                        .load(santo.imgurl)
                        .placeholder(R.drawable.ic_santo)
                        .error(R.drawable.ic_santo)
                        .into(view.imgSanto)
            }

            view.setOnClickListener(View.OnClickListener {
                fragmentAbstract?.itemClickListenr(santo)
            })
        }

    }

}
