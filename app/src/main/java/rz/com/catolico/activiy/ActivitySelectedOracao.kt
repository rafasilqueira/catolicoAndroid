package rz.com.catolico.activiy

import android.os.Bundle
import android.view.Menu
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_selected_oracao.*
import rz.com.catolico.R
import rz.com.catolico.bean.Oracao
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.interfaces.IFavoriteOracao
import rz.com.catolico.utils.Constantes

class ActivitySelectedOracao : ActivitySelectable(), IFavoriteOracao {

    private lateinit var oracao: Oracao

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        isFavorite(oracao.favorite)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_oracao)
        oracao = getIntentObject("oracao") as Oracao?
                ?: throw CatolicoException("param oracao is invalid")

        setupToolbar(oracao.name)

        txtOracao.text = oracao.name
        txtDescricao.text = oracao.descricao
    }

    override fun onShareListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFavoriteListener() {
        getUser()?.let { onUpdateFavorite(oracao, it) }
    }

    override fun onSucessUpdateFavorite(type: Oracao) {
        getUser()?.let {
            Hawk.put(Constantes.USER_KEY, it)
        }

        isFavorite(type.favorite)
    }
}