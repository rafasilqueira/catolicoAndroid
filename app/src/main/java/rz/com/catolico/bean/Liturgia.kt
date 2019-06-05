package rz.com.catolico.bean

import java.io.Serializable
import java.util.*


class Liturgia : BaseEntityName(), Serializable {
    var data: Date? = null
    var today = false
    var tempo: TempoLiturgico = TempoLiturgico()
    var cor: CorLiturgica = CorLiturgica()
    var leituras: MutableList<Leitura> = ArrayList()
}