package kioli.rx

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kioli.rx.entity.Quote
import kotlinx.android.synthetic.main.activity_main.*

internal class QuoteActivity : AppCompatActivity(), QuoteContract.QuoteView {

    private var presenter: QuoteContract.QuotePresenter = QuotePresenter(QuoteModel())

    /**
     * Attach the view on creation so it does not happen every time
     * the activity is paused or minimised and onResume is called
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        presenter.callData()
    }

    override fun showQuote(quote: Quote) {
        text.text = quote.text
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    /**
     * Detach the view to avoid memory leaks and dispose of all subscriptions
     * to have them refreshed when the new Activity spawns
     *
     * Note: When the activity is paused it keeps receiving updates
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
