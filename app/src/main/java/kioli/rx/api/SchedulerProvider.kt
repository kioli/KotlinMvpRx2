package kioli.rx.api

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Utility class to acquire different schedulers used to perform Rx operations
 */
internal object SchedulerProvider {
    fun ui(): Scheduler = AndroidSchedulers.mainThread()

    fun computation(): Scheduler = Schedulers.computation()

    fun trampoline(): Scheduler = Schedulers.trampoline()

    fun newThread(): Scheduler = Schedulers.newThread()

    fun io(): Scheduler = Schedulers.io()
}