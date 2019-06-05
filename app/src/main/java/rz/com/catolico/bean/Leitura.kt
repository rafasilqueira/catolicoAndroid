package rz.com.catolico.bean

import java.io.Serializable

class Leitura : BaseEntity(), Serializable {
    var passagem = ""
    var descricao = ""
    var tipoLeitura = TipoLeitura()
}