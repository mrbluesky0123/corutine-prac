package book.ch5

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/**
 * 일시중단 가능한 시퀀스: Suspendable sequence
 * 일시중단 가능한 이터레이터: Suspendable iterator
 * 일시중단 가능한 데이터 소스에서 데이터 산출
 * 시퀀스와 이터레이터의 차이점
 * 프로듀서를 사용한 비동기 데이터 검색
 * 프로듀서 실제 사례
 */

/**
 * 일시중단 가능한 시퀀스와 이터레이터
 * 특성:
 *   - 호출 사이에서 일시 중단되지만, 실행 중에는 일시중단될 수 없다 -> 일시중단 연산이 없어도 반복 가능
 *   - 시퀀스와 이터레이터의 빌더는 CoroutineContext를 받지 않는다. 기본적으로 코드를 호출한 컨텍스트와 동일한 컨텍스트에서 코드가 실행됨
 *   - 정보산출 후에만 일시 중지 할 수 있다. 이를 위해서는 yield(), yieldAll() 함수를 호출해야 한다.
 */

/**
 * 이터레이터: 요소들의 컬렉션을 순서대로 살펴보는데 유용
 *   - 인덱스로 요소를 검색할 수 없으므로 순서대로만 접근 가능
 *   - 더 많은 요소가 있는지의 여부를 나타내는 hasNext()
 *   - 요소는 한 방향으로만 검색 가능. 이전 요소는 검색 불가
 *   - 재설정 할 수 없으므로 한 번만 반복 가능
 */
//fun main(args: Array<String>) {
//    val iterator: Iterator<String> = iterator {
//        yield("First")
//        yield("Second")
//        yield("Third")
//    }
//
//    println(iterator.next())
//    println(iterator.next())
//    println(iterator.next())
//    println(iterator.next())
//
////    iterator.forEachRemaining {
////        println(it)   // hasNext()를 내부적으로 호출
////    }
//
////    iterator.forEach {
////        println(it)
////    }
//}

//fun main(args: Array<String>) {
//    val iterator: Iterator<String> = iterator {
//        println("yielding 1")
//        yield("First")
//        println("yielding 2")
//        yield("Second")
//    }
//
//    if(iterator.hasNext()) {
//        println("iterator has next")
//        iterator.next()
//    }
//
////    iterator.forEachRemaining {
////        println(it)   // hasNext()를 내부적으로 호출
////    }
//
////    iterator.forEach {
////        println(it)
////    }
//}


/**
 * 시퀀스:
 *   - 인덱스로 값을 가져올 수 있다
 *   - 상태가 저장되지 않으며(stateless), 상호작용 후 자동으로 재성정 된다.
 *   - 한 번의 호출로 그룹을 가져올 수 있다.
 */

//fun main(args: Array<String>) {
//    val iterator: Iterator<String> = iterator {
//        println("yielding 1")
//        yield("First")
//        println("yielding 2")
//        yield("Second")
//    }
//
//    if(iterator.hasNext()) {
//        println("iterator has next")
//        iterator.next()
//    }
//
////    iterator.forEachRemaining {
////        println(it)   // hasNext()를 내부적으로 호출
////    }
//
////    iterator.forEach {
////        println(it)
////    }
//}


/**
 * 시퀀스
 *   - 인덱스로 값을 가져올 수 있다.
 *   - stateless하며 상호작용한 후 자동으로 재설정된다.
 *   - 한 번의 호출로 값 그룹을 가져올 수 있다.
 */
//fun main(args: Array<String>): Unit = runBlocking {
//    val seq = sequence<Int>{
//        yield(1)
//        yield(1)
//        yield(2)
//        yield(3)
//        yield(5)
//        yield(8)
//        yield(13)
//        yield(21)
//    }
//
//    seq.forEach {
//        print("$it ")
//    }
//    // 한 번 읽어도 또 읽을 수 있네? cf) iterator는 stream으로 한 번 읽으면 또 못읽음
//    seq.forEachIndexed {index, value ->
//        println("element at $index is $value")
//    }
//
//    println(seq.elementAt(4))
//    println(seq.elementAtOrElse(20) { it * 2 })
//    println(seq.elementAtOrNull(20))
//
//    val firstFive = seq.take(5)
//    println(firstFive.joinToString(limit = 3))
//
//}

/**
 * 시퀀스를 이용한 피보나치 수열 작성
 */
//fun main(args: Array<String>): Unit = runBlocking {
//    val fibonacci = sequence {
//        yield(1L)
//        var current = 1L
//        var next = 1L
//        while(true) {
//            yield(next)
//            val tmpNext = current + next
//            current = next
//            next = tmpNext
//        }
//    }
//    val indexed = fibonacci.take(50).withIndex()
//    for((index, value) in indexed) {
//        println("$index : $value")
//    }
//}

/**
 * 이터레이터로 피보나치
 */
//fun main(args: Array<String>): Unit = runBlocking {
//    val fibonacci = iterator {
//        yield(1L)
//        var current = 1L
//        var next = 1L
//        while(true) {
//            yield(next)
//            val tmpNext = current + next
//            current = next
//            next = tmpNext
//        }
//    }
//    for(i in 0..91) {
//        println("$i is ${fibonacci.next()}")
//    }
//}