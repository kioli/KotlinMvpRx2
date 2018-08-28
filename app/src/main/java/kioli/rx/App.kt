package kioli.rx

import android.app.Application
import android.support.annotation.VisibleForTesting
import kioli.rx.common.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {

    @VisibleForTesting
    var overrideBindings: Kodein.MainBuilder.() -> Unit = {}

    override val kodein = Kodein.lazy {
        import(appModule(applicationContext))
    }
}