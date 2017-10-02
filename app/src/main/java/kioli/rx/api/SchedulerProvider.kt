package kioli.rx.api

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Utility class to acquire different schedulers used to perform Rx operations
 */
internal object SchedulerProvider {

    internal class SchedulerProviderWrapper {
        fun ui(): Scheduler = SchedulerProvider.ui()

        fun computation(): Scheduler = SchedulerProvider.computation()

        fun trampoline(): Scheduler = SchedulerProvider.trampoline()

        fun newThread(): Scheduler = SchedulerProvider.newThread()

        fun io(): Scheduler = SchedulerProvider.io()

        fun <T> getService(c: Class<T>): T {
            return ServiceGenerator.getService(c)
        }
    }

    fun ui(): Scheduler = AndroidSchedulers.mainThread()

    fun computation(): Scheduler = Schedulers.computation()

    fun trampoline(): Scheduler = Schedulers.trampoline()

    fun newThread(): Scheduler = Schedulers.newThread()

    fun io(): Scheduler = Schedulers.io()
}