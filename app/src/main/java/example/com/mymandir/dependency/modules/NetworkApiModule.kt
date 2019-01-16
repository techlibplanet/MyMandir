package com.example.mayank.kwizzapp.dependency.modules

import com.example.mayank.kwizzapp.dependency.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import example.com.mymandir.network.IMyMandir
import retrofit2.Retrofit

@Module(includes = arrayOf(HttpModule::class))
class NetworkApiModule {

    @Provides
    @ApplicationScope
    fun myMandir(retrofit: Retrofit): IMyMandir {
        return retrofit.create(IMyMandir::class.java)
    }

}