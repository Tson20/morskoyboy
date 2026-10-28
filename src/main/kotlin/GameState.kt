package org.example

enum class GameState {
    PLACING,
    PLAYER_TURN,
    ENEMY_TURN,
    FINISHED;

    fun describe(): String {
        return when (this) {
            GameState.PLACING -> "Расстановка кораблей"
            GameState.PLAYER_TURN -> "Ход игрока"
            GameState.ENEMY_TURN -> "Ход противника"
            GameState.FINISHED -> "Игра завершена"
        }
    }
}