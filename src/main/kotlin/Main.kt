import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun main() {

    val str = "{\"organization_id\":\"resurs\"}"
    val gson = GsonBuilder()
        .registerTypeAdapter(Request::class.java, RetrofitRequestSerializer())
        .create()
    val obj = gson.fromJson(str, Request::class.java)
//    println(obj)
    val request = gson.toJson(Request("resurs"))
//    println("1 $request")

//    val map = mapOf("organization_id" to "resurs")
//    val requestMap = gson.toJson(map)
//    println(requestMap)
//
//    val requestKotlin = Json.encodeToString(RequestKotlin("resurs"))
//    println(requestKotlin)
//
//    val mapType = object : TypeToken<Map<String, String>>() {}


    val api = createRetrofit()
    api.getWells(Request("resurs"))
        .subscribe({ result ->
            println("ok $result")
        }, { error ->
            println("error $error")
        })
}

fun createRetrofit(): OnixApi {
    val baseUrl = "http://192.168.170.64/geo/query/"

    val gson = GsonBuilder()
        .create()

    val loggingInterceptor = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .readTimeout(110, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .build()
    return retrofit.create(OnixApi::class.java)
}