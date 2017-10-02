package kioli.rx.section

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kioli.rx.api.FlowableManager
import kioli.rx.api.SchedulerProvider
import kioli.rx.api.ServiceGenerator
import kotlinx.android.synthetic.main.view_quote.view.*

internal class QuoteActivity : AppCompatActivity() {

    private val retainedQuote = "retained quote"
    private lateinit var view: QuoteView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = QuoteView(this, QuotePresenter(
                QuoteModel(ServiceGenerator.ServiceGeneratorWrapper()),
                FlowableManager.FlowableManagerWrapper(),
                SchedulerProvider.SchedulerProviderWrapper()))
        setContentView(view)
        savedInstanceState?.getString(retainedQuote)?.let { view.text.text = it }
    }

    /**
     * The retention of the quote is made for the edge ase when a rotation of the device
     * occurs while loading a new result (via refreshing the page) when a previous quote
     * was already showing on the page
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(retainedQuote, view.text.text.toString())
    }
}
