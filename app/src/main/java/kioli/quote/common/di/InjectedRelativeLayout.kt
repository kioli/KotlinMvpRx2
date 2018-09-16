package kioli.quote.common.di

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

open class InjectedRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : RelativeLayout(context, attrs), KodeinAware {

    // closestKodein() automatically fetches activity Kodein scope.
    private val activityKodein by closestKodein()

    override val kodein = Kodein.lazy {
        extend(activityKodein)
        import(viewModule())
    }

    open fun viewModule() = Kodein.Module {}

}