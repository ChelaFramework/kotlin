package chela.kotlin.model

import chela.kotlin.i18n.ChI18n
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.full.createInstance

object ChModel{
    @JvmStatic internal val repo: MutableMap<String, Model> = HashMap()
    @JvmStatic fun get(v: List<String>):Any{
        if(v.isEmpty()) throw Exception("invalid list size == 0")
        if(v[0] == "i18n") return ChI18n.get(v)
        repo[v[0]]?.let { return find(v, it) } ?: throw Exception("invalid key:" + v[0])
    }
    @JvmStatic fun record(v: List<String>, record: Model): Any {
        if (v.isEmpty()) throw Exception("invalid list size == 0")
        return find(v, record)
    }
    @JvmStatic private fun find(v: List<String>, it: Model): Any {
        var model: Model? = it
        var list:MutableList<Any>? = null
        var r: Any = 0
        for(idx in 1 until v.size) {
            r = model?.get(v[idx]) ?: list?.get(v[idx].toInt()) ?: throw Exception("invalid key:${v[idx]} in $v")
            when(r){
                is Model ->{
                    model = r
                    list = null
                }
                is List<*>->{
                    model = null
                    @Suppress("UNCHECKED_CAST")
                    list = r as MutableList<Any>
                }
            }
        }
        return r
    }
    @JvmStatic private fun arr(o:JSONArray, target:MutableList<Any>){
        (0 until o.length()).forEach {
            val v = o[it]
            @Suppress("UNCHECKED_CAST")
            when (v) {
                is JSONObject -> obj(v, (target[it] as? Model) ?: throw Exception("invalid model idx:$it"))
                is JSONArray -> arr(v, (target[it] as? MutableList<Any>) ?: throw Exception("invalid key $it"))
                else -> target.add(v)
            }
        }
    }
    @JvmStatic private fun obj(o:JSONObject, target:Model){
        val setter = target.ref.setter
        val getter = target.ref.getter
        o.keys().forEach {k->
            setter[k]?.let { s ->
                val v = o.get(k)
                @Suppress("UNCHECKED_CAST")
                when (v) {
                    is JSONObject -> obj(v, (getter[k]?.call(target) as? Model) ?: throw Exception("invalid key $k"))
                    is JSONArray -> arr(v, (getter[k]?.call(target) as? MutableList<Any>) ?: throw Exception("invalid key $k"))
                    else -> s.call(target, v)
                }
            }
        }
    }
    @JvmStatic fun jsonToModel(json:JSONObject, model:String):Model? =
        try {
            (Class.forName(model).kotlin.createInstance() as? Model)?.let {
                obj(json, it)
                it
            }
        }catch(e:Exception){
            null
        }
}
