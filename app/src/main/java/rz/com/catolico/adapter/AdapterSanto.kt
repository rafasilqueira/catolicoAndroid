package rz.com.catolico.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tupinamba.model.bean.Santo
import com.squareup.picasso.Picasso
import rz.com.catolico.R
import java.text.SimpleDateFormat

class AdapterSanto(context: Context, mItems: List<Santo>) : GenericAdapter<Santo>(context, mItems) {

    private var fragment: Fragment? = null
    private val formatterComemoracao = SimpleDateFormat("dd/MM")

    constructor(context: Context, fragment: Fragment, mItems: List<Santo>) : this(context, mItems) {
        this.fragment = fragment
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
            view.txtComemoracao.text = formatterComemoracao.format(santo?.comemoracao)
            view.txtDiaData.text = santo?.diasData.toString()
            if (santo?.imgurl != null && !santo.imgurl.equals("")) {
                Picasso.with(context)
                        .load(santo.imgurl)
                        .placeholder(R.drawable.ic_santo)
                        .error(R.drawable.ic_santo)
                        .into(view.imgSanto)
            }

            view.setOnClickListener(View.OnClickListener {
                println("aqui caraio")
            })
        }

    }

}
