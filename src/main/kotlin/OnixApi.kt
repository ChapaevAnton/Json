import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type


interface OnixApi {

    @GET("well")
    fun getWells(@Query("query") request: Request): Single<List<Well>>
}

@Serializable
data class RequestKotlin(
    @SerialName("organization_id")
    val value: String
) {
    override fun toString(): String =
        Json.encodeToString(this)
}

data class Request(
    @SerializedName("organization_id")
    val value: String
)

data class Well(
    val block_number: Int?,
    val number: Int?,
    val location: Location?,
    val depth: Double?,
    val azimuth: Int?,
    val dm: Double?
)

data class Location(
    val type: String?,
    val coordinates: List<List<Double>>?
)

class RetrofitRequestSerializer : JsonSerializer<Request> {
    override fun serialize(srcRequest: Request?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        if (srcRequest == null)
            return null
        val gson = Gson()
        val value = gson.toJson(srcRequest)
        return JsonPrimitive(value)
    }
}

class RequestAdapter : TypeAdapter<Request>() {
    override fun write(out: JsonWriter?, value: Request?) {
        TODO("Not yet implemented")
    }

    override fun read(`in`: JsonReader?): Request {
        TODO("Not yet implemented")
    }

}