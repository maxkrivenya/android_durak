package com.example.pms.view

import com.example.pms.model.Card
import com.example.pms.model.Game

class Linker {
    fun deckAction(game: Game): Game {
        game.drawCards()
        return game
    }

    fun spadeAction(game: Game, card: Card): Game {
        game.cardFromDeckIntoValidatedOnes(card, 0)
        for (i in 0..6) {
            game.cardFromPileIntoValidatedOnes(card, i, 0)
        }
        return game
    }

    fun diamondAction(game: Game, card: Card): Game {
        game.cardFromDeckIntoValidatedOnes(card, 1)
        for (i in 0..6) {
            game.cardFromPileIntoValidatedOnes(card, i, 1)
        }
        return game
    }

    fun clubAction(game: Game, card: Card): Game {
        game.cardFromDeckIntoValidatedOnes(card, 2)
        for (i in 0..6) {
            game.cardFromPileIntoValidatedOnes(card, i, 2)
        }
        return game
    }

    fun heartAction(game: Game, card: Card): Game {
        game.cardFromDeckIntoValidatedOnes(card, 3)
        for (i in 0..6) {
            game.cardFromPileIntoValidatedOnes(card, i, 3)
        }
        return game
    }

    fun pileAction(game: Game, card: Card, index: Int): Game {
        game.cardFromDeckIntoPile(card, index)
        for (i in 0..6) {
            game.cardFromPileIntoPile(card, i, index)
        }
        return game
    }
}