package basic

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main(): Unit = runBlocking {

    val time = measureTimeMillis {
        val job1 = async(start = CoroutineStart.LAZY) { apiCall1() }
        val job2 = async(start = CoroutineStart.LAZY) { apiCall2() }
        // todo. job1의 반환값을 인자로 넘겨주는 것도 가능 => 이렇게 되면 알아서 기다렸다가 실행됨

        // async에 LAZY 옵션을 주면 다 기다린다
        // start() 해주면 기다리지 않고도 가능

//        job1.start()
//        job2.start()

        printWithThread(job1.await() + job2.await())
    }

    printWithThread("소요시간 : ${time}ms")
}


fun example4(): Unit = runBlocking {

    val time = measureTimeMillis {
        val job1 = async { apiCall1() }
        val job2 = async { apiCall2() }

        printWithThread(job1.await() + job2.await())
    }

    printWithThread("소요시간 : ${time}ms")
}


suspend fun apiCall1(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(): Int {
    delay(1_000L)
    return 2
}
