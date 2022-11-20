package book.ch3

import kotlinx.coroutines.*

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
