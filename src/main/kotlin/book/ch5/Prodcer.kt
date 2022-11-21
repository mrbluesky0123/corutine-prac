package book.ch5

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

/**
 * 시퀀스와 이터레이터는 실행중에 일시 중단할 수 없다는 제한이 있음
 * -> 이상적으로는 다른 작업이 끝나기를 기다리는 동안 일시중단 할 수 있어야 해서 이 부분은 제약임
 */

/**
 * 위와 같은 한계를 극복하기 위하여 '프로듀서'를 사용
 *   - 프로듀서는 값이 생성된 후 일시 중단되며, 새로운 값이 요청될 때 다시 재개됨
 *   - 이는 일시중단 시퀀스 및 이터레이터와 유사함
 *   - 프로듀서는 특정 CoroutineContext로 생성할 수 있음
 *   - 전달되는 일시중단 람다의 본문은 언제든 일시중단 될 수 있음
 *   - 어느 시점에서든 일시중단 할 수 있으므로 프로듀서의 값은 일시 중단 연산에서만 수신될 수 있음
 *   - 채널을 사용해 작동하므로 데이터를 스트림처럼 생각할 수 있음 -> 요소를 수신하면 스트림에서 요소는 제거됨
 */

//fun main(args: Array<String>): Unit = runBlocking {
//    val context = newSingleThreadContext("myThread")
//    val producer: ReceiveChannel<Int> = GlobalScope.produce(context) {
//        for(i in 0..9) {
//            send(i)
//        }
//    }
//    // 모든 요소 읽기
////    producer.consumeEach {
////        println(it)
////    }
//
//    // 단일 요소 받기
//    println(producer.receive())
//    println(producer.receive())
//    producer.receive()
//    producer.receive()
//    println()
//
//    producer.consumeEach {
//        println("$it")
//    }
//}

/**
 * 프로듀서를 이용한 일시중단 프로듀서
 */
val context = newSingleThreadContext("myThread")
val fibonacci = GlobalScope.produce(context) {
    send(1L)
    var current = 1L
    var next = 1L
    for (x in 1..10) {
        send(next)
        val tmpNext = current + next
        current = next
        next = tmpNext
    }
}
fun main(args: Array<String>) = runBlocking {
    fibonacci.consumeEach {
        println(it)
    }
}


/**
 * Summarize
 *   - 시퀀스 -> stateless / 이터레이터 -> stateful
 *   - 시퀀스와 이터레이터는 실행 중에는 일시중단 할 수 없으므로 비중단 연산에서 호출할 수 있다.
 *   - 프로듀서는 실행 중 언제든지 중단할 수 있다.
 *   - 프로듀서는 실행 중 일시중단 될 수 있기 때문에 일시중단연산이나 코루틴에서만 호출할 수 있다.
 *   - 프로듀서는 채널을 사용해 데이터를 출력한다.
 */