package com.example.pms.model

import java.io.Serializable
import java.util.ArrayDeque
import java.util.Deque

class Deck : Serializable {
    var unseen: Deque<Card?>
        private set

    /*---------- DRAWN CARDS :
             - Position 2 : real seen card
             - Position 1 : drawn but we can't use it before 2
             - Position 0 : drawn but we can't use it before 1
    ----------------------------------*/
    var drawnCards: ArrayDeque<Card?>
        private set
    var alreadySeen: ArrayDeque<Card?>
        private set

    constructor() {
        unseen = ArrayDeque()
        drawnCards = ArrayDeque()
        alreadySeen = ArrayDeque()
    }

    constructor(anotherDeck: Deck) {
        this.unseen = ArrayDeque(anotherDeck.unseen)
        this.drawnCards =  ArrayDeque(anotherDeck.drawnCards)
        this.alreadySeen = ArrayDeque(anotherDeck.alreadySeen)
    }

    fun drawCards(): Deque<Card?>? {
        if (unseen.size == 0) {
            reInitializeDeck()
            return null
        }
        reInitializeDrawnCards()
        while (drawnCards.size < 3 && unseen.size > 0) {
            val drawnCard = unseen.pop()
            drawnCards.push(drawnCard)
        }
        return drawnCards
    }

    private fun reInitializeDrawnCards() {
        val size = drawnCards.size
        for (i in 0 until size) {
            val card = drawnCards.removeLast()
            alreadySeen.push(card)
        }
        drawnCards.clear()
    }

    private fun reInitializeDeck() {
        while (drawnCards.size > 0) {
            val card = drawnCards.pop()
            unseen.push(card)
        }
        while (alreadySeen.size > 0) {
            val card = alreadySeen.pop()
            unseen.push(card)
        }
        alreadySeen.clear()
    }

    fun addCard(card: Card?) {
        unseen.push(card)
    }

    fun addCardToAlreadySeen(card: Card?) {
        alreadySeen.push(card)
    }

    fun addCardToDrawnCards(card: Card?) {
        drawnCards.push(card)
    }

    fun removeCard(card: Card?): Boolean {
        if (drawnCards.size > 0) {
            val drawnCard = drawnCards.pop()
            if (drawnCard!!.equals(card)) {
                return true
            } else {
                drawnCards.push(drawnCard)
                return false
            }
        } else {
            return false
        }
    }

    fun clearDeck() {
        alreadySeen.clear()
        unseen.clear()
        drawnCards.clear()
    }
}