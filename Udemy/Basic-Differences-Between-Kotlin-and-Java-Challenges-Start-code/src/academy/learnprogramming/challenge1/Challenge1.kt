package academy.learnprogramming.challenge1

fun main(args: Array<String>) {
    val hello1 = "Hello"
    val hello2 = "Hello"

    println("hello1 s referentially equal to hello2: ${hello1 === hello2}")

    println("hello1 s structurally equal to hello2: ${hello1 == hello2}")

    var int = 2988

    val text: Any = "Any type is the root of the Kotlin class hierarchy"
  if (text is String) {
      println(text.toUpperCase())
  }


    println("""     1   
                 |  11
                 | 111
                 |1111""".trimMargin("1"))
}

