package kioli.quote.common.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import kioli.quote.BuildConfig
import kioli.quote.common.api.QuoteApi
import kioli.quote.common.entity.Quote
import kioli.quote.common.interactor.Invoker
import kioli.quote.common.interactor.UseCase
import kioli.quote.common.interactor.UseCaseInvoker
import kioli.quote.common.repository.LocalDataSource
import kioli.quote.common.repository.NetworkDataSource
import kioli.quote.section.interactor.QuoteUseCase
import kioli.quote.section.mvp.QuoteContract
import kioli.quote.section.mvp.QuotePresenter
import kioli.quote.section.repository.QuoteLocalDataSource
import kioli.quote.section.repository.QuoteNetworkDataSource
import kioli.quote.section.repository.QuoteRepository
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.asCoroutineDispatcher
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
    bind<CoroutineDispatcher>() with provider { AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher() }
    bind<Invoker>() with singleton { UseCaseInvoker(instance()) }
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