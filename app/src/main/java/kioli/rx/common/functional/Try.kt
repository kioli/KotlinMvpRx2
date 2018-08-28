package kioli.rx.common.functional

sealed class Try<out A> {

    companion object {
        inline operator fun <A> invoke(f: () -> A): Try<A> =
                try {
                    Success(f())
                } catch (e: Throwable) {
                    Failure(e)
                }
    }

    abstract fun isFailure(): Boolean
    abstract fun isSuccess(): Boolean
    abstract fun get(): A

    /**
     * The `Failure` type represents a computation that result in an exception.
     */
    data class Failure<out A>(val exception: Throwable) : Try<A>() {
        override fun isFailure(): Boolean = true

        override fun isSuccess(): Boolean = false

        override fun get(): A {
            throw exception
        }
    }

    /**
     * The `Success` type represents a computation that return a successfully computed value.
     */
    data class Success<out A>(val value: A) : Try<A>() {
        override fun isFailure(): Boolean = false

        override fun isSuccess(): Boolean = true

        override fun get(): A = value
    }

    /**
     * Applies `ifFailure` if this is a `Failure` or `ifSuccess` if this is a `Success`.
     */
    inline fun <B> fold(ifFailure: (Throwable) -> B, ifSuccess: (A) -> B): B =
            when (this) {
                is Failure -> ifFailure(exception)
                is Success -> ifSuccess(value)
            }
}