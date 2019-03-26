package rz.com.catolico.adapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rz.com.catolico.R

class AdapterSanto(activity: Activity,fragment: Fragment ,santoArrayList : List<Santo>) : RecyclerView.Adapter<AdapterSanto.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSanto.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_santos, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: AdapterSanto.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgSanto: AppCompatImageView
        var txtSantoNome: TextView
        var txtSantoComemoracao: TextView
        var txtDiaData: TextView

        init {
            imgSanto = itemView.findViewById<View>(R.id.img_santo) as AppCompatImageView
            txtSantoNome = itemView.findViewById<View>(R.id.txt_santo_nome) as TextView
            txtSantoComemoracao = itemView.findViewById<View>(R.id.txt_santo_comemoracao) as TextView
            txtDiaData = itemView.findViewById<View>(R.id.txt_dia_data) as TextView
        }
    }

}