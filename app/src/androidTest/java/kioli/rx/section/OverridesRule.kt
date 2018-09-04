package kioli.rx.section

import android.support.test.InstrumentationRegistry
import kioli.rx.App
import org.junit.rules.ExternalResource
import org.kodein.di.Kodein

/**
 * Used to retrieve test application before every test and override dependencies.
 */
class OverridesRule(private val bindings: Kodein.MainBuilder.() -> Unit = {}) : ExternalResource() {

    private fun app(): App = InstrumentationRegistry.getTargetContext().applicationContext as App

    override fun before() {
        app().overrideBindings = bindings
    }

    override fun after() {
        app().overrideBindings = {}
    }
}