package book.ch3

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 예외처리
 */
//fun main(args: Array<String>) = runBlocking {
//    val handler = CoroutineExceptionHandler { context, throwable ->
//        println("Exception captured in $context")
//        println("Message: ${throwable.message}")
//    }
//    GlobalScope.launch(handler) {
//        TODO("Not implemented yet!")
//    }
//    delay(400)
//}

/**
 * Non-cancellable
 * 위와 같이 코루틴 실행이 취소되면 코루틴 내부에 CancellationException 유형의 예외가 발생하고 코루틴 종료
 *
 */
//fun main(args: Array<String>) = runBlocking {
//    val duration = measureTimeMillis {
//        val job = launch {
//            try {
//                while(isActive) {
//                    delay(500)
//                    println("Still running..")
//                }
//            } finally {
//                println("cancelled, will end now..")
//            }
//        }
//        delay(1200)
//        job.cancelAndJoin()
//
//    }
//    println("Took $duration ms")
//    delay(400)
//
//    // Still running..
//    // Still running..
//    // cancelled, will end now..
//    // Took 1212 ms
//}

/**
 * finally 블록에서 실제 지연은 일어나지 않고 코루틴은 일시 중단된 후 바로 종료됨
 * 취소중인 코루틴은 일시중단 될 수 없도록 설계되었기 때문
 */
//fun main(args: Array<String>) = runBlocking {
//    val duration = measureTimeMillis {
//        val job = launch {
//            try {
//                while(isActive) {
//                    delay(500)
//                    println("Still running..")
//                }
//            } finally {
//                println("cancelled, will end now..")
//                delay(5000)
//                println("delay completed, bye bye~")
//            }
//        }
//        delay(1200)
//        job.cancelAndJoin()
//
//    }
//    println("Took $duration ms")
//    delay(400)
//
//    // Still running..
//    // Still running..
//    // cancelled, will end now..
//    // Took 1212 ms
//}

/**
 * 취소중인 코루틴에 일시중지가 필요할 경우 NonCancellable 컨텍스트를 사용해야 함
 */
//fun main(args: Array<String>) = runBlocking {
//    val duration = measureTimeMillis {
//        val job = launch {
//            try {
//                while(isActive) {
//                    delay(500)
//                    println("Still running..")
//                }
//            } finally {
//                withContext(NonCancellable) {
//                    println("cancelled, will end now..")
//                    delay(5000)
//                    println("delay completed, bye bye~")
//                }
//            }
//        }
//        delay(1200)
//        job.cancelAndJoin()
//
//    }
//    println("Took $duration ms")
//    delay(400)
//
//    // Still running..
//    // Still running..
//    // cancelled, will end now..
//    // delay completed, bye bye~
//    // Took 6231 ms
//}