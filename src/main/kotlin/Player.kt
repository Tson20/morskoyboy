package org.example

class Player(val name: String) {

    // ===== ПОЛЕ =====

    private val _field: Array<Array<CellState>> = Array(10) { Array(10) { CellState.EMPTY } }

    fun field(): Array<Array<CellState>> = _field

    // ===== СТАТИСТИКА =====

    private var _shots: Int = 0
    private var _hits: Int = 0

    val shots: Int get() = _shots
    val hits: Int get() = _hits

    // ===== МЕТОДЫ =====

    fun accuracy(): Double {
        if (_shots == 0) return 0.0
        return _hits * 100.0 / _shots
    }

    fun registerShot(hit: Boolean) {
        _shots++
        if (hit) _hits++
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

    fun status(): String {
        val acc = String.format(java.util.Locale.US, "%.1f", accuracy())
        return "Игрок $name: выстрелов $_shots, попаданий $_hits, точность $acc%"
    }

    // ===== ПРОВЕРКИ (Boolean-функции) =====

    // 1. Проверка, что координата (row,col) внутри поля 10х10.
    fun isInside(row: Int, col: Int): Boolean {
        return row in 0..9 && col in 0..9
    }

    // 2. Проверка, можно ли поставить корабль длины size
    fun canPlaceShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        // Перебираем все палубы будущего корабля.
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol

            // 1. Палуба должна быть внутри поля.
            if (!isInside(r, c)) return false

            // 2. Палуба должна быть пустой.
            if (_field[r][c] != CellState.EMPTY) return false

            // 3. Все 8 соседей должны быть пустыми (или за границей)
            for (dr in -1..1) {
                for (dc in -1..1) {
                    if (dr == 0 && dc == 0) continue
                    val nr = r + dr
                    val nc = c + dc
                    if (isInside(nr, nc) && _field[nr][nc] == CellState.SHIP) {
                        return false
                    }
                }
            }
        }
        return true
    }

    // 3. Есть ли еще корабли?
    fun hasShips(): Boolean {
        for (r in 0..9) {
            for (c in 0..9) {
                if (_field[r][c] == CellState.SHIP) return true
            }
        }
        return false
    }

    // ===== ДЕЙСТВИЯ =====

    // 4. Ставим целый корабль
    fun placeShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        if (!canPlaceShip(startRow, startCol, size, horizontal)) {
            return false
        }
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol
            _field[r][c] = CellState.SHIP
        }
        return true
    }

    // 5. Выстрел
    fun shoot(row: Int, col: Int): Boolean {
        if (!isInside(row, col)) {
            println("Выстрел за границей поля!")
            return false
        }
        val current = _field[row][col]
        if (current == CellState.HIT || current == CellState.MISS) {
            println("Сюда уже стреляли!")
            return false
        }

        val hit = (current == CellState.SHIP)
        _field[row][col] = if (hit) CellState.HIT else CellState.MISS
        registerShot(hit)
        return hit
    }
}