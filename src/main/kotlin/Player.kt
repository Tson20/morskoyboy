package org.example

class Player {
    var name: String = ""
    var field: Array<CharArray> = Array(10) { CharArray(10) { '.' } }

    var shots: Int = 0
    var hits: Int = 0
    var shipsLeft: Int = 20 // Задание 1

    // Задание 2
    fun isAlive(): Boolean {
        return shipsLeft > 0
    }

    // Задание 3
    fun accuracy(): Double {
        if (shots == 0) return 0.0
        return hits * 100.0 / shots
    }

    // Задание 4
    fun status(): String {
        val acc = String.format(java.util.Locale.US, "%.1f", accuracy())
        return "Игрок $name: палуб осталось $shipsLeft, выстрелов $shots, попаданий $hits, точность $acc%"
    }

    // Задание 5
    fun takeDamage(): Boolean {
        shipsLeft--
        return shipsLeft > 0
    }

    // Задание 6
    fun registerShot(hit: Boolean) {
        shots++
        if (hit) {
            hits++
        }
    }

    // Задание 7
    fun reset() {
        shots = 0
        hits = 0
        shipsLeft = 20
        // Очищаем поле
        for (r in 0..9) {
            for (c in 0..9) {
                field[r][c] = '.'
            }
        }
    }

    fun printField() {
        println("=== Поле игрока $name ===")
        print("   ")
        for (c in 0..9) print("$c ")
        println()
        for (r in 0..9) {
            print("$r  ")
            for (c in 0..9) {
                print("${field[r][c]} ")
            }
            println()
        }
    }
}