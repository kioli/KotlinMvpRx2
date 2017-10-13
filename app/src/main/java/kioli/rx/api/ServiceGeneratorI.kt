package kioli.rx.api

internal interface ServiceGeneratorI {

    fun <T> getService(c: Class<T>): T

}