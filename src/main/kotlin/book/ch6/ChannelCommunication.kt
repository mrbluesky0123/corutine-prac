package book.ch6

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Channel<T>의 동작은 SendChannel<T>와 ReceiveChannel<T>의 두 개의 인터페이스로 이루어져 있음
 */

/**
 * SendChannel
 * 채널을 통해 요소를 보내기 위한 몇 개의 함수와 무언가를 보낼 수 있는지 검증하기 위한 함수들을 정의
 */

//fun main(args: Array<String>): Unit = runBlocking {
//    val timeMeasured = measureTimeMillis {
//        val channel = Channel<Int>()
//        val sender = GlobalScope.launch {
//            println("channel.isClosedForSend: ${channel.isClosedForSend}")
//            repeat(10) {
//                channel.send(it)
//                println("Sent $it")
//            }
//            println("channel.isClosedForSend: ${channel.isClosedForSend}")
//            channel.close()
//            println("channel.close()")
//            println("channel.isClosedForSend: ${channel.isClosedForSend}")
//        }
//        delay(500)
//
//        repeat(10) {
//            val received = channel.receive()
//            println("Received: $received")
//        }
//
//    }
//    println("Took ${timeMeasured}ms")
//}

/**
 * ReceiveChannel
 * 예외를 피하거나 일반적인 코드 흐름의 개선을 위해 ReceiveChannel에서 정보를 읽기 전 몇 가지 확인할 사항이 있음
 */
fun main(args: Array<String>): Unit = runBlocking {
    val timeMeasured = measureTimeMillis {
        val channel = Channel<Int>()
        val sender = GlobalScope.launch {
            repeat(10) {
                println("Sent $it")
                channel.send(it)
            }
        }
        delay(500)
        println("channel.isClosedForReceive: ${channel.isClosedForReceive}")
        repeat(10) {
            val received = channel.receive()
            println("Received: $received")
        }
        channel.close()
        println("channel.isClosedForReceive: ${channel.isClosedForReceive}")
    }
    println("Took ${timeMeasured}ms")
}

/**
 * Summarize
 * - UnbufferedChannel: 채널의 각 요소를 위한 receive()가 호출될 때까지 send()를 일시 중단
 * - ConflatedChannel: 전송된 마지막 요소만 유지한다.
 * - LinkedListChannel: 무제한 요소를 갖거나 최소한의 가용한 메모리에서 가능한 많은 요소를 가질 수 있어
 *   send()가 호출되더라도 중단되지 않음
 * - ArrayChannel: 요소의 양이 버퍼의 크기에 도달하면 send()에 의해 일시중단
 */