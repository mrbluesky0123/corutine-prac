package book.ch3

import kotlinx.coroutines.*

/**
 * <코루틴 컨텍스트>
 * 코루틴은 항상 컨텍스트 안에서 실행된다.
 * 컨텍스트는 코루틴이 어떻게 실행되고 동작해야 하는지 정의할 수 있게 해주는 요소들의 그룹이다.
 */

/**
 * <디스패처>
 * Dispatcher는 코루틴이 실행될 스레드를 결정한다. -> 시작될 곳, 중단 후 재개될 곳 모두 포함
 */

/**
 * CommonPool
 * CPU 바운드 작업을 위해 프레임웍에서 자동으로 생성
 * 스레드풀의 최대 크기는 시스템의 코어수에서 1을 뺀 값
 */
//fun main(args: Array<String>): Unit = runBlocking {
//
//    GlobalScope.launch {
//        println("##### ${Thread.currentThread().name}")
//        // ##### DefaultDispatcher-worker-1
//    }
//
//}

/**
 * Unconfined
 * 펏 번째 중단 지점에 도달할 때까지 현재 스레드에 있는 코루틴을 실행
 * 일시 중지된 후, 일시 중단 연산에서 사용된 기존스레드에서 다시 시작됨
 */

//fun main(args: Array<String>): Unit = runBlocking {
//
//    GlobalScope.launch(Dispatchers.Unconfined) {
//        println("##### Start: ${Thread.currentThread().name}")
//        delay(500)
//        println("##### Resume: ${Thread.currentThread().name}")
//        // ##### Start: main
//        // ##### Resume: kotlinx.coroutines.DefaultExecutor
//        // 일시중단 연산이 실행된 default executor 스레드로 이동
//    }.join()
//
//}


/**
 * 단일 스레드는 항상 코루틴이 특정 스레드 안에서 실행 되는 것을 보장한다.
 */

//fun main(args: Array<String>): Unit = runBlocking {
//
//    val dispatcher = newSingleThreadContext("myThread")
//
//    GlobalScope.launch(dispatcher) {
//        println("##### Start: ${Thread.currentThread().name}")
//        delay(500)
//        println("##### Resume: ${Thread.currentThread().name}")
//        // ##### Start: myThread
//        // ##### Resume: myThread
//        // 일시중단 연산이 실행된 default executor 스레드로 이동
//    }.join()
//}

/**
 * 스레드풀은 풀을 갖고 있으며 해당 풀에서 가용한 스레드에서 코루틴을 시작 및 재개
 */
//fun main(args: Array<String>): Unit = runBlocking {
//
//    val dispatcher = newFixedThreadPoolContext(4, "myPool")
//
//    GlobalScope.launch(dispatcher) {
//        println("##### Start: ${Thread.currentThread().name}")
//        delay(500)
//        println("##### Resume: ${Thread.currentThread().name}")
//        // ##### Start: myPool-1
//        // ##### Resume: myPool-2
//        // 일시중단 연산이 실행된 default executor 스레드로 이동
//    }.join()
//}

/**
 * 컨텍스트 추가정보
 * 컨텍스트 조합
 * 단일 디스패처와 예외 처리를 결합
 */
//fun main(args: Array<String>) = runBlocking {
//    val dispatcher = newSingleThreadContext("myDispatcher")
//    val handler = CoroutineExceptionHandler {_, throwable ->
//        println("Error Captured")
//        println("Message: ${throwable.message}")
//    }
//
//    GlobalScope.launch(dispatcher + handler) {
//        println("Running in ${Thread.currentThread().name}")
//        TODO("Not implement yet!")
//    }.join()
//}

/**
 * 컨텍스트 분리
 * 결합된 컨텍스트에서 컨텍스트 요소를 제거할 수도 있음
 */
//fun main(args: Array<String>) = runBlocking {
//    val dispatcher = newSingleThreadContext("myDispatcher")
//    val handler = CoroutineExceptionHandler {_, throwable ->
//        println("Error Captured")
//        println("Message: ${throwable.message}")
//    }
//    val context = dispatcher + handler
//    val tmpCtx = context.minusKey(dispatcher.key)
//
//    GlobalScope.launch(tmpCtx) {
//        println("Running in ${Thread.currentThread().name}")
//        TODO("Not implement yet!")
//    }.join()
//}

/**
 * 임시 컨텍스트 스위치
 * 이미 일시중단 상태에 있을 때 withContext()를 사용해 코드 블록에 대한 컨텍스트를 변경할 수 있다.
 * withContext()는 코드 블록 실행을 위해 주어진 컨텍스트를 사용할 일시중단 함수임
 */
fun main(args: Array<String>) = runBlocking {
    val dispatcher = newSingleThreadContext("myDispatcher")
    val handler = CoroutineExceptionHandler {_, throwable ->
        println("Error Captured")
        println("Message: ${throwable.message}")
    }
    val name = GlobalScope.async(dispatcher) {
        "Susan Calvin"
    }.await()

    // 위의 코드는 아래 코드와 같음

    val name2 = withContext(dispatcher) {
        "Susan Calvin"
    }
    println("User: $name")
}