package officialreference

import kotlinx.coroutines.*

/**
 * https://kotlinlang.org/docs/cancellation-and-timeouts.html#cancellation-is-cooperative
 */

var acquired  = 0

class Resource {
    init {
        acquired++
    }
    fun close() {
        acquired--
    }
}

fun main(args: Array<String>) = runBlocking {
    /* Cancelling coroutine execution */
//    val job = launch {
//        repeat(1000) {
//            println("job: I'm sleeping $it ...")
//            delay(500L)
//        }
//        println("!!!!")
//    }


    /* Cancellation is cooperative */
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//        while (i < 5) { // computation loop. just wastes CPU
//            // print a message twice a second
//            if(System.currentTimeMillis() >= nextPrintTime) {
//                println("job: I'm sleeping ${i++}")
//                nextPrintTime += 500L
//            }
//
//        }
//    }

//    val job = launch(Dispatchers.Default) {
//        repeat(5) {
//            try {
//                println("job: I am sleeping $it ...")
//                delay(500)
//            } catch (e: Exception) {
//                println(e)
//            }
//        }
//    }

    /* Making computation code cancellableMaking computation code cancellable */
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//        while (isActive) {
//            if(System.currentTimeMillis() >= nextPrintTime) {
//                println("job: Im sleeping.... ${i++}")
//                nextPrintTime += 500L
//            }
//        }
//    }

    /* Closing resources with finally */
//    val job = launch {
//        try {
//            repeat(1000) {
//                println("job: I'm sleeping")
//                delay(500L)
//            }
//        } finally {
//            println("job: I'm running finally")
//        }
//    }

    /* Run non-cancellable block */
//    val job = launch {
//        try {
//            repeat(1000) {
//                println("job: I'm sleeping $it")
//                delay(500L)
//            }
//        } finally {
//            withContext(NonCancellable) {
//                println("job: I'm running finally")
//                delay(1000L)
//                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
//            }
//        }
//    }

    /* Timeout */
//    withTimeout(1300L) {
//        repeat(1000) {
//            println("I'm sleeping $it")
//            delay(500L)
//        }
//    }
//    val result = withTimeoutOrNull(1300L) {
//        repeat(1000) {
//            println("I'm sleeping $it")
//            delay(500L)
//        }
//        "Done"
//    }
//    println("Result is $result")


//    delay(1300L)
//    println("main: I'm tired of waiting!!")
//    // job.cancel(\/ job.join()
//    job.cancelAndJoin()
//    println("main: Now I can quit!")

    /* Asynchronous timeout and resources */
    runBlocking {
        repeat(100_000) {
            launch {
                val resource = withTimeout(60) {
                    delay(50)
                    Resource()
                }
                resource.close()
            }
        }
        println(acquired)
    }


}