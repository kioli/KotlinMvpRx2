package kioli.rx.mvp

import android.support.v4.util.LruCache
import io.reactivex.Flowable

object FlowableManager {

    private val cache = LruCache<String, Flowable<*>>(50)

    fun cacheFlowable(symbol: String, flowable: Flowable<*>): Flowable<*> {
        var cachedFlowable = cache.get(symbol)
        cachedFlowable?.let {
            return it
        }
        /**
         * If we don't call the cache() method on the observable before we add it to the LruCache
         * the instance of the observable will be saved but the response/request will not
         */
        cachedFlowable = flowable.cache()
        updateCache(symbol, cachedFlowable)
        return cachedFlowable
    }

    private fun updateCache(stockSymbol: String, flowable: Flowable<*>) {
        cache.put(stockSymbol, flowable)
    }
}