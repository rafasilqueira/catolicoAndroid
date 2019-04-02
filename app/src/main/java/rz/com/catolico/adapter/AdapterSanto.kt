package rz.com.catolico.adapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.tupinamba.model.bean.Santo
import com.squareup.picasso.Picasso
import rz.com.catolico.R
import java.text.SimpleDateFormat

class AdapterSanto(activity: Activity, santoArrayList: List<Santo>) : RecyclerView.Adapter<AdapterSanto.ViewHolder>() {

    private var santoArrayList: List<Santo>? = null
    private var activity: Activity? = null
    private var fragment: Fragment? = null
    private val formatterComemoracao = SimpleDateFormat("dd/MM")

    constructor(activity: Activity, fragment: Fragment, santoArrayList: List<Santo>) : this(activity, santoArrayList) {
        this.fragment = fragment
    }

    init {
        this.santoArrayList = santoArrayList
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSanto.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_santos, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return santoArrayList?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: AdapterSanto.ViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)
        val santo = santoArrayList?.get(position)
        viewHolder.txtSantoNome.text = santo?.nome
        if (santo?.imgurl != null && !santo.imgurl.equals("")) {
            Picasso.with(activity)
                    .load(santo.imgurl)
                    .placeholder(R.drawable.ic_santo)
                    .error(R.drawable.ic_santo)
                    .into(viewHolder.imgSanto)
        } else {
            viewHolder.imgSanto.setImageResource(R.drawable.ic_santo)
        }

        viewHolder.txtSantoComemoracao.text = formatterComemoracao.format(santo?.comemoracao)
        viewHolder.txtDiaData.text = santo?.diasData.toString()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgSanto: AppCompatImageView = itemView.findViewById<View>(R.id.img_santo) as AppCompatImageView
        var txtSantoNome: TextView = itemView.findViewById<View>(R.id.txt_santo_nome) as TextView
        var txtSantoComemoracao: TextView = itemView.findViewById<View>(R.id.txt_santo_comemoracao) as TextView
        var txtDiaData: TextView = itemView.findViewById<View>(R.id.txt_dia_data) as TextView
    }

}