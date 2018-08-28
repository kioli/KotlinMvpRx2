package kioli.rx.section.mvp

import android.os.Bundle
import android.view.View
import kioli.rx.R
import kioli.rx.common.di.InjectedActivity
import kioli.rx.common.di.quoteActivityModule
import kioli.rx.common.entity.Quote
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

internal class QuoteActivity : InjectedActivity(), QuoteContract.View {

    private val presenter by instance<QuoteContract.Presenter>()

    override fun activityModule() = Kodein.Module {
        import(quoteActivityModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipe_to_refresh.setOnRefreshListener {
            swipe_to_refresh.isRefreshing = false
            presenter.getQuote(true)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.getQuote(false)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun returnResultQuote(quote: Quote?) {
        quote?.text?.let { text.text = it }
    }
}
