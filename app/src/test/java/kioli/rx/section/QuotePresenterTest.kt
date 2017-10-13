package kioli.rx.section

import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import kioli.rx.api.FlowableManager
import kioli.rx.api.SchedulerProvider
import kioli.rx.entity.Quote
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class QuotePresenterTest {

    @Mock
    private lateinit var mockView: QuoteContract.QuoteView
    @Mock
    private lateinit var mockModel: QuoteContract.Model
    @Mock
    private lateinit var mockManager: FlowableManager.FlowableManagerWrapper
    @Mock
    private lateinit var mockSchedulerProvider: SchedulerProvider.SchedulerProviderWrapper

    @Test
    fun getQuote_returnsCorrectResult() {
        // 1. Prepare
        val quotePresenter = QuotePresenter(mockModel, mockManager, mockSchedulerProvider)
        quotePresenter.attachView(mockView)

        val stubQuote = Quote("Lao Tsu", "One fish is better than no fish at all")
        val mockFlowable = spy(Flowable.just(stubQuote))
        val mockDelayedFlowable = spy(mockFlowable)
        val mockSubscribedOnFlowable = spy(mockDelayedFlowable)
        val mockObservedOnFlowable = spy(mockSubscribedOnFlowable)
        val mockCachedFlowable = spy(mockObservedOnFlowable)

        val stubScheduler = TestScheduler()
        `when`(mockSchedulerProvider.newThread()).thenReturn(stubScheduler)
        `when`(mockSchedulerProvider.ui()).thenReturn(stubScheduler)

        `when`(mockModel.fetchQuote()).thenReturn(mockFlowable)
        `when`(mockFlowable.delay(2, TimeUnit.SECONDS)).thenReturn(mockDelayedFlowable)
        `when`(mockDelayedFlowable.subscribeOn(stubScheduler)).thenReturn(mockSubscribedOnFlowable)
        `when`(mockSubscribedOnFlowable.observeOn(stubScheduler)).thenReturn(mockObservedOnFlowable)
        `when`(mockManager.cacheFlowable(anyString(), kioli.rx.any<Flowable<*>>(), anyBoolean())).thenReturn(mockCachedFlowable)

        // 2. Execute
        quotePresenter.getQuote(false)

        // 3. Verify
        Mockito.verify(mockView, Mockito.times(1)).showLoading()
        Mockito.verify(mockView, Mockito.times(1)).hideLoading()
        Mockito.verify(mockView, Mockito.times(1)).returnResultQuote(stubQuote)
    }

    @Test
    fun getQuote_returnsErrorResult() {
        // 1. Prepare
        val quotePresenter = QuotePresenter(mockModel, mockManager, mockSchedulerProvider)
        quotePresenter.attachView(mockView)

        val mockFlowable = spy(Flowable.fromCallable(Callable {throw NullPointerException(":(")} as Callable<Quote>))
        val mockDelayedFlowable = spy(mockFlowable)
        val mockSubscribedOnFlowable = spy(mockDelayedFlowable)
        val mockObservedOnFlowable = spy(mockSubscribedOnFlowable)
        val mockCachedFlowable = spy(mockObservedOnFlowable)

        val stubScheduler = TestScheduler()
        `when`(mockSchedulerProvider.newThread()).thenReturn(stubScheduler)
        `when`(mockSchedulerProvider.ui()).thenReturn(stubScheduler)

        `when`(mockModel.fetchQuote()).thenReturn(mockFlowable)
        `when`(mockFlowable.delay(2, TimeUnit.SECONDS)).thenReturn(mockDelayedFlowable)
        `when`(mockDelayedFlowable.subscribeOn(stubScheduler)).thenReturn(mockSubscribedOnFlowable)
        `when`(mockSubscribedOnFlowable.observeOn(stubScheduler)).thenReturn(mockObservedOnFlowable)
        `when`(mockManager.cacheFlowable(anyString(), kioli.rx.any(), anyBoolean())).thenReturn(mockCachedFlowable)

        // 2. Execute
        quotePresenter.getQuote(false)

        // 3. Verify
        Mockito.verify(mockView, Mockito.times(1)).showLoading()
        Mockito.verify(mockView, Mockito.times(1)).hideLoading()
        Mockito.verify(mockView, Mockito.times(1)).returnResultQuote(null)
    }
}