package kioli.quote.section.repository

import android.util.Log
import kioli.quote.common.error.Error
import kioli.quote.common.functional.Either
import kioli.quote.common.functional.right
import kioli.quote.common.repository.CachePolicy
import kioli.quote.common.repository.CachePolicy.*
import kioli.quote.common.repository.LocalDataSource
import kioli.quote.common.repository.NetworkDataSource
import kioli.quote.common.entity.Quote

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