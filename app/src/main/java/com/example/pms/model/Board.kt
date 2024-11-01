package com.example.pms.model;

import java.io.Serializable

class Board : Serializable {
    private val NUMBER_OF_PILES = 7
    var piles: Array<Pile?>
    var validatedCards: Array<Pile?>
    var deck: Deck

    constructor() {
        piles = arrayOfNulls<Pile>(NUMBER_OF_PILES)
        validatedCards = arrayOfNulls<Pile>(Card.Suit.values().size)
        deck = Deck()
    }

    constructor(anotherBoard: Board) {
        this.piles = arrayOfNulls<Pile>(NUMBER_OF_PILES)
        for (i in 0 until NUMBER_OF_PILES) {
            piles[i] = Pile(anotherBoard.piles[i])
        }
        this.validatedCards = arrayOfNulls<Pile>(Card.Suit.values().size)
        for (i in anotherBoard.validatedCards.indices) {
            validatedCards[i] = Pile(anotherBoard.validatedCards[i])
        }
        deck = Deck(anotherBoard.deck)
    }

}