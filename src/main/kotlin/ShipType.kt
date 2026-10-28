package org.example

enum class ShipType(val size: Int) {
    BATTLESHIP(4),
    CRUISER(3),
    DESTROYER(2),
    BOAT(1);

    fun isBig(): Boolean {
        return size >= 3
    }
}