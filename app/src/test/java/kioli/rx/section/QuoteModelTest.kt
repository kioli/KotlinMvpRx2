package kioli.rx.section

import io.reactivex.Flowable
import kioli.rx.api.QuoteApi
import kioli.rx.api.ServiceGeneratorI
import kioli.rx.entity.Quote
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class QuoteModelTest {

    @Test
    fun `fetching quote returns flowable`() {
        // 1. Prepare
        val mockServiceGeneratorWrapper = mock(ServiceGeneratorI::class.java)
        val mockService = mock(QuoteApi::class.java)
        doReturn(mockService).`when`(mockServiceGeneratorWrapper).getService(QuoteApi::class.java)
        val mockFlowable = mock(Flowable::class.java)
        doReturn(mockFlowable).`when`(mockService).loadQuote()

        // 2. Execute
        val result = QuoteModel(mockServiceGeneratorWrapper).fetchQuote()

        // 3. Verify
        verify(mockServiceGeneratorWrapper, times(1)).getService(QuoteApi::class.java)
        verify(mockService, times(1)).loadQuote()
        assertEquals(mockFlowable, result)
    }
}
