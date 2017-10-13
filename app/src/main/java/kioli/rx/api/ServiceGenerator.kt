package kioli.rx.api

import kioli.rx.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

internal object ServiceGenerator: ServiceGeneratorI {

    private val endpoint = "http://api.forismatic.com/api/1.0/"
    private val builder: Retrofit.Builder

    init {
        builder = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            builder?.client(client)
        }
    }

    override fun <T> getService(c: Class<T>): T {
        return builder.build().create(c)
    }

}