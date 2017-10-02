package kioli.rx.section

import io.reactivex.Flowable
import kioli.rx.api.QuoteApi
import kioli.rx.api.ServiceGenerator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class QuoteModelTest {

    @Test
    fun fetchQuote_returnsFlowable() {
        // 1. Prepare
        val mockServiceGeneratorWrapper = mock(ServiceGenerator.ServiceGeneratorWrapper::class.java)
        val mockService = mock(QuoteApi::class.java)
        doReturn(mockService).`when`(mockServiceGeneratorWrapper).getService(QuoteApi::class.java)
        val mockFlowable = mock(Flowable::class.java)
        doReturn(mockFlowable).`when`(mockService).loadQuote()

        // 2. Execute
        val quoteModel = QuoteModel(mockServiceGeneratorWrapper)
        val result = quoteModel.fetchQuote()

        // 3. Verify
        verify(mockServiceGeneratorWrapper, times(1)).getService(QuoteApi::class.java)
        verify(mockService, times(1)).loadQuote()
        assertEquals(mockFlowable, result)
    }
}
