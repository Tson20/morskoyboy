package org.example
import kotlin.random.Random

fun placePlayerShips(player: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    var index = 0

    while (index < plan.size) {
        val expectedSize = plan[index]
        println("\nСтавим корабль №${index + 1} из ${plan.size} (размер $expectedSize):")
        printField(player, false)

        println("Введите начальную координату (row col):")
        val input = readln().trim().split(" ")
        if (input.size != 2) {
            println("Ошибка: нужно два числа через пробел.")
            continue
        }
        val startRow = input[0].toIntOrNull()
        val startCol = input[1].toIntOrNull()
        if (startRow == null || startCol == null) {
            println("Ошибка: координаты должны быть числами.")
            continue
        }

        println("Введите размер корабля (1, 2, 3 или 4):")
        val size = readln().trim().toIntOrNull()
        if (size == null || size !in 1..4) {
            println("Ошибка: размер должен быть 1, 2, 3 или 4.")
            continue
        }
        if (size != expectedSize) {
            println("Сейчас нужно ставить корабль из $expectedSize палуб, а не $size.")
            continue
        }

        println("Введите направление (h - вправо, v - вниз):")
        val dir = readln().trim().lowercase()
        if (dir != "h" && dir != "v") {
            println("Ошибка: направление h или v.")
            continue
        }
        val horizontal = (dir == "h")

        if (player.placeShip(startRow, startCol, size, horizontal)) {
            println("Корабль поставлен!")
            index++
        } else {
            println("Нельзя поставить корабль здесь. Попробуйте снова.")
        }
    }
    println("Все корабли расставлены!")
    printField(player, false)
}

fun placeEnemyShips(enemy: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1).shuffled()
    var index = 0

    while (index < plan.size) {
        val size = plan[index]
        val cells = mutableListOf<Pair<Int, Int>>()
        for (r in 0..9) {
            for (c in 0..9) {
                cells.add(Pair(r, c))
            }
        }
        cells.shuffle()
        var placed = false

        for (cell in cells) {
            val r = cell.first
            val c = cell.second
            if (!placed && enemy.placeShip(r, c, size, true)) placed = true
            if (!placed && enemy.placeShip(r, c, size, false)) placed = true
        }

        if (placed) index++
        else {
            enemy.reset()
            index = 0
        }
    }
    println("Компьютер расставил корабли.")
}