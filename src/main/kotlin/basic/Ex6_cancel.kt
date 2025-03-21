package basic

import kotlinx.coroutines.*

fun example7(): Unit = runBlocking {
    val job1 = launch {
        delay(1_000L)
        printWithThread("Job 1")
    }
    val job2 = launch {
        delay(1_000L)
        printWithThread("Job 2")
    }

    delay(100L) // 첫 번째 코루틴 코드가 시작되는 것을 잠시 기다린다.
    job1.cancel()
    // job1은 취소되어 Job 1 d이 출력되지 않음.
}

// Q. job1은 어떻게 취소가 될 수 있었을까?
// A. delay / yield 와 같은 코루틴이 제공해주는 suspend 함수는 취소에 협조할 수 있다.


fun example8(): Unit = runBlocking {
    val job = launch {
        var i = 1
        var nextPrintTime = System.currentTimeMillis()
        while (i <= 5) {
            if (nextPrintTime <= System.currentTimeMillis()) {
                printWithThread("${i++} 번째 출력!")
                nextPrintTime += 1_000L // 1초 후에 다시 출력되도록 한다.
            }
        }
    }

    delay(100L)
    job.cancel()
    // 취소되지 않고 계속 출력된다 ==> 코루틴의 취소에 협조하지 않았다!!
}


// CancellationException 과 Dispatchers.Default 의 조합으로 협조해보자
fun example9(): Unit = runBlocking {
    val job = launch(Dispatchers.Default) {// main 스레드가 아니라 다른 스레드에서 launch를 실행하겠다.
        var i = 1
        var nextPrintTime = System.currentTimeMillis()
        while (i <= 5) {
            if (nextPrintTime <= System.currentTimeMillis()) {
                printWithThread("${i++} 번째 출력!")
                nextPrintTime += 1_000L // 1초 후에 다시 출력되도록 한다.
            }

            if (!isActive) {
                // 취소신호를 받았다면 예외를 던진다
                throw CancellationException()
            }
        }
    }

    delay(100L)
    job.cancel()

}

// 사실 delay도 CancellationException 를 던져 취소에 협조하고 있었다. 확인해볼까?
fun main(): Unit = runBlocking {
    val job = launch {
        try {
            delay(1_000L)
        } catch (e: CancellationException) {
            // 예외를 잡아서 먹어버린다!
        }
        printWithThread("delay에 의해 최소되지 않았다!")
    }

    delay(100L)
    job.cancel()

}
