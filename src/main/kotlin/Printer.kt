package org.example

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
            val shown = if (state == CellState.SHIP) CellState.EMPTY else state
            print("${shown.toChar()} ")
        }
        println()
    }
}

fun printRules() {
    println("--- МОРСКОЙ БОЙ — ПРАВИЛА ---")
    println("Поле 10x10. Координаты: сначала строка (0-9), потом столбец (0-9).")
    println("Корабли: 1x4, 2x3, 3x2, 4x1. Всего 10 кораблей.")
    println("Корабли не могут касаться друг друга даже углами.")
}