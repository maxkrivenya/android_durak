package com.example.pms.view

import com.example.pms.model.Card
import com.example.pms.model.Game
import com.example.pms.model.Pile

/**
 * Created by Sophie on 26/11/2016.
 */
class ArtificialIntelligence {
    fun lookIfGameLost(game: Game): Boolean {
        if (game.isGameInitialized) {
            val copy: Game = Game(game)

            //STEP 1 : DECK : every visible Card
            var currentDeckCardMaybe: Card? = copy.board.deck.drawnCards.peek()
            if (currentDeckCardMaybe != null) {
                var currentDeckCard: Card = currentDeckCardMaybe;
                while (copy.board.deck.drawnCards.size !== 0) {
                    for (i in 0 until copy.board.piles.size) {
                        copy.cardFromDeckIntoPile(currentDeckCard, i)
                        if (i < 4) {
                            copy.cardFromDeckIntoValidatedOnes(currentDeckCard, i)
                        }
                        if (copy.board.deck.drawnCards.size === 0 ||
                            (copy.board.deck.drawnCards.size !== 0
                                    && copy.board.deck.drawnCards
                                .peek()!! !== currentDeckCard)
                        ) {
                            println("DECK")
                            System.out.println(currentDeckCard)
                            println(i)
                            return false
                        }
                    }
                    copy.drawCards()
                    currentDeckCardMaybe = copy.board.deck.drawnCards.peek()
                    if (currentDeckCardMaybe != null) {
                        currentDeckCard = currentDeckCardMaybe
                    }
                }

                copy.drawCards()
                currentDeckCardMaybe = copy.board.deck.drawnCards.peek()!!
                if (currentDeckCardMaybe != null) {
                    currentDeckCard = currentDeckCardMaybe
                }

                while (copy.board.deck.drawnCards.size !== 0) {
                    for (i in 0 until copy.board.piles.size) {
                        copy.cardFromDeckIntoPile(currentDeckCard, i)
                        if (i < 4) {
                            copy.cardFromDeckIntoValidatedOnes(currentDeckCard, i)
                        }
                        if (copy.board.deck.drawnCards.size === 0 ||
                            (copy.board.deck.drawnCards.size !== 0
                                    && copy.board.deck.drawnCards
                                .peek()!! !== currentDeckCard)
                        ) {
                            println("DECK")
                            System.out.println(currentDeckCard)
                            println(i)
                            return false
                        }
                    }
                    copy.drawCards()
                    currentDeckCardMaybe = copy.board.deck.drawnCards.peek()!!
                    if (currentDeckCardMaybe != null) {
                        currentDeckCard = currentDeckCardMaybe
                    }
                }


                copy.drawCards()
                currentDeckCardMaybe = copy.board.deck.drawnCards.peek()!!
                if (currentDeckCardMaybe != null) {
                    currentDeckCard = currentDeckCardMaybe
                }

                while (copy.board.deck.drawnCards.size !== 0) {
                    for (i in 0 until copy.board.piles.size) {
                        copy.cardFromDeckIntoPile(currentDeckCard, i)
                        if (i < 4) {
                            copy.cardFromDeckIntoValidatedOnes(currentDeckCard, i)
                        }
                        if (copy.board.deck.drawnCards.size === 0 ||
                            (copy.board.deck.drawnCards.size !== 0
                                    && copy.board.deck.drawnCards
                                .peek()!! !== currentDeckCard)
                        ) {
                            println("DECK")
                            System.out.println(currentDeckCard)
                            println(i)
                            return false
                        }
                    }
                    copy.drawCards()
                    currentDeckCardMaybe = copy.board.deck.drawnCards.peek()!!
                    if (currentDeckCardMaybe != null) {
                        currentDeckCard = currentDeckCardMaybe
                    }
                }

                //STEP 2 : PILES : Every first visible card
                for (i in 0 until copy.board.piles.size) {
                    if (copy.board.piles.get(i)?.visibleCards != null
                        && copy.board.piles.get(i)!!.visibleCards!!.size !== 0
                    ) {
                        val currentCard: Card =
                            copy.board.piles.get(i)?.visibleCards!!.getLast()
                        for (j in 0 until copy.board.piles.size) {
                            copy.cardFromPileIntoPile(currentCard, i, j)
                            if (copy.board.piles.get(i)?.visibleCards != null
                                && copy.board!!.piles
                                    .get(i)?.visibleCards!!.size !== 0 && copy.board!!.piles
                                    .get(i)?.visibleCards!!.getLast() !== currentCard
                            ) {
                                println("PILE TO PILE")
                                println(i)
                                println(j)
                                return false
                            }
                        }
                    }
                }
                for (i in 0 until copy.board.piles.size) {
                    if (copy.board.piles.get(i)?.visibleCards != null
                        && copy.board.piles.get(i)?.visibleCards!!.size !== 0
                    ) {
                        val currentCard: Card =
                            copy.board!!.piles.get(i)?.visibleCards!!.getFirst()
                        for (j in 0 until copy.board.validatedCards.size) {
                            if (copy.board.piles.get(i)?.visibleCards!!.size !== 0) {
                                copy.cardFromPileIntoValidatedOnes(currentCard, i, j)
                                if (copy.board.piles.get(i)?.visibleCards!! != null
                                    && copy.board.piles.get(i)?.visibleCards!!
                                        .getFirst() !== currentCard
                                ) {
                                    println("PILE TO VALIDATED")
                                    println(i)
                                    println(j)
                                    return false
                                }
                            }
                        }
                    }
                }
            }else{
                return false
            }
            return true
        }
        return false
    }

    fun lookIfGameWon(game: Game): Boolean {
        val validated: Array<Pile?> = game.board.validatedCards
        for (i in validated.indices) {
            if (validated[i] == null || validated[i]?.getCards() == null || validated[i]?.getCards()?.size === 0
            ) {
                return false
            }
            if (validated[i]?.getCards()?.peek()!!.getValue() !== Card.Value.KING) {
                return false
            }
        }
        return true
    }
}