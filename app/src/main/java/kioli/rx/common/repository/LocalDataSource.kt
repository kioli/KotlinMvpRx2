package kioli.rx.common.repository

import kioli.rx.common.functional.Either
import kioli.rx.common.error.Error
import kioli.rx.common.entity.Quote

/**
 * The data source contracts are part of the domain layer.
 * Their implementations belong to the data layer.
 */
internal interface LocalDataSource {

    fun getQuote(): Either<Error, Quote>

    fun saveQuote(quote: Quote)
}
