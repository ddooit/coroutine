package basic

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun example1(): Unit = runBlocking {
    printWithThread("START")

    val job = launch(start = CoroutineStart.LAZY) {
        // launch 에 LAZY 옵션을 주면 start 할때 실행 되는 것으로 설정 가능

        delay(1_000L)
        printWithThread("LAUNCH")
    }
    // 코루틴을 제어할 수 있는 객체를 반환받는다.
    job.start()
    printWithThread("END")
}

fun example2(): Unit = runBlocking {
    val job = launch() {
        (1..5).forEach{
            printWithThread(it)
            delay(500L)
        }
    }

    delay(1_000L)
    job.cancel()
    // 2 까지만 출력됨
}

fun example3():Unit = runBlocking {
    val job1 = launch{
        delay(1_000L)
        printWithThread("JOB_1")
    }

    job1.join() // 완전히 실행될 때까지 기다림

    val job2 = launch{
        delay(1_000L)
        printWithThread("JOB_2")
    }
}
