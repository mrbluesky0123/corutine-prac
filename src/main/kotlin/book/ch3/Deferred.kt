package book.ch3

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Deferred 연기된
 * 결과를 갖는 비동기 작업을 수행하기 위하여 Job을 확장
 * (Future in JAVA, Promise in Javascript)
 * 기본적인 컨셉은 연산이 객체를 반환할 것이며, 객체는 비동기 작업이 완료될 대까지 비어있다.
 */

//fun main(args: Array<String>): Unit = runBlocking {
//    val headlineTask = GlobalScope.async {
//        println(getHeadlines())
//    }
//    headlineTask.await()
//}
//
//fun getHeadlines(): String = "HEADLINES!"


/**
 * 예외처리: 순수한 job과 달리 deferred는 처리되지 않은 예외를 자동으로 전파하지 않는다.
 * 디퍼디의 결과를 대기할 것으로 예상하기 때문에 이런 방식을 사용하였으며, 실행이 성공했는지 확인하는 것은 사용자의 몫이다.
 */

//fun main(args:Array<String>) = runBlocking<Unit> {
//    val deferred = GlobalScope.async {
////        TODO("Not implemented yet!")
//        println("ddddd")
//    }
//
////    delay(2000)
////    deferred.await() // 아무짓도 안하고 이렇게 하면 예외가 발생
//    try {
//        deferred.await()
//    } catch (throwable: Throwable) {
//        println("Deferred cancelled due to ${throwable.message}")
//    }
//}


/**
 * Job은 특정 상태에 도달하면 이전 상태로는 되돌아 가지 않음
 */

//fun main(args: Array<String>) = runBlocking {
//
//   val time = measureTimeMillis {
//       val job = GlobalScope.launch {
//           delay(2000)
//       }
//       // Wait for it to complete once
//       job.join()
//
//       // Restart the job
//       job.start()
//       job.join()
//   }
//    println("$time ms")
//
//}