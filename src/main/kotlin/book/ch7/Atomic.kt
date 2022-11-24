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
//private var counter = 0
//private val context = newSingleThreadContext("counter")
//fun getCounter() = counter
//val actorCounter = GlobalScope.actor<Void?> {
//    for(msg in channel) {
//        counter++
//    }
//}
//fun asyncIncrement(by: Int, name: String) = GlobalScope.async {
//    for(i in 0 until by) {
//        println("${name}|${Thread.currentThread().name} : $i")
//        actorCounter.send(null)
//    }
//}
//fun main(args: Array<String>) = runBlocking {
//    val workerA = asyncIncrement(200, "A")
//    val workerB = asyncIncrement(1500, "B")
//
//    workerA.await()
//    workerB.await()
//
//    println("counter [${getCounter()}]")
//}

/**
 * 액터를 사용한 기능 확장
 * 액터를 사용 하면 채널이 전체 코루틴을 원자적으로 유지하면서 더 높은 유연성을 허용할 수 있다.
 * 액터를 사용하여 카운터를 늘리거나 줄일 수 있도록 만들어보자
 */

//var counter = 0
//enum class Action {
//    INCREASE, DECREASE
//}
//
//val context = newFixedThreadPoolContext(20, "threadPool")
//var actorCounter = GlobalScope.actor<Action>(context) {
//    for(msg in channel) {
//        when(msg) {
//            Action.DECREASE -> {
//                println("[${Thread.currentThread().name}] $msg")
//                counter--
//            }
//            Action.INCREASE -> {
//                println("[${Thread.currentThread().name}] $msg")
//                counter++
//            }
//        }
//    }
//}
//
//fun asyncDecrement(by: Int) = GlobalScope.async {
//    for(i in 0 until by) {
//        actorCounter.send(Action.DECREASE)
////        counter--
//    }
//}
//
//fun asyncIncrement(by: Int) = GlobalScope.async {
//    for(i in 0 until by) {
//        actorCounter.send(Action.INCREASE)
////        counter++
//    }
//}
//
//fun main(args: Array<String>) = runBlocking {
//    /*
//     * 메인 스레드는 CommonPool을 사용하여 카운터를 동시에 증가 및 감소시키는 코루틴을 생성하며,
//     * 코루틴은 액션과 함께 메시지를 액터에 보내서 이를 수행한다.
//     * 액터는 특정 스레드에 한정된 코루틴이기 때문에 모든 수정은 원자적
//     */
//    val workerA = asyncIncrement(2000)
//    val workerB = asyncIncrement(100)
//    val workerC = asyncDecrement(1000)
//
//    workerA.await()
//    workerB.await()
//    workerC.await()
//    println("counter $counter")
//
//}

/**
 * 버퍼드 액터
 * 액터는 다른 송신채널과 동일하게 버퍼링될 수 있음
 * 기본적으로 모든 액터는 버퍼링 되지 않음. 메시지가 수신될 때까지 발신자를 send()에서 일시중단함
 */