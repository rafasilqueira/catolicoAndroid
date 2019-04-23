package rz.com.catolico.bean

import java.io.Serializable
import java.util.*


class Liturgia : BaseEntityName(), Serializable {
    var data: Date? = null
    var today: Boolean = false
    var tempo: TempoLiturgico? = null
    var cor: CorLiturgica? = null
    var leituras: List<Leitura>? = null
}