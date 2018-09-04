package kioli.rx.section.repository

import android.util.Log
import kioli.rx.common.error.Error
import kioli.rx.common.functional.Either
import kioli.rx.common.functional.right
import kioli.rx.common.repository.CachePolicy
import kioli.rx.common.repository.CachePolicy.*
import kioli.rx.common.repository.LocalDataSource
import kioli.rx.common.repository.NetworkDataSource
import kioli.rx.common.entity.Quote

internal class QuoteRepository(
        private val localDataSource: LocalDataSource,
        private val networkDataSource: NetworkDataSource) {

    fun getQuote(policy: CachePolicy): Either<Error, Quote> {
        return when (policy) {
            NetworkFirst -> networkDataSource.getQuote().fold(
                    {
                        Log.w("QuoteKioli", "error loading quote from network: $it")
                        localDataSource.getQuote()
                    },
                    {
                        localDataSource.saveQuote(it)
                        it.right()
                    })
            LocalFirst -> localDataSource.getQuote().fold(
                    {
                        Log.w("QuoteKioli", "error loading quote from local cache: $it")
                        networkDataSource.getQuote().map {
                            localDataSource.saveQuote(it)
                            it
                        }
                    },
                    { it.right() })
            LocalOnly -> localDataSource.getQuote()
            NetworkOnly -> networkDataSource.getQuote().map {
                localDataSource.saveQuote(it)
                it
            }
        }
    }
}