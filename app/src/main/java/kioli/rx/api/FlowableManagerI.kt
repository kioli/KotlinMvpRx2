package kioli.rx.api

import android.support.v4.util.LruCache
import io.reactivex.Flowable

internal interface FlowableManagerI {

    val cache: LruCache<String, Flowable<*>>

    fun cacheFlowable(symbol: String, flowable: Flowable<*>, forceNew: Boolean): Flowable<*>
}