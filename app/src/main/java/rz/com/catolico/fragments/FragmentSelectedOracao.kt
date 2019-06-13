package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig

class FragmentSelectedOracao : FragmentAbstract<ActivityCatolicoMain>(R.layout.fragment_selected_oracao) {

    private var parentContext: ActivityCatolicoMain? = null
    private var txtOracao: TextView? = null
    private var txtDescricao: TextView? = null
    private var oracao: Oracao? = null

    companion object {
        fun instance(oracao: Oracao): FragmentSelectedOracao {
            val fragmentOracaoContent = FragmentSelectedOracao()
            val bundle = Bundle()
            bundle.putSerializable("oracao", oracao)
            fragmentOracaoContent.arguments = bundle
            return fragmentOracaoContent
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentContext = context as ActivityCatolicoMain
        usuario = parentContext!!.getIntentUser()
        parentContext!!.showIconsSelectedContent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_oracao, container, false) as ViewGroup
        oracao = arguments?.getSerializable("oracao") as Oracao
        txtOracao = view.findViewById(R.id.txtOracao)
        txtDescricao = view.findViewById(R.id.txtDescricao)
        txtOracao?.text = oracao?.name
        txtDescricao?.text = oracao?.descricao
        parentContext?.isFavorite(oracao!!.favorite)
        return view
    }

    fun favoriteButtonListener() {
        if (usuario != null) {
            val userClone = usuario?.clone() as Usuario

            if (oracao?.favorite!!) {
                userClone.removeOracao(oracao!!)
            } else {
                userClone.addOracao(oracao!!)
            }

            val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
            call.enqueue(object : Callback<Boolean> {

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        oracao?.favorite = !oracao?.favorite!!
                        if(oracao?.favorite!!){
                            usuario!!.addOracao(oracao!!.clone() as Oracao)
                        }else{
                            usuario!!.removeOracao(oracao!!)
                        }
                        parentContext?.isFavorite(oracao!!.favorite)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

}