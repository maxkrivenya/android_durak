package com.example.pms.model;

import java.io.Serializable

class Board : Serializable {
    private val NUMBER_OF_PILES = 7
    private var piles: Array<Pile?>
    private var validatedCards: Array<Pile?>
    private var deck: Deck

    constructor() {
        piles = arrayOfNulls<Pile>(NUMBER_OF_PILES)
        validatedCards = arrayOfNulls<Pile>(Card.Suit.values().size)
        deck = Deck()
    }

    constructor(anotherBoard: Board) {
        this.piles = arrayOfNulls<Pile>(NUMBER_OF_PILES)
        for (i in 0 until NUMBER_OF_PILES) {
            piles[i] = Pile(anotherBoard.getPiles()[i])
        }
        this.validatedCards = arrayOfNulls<Pile>(Card.Suit.values().size)
        for (i in anotherBoard.getValidatedCards().indices) {
            validatedCards[i] = Pile(anotherBoard.getValidatedCards()[i])
        }
        deck = Deck(anotherBoard.getDeck())
    }

    fun getPiles(): Array<Pile?> {
        return piles
    }

    fun getValidatedCards(): Array<Pile?> {
        return validatedCards
    }

    fun getDeck(): Deck {
        return deck
    }
}