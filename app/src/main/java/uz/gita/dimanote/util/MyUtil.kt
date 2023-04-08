package uz.gita.dimanote.util

fun <T> T. myApply(block: T.() -> Unit) {
    block(this)
}
