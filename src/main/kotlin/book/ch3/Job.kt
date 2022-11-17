package book.ch3

import kotlinx.coroutines.*

/**
 * 비동기 함수
 * - 결과가 없는 비동기 함수
 * - 결과를 반환하는 비동기 함수
 *
 */

// Job: fire-and-forget
//fun main(args: Array<String>) = runBlocking {
//    val job = GlobalScope.launch {
//        // Do backgroung task here
//    }
//    // OR
//    val job2 = Job()
//}


// Job - lazy start
//fun main(args: Array<String>) = runBlocking {
////    GlobalScope.launch {
//    GlobalScope.launch(start = CoroutineStart.LAZY) {
//        println("Hi~")
//    }
//    delay(10)
//}

/*
 * 활성 (Active)
 * 일반적으로 start()나 join()을 호출하여 실행하는데,
 * 둘의 차이점은 전자의 경우 완료될 때까지 기다리지 않고 job을 시작하는 반면
 * 후자는 job이 완료될 때까지 실행을 일시 중단
 */

//fun main(args: Array<String>) = runBlocking {
//    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//        delay(3000)
//    }
////    job.join() // job이 끝날 때까지 기다림
//    job.start()  // job이 끝날 때까지 기다리지 않음
//    // 시작된 모든 job은 활성상태이며 실행이 완료되거나 취소가 요청될 때까지 활성상태가 됨
//    println("FF")
//}




/*
 * 취소 중
 * 취소 요청을 받은 활성 job은 취소 중(cancelling)이라는 스테이징 상태로 들어갈 수 있음
 *
 * 취소 됨
 * 취소 / 처리되지 않은 예외로 인해 실행이 종료된 job은 취소됨으로 간주됨
 */
//@OptIn(InternalCoroutinesApi::class)
//fun main(args: Array<String>) = runBlocking {
//    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//        delay(5000)
//    }
//    val job2 = Job()
//    delay(2000)
//    // cancel with a cause
//    job.cancel(message = "Timeout!", cause = Exception("WWWW"))
//    val cancellation = job.getCancellationException()
//    println(cancellation.message)
//}

//@OptIn(InternalCoroutinesApi::class)
//fun main(args: Array<String>) = runBlocking {
//    val exceptionHandler = CoroutineExceptionHandler {
//        _: CoroutineContext, throwable: Throwable ->
//        println("Handled ${throwable.message}")
//    }
//    val job = GlobalScope.launch(exceptionHandler) {
//        throw Exception("######!!!")
//    }
//    // cancel with a cause
//    job.cancel(message = "Timeout!", cause = Exception("WWWW"))
//    val cancellation = job.getCancellationException()
//    println(cancellation.message)
//}

fun main(args: Array<String>) = runBlocking {
    GlobalScope.launch {
        throw Exception("######!!!")
    }.invokeOnCompletion {
        cause ->
        cause?.let {
            println("Job cancelled due to ${it.message}")
        }
    }
    delay(2000)
}

/*
 * 완료됨: 실행이 중지된 잡은 정상적으로 종료됐거나, 취소됐거나, 예외 종료 했거나 여부에 관계없이 완료로 봄
 */