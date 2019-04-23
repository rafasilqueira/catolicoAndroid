package rz.com.catolico.bean

import java.io.Serializable

class Leitura:BaseEntityName(),Serializable {
    var passagem: String? = null
    var tipoLeitura: TipoLeitura? = null
}