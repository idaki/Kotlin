package academy.learnprogramming.section6challenge


fun main(args: Array<String>) {

    // using a range, print 5, 10, 15, 20, 25... 5000, each number
    for (i in 1..5000 step 5) {
        print("$i ")
    }

    // on a separate line

    // using a range, -500..0, each number on a separate line

    for (i in -500..0 step 5) {
        print("$i ")
    }

    println()
    var a = 0
    var b = 0
    for (i in 0..14) {
        when (i) {
            0 -> print("$i, ")
            1 -> {
                print("$i, ")
                a = 0
                b = 1
            }

            else -> {
                val sum = a + b
                print("$sum ,")
                a = b
                b = sum
            }
        }
    }
    // using a range, print the first 15 numbers in the
    // Fibonacci sequence, each
    // number on a separate line
    // Hint: you'll have to print the first one or two numbers
    // outside the range
    // 0, 1, 1, 2, 3, 5, 8...

    // Modify the following code so that it prints the following,
    // each number on a separate line
    // 1, 11, 100, 99, 98, 2
    println()
    iloop@ for (i in 1..5) {
        println(i)
        if (i == 2) {
            break
        }
        for (j in 11..20) {
            println(j)
            for (k in 100 downTo 90) {
                println(k)
                if (k == 98) {
                    continue@iloop
                }
            }
        }
    }


}