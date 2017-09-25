package kioli.rx.entity

import com.squareup.moshi.Json

internal data class Quote(@Json(name = "quoteAuthor") val author: String = "",
                          @Json(name = "quoteText") val text: String = "")