package bullscows

import kotlin.math.pow
import kotlin.random.Random

class InvalidCount(message: String) : Exception(message)
class InvalidPossibleSymbolCount(message: String) : Exception(message)
class InvalidLength(input: String) : Exception(input)

fun main() {
    try {
        println("Input the length of the secret code:")
        var digitsStr: String = ""
        var digits: Int
        try {
            digitsStr = readln()
            digits = digitsStr.toInt()
        } catch (e: Exception) {
            throw InvalidLength("Error: \"$digitsStr\" isn't a valid number.")
        }

        if (digits < 1 || digits > 36) {
            throw InvalidCount("Error: Invalid digit count number.")
        }

        println("Input the number of possible symbols in the code:")
        var possibleSymStr: String = ""
        var possibleSym: Int
        try {
            possibleSymStr = readln()
            possibleSym = possibleSymStr.toInt()
        } catch (e: Exception) {
            throw InvalidLength("Error: \"$possibleSymStr\" isn't a valid number.")
        }

        if (possibleSym < digits) {
            throw InvalidPossibleSymbolCount("Error: it's not possible to generate a code with a length of $digits with $possibleSym unique symbols.")
        }

        if (possibleSym > 36) {
            throw InvalidPossibleSymbolCount("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
        }

        var alph = "1234567890abcdefghijklmnopqrstuxwvyz"

        var secret = mutableSetOf<Char>()

        alph = alph.substring(0, possibleSym)
        while (secret.size < digits) {
            secret.add(alph.random())

        }
        val alphanumeric = secret.joinToString("")

        println("The secret is prepared: ${"*".repeat(digits)} (0-9, ${alph[0]}-${alph[possibleSym - 1]}).")
        println("Okay, let's start a game!")

        var turn = 1

        var finished = false
        var guess: String

        var bulls: Int = 0
        var cows: Int = 0

        var bullString = "bull"
        var cowString = "cow"

        while (!finished) {
            bulls = 0
            cows = 0
            println("Turn $turn:")
            guess = readln()

            for (i in guess.indices) {
                if (i >= alphanumeric.length) {
                    continue
                }
                if (guess[i] == alphanumeric[i]) {
                    bulls++
                } else if (alphanumeric.contains(guess[i])) {
                    cows++
                }
            }

            if (bulls == 1) {
                bullString = "bull"
            } else if (bulls > 1) {
                bullString = "bulls"
            }

            if (cows == 1) {
                cowString = "cow"
            } else if (bulls > 1) {
                cowString = "cows"
            }
            if (bulls > 0 || cows > 0) {
                print("Grade: ")
                if (bulls > 0) {
                    print("$bulls $bullString")
                    if (cows > 0) {
                        print(" and $cows $cowString.")
                    } else {
                        print(".")
                    }
                } else if (cows > 0) {
                    print("$cows $cowString.")
                }
            } else {
                println("Grade: None.")
            }


            if (bulls == alphanumeric.length) {
                finished = true
            }
            turn++
            println()
        }

        println("Congratulations! You guessed the secret code.")
    } catch (e: Exception) {
        println(e.message)
    }

}