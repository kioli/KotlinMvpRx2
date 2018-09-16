package kioli.quote.common.entity

import com.google.gson.annotations.SerializedName

internal data class Quote(@SerializedName("quoteAuthor") val author: String = "",
                          @SerializedName("quoteText") val text: String = "")