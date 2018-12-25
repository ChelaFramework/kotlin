package chela.kotlin.validation.rule

import chela.kotlin.validation.ChRuleSet
import kotlin.reflect.KFunction

abstract class ChRule{
    companion object{
        @JvmStatic internal val rules = mutableMapOf<String, Pair<KFunction<*>, ChRule>>()
    }
    init{
        @Suppress("LeakingThis")
        this::class.members.forEach{
            if(it.isFinal && it is KFunction){
                val k = it.name.toLowerCase()
                if (rules[k] != null) throw Exception("exist key:$k")
                rules[k] = it to this
            }
        }
    }
}