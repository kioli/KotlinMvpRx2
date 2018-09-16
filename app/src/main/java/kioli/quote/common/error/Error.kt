package kioli.quote.common.error

/**
 * Domain errors will fit with one of these types.
 */
sealed class Error {
    object NetworkConnection : Error()
    object ServerError : Error()

    abstract class FeatureError : Error()
}
