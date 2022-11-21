package book.ch3

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

/**
 * 컨텍스트 결합
 */
fun main(args: Array<String>) = runBlocking {
    val dispatcher = newSingleThreadContext("")
}