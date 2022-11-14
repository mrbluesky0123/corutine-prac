import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        val job = launch {
            delay(1000L)
            println("World!")
        }
        println("Hello, ")
        job.join()
        println("Done")
    }
}
