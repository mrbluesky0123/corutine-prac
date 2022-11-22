package book.ch6

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * 채널의 이해
 * 채널은 동시성 코드간에 서로 안전한 통신을 할 수 있도록 해주는 도구
 */

/**
 * 채널 유형과 배압
 * Channel의 send()는 일시중단 함수임
 * -> 실제로 데이터를 수신하는 누군가가 있을 때까지 전송하는 코드를 일시 중단 하고 싶을 수도 있기 때문
 * 이를 흔히 배압(backpressure)이라고 하며 리시버가 실제로 처리할 수 있는 것보다 채널이 넘치지 않도록 도와줌
 */

/**
 * 언버퍼드 채널
 * 버퍼가 없는 채널을 의마. 본 채널은 버퍼가 전혀 없어서 send()를 호출하면 리시버가 receive()를 호출할 때까지 일시중단됨
 */

//fun main(args: Array<String>) = runBlocking {
////    val channel = Channel<Int>(0)
//    val time = measureTimeMillis {
//        val channel = Channel<Int>(0)
//        val sender = GlobalScope.launch {
//            repeat(10) {
//                channel.send(it)
//                println("Sent $it")
//            }
//        }
//        channel.receive()
//        channel.receive()
//    }
//    println("Took ${time}ms")
//}


/**
 * 버퍼드 채널
 * 채널 내 요소의 수가 버퍼의 크기와 같을 때마다 송신자의 실행을 중지
 * 버퍼의 크기에 따라 몇 가지 종류의 버퍼드 채널이 존재
 */

/**
 * LinkedListChannel
 * 중단 없이 무한의 요소를 전송할 수 있는 채널
 */

//fun main(args: Array<String>): Unit = runBlocking {
//    val timeMeasured = measureTimeMillis {
//        val channel = Channel<Int>(Channel.UNLIMITED)   // 송신자를 절대로 일시중지 하지 않음
//        val sender = GlobalScope.launch {
//            repeat(5) {
//                println("Sending $it")
//                channel.send(it)
//            }
//        }
//        delay(500)
//    }
//    println("Took ${timeMeasured}ms")
//}

/**
 * ArrayChannel
 * 이 채널 유형은 버퍼 크기를 0부터 최대 int.MAX_VALUE -1 까지 가지며,
 * 가지고 있는 요소의 양이 버퍼 크기에 이르면 송신자를 일시 중단한다.
 */

//fun main(args: Array<String>): Unit = runBlocking {
//    val timeMeasured = measureTimeMillis {
//        val channel = Channel<Int>(4)
//        val sender = GlobalScope.launch {
//            repeat(10) {
//                channel.send(it)
//                println("Sending $it")
//            }
//        }
//        delay(500)
//        println("Taking two")
//        channel.receive()
//        channel.receive()
//        delay(500)
//    }
//    println("Took ${timeMeasured}ms")
//}

/**
 * ConflatedChannel
 * 이 유형의 버퍼드 채널은 내보낸 요소가 유실되어도 괜찮다는 생각이 깔려있다.
 * 하나의 요소의 버퍼만 갖고있고, 새로운 요소가 보내질 때마다 이전 요소는 유실됨
 * 또한 송신자가 절대로 일시중지 되지 않는다는 것을 의미
 */
//fun main(args: Array<String>): Unit = runBlocking {
//    val timeMeasured = measureTimeMillis {
//        val channel = Channel<Int>(Channel.CONFLATED)
//        val sender = GlobalScope.launch {
//            repeat(10) {
//                channel.send(it)
//                println("Sent $it")
//            }
//        }
//        delay(500)
//        val received = channel.receive()
//        println("Received: $received")
//    }
//    println("Took ${timeMeasured}ms")
//}