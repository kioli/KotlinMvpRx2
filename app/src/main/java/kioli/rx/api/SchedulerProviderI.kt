package kioli.rx.api

import io.reactivex.Scheduler

internal interface SchedulerProviderI {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun trampoline(): Scheduler

    fun newThread(): Scheduler

    fun io(): Scheduler

    fun <T> getService(c: Class<T>): T
}