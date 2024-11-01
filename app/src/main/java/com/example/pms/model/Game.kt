package com.example.pms.model

import androidx.annotation.NonNull
import java.io.Serializable
import java.util.Deque
import java.util.Random

class Game : Serializable {
    var board: Board
        private set
    var isGameInitialized: Boolean = false
        private set

    constructor(anotherGame: Game) {
        val copiedBoard = Board(anotherGame.board)
        this.board = copiedBoard
    }

    constructor() {
        this.board = Board()
        isGameInitialized = false
    }

    /*-------------------------------------------------------------------------
    SIMPLE POSSIBLE ACTIONS FOR A PLAYER :
    - Draw three cards
    - Take a drawn card to a pile
    - Take a drawn card to the validated ones
    - Take a card in the pile to the validated ones (same function as above)
    -------------------------------------------------------------------------*/
    fun drawCards(): Deque<Card?>? {
        return board.deck.drawCards()
    }

    fun cardFromDeckIntoPile(card: Card, pileNumber: Int) {
        if (board.piles[pileNumber] == null || board.piles[pileNumber]?.visibleCards?.size === 0
        ) {
            if (!card.getValue().equals(Card.Value.KING)) {
                return
            } else {
                if (board.deck.removeCard(card)) {
                    board.piles[pileNumber] = Pile()
                    board.piles[pileNumber]!!.addVisibleCard(card)
                }
            }
        } else {
            val lastCardInPile: Card? = board.piles[pileNumber]?.visibleCards?.getFirst()
            if ((lastCardInPile?.getValue()!!.ordinal - 1 !== card.getValue().ordinal)
                || !(lastCardInPile?.isOppositeColor(card))!!
            ) {
                return
            }
            if (board.deck.removeCard(card)) {
                board.piles[pileNumber]!!.addVisibleCard(card)
            }
        }
    }

    fun cardFromDeckIntoValidatedOnes(card: Card, validatedNumber: Int) {
        if (board.validatedCards[validatedNumber] == null
            || board.validatedCards[validatedNumber]!!.getCards() == null
        ) {
            if (!card.getValue().equals(Card.Value.AS)) {
                return
            } else {
                if (board.deck.removeCard(card)) {
                    board.validatedCards[validatedNumber] = Pile()
                    board.validatedCards[validatedNumber]!!.addCard(card)
                }
            }
        } else {
            val lastCardInValidated = board.validatedCards[validatedNumber]!!.getCards()!!
                .first
            if ((lastCardInValidated.getValue().ordinal + 1 !== card.getValue().ordinal)
                || (lastCardInValidated.getSuit() !== card.getSuit())
            ) {
                return
            }
            if (board.deck.removeCard(card)) {
                board.validatedCards[validatedNumber]!!.addCard(card)
            }
        }
    }

    fun cardFromPileIntoValidatedOnes(card: Card, pileNumber: Int, validatedNumber: Int) {
        if (board.validatedCards[validatedNumber] == null || board.validatedCards[validatedNumber]!!.getCards() == null || board.validatedCards[validatedNumber]!!.getCards()!!
                .size === 0
        ) {
            if (!card.getValue().equals(Card.Value.AS)) {
                return
            } else {
                if (board.piles[pileNumber]!!.removeCard(card)) {
                    board.validatedCards[validatedNumber] = Pile()
                    board.validatedCards[validatedNumber]!!.addCard(card)
                }
            }
        } else {
            val lastCardInValidated = board.validatedCards[validatedNumber]!!.getCards()!!
                .first
            if ((lastCardInValidated.getValue().ordinal + 1 !== card.getValue().ordinal)
                || !(lastCardInValidated.getSuit().equals(card.getSuit()))
            ) {
                return
            }
            if (board.piles[pileNumber]!!.removeCard(card)) {
                board.validatedCards[validatedNumber]!!.addCard(card)
            }
        }
    }

    fun cardFromPileIntoPile(card: Card, pileNumber: Int, pileNumber2: Int) {
        if (pileNumber == pileNumber2) {
            return
        }
        if (board.piles[pileNumber2] == null || board.piles[pileNumber2]?.visibleCards
                ?.size === 0
        ) {
            if (!card.getValue().equals(Card.Value.KING)) {
                return
            } else {
                val cardsUnder =
                    board.piles[pileNumber]!!.getCardsUnderThisOne(card) ?: return
                board.piles[pileNumber2] = Pile()
                while (cardsUnder.size > 0) {
                    val currentCard = cardsUnder.pop()
                    board.piles[pileNumber]!!.removeCard(currentCard)
                    board.piles[pileNumber2]!!.addVisibleCard(currentCard)
                }
            }
        } else {
            val lastCardInPile2: Card = board.piles[pileNumber2]?.visibleCards!!.getFirst()
            if ((lastCardInPile2.getValue().ordinal - 1 !== card.getValue().ordinal)
                || !(lastCardInPile2.isOppositeColor(card))
            ) {
                return
            }
            val cardsUnder =
                board.piles[pileNumber]!!.getCardsUnderThisOne(card) ?: return
            while (cardsUnder.size > 0) {
                val currentCard = cardsUnder.pop()
                board.piles[pileNumber]!!.removeCard(currentCard)
                board.piles[pileNumber2]!!.addVisibleCard(currentCard)
            }
        }
    }

    /*---------------------------- GAMES ACTIONS ----------------------------*/
    fun initGame() {
        clearValidatedCards()
        val cardsLeft: MutableList<Card> = ArrayList()
        getEveryCard(cardsLeft)
        initDeck(cardsLeft)
        initPiles(cardsLeft)
        isGameInitialized = true
    }

    private fun clearValidatedCards() {
        val validatedCards = board.validatedCards
        for (pile in validatedCards) {
            pile?.clearPile()
        }
    }

    private fun initPiles(cardsLeft: MutableList<Card>) {
        val piles = board.piles
        for (i in piles.indices) {
            piles[i] = Pile()
            piles[i]!!.clearPile()
            for (j in 0 until i + 1) {
                //i+1 is the maximum number of cards in the pile
                val card = getRandomCard(cardsLeft)
                cardsLeft.remove(card)
                piles[i]!!.addCard(card)
            }
            val toChange = piles[i]!!.getCards()!!.pop()
            piles[i]?.visibleCards?.add(toChange)
        }
    }

    private fun initDeck(cardsLeft: MutableList<Card>) {
        val deck = board.deck
        deck.clearDeck()
        for (i in 0..23) {
            val card = getRandomCard(cardsLeft)
            cardsLeft.remove(card)
            deck.addCard(card)
        }
    }

    private fun getEveryCard(cardsLeft: MutableList<Card>) {
        for (value in 0..12) {
            for (suit in 0..3) {
                val card = Card(Card.Value.entries[value], Card.Suit.entries[suit])
                cardsLeft.add(card)
            }
        }
    }

    @NonNull
    private fun getRandomCard(cardsLeft: List<Card>): Card {
        val random = Random()
        val randomNumber = random.nextInt((cardsLeft.size))
        val card = cardsLeft[randomNumber]
        return card
    }
}