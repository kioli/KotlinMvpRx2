package kioli.rx.section

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kioli.rx.R
import kioli.rx.entity.Quote
import kotlinx.android.synthetic.main.view_quote.view.*

internal class QuoteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : RelativeLayout(context, attrs), QuoteContract.View {

    override lateinit var presenter: QuoteContract.Presenter

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_quote, this, true)
        swipe_to_refresh.setOnRefreshListener {
            swipe_to_refresh.isRefreshing = false
            swipe_to_refresh.isEnabled = false
            presenter.getQuote(true)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.start()
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun returnResultQuote(quote: Quote?) {
        quote?.text.let { text.text = it }
        swipe_to_refresh.isEnabled = true
    }
}
