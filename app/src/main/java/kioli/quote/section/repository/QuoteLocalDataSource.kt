package kioli.quote.section.repository

import android.content.SharedPreferences
import android.util.Log
import kioli.quote.common.error.Error
import kioli.quote.common.functional.Either
import kioli.quote.common.functional.left
import kioli.quote.common.functional.right
import kioli.quote.common.repository.LocalDataSource
import kioli.quote.common.entity.Quote

internal class QuoteLocalDataSource(private val pref: SharedPreferences) : LocalDataSource {

    private val quoteKey = "quote key pref"

    override fun getQuote(): Either<Error, Quote> {
        pref.getString(quoteKey, null)?.let {
            return Quote(text = it).right()
        }
        Log.e("QuoteKioli", "Quote nt found in the local cache")
        return QuoteNotFound().left()
    }

    override fun saveQuote(quote: Quote) {
        pref.edit().putString(quoteKey, quote.text).apply()
    }
}
