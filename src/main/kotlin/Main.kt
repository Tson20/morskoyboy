package org.example

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
        for (c in 0..9) print("${player.field[r][c]} ")

        print("  ")

        print("$r  ")
        for (c in 0..9) print("${enemy.field[r][c]} ")
        println()
    }
}

fun main() {
    val player = Player()
    player.name = "Вы"

    player.registerShot(true)    // попал
    player.registerShot(false)   // промах
    player.registerShot(true)    // попал

    println(player.status())
    // Ожидается: Игрок Вы: палуб осталось 20, выстрелов 3, попаданий 2, точность 66.7%

    player.takeDamage()
    player.takeDamage()
    println("Палуб осталось: ${player.shipsLeft}") // Ожидается: 18
    println("Жив ли игрок? ${player.isAlive()}")   // Ожидается: true

    player.reset()
    println(player.status())
    // Ожидается: Игрок Вы: палуб осталось 20, выстрелов 0, попаданий 0, точность 0.0%

    println("\n--- Дополнительная проверка печати полей ---")
    val enemy = Player()
    enemy.name = "Компьютер"

    player.field[3][2] = '#'
    enemy.field[5][5] = 'X'

    printBothFields(player, enemy)
}