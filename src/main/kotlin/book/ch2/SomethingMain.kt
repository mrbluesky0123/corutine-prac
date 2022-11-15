package book.ch2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
//    val netDispatcher = newSingleThreadContext(name = "ServiceCall")
    val task = GlobalScope.async {
        doSomething()
    }
    task.join()
//    task.await()
    if(task.isCancelled) {
//        val exception = task.getCancellationException()
//        println("Error with message: ${exception.message}")
    } else {
        println("Success")
    }
}

fun doSomething() {
    throw UnsupportedOperationException("I can't do this.")
}