package example.com.mymandir.helper

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


object MoshiJsonHelper {

    inline fun <reified T> jsonToKt(jsonString: String): Any? = moshiBuilder().adapter<Any>(Any::class.java).fromJson(jsonString)

    fun ktToJson(obj: Any): String = moshiBuilder().adapter(Any::class.java).toJson(obj)

    fun moshiBuilder() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

}
