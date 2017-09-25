package kioli.rx

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kioli.rx.QuoteContract.QuoteView
import kioli.rx.entity.Quote
import kotlinx.android.synthetic.main.view_quote.view.*

internal class QuoteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : RelativeLayout(context, attrs), QuoteView {

    private var presenter: QuoteContract.QuotePresenter = QuotePresenter(QuoteModel())

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_quote, this, true)
        swipe_to_refresh.setOnRefreshListener {
            swipe_to_refresh.isRefreshing = false
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

    override fun showQuote(quote: Quote) {
        text.text = quote.text
    }
}
