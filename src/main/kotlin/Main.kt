package org.example
import kotlin.random.Random

// Функция печати одного поля
fun printField(player: Player, hideShips: Boolean) {
    println("=== Поле игрока ${player.name} ===")
    print("   ")
    for (c in 0..9) print("$c ")
    println()

    for (r in 0..9) {
        print("$r  ")
        for (c in 0..9) {
            val state = player.field()[r][c]
            val shown = if (hideShips && state == CellState.SHIP) CellState.EMPTY else state
            print("${shown.toChar()} ")
        }
        println()
    }
}

// Функция печати двух полей
fun printBothFields(player: Player, enemy: Player) {
    println("=== Поле игрока ${player.name} ===   === Поле игрока ${enemy.name} ===")
    print("   ")
    for (c in 0..9) print("$c ")
    print("  ")
    print("   ")
    for (c in 0..9) print("$c ")
    println()

    for (r in 0..9) {
        print("$r  ")
        for (c in 0..9) print("${player.field()[r][c].toChar()} ")
        print("  ")
        print("$r  ")
        for (c in 0..9) {
            val state = enemy.field()[r][c]
            // Скрываем корабли противника
            val shown = if (state == CellState.SHIP) CellState.EMPTY else state
            print("${shown.toChar()} ")
        }
        println()
    }
}

// Правила игры
fun printRules() {
    println("--- МОРСКОЙ БОЙ — ПРАВИЛА ---")
    println("Поле 10x10. Координаты: сначала строка (0-9), потом столбец (0-9).")
    println("Корабли: 1x4, 2x3, 3x2, 4x1. Всего 10 кораблей.")
    println("Корабли не могут касаться друг друга даже углами.")
}

// Расстановка кораблей человеком
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

// Автоматическая расстановка кораблей компьютера
fun placeEnemyShips(enemy: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    var index = 0
    while (index < plan.size) {
        val size = plan[index]
        val startRow = Random.nextInt(0, 10)
        val startCol = Random.nextInt(0, 10)
        val horizontal = Random.nextBoolean()

        if (enemy.placeShip(startRow, startCol, size, horizontal)) {
            index++
        }
    }
    println("Компьютер расставил корабли.")
}

fun main() {
    val player = Player("Вы")
    val enemy = Player("Компьютер")

    printRules()
    placePlayerShips(player)
    placeEnemyShips(enemy)
    println("\n--- ИГРА НАЧАЛАСЬ ---")

    var playerTurn = true

    while (player.hasShips() && enemy.hasShips()) {
        printBothFields(player, enemy)
        println()

        if (playerTurn) {
            println("Ваш ход. Введите координаты выстрела (row col):")
            val input = readln().trim().split(" ")
            if (input.size != 2) {
                println("Ошибка: нужно два числа.")
                continue
            }
            val r = input[0].toIntOrNull()
            val c = input[1].toIntOrNull()
            if (r == null || c == null) {
                println("Ошибка: координаты должны быть числами.")
                continue
            }

            // Если выстрел невозможен (уже стреляли или за границей), ход НЕ передается
            if (!enemy.isInside(r, c)) {
                println("Выстрел за границей! Попробуйте снова.")
                continue
            }
            val cell = enemy.field()[r][c]
            if (cell == CellState.HIT || cell == CellState.MISS) {
                println("Сюда уже стреляли! Попробуйте снова.")
                continue
            }

            val hit = enemy.shoot(r, c)
            if (hit) {
                println("Попадание!")
            } else {
                println("Промах.")
                playerTurn = false
            }

        } else {
            println("Ход компьютера...")
            var r: Int
            var c: Int
            do {
                r = Random.nextInt(0, 10)
                c = Random.nextInt(0, 10)
            } while (player.field()[r][c] == CellState.HIT || player.field()[r][c] == CellState.MISS)

            val hit = player.shoot(r, c)
            if (hit) {
                println("Компьютер попал в ($r, $c)!")
            } else {
                println("Компьютер промахнулся в ($r, $c).")
                playerTurn = true
            }
        }
        println()
    }

    // Конец игры
    if (enemy.hasShips()) {
        println("Победил компьютер!")
    } else {
        println("Вы победили!")
    }
}