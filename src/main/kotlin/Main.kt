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
        for (c in 0..9) {
            print("${player.field()[r][c]} ")
        }
        print("  ")
        print("$r  ")
        for (c in 0..9) {
            print("${enemy.field()[r][c]} ")
        }
        println()
    }
}

fun main() {
    // Задание 5: Игроки создаются через конструктор
    val player = Player("Вы")
    val enemy = Player("Компьютер")

    // Задание 5: Корабли указываются через placeShip
    player.placeShip(3, 2)
    player.placeShip(3, 3)
    player.placeShip(3, 4)
    player.placeShip(3, 5)

    enemy.placeShip(5, 7)
    enemy.placeShip(6, 7)
    enemy.placeShip(7, 7)

    // Печатаем поля
    printBothFields(player, enemy)
    println()

    // Проверяем registerShot
    player.registerShot(true)
    player.registerShot(false)
    player.registerShot(true)

    println(player.status())

    println("Выстрелов: ${player.shots}")
    println("Попаданий: ${player.hits}")

    player.reset()
    println(player.status())

    println("\n--- Проверка markShot (Задание 6) ---")
    enemy.markShot(3, 3, true)  // Попадание (X)
    enemy.markShot(4, 4, false) // Промах (O)
    println("Смотрим на поле противника (там должны быть X и O):")

    // Выведем только поле противника для проверки
    println("=== Поле ${enemy.name} ===")
    print("   ")
    for (c in 0..9) print("$c ")
    println()
    for (r in 0..9) {
        print("$r  ")
        for (c in 0..9) print("${enemy.field()[r][c]} ")
        println()
    }

    // Проверка защиты (должны быть красные ошибки, если раскомментировать)
    // player.shots = 100          // ОШИБКА: val нельзя изменить
    // player.hits = -5            // ОШИБКА: val нельзя изменить
    // player.name = "Другой"      // ОШИБКА: val нельзя изменить
}