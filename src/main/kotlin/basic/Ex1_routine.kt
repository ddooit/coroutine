package basic

fun main() {
    println("START")
    newRoutine()
    println("END")
}

private fun newRoutine() {
    val num1 = 1
    val num2 = 2

    println("${num1 + num2}")
}
