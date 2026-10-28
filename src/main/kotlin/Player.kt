package org.example

class Player(val name: String) {
    private val _field: Array<Array<CellState>> = Array(10) { Array(10) { CellState.EMPTY } }
    fun field(): Array<Array<CellState>> = _field

    private var _shots: Int = 0
    private var _hits: Int = 0
    val shots: Int get() = _shots
    val hits: Int get() = _hits

    fun accuracy(): Double = if (_shots == 0) 0.0 else _hits * 100.0 / _shots

    fun registerShot(hit: Boolean) {
        _shots++
        if (hit) _hits++
    }

    fun reset() {
        _shots = 0
        _hits = 0
        for (r in 0..9) for (c in 0..9) _field[r][c] = CellState.EMPTY
    }

    fun status(): String {
        val acc = String.format(java.util.Locale.US, "%.1f", accuracy())
        return "Игрок $name: выстрелов $_shots, попаданий $_hits, точность $acc%"
    }

    fun isInside(row: Int, col: Int): Boolean = row in 0..9 && col in 0..9

    fun canPlaceShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol
            if (!isInside(r, c)) return false
            if (_field[r][c] != CellState.EMPTY) return false
            for (dr in -1..1) {
                for (dc in -1..1) {
                    if (dr == 0 && dc == 0) continue
                    val nr = r + dr
                    val nc = c + dc
                    if (isInside(nr, nc) && _field[nr][nc] == CellState.SHIP) return false
                }
            }
        }
        return true
    }

    fun hasShips(): Boolean {
        for (r in 0..9) for (c in 0..9) if (_field[r][c] == CellState.SHIP) return true
        return false
    }

    fun canShootAt(row: Int, col: Int): Boolean {
        if (!isInside(row, col)) return false
        val state = _field[row][col]
        return state != CellState.HIT && state != CellState.MISS
    }

    fun placeShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        if (!canPlaceShip(startRow, startCol, size, horizontal)) return false
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol
            _field[r][c] = CellState.SHIP
        }
        return true
    }

    fun shoot(row: Int, col: Int): Boolean {
        if (!canShootAt(row, col)) {
            println("Выстрел невозможен: за границей или сюда уже стреляли.")
            return false
        }
        val hit = (_field[row][col] == CellState.SHIP)
        _field[row][col] = if (hit) CellState.HIT else CellState.MISS
        registerShot(hit)
        return hit
    }
}