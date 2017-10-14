package kioli.rx.section

import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import kioli.rx.anything
import kioli.rx.api.FlowableManagerI
import kioli.rx.api.SchedulerProviderI
import kioli.rx.entity.Quote
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Callable

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
class QuotePresenterTest {

    @Mock
    private lateinit var mockView: QuoteContract.View
    @Mock
    private lateinit var mockModel: QuoteContract.Model
    @Mock
    private lateinit var mockManager: FlowableManagerI
    @Mock
    private lateinit var mockSchedulerProvider: SchedulerProviderI

    @Test
    fun `get quote with successful flowable handles result`() {
        // 1. Prepare
        val quotePresenter = QuotePresenter(mockModel, mockView, mockManager, mockSchedulerProvider)
        val stubQuote = Quote("Lao Tsu", "One fish is better than no fish at all")
        val mockFlowable = spy(Flowable.just(stubQuote))
        val stubScheduler = TestScheduler()

        `when`(mockSchedulerProvider.newThread()).thenReturn(stubScheduler)
        `when`(mockSchedulerProvider.ui()).thenReturn(stubScheduler)
        `when`(mockModel.fetchQuote()).thenReturn(mockFlowable)
        `when`(mockManager.cacheFlowable(anyString(), anything(), anyBoolean())).thenReturn(mockFlowable)

        // 2. Execute
        quotePresenter.getQuote(false)

        // 3. Verify
        verify(mockView, Mockito.times(1)).showLoading()
        verify(mockView, Mockito.times(1)).hideLoading()
        verify(mockView, Mockito.times(1)).returnResultQuote(stubQuote)
    }

    @Test
    fun `get quote with failing flowable handles null`() {
        // 1. Prepare
        val quotePresenter = QuotePresenter(mockModel, mockView, mockManager, mockSchedulerProvider)
        val mockFlowable = spy(Flowable.fromCallable(Callable {throw NullPointerException(":(")} as Callable<Quote>))
        val stubScheduler = TestScheduler()

        `when`(mockSchedulerProvider.newThread()).thenReturn(stubScheduler)
        `when`(mockSchedulerProvider.ui()).thenReturn(stubScheduler)
        `when`(mockModel.fetchQuote()).thenReturn(mockFlowable)
        `when`(mockManager.cacheFlowable(anyString(), anything(), anyBoolean())).thenReturn(mockFlowable)

        // 2. Execute
        quotePresenter.getQuote(false)

        // 3. Verify
        verify(mockView, Mockito.times(1)).showLoading()
        verify(mockView, Mockito.times(1)).hideLoading()
        verify(mockView, Mockito.times(1)).returnResultQuote(null)
    }
}