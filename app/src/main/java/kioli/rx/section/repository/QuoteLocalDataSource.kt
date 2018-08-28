package kioli.rx.section.repository

import android.content.SharedPreferences
import android.util.Log
import kioli.rx.common.error.Error
import kioli.rx.common.functional.Either
import kioli.rx.common.functional.left
import kioli.rx.common.functional.right
import kioli.rx.common.repository.LocalDataSource
import kioli.rx.common.entity.Quote

internal class QuoteLocalDataSource(private val pref: SharedPreferences) : LocalDataSource {

    private val quoteKey = "quote key pref"

    override fun getQuote(): Either<Error, Quote> {
        pref.getString(quoteKey, null)?.let {
            return Quote(text = it).right()
        }
        Log.e("RxKioli", "Quote nt found in the local cache")
        return QuoteNotFound().left()
    }

    override fun saveQuote(quote: Quote) {
        pref.edit().putString(quoteKey, quote.text).apply()
    }
}
