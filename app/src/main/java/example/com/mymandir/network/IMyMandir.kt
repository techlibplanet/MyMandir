package example.com.mymandir.network

import example.com.mymandir.models.MyMandirModel
import io.reactivex.Observable
import retrofit2.http.GET

interface IMyMandir {

    @GET("dummy")
    fun getData() : Observable<MutableList<MyMandirModel>>
}