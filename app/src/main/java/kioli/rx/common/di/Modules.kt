package kioli.rx.common.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import kioli.rx.BuildConfig
import kioli.rx.common.api.QuoteApi
import kioli.rx.common.entity.Quote
import kioli.rx.common.interactor.Invoker
import kioli.rx.common.interactor.UseCase
import kioli.rx.common.interactor.UseCaseInvoker
import kioli.rx.common.repository.LocalDataSource
import kioli.rx.common.repository.NetworkDataSource
import kioli.rx.section.interactor.QuoteUseCase
import kioli.rx.section.mvp.QuoteContract
import kioli.rx.section.mvp.QuotePresenter
import kioli.rx.section.repository.QuoteLocalDataSource
import kioli.rx.section.repository.QuoteNetworkDataSource
import kioli.rx.section.repository.QuoteRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun appModule(appContext: Context) = Kodein.Module {
    bind<Context>() with provider { appContext }
    bind<Invoker>() with singleton { UseCaseInvoker() }
    bind<NetworkDataSource>() with singleton { QuoteNetworkDataSource(instance()) }
    bind<LocalDataSource>() with singleton { QuoteLocalDataSource(instance()) }
    bind<SharedPreferences>() with singleton { appContext.getSharedPreferences("quotePreferences", MODE_PRIVATE) }
    import(httpAppModule())
}

fun httpAppModule() = Kodein.Module {
    bind<Interceptor>() with singleton {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(instance())
            }
        }.build()
    }
    bind<QuoteApi>() with singleton {
        Retrofit.Builder()
                .baseUrl("http://api.forismatic.com/api/1.0/")
                .client(instance())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuoteApi::class.java)
    }
}

fun injectedActivityModule(activity: AppCompatActivity) = Kodein.Module {
    bind<Context>(overrides = true) with provider { activity }
}

fun quoteActivityModule() = Kodein.Module {
    bind<QuoteContract.Presenter>() with provider { QuotePresenter(instance(), instance()) }
    bind<QuoteRepository>() with provider { QuoteRepository(instance(), instance()) }
    bind<UseCase<QuoteUseCase.Params, Quote>>() with provider { QuoteUseCase(instance()) }
}