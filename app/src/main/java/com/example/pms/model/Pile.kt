package com.example.pms.model

import java.io.Serializable
import java.util.ArrayDeque
import java.util.Deque

class Pile : Serializable {
    private var cards: Deque<Card>? = null
    private var visible: Deque<Card>? = null

    fun getCards(): Deque<Card>? {
        return cards
    }

    val visibleCards: Deque<Card>?
        get() = visible

    constructor() {
        cards = ArrayDeque<Card>()
        visible = ArrayDeque<Card>()
    }

    constructor(pile: Pile?) {
        if (pile != null) {
            this.cards = ArrayDeque(pile.getCards())
            this.visible = ArrayDeque(pile.visibleCards)
        }
    }

    fun clearPile() {
        cards!!.clear()
        visible!!.clear()
    }

    fun addCard(card: Card) {
        cards!!.push(card)
    }

    fun addVisibleCard(card: Card) {
        visible!!.push(card)
    }

    fun removeCard(card: Card): Boolean {
        if (visible!!.contains(card)) {
            visible!!.remove(card)
            if (visible!!.size == 0 && cards!!.size > 0) {
                val toChange: Card = cards!!.pop()
                visible!!.add(toChange)
            }
            return true
        } else {
            return false
        }
    }

    fun getCardsUnderThisOne(card: Card?): Deque<Card>? {
        if (!visible!!.contains(card)) {
            return null
        }
        val result: Deque<Card> = ArrayDeque<Card>()
        val saveCards: ArrayList<Card> = ArrayList<Card>()
        var currentCard: Card = visible!!.pop()
        while (!currentCard.equals(card)) {
            result.push(currentCard)
            saveCards.add(currentCard)
            currentCard = visible!!.pop()
        }
        result.push(card)
        saveCards.add(card)
        for (i in saveCards.indices.reversed()) {
            visible!!.push(saveCards[i])
        }
        return result
    }
}