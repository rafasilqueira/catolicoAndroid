package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tupinamba.model.bean.Santo
import com.squareup.picasso.Picasso
import rz.com.catolico.R
import rz.com.catolico.fragments.AbstractFragment
import java.text.SimpleDateFormat
import rz.com.catolico.utils.ActivityUtils.Companion.getResourceString

class AdapterSanto(context: Context, mItems: List<Santo>) : GenericAdapter<Santo>(context, mItems) {

    private var fragment: AbstractFragment<Santo>? = null
    private val formatterComemoracao = SimpleDateFormat("dd/MM")
    val TODAY : String = getResourceString(context,R.string.hoje)
    val DAYS_TO_DATE : String = getResourceString(context,R.string.days_qtde)
    val IS_TODAY : Int = 0

    constructor(context: Context, fragment: AbstractFragment<Santo>, mItems: List<Santo>) : this(context, mItems) {
        this.fragment = fragment
    }

    fun getDaysToDate(days : Int) : String{
        return if (days == IS_TODAY) TODAY else getResourceString(context,R.string.at) + " %02d ".format(days) + DAYS_TO_DATE
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
            view.txtSantoNome.text = santo.descricao
            view.txtComemoracao.text = formatterComemoracao.format(santo.comemoracao)
            view.txtDiaData.text = getDaysToDate(santo.diasData)

            if(santo.diasData == IS_TODAY){
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
                fragment?.itemClickListenr(santo)
            })
        }

    }

}
