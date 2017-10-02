package kioli.rx.api

import android.support.v4.util.LruCache
import io.reactivex.Flowable

internal object FlowableManager {

    internal class FlowableManagerWrapper {
        fun cacheFlowable(symbol: String, flowable: Flowable<*>, forceNew: Boolean): Flowable<*> {
            return FlowableManager.cacheFlowable(symbol, flowable, forceNew)
        }
    }

    private val cache = LruCache<String, Flowable<*>>(10)

    fun cacheFlowable(symbol: String, flowable: Flowable<*>, forceNew: Boolean): Flowable<*> {
        if (!forceNew) {
            cache.get(symbol)?.let { return it }
        }

        /**
         * If we don't call the cache() method on the observable before we add it to the LruCache
         * the instance of the observable will be saved but the response/request will not
         */
        val flowableToCache = flowable.cache()
        cache.put(symbol, flowableToCache)
        return flowableToCache
    }
}