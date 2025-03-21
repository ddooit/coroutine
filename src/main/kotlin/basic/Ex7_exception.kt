package basic

import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

/**
 * job1과 job2는 corountine1의 자식
 */
fun corountine1(): Unit = runBlocking {
    val job1 = launch { }
    val job2 = launch { }
}

/**
 * job1, job2, coroutine2 는 모두 각각 독립적인 코루틴
 */
fun coroutine2(): Unit = runBlocking {
    val job1 = CoroutineScope(Dispatchers.Default).launch { }
    val job2 = CoroutineScope(Dispatchers.Default).launch { }
}


/**
 * launch와 async의 예외처리
 */
fun coroutine3(): Unit = runBlocking {

    val job = CoroutineScope(Dispatchers.Default).async {
        throw IllegalArgumentException()
    }
    // launch : 예외를 출력하고 코루틴을 종료시킨다
    // async  : 예외를 출력하지 않는다. await를 해줘야 출력한다

    delay(1_000L)
    printWithThread("END")
}

/**
 * 자식코루틴의 예외는 부모에게 전파된다
 */
fun coroutine4(): Unit = runBlocking {

    val job = async {// 이 경우, launch , async 모두 동일하게 부모 자식의 관계를 맺고 있기 때문에 예외 전파됨.
        throw IllegalArgumentException()
    }

    delay(1_000L)
    printWithThread("END")
}

fun coroutine5(): Unit = runBlocking {

    val job = async {// 이 경우, launch , async 모두 동일하게 부모 자식의 관계를 맺고 있기 때문에 예외 전파됨.
        try {
            throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            printWithThread("예외 발생 했지만 정상 종료")
        }
    }

    delay(1_000L)
    printWithThread("END")
}

/**
 * 또 다른 방법 CoroutineExceptionHandler
 * launch 에만 적용 가능, 부모 코루틴이 있으면 동작하지 않는다.
 */
fun main(): Unit = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        printWithThread("예외 발생")
//        throw throwable
    }

    val job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
        throw IllegalArgumentException()
    }

    delay(1_000L)

}
