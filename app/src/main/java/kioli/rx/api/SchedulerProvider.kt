package kioli.rx.api

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Utility class to acquire different schedulers used to perform Rx operations
 */
internal object SchedulerProvider: SchedulerProviderI {

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun trampoline(): Scheduler = Schedulers.trampoline()

    override fun newThread(): Scheduler = Schedulers.newThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun <T> getService(c: Class<T>): T {
        return ServiceGenerator.getService(c)
    }
}