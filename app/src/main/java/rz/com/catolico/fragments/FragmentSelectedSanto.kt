package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao

class FragmentSelectedSanto : Fragment() {

    private var parentContext: ActivityCatolicoMain? = null
    private var santo: Santo? = null
    private var usuario: Usuario? = null

    companion object {
        fun instance(santo: Santo): FragmentSelectedSanto {
            val fragmentselectedSanto = FragmentSelectedSanto()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentselectedSanto.arguments = bundle
            return fragmentselectedSanto
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentContext = context as ActivityCatolicoMain
        usuario = parentContext!!.getIntentUser()
        parentContext!!.disableAllFragmentIcons()
        parentContext!!.showIconsSelectedContent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_santo, container, false) as ViewGroup
        santo = arguments?.getSerializable("santo") as Santo
        parentContext?.isFavorite(santo!!.favorite)

        val txtNome = view.findViewById<TextView>(R.id.txtNome)
        val txtComemoracao = view.findViewById<TextView>(R.id.txtComemoracao)
        val txtDescricao = view.findViewById<TextView>(R.id.txtDescricao)
        val imgSanto = view.findViewById<AppCompatImageView>(R.id.imgSanto)

        txtNome.text = santo?.name
        txtComemoracao.text = formatterComemoracao.format(santo?.comemoracao)
        txtDescricao.text = santo?.descricao
        if (santo?.imgurl != null && !santo?.imgurl.equals("")) { Picasso.with(context).load(santo?.imgurl).into(imgSanto) }
        return view
    }

    fun favoriteButtonListener() {
        if (usuario != null) {
            val userClone = usuario?.clone() as Usuario

            if (santo?.favorite!!) {
                userClone.removeSanto(santo!!)
            } else {
                userClone.addSanto(santo!!)
            }

            val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
            call.enqueue(object : Callback<Boolean> {

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        santo?.favorite = !santo?.favorite!!
                        if (santo?.favorite!!) {
                            usuario!!.addSanto(santo!!.clone() as Santo)
                        } else {
                            usuario!!.removeSanto(santo!!)
                        }
                        parentContext?.isFavorite(santo!!.favorite)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

}