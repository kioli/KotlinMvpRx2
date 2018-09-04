package kioli.rx

import org.mockito.Mockito

fun <T> anything(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T