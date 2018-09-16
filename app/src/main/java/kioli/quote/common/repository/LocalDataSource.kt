package kioli.quote.common.repository

import kioli.quote.common.functional.Either
import kioli.quote.common.error.Error
import kioli.quote.common.entity.Quote

/**
 * The data source contracts are part of the domain layer.
 * Their implementations belong to the data layer.
 */
internal interface LocalDataSource {

    fun getQuote(): Either<Error, Quote>

    fun saveQuote(quote: Quote)
}
