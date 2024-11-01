package com.example.pms.model

import java.io.Serializable

class Card(
    private val value: Value,
    private val suit: Suit
) :
    Comparable<Any?>, Serializable {
    enum class Suit(private val symbol: String) {
        SPADE(" ♤"),
        HEART(" ♥"),
        DIAMOND(" ♦"),
        CLUB(" ♧");

        override fun toString(): String {
            return symbol
        }
    }

    enum class Value(val value: String) {
        AS("A"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("J"),
        QUEEN("Q"),
        KING("K");

        override fun toString(): String {
            return value
        }
    }

    override fun equals(o: Any?): Boolean {
        if (o is Card) {
            val card: Card = o
            return value == card.value && suit == card.suit
        } else {
            return false
        }
    }

    override fun compareTo(o: Any?): Int {
        val lessThan = -1
        val equal = 0
        val greaterThan = 1

        if (o is com.example.pms.model.Card) {
            val card: com.example.pms.model.Card = o as com.example.pms.model.Card
            if (this == card) {
                return equal
            }
            if (value.ordinal < card.value.ordinal) {
                return lessThan
            }
            return greaterThan
        }
        return equal
    }

    fun isOppositeColor(other: com.example.pms.model.Card): Boolean {
        return if (suit == com.example.pms.model.Card.Suit.HEART || suit == com.example.pms.model.Card.Suit.DIAMOND) {
            if (other.getSuit() == com.example.pms.model.Card.Suit.CLUB || other.getSuit() == com.example.pms.model.Card.Suit.SPADE) {
                true
            } else {
                false
            }
        } else {
            if (other.getSuit() == com.example.pms.model.Card.Suit.DIAMOND || other.getSuit() == com.example.pms.model.Card.Suit.HEART) {
                true
            } else {
                false
            }
        }
    }

    override fun toString(): String {
        return value.toString() + suit.toString()
    }

    fun getValue(): com.example.pms.model.Card.Value {
        return value
    }

    fun getSuit(): com.example.pms.model.Card.Suit {
        return suit
    }
}