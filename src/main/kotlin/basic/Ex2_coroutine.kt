package basic

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() :Unit = runBlocking {

    printWithThread("START")
    launch{
        newRoutine()
    }

    yield()

    printWithThread("END")
}

private suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2

    yield()

    printWithThread("${num1 + num2}")
}

fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] $str")
}
