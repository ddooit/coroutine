package basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        // 이름에서처럼 스레드를 blocking !! 즉, 다 실행이 되어야 END가 출력됨
        printWithThread("START")

        launch {
            delay(2_000L)
            printWithThread("LAUNCH END")
        }

    }

    printWithThread("END")
}
