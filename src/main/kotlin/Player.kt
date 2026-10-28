package org.example

class Player(val name: String) {

    private val _field: Array<Array<CellState>> = Array(10) { Array(10) { CellState.EMPTY } }

    fun field(): Array<Array<CellState>> = _field

    private var _shots: Int = 0
    private var _hits: Int = 0

    val shots: Int get() = _shots
    val hits: Int get() = _hits

    fun registerShot(hit: Boolean) {
        _shots++
        if (hit) _hits++
    }

    fun placeShip(row: Int, col: Int) {
        if (row in 0..9 && col in 0..9) {
            _field[row][col] = CellState.SHIP
        }
    }

    fun markShot(row: Int, col: Int, hit: Boolean) {
        if (row in 0..9 && col in 0..9) {
            _field[row][col] = if (hit) CellState.HIT else CellState.MISS
        }
    }

    fun accuracy(): Double {
        if (_shots == 0) return 0.0
        return _hits * 100.0 / _shots
    }

    fun status(): String {
        val acc = String.format(java.util.Locale.US, "%.1f", accuracy())
        return "Игрок $name: выстрелов $_shots, попаданий $_hits, точность $acc%"
    }

    fun reset() {
        _shots = 0
        _hits = 0
        for (r in 0..9) {
            for (c in 0..9) {
                _field[r][c] = CellState.EMPTY
            }
        }
    }
}