package kioli.quote.common.functional

sealed class Either<out L, out R> {

    /**
     * Returns `true` if this is a [Right], `false` otherwise.
     * Used only for performance instead of fold.
     */
    internal abstract val isRight: Boolean

    /**
     * Returns `true` if this is a [Left], `false` otherwise.
     * Used only for performance instead of fold.
     */
    internal abstract val isLeft: Boolean

    fun isLeft(): Boolean = isLeft

    fun isRight(): Boolean = isRight

    /**
     * The left side of the disjoint union, as opposed to the [Right] side.
     */
    @Suppress("DataClassPrivateConstructor")
    data class Left<out A, out B> @PublishedApi internal constructor(val a: A) : Either<A, B>() {
        override val isLeft
            get() = true
        override val isRight
            get() = false

        companion object {
            inline operator fun <A> invoke(a: A): Either<A, Nothing> = Left(a)
        }
    }

    /**
     * The right side of the disjoint union, as opposed to the [Left] side.
     */
    @Suppress("DataClassPrivateConstructor")
    data class Right<out A, out B> @PublishedApi internal constructor(val b: B) : Either<A, B>() {
        override val isLeft
            get() = false
        override val isRight
            get() = true

        companion object {
            inline operator fun <B> invoke(b: B): Either<Nothing, B> = Right(b)
        }
    }

    inline fun <C> fold(crossinline ifLeft: (L) -> C, crossinline ifRight: (R) -> C): C = when (this) {
        is Right<L, R> -> ifRight(b)
        is Left<L, R> -> ifLeft(a)
    }

    /**
     * The given function is applied if this is a `Right`.
     *
     * Example:
     * ```
     * Right(12).map { "flower" } // Result: Right("flower")
     * Left(12).map { "flower" }  // Result: Left(12)
     * ```
     */
    inline fun <C> map(crossinline f: (R) -> C): Either<L, C> = fold({ Left(it) }, { Right(f(it)) })
}


fun <A> A.left(): Either<A, Nothing> = Either.Left(this)

fun <A> A.right(): Either<Nothing, A> = Either.Right(this)