package kioli.rx.section

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kioli.rx.R
import kioli.rx.entity.Quote
import kioli.rx.section.QuoteContract.QuoteView
import kotlinx.android.synthetic.main.view_quote.view.*

internal class QuoteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : RelativeLayout(context, attrs), QuoteView {

    constructor(context: Context, presenter: QuoteContract.QuotePresenter) : this(context) {
        this.presenter = presenter
    }

    private lateinit var presenter: QuoteContract.QuotePresenter

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
        presenter.attachView(this)
        presenter.getQuote(false)
    }

    /**
     * Detach the view to avoid memory leaks and dispose of all subscriptions
     * to have them refreshed when the new Activity spawns
     *
     * Note: When the activity is paused it keeps receiving updates
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
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
