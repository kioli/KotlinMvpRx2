package kioli.rx.util

import android.support.test.espresso.IdlingResource

/**
 * Contains a static reference to [IdlingResource]
 */
internal object EspressoIdlingResource {

    private val RESOURCE = "GLOBAL"

    val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}
