package book.ch6

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
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

fun main(args: Array<String>) = runBlocking {
//    val channel = Channel<Int>(0)
    val time = measureTimeMillis {
        val channel = Channel<Int>(0)
        val sender = GlobalScope.launch {
            repeat(10) {
                channel.send(it)
                println("Sent $it")
            }
        }
        channel.receive()
        channel.receive()
    }
    println("Took ${time}ms")
}