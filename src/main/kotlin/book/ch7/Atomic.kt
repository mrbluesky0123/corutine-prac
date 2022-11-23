package book.ch7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor

/**
 * 원자성 위반: 정확한 동기화 없이 객체의 상태를 동시에 수정할 때 발생
 * 원자성의 의미: 소프트웨어 실행의 관점에서 연산이 단일하고 분할할 수 없을 때 이 연산을 원자적(atomic)이라 함
 * 코드 블록을 원자적으로 만들려면 블록 안에서 발생하는 어떤 메모리 엑세스도 동시에 실행되지 않도록 해야한다.
 */

// 문제의 코드: counter가 동기적으로 증가하지 않음
//var counter = 0
//fun asyncIncrement(by: Int, name: String) = GlobalScope.async {
//    GlobalScope.async {
//        for(i in 0 until by) {
//            counter++
//        }
//    }
//
//}
//fun main(args: Array<String>) = runBlocking {
//    val workerA = asyncIncrement(200, "A")
//    val workerB = asyncIncrement(1500, "B")
//
//    workerA.await()
//    workerB.await()
//
//    println("counter [${counter}]")
//}

/**
 * 스레드 한정
 * 공유 상태에 접근하는 모든 코루틴을 단일 스레드에서 실행되도록 한정하는 것
 */
//var counter = 0
//val context = newSingleThreadContext("counter")
//fun asyncIncrement(by: Int, name: String) = GlobalScope.async(context) {
//    for(i in 0 until by) {
//        counter++
//    }
//}
//fun main(args: Array<String>) = runBlocking {
//    val workerA = asyncIncrement(200, "A")
//    val workerB = asyncIncrement(1500, "B")
//
//    workerA.await()
//    workerB.await()
//
//    println("counter [${counter}]")
//}

/**
 * 액터
 * 스레드 한정은 앱의 다른 여러 부분에서 공유 상태를 수정해야 하거나, 원자블록에 더 높은 유연성을 원하는 시나리오의 경우
 * 이를 확장하는 방법이 필요하다.
 */
/**
 * 액터의 역할
 * 액터는 상태 엑세스를 단일 스레드로 한정하고, 다른 스레드가 채널로 상태 수정을 요청할 수 있다.
 */
private var counter = 0
private val context = newSingleThreadContext("counter")
fun getCounter() = counter
val actorCounter = GlobalScope.actor<Void?> {
    for(msg in channel) {
        counter++
    }
}
fun asyncIncrement(by: Int, name: String) = GlobalScope.async {
    for(i in 0 until by) {
        println("${name}|${Thread.currentThread().name} : $i")
        actorCounter.send(null)
    }
}
fun main(args: Array<String>) = runBlocking {
    val workerA = asyncIncrement(200, "A")
    val workerB = asyncIncrement(1500, "B")

    workerA.await()
    workerB.await()

    println("counter [${getCounter()}]")
}

/**
 * 액터를 사용한 기능 확장
 */