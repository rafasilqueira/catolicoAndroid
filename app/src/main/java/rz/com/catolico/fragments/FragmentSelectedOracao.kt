package rz.com.catolico.fragments

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

class FragmentSelectedOracao : FragmentAbstract<ActivityCatolicoMain>() {

    companion object {
        fun instance(oracao: Oracao): FragmentSelectedOracao {
            val fragmentOracaoContent = FragmentSelectedOracao()
            val bundle = Bundle()
            bundle.putSerializable("oracao", oracao)
            fragmentOracaoContent.arguments = bundle
            return fragmentOracaoContent
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_oracao, container, false) as ViewGroup
        val oracao = getOracao("oracao")

        oracao?.let {
            view.findViewById<TextView>(R.id.txtOracao)?.text = it.name
            view.findViewById<TextView>(R.id.txtDescricao)?.text = it.descricao
            getParentActivity().isFavorite(it.favorite)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disableAllIcons()
        getParentActivity().showIconsSelectedContent()
    }

    private fun getOracao(key: String): Oracao? = getSerialiableArgumentExtra(key) as Oracao?

    fun favoriteButtonListener() {
        val oracao = getOracao("oracao")
        if (getUser() != null && oracao != null) {
            val userClone = getUser()?.clone() as Usuario

            if (oracao.favorite) {
                userClone.removeOracao(oracao)
            } else {
                userClone.addOracao(oracao)
            }

            val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
            call.enqueue(object : Callback<Boolean> {

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        oracao.favorite = !oracao.favorite
                        if (oracao.favorite) {
                            getUser()!!.addOracao(oracao.clone() as Oracao)
                        } else {
                            getUser()!!.removeOracao(oracao)
                        }
                        getParentActivity().isFavorite(oracao.favorite)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

}