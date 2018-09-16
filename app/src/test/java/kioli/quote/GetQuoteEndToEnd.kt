package kioli.quote

import android.content.Context
import kioli.quote.common.di.appModule
import kioli.quote.common.di.quoteActivityModule
import kioli.quote.common.entity.Quote
import kioli.quote.common.functional.left
import kioli.quote.common.functional.right
import kioli.quote.common.interactor.Invoker
import kioli.quote.common.repository.LocalDataSource
import kioli.quote.common.repository.NetworkDataSource
import kioli.quote.section.mvp.QuoteContract
import kioli.quote.section.repository.QuoteLocalDataSource
import kioli.quote.section.repository.QuoteNetworkDataSource
import kioli.quote.section.repository.QuoteNotFound
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * End to end test mocking out view implementation, threading and data source implementations.
 *
 * We are testing all the resting layers on integration by providing injection of all the parts in
 * between both edges by using Kodein.
 */
@RunWith(MockitoJUnitRunner::class)
internal class GetQuoteEndToEnd : KodeinAware {

    private val presenter: QuoteContract.Presenter by instance()
    private val quote: Quote = Quote(text = "mock text", author = "mock author")

    @Mock
    lateinit var context: Context
    @Mock
    lateinit var localDataSource: QuoteLocalDataSource
    @Mock
    lateinit var networkDataSource: QuoteNetworkDataSource
    @Mock
    lateinit var view: QuoteContract.View

    override val kodein = Kodein.lazy {
        // production modules
        import(appModule(context))
        import(quoteActivityModule())

        // overrides
        bind<Invoker>(overrides = true) with singleton { BlockingUseCaseInvoker() }
        bind<LocalDataSource>(overrides = true) with singleton { localDataSource }
        bind<NetworkDataSource>(overrides = true) with singleton { networkDataSource }
    }

    @Test
    fun `load quote from local cache`() {
        // 1. Prepare
        `when`(localDataSource.getQuote()).thenReturn(quote.right())
        presenter.attachView(view)

        // 2. Act
        presenter.getQuote(false)

        // 3. Verify
        verify(view).returnResultQuote(quote)
    }

    @Test
    fun `load quote from network when not available in local cache`() {
        // 1. Prepare
        `when`(localDataSource.getQuote()).thenReturn(QuoteNotFound().left())
        `when`(networkDataSource.getQuote()).thenReturn(quote.right())
        presenter.attachView(view)

        // 2. Act
        presenter.getQuote(false)

        // 3. Verify
        verify(view).returnResultQuote(quote)
    }

    @Test
    fun `load null when quote is not available neither from network nor in local cache`() {
        // 1. Prepare
        `when`(localDataSource.getQuote()).thenReturn(QuoteNotFound().left())
        `when`(networkDataSource.getQuote()).thenReturn(QuoteNotFound().left())
        presenter.attachView(view)

        // 2. Act
        presenter.getQuote(true)

        // 3. Verify
        verify(view).returnResultQuote(null)
    }
}
