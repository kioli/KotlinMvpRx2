package kioli.quote.common.di

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import kioli.quote.App
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class InjectedActivity : AppCompatActivity(), KodeinAware {

    // closestKodein() automatically fetches app Kodein scope.
    private val appKodein by closestKodein()

    // retainedKodein handles activity lifecycle
    override val kodein by retainedKodein {
        extend(appKodein)
        import(injectedActivityModule(this@InjectedActivity), allowOverride = true)
        import(activityModule())
        (app().overrideBindings)()
    }

    open fun activityModule() = Kodein.Module {}
}

fun Activity.app() = applicationContext as App