package com.example.pms.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import com.example.pms.R
import com.example.pms.R.drawable.back
import com.example.pms.model.Card
import com.example.pms.model.Deck
import com.example.pms.model.Game
import com.example.pms.model.Pile
import java.util.ArrayDeque
import java.util.Deque

class Panel(context: Context?, attrs: AttributeSet?) :
    SurfaceView(context, attrs) {
    private val linker: Linker
    var paint: Paint
    private val artificialIntelligence: ArtificialIntelligence
    private val bitmaps = ArrayList<Bitmap>()
    private var missing: Bitmap? = null
    private var lost: Bitmap? = null
    private var newGame: Bitmap? = null
    private var wonGame: Bitmap? = null
    private var positionSpade: Matrix? = null
    private var positionDiamond: Matrix? = null
    private var positionClub: Matrix? = null
    private var positionHeart: Matrix? = null
    private var positionSeen1: Matrix? = null
    private var positionSeen2: Matrix? = null
    private var positionSeen3: Matrix? = null
    private var positionDeck: Matrix? = null
    private var positionPile1: Matrix? = null
    private var positionPile2: Matrix? = null
    private var positionPile3: Matrix? = null
    private var positionPile4: Matrix? = null
    private var positionPile5: Matrix? = null
    private var positionPile6: Matrix? = null
    private var positionPile7: Matrix? = null
    private var positionNew: Matrix? = null
    private var positionTextGameLost: Matrix? = null
    var realWidth: Float = 0f
        private set
    var realHeight: Float = 0f
        private set
    private var game: Game? = null
    private var currentMovingPosition: Matrix? = null
    private var currentMovingCard: Card? = null
    private var cardsUnder: Deque<Card>? = null

    private var xMinNew = 0f
    private var yMinNew = 0f
    private var xMinDeck = 0f
    private var xMaxDeck = 0f
    private var yMinDeck = 0f
    private var yMaxDeck = 0f
    private var xMinDrawnDeck = 0f
    private var xMaxDrawnDeck = 0f
    private var yMinDrawnDeck = 0f
    private var yMaxDrawnDeck = 0f
    private var xMinValidated = 0f
    private var xMaxValidated = 0f
    private var yMinValidated = 0f
    private var yMaxValidated = 0f
    private var xMinPile: ArrayList<Float>? = null
    private var xMaxPile: ArrayList<Float>? = null
    private var yMinPile: ArrayList<Float>? = null
    private var yMaxPile: ArrayList<Float>? = null
    private val cardVisible: ArrayList<ArrayList<Card>> = ArrayList<ArrayList<Card>>()

    init {
        setWillNotDraw(false)
        isFocusable = true
        paint = Paint()
        linker = Linker()
        artificialIntelligence = ArtificialIntelligence()
        loadBitmaps()
    }

    private fun loadBitmaps() {
        bitmaps.add(BitmapFactory.decodeResource(resources, back))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades1))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades2))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades3))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades4))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades5))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades6))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades7))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades8))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades9))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades10))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades11))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades12))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.spades13))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts1))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts2))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts3))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts4))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts5))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts6))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts7))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts8))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts9))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts10))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts11))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts12))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.hearts13))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds1))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds2))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds3))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds4))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds5))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds6))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds7))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds8))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds9))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds10))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds11))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds12))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.diamonds13))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs1))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs2))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs3))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs4))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs5))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs6))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs7))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs8))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs9))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs10))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs11))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs12))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.clubs13))
        missing = BitmapFactory.decodeResource(resources, R.drawable.green2)
        lost = BitmapFactory.decodeResource(resources, R.drawable.lost)
        newGame = BitmapFactory.decodeResource(resources, R.drawable.newgame)
        wonGame = BitmapFactory.decodeResource(resources, R.drawable.won)
    }

    private fun initializePositions(unityBase: Float) {
        positionSpade = Matrix()
        positionDiamond = Matrix()
        positionClub = Matrix()
        positionHeart = Matrix()
        positionSeen1 = Matrix()
        positionSeen2 = Matrix()
        positionSeen3 = Matrix()
        positionDeck = Matrix()
        positionPile1 = Matrix()
        positionPile2 = Matrix()
        positionPile3 = Matrix()
        positionPile4 = Matrix()
        positionPile5 = Matrix()
        positionPile6 = Matrix()
        positionPile7 = Matrix()
        positionTextGameLost = Matrix()
        positionNew = Matrix()

        val unityStep = 1.3f * realWidth / 10.0f
        val unityMin = realWidth / 30.0f

        var tmp = Matrix()
        tmp.postScale(unityBase, unityBase)
        tmp.postTranslate(unityMin, 2 * unityMin)
        positionSpade!!.set(tmp)

        tmp.postTranslate(unityStep, 0f)
        positionDiamond!!.set(tmp)

        tmp.postTranslate(unityStep, 0f)
        positionClub!!.set(tmp)

        tmp.postTranslate(unityStep, 0f)
        positionHeart!!.set(tmp)

        tmp.postTranslate(1.75f * unityStep, 0f)
        positionSeen1!!.set(tmp)

        tmp.postTranslate(unityMin, 0f)
        positionSeen2!!.set(tmp)

        tmp.postTranslate(unityMin, 0f)
        positionSeen3!!.set(tmp)

        tmp.postTranslate(unityStep, 0f)
        positionDeck!!.set(tmp)

        tmp = Matrix()
        tmp.postScale(unityBase, unityBase)
        tmp.postTranslate(unityMin, 3.5f * unityStep)
        positionPile1!!.set(tmp)

        tmp.postTranslate(unityStep, 0f)
        positionPile2!!.set(tmp)
        tmp.postTranslate(unityStep, 0f)
        positionPile3!!.set(tmp)
        tmp.postTranslate(unityStep, 0f)
        positionPile4!!.set(tmp)
        tmp.postTranslate(unityStep, 0f)
        positionPile5!!.set(tmp)
        tmp.postTranslate(unityStep, 0f)
        positionPile6!!.set(tmp)
        tmp.postTranslate(unityStep, 0f)
        positionPile7!!.set(tmp)

        initPositionForListener(unityMin, unityStep, unityBase)

        tmp = Matrix()
        var scale = realHeight / (8.0f * lost!!.height)
        tmp.postScale(scale, scale)
        tmp.postTranslate(
            realWidth / 2.0f - lost!!.width * scale / 2.0f,
            realHeight - realHeight / 4.0f
        )
        positionTextGameLost!!.set(tmp)

        tmp = Matrix()
        scale = realHeight / (12.0f * newGame!!.height)
        tmp.postScale(scale, scale)
        tmp.postTranslate(realWidth - realHeight / 8.0f, realHeight - realHeight / 8.0f)
        positionNew!!.set(tmp)
    }

    private fun initPositionForListener(unityMin: Float, unityStep: Float, unityBase: Float) {
        xMinDeck = unityMin + 3.0f * unityStep + 1.75f * unityStep + 2.0f * unityMin + unityStep
        xMaxDeck = xMinDeck + unityStep
        yMinDeck = 2 * unityMin
        yMaxDeck = yMinDeck + bitmaps[0].height

        xMinDrawnDeck = unityMin + 3.0f * unityStep + 1.75f * unityStep
        xMaxDrawnDeck = xMaxDrawnDeck + 2.0f * unityMin
        yMinDrawnDeck = yMinDeck
        yMaxDrawnDeck = yMaxDeck

        xMinValidated = unityMin
        xMaxValidated = xMinValidated + 4 * unityStep
        yMinValidated = yMinDeck
        yMaxValidated = yMaxDeck

        xMinPile = ArrayList()
        xMaxPile = ArrayList()
        yMinPile = ArrayList()
        yMaxPile = ArrayList()

        xMinPile!!.add(unityMin)
        xMaxPile!!.add(unityMin + unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinPile!!.add(unityMin + unityStep)
        xMaxPile!!.add(unityMin + 2 * unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinPile!!.add(unityMin + 2 * unityStep)
        xMaxPile!!.add(unityMin + 3 * unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinPile!!.add(unityMin + 3 * unityStep)
        xMaxPile!!.add(unityMin + 4 * unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinPile!!.add(unityMin + 4 * unityStep)
        xMaxPile!!.add(unityMin + 5 * unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinPile!!.add(unityMin + 5 * unityStep)
        xMaxPile!!.add(unityMin + 6 * unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinPile!!.add(unityMin + 6 * unityStep)
        xMaxPile!!.add(unityMin + 7 * unityStep)
        yMinPile!!.add(5.1f * unityStep)
        yMaxPile!!.add(5.1f * unityStep + bitmaps[0].height * unityBase)

        xMinNew = realWidth - realHeight / 8.0f
        yMinNew = realHeight - realHeight / 8.0f
    }

    //Works like screen refreshing
    public override fun onDraw(canvas: Canvas) {
        realWidth = canvas.width.toFloat()
        realHeight = canvas.height.toFloat()
        val unityBase = (1.0f / 9.0f * realWidth) / bitmaps[1].width

        initializePositions(unityBase)

        displayCards(canvas)

        if (artificialIntelligence.lookIfGameLost(game!!)) {
            canvas.drawBitmap(lost!!, positionTextGameLost!!, null)
        } else if (artificialIntelligence.lookIfGameWon(game!!)) {
            canvas.drawBitmap(wonGame!!, positionTextGameLost!!, null)
        }
        canvas.drawBitmap(newGame!!, positionNew!!, null)
    }

    private fun displayCards(canvas: Canvas) {
        canvas.drawColor(resources.getColor(R.color.colorPrimary))

        //UP
        val validatedCards: Array<Pile?> = game?.board!!.validatedCards
        drawPositionForValidated(canvas, validatedCards, positionSpade, 0)
        drawPositionForValidated(canvas, validatedCards, positionHeart, 1)
        drawPositionForValidated(canvas, validatedCards, positionDiamond, 2)
        drawPositionForValidated(canvas, validatedCards, positionClub, 3)

        val deck: Deck = game!!.board.deck
        val drawn = deck.drawnCards
        drawPositionForSeen(canvas, drawn)

        if (deck.unseen.size > 0) {
            canvas.drawBitmap(bitmaps[0], positionDeck!!, null)
        } else {
            canvas.drawBitmap(missing!!, positionDeck!!, null)
        }

        //DOWN
        val piles: Array<Pile?> = game!!.board.piles
        drawPositionForPiles(canvas, piles, positionPile1, 0)
        drawPositionForPiles(canvas, piles, positionPile2, 1)
        drawPositionForPiles(canvas, piles, positionPile3, 2)
        drawPositionForPiles(canvas, piles, positionPile4, 3)
        drawPositionForPiles(canvas, piles, positionPile5, 4)
        drawPositionForPiles(canvas, piles, positionPile6, 5)
        drawPositionForPiles(canvas, piles, positionPile7, 6)

        //MOVING
        if (currentMovingPosition != null) {
            val unityMin = realWidth / 30.0f
            if (cardsUnder != null && cardsUnder!!.size > 0) {
                val saveStateCards: Deque<Card> = ArrayDeque<Card>()
                while (cardsUnder!!.size > 0) {
                    val current: Card = cardsUnder!!.pop()
                    currentMovingPosition!!.postTranslate(0f, 2 * unityMin)
                    val bitmapPosition: Int =
                        (current.getSuit().ordinal * 13) + current.getValue().ordinal + 1
                    canvas.drawBitmap(bitmaps[bitmapPosition], currentMovingPosition!!, null)
                    saveStateCards.push(current)
                }
                while (saveStateCards.size > 0) {
                    val card: Card = saveStateCards.pop()
                    cardsUnder!!.push(card)
                }
            } else {
                val bitmapPosition: Int =
                    (currentMovingCard?.getSuit()?.ordinal!! * 13) + currentMovingCard!!.getValue().ordinal + 1
                canvas.drawBitmap(bitmaps[bitmapPosition], currentMovingPosition!!, null)
            }
        }
    }

    private fun drawPositionForValidated(
        canvas: Canvas,
        validatedCards: Array<Pile?>,
        position: Matrix?,
        index: Int
    ) {
        if (validatedCards.size > index && validatedCards[index] != null && validatedCards[index]?.getCards()!!.size > 0
        ) {
            val card: Card = validatedCards[index]?.getCards()?.peek()!!
            if (card === currentMovingCard) {
                return
            }
            val bitmapPosition: Int =
                (card.getSuit().ordinal * 13) + card.getValue().ordinal + 1
            canvas.drawBitmap(bitmaps[bitmapPosition], position!!, null)
        } else {
            canvas.drawBitmap(missing!!, position!!, null)
        }
    }

    private fun drawPositionForSeen(canvas: Canvas, drawn: ArrayDeque<Card?>) {
        if (drawn.size > 2) {
            canvas.drawBitmap(bitmaps[0], positionSeen3!!, null)
        }
        if (drawn.size > 1) {
            canvas.drawBitmap(bitmaps[0], positionSeen2!!, null)
        }
        if (drawn.size > 0) {
            val card: Card = drawn.peek()!!
            if (card === currentMovingCard) {
                return
            }
            val bitmapPosition: Int =
                (card.getSuit().ordinal * 13) + card.getValue().ordinal + 1
            canvas.drawBitmap(bitmaps[bitmapPosition], positionSeen1!!, null)
        }
    }

    private fun drawPositionForPiles(
        canvas: Canvas,
        piles: Array<Pile?>,
        position: Matrix?,
        index: Int
    ) {
        if (piles.size > index && piles[index] != null && piles[index]?.visibleCards!!.size > 0
        ) {
            var size: Int = piles[index]?.getCards()!!.size
            while (size > 0) {
                canvas.drawBitmap(bitmaps[0], position!!, null)
                val unityMin = realWidth / 30.0f
                position.postTranslate(0f, unityMin)
                yMaxPile!![index] = yMaxPile!![index] + unityMin
                size--
            }
            size = piles[index]?.visibleCards!!.size
            val inverse: ArrayDeque<Card> = ArrayDeque<Card>()
            val underThisCard = piles[index]?.getCardsUnderThisOne(currentMovingCard)
            while (size > 0) {
                val card: Card = piles[index]?.visibleCards!!.pop()
                if (underThisCard != null && underThisCard.contains(card)) {
                    size--
                    continue
                }
                inverse.push(card)
                size--
            }
            cardVisible.add(ArrayList<Card>())
            while (inverse.size > 0) {
                val card: Card = inverse.pop()
                val bitmapPosition: Int =
                    (card.getSuit().ordinal * 13) + card.getValue().ordinal + 1
                canvas.drawBitmap(bitmaps[bitmapPosition], position!!, null)
                val unityMin = realWidth / 30.0f
                yMaxPile!![index] = yMaxPile!![index] + 2 * unityMin
                position.postTranslate(0f, 2 * unityMin)

                cardVisible[index].add(card)
                piles[index]?.visibleCards!!.push(card)
            }
            if (underThisCard != null) {
                while (underThisCard.size > 0) {
                    val current: Card = underThisCard.pop()
                    piles[index]?.visibleCards!!.push(current)
                }
            }
        } else {
            canvas.drawBitmap(missing!!, position!!, null)
        }
    }

    private fun updateVisibleCards() {
        val piles: Array<Pile?> = game?.board!!.piles
        for (i in piles.indices) {
            if (piles[i] != null && piles[i]?.visibleCards!!.size > 0 && currentMovingCard != null) {
                var size: Int = piles[i]?.visibleCards!!.size
                val inverse: ArrayDeque<Card> = ArrayDeque<Card>()
                val underThisCard = piles[i]?.getCardsUnderThisOne(currentMovingCard)
                while (size > 0) {
                    val card: Card = piles[i]?.visibleCards!!.pop()
                    if (underThisCard != null && underThisCard.contains(card)) {
                        size--
                        continue
                    }
                    inverse.push(card)
                    size--
                }
                cardVisible.add(ArrayList<Card>())
                while (inverse.size > 0) {
                    val card: Card = inverse.pop()
                    cardVisible[i].add(card)
                    piles[i]?.visibleCards?.push(card)
                }
                if (underThisCard != null) {
                    while (underThisCard.size > 0) {
                        val current: Card = underThisCard.pop()
                        piles[i]?.visibleCards?.push(current)
                    }
                }
            }
        }
    }

    fun receiveTouchEvent(x: Float, y: Float) {
        updateVisibleCards()
        val unity = 2 * realWidth / 30.0f
        if (x > xMinDeck && x < xMaxDeck && y < yMaxDeck && y > yMinDeck) {
            game = linker.deckAction(game!!)
        } else if (x > xMinDrawnDeck && x < xMaxDrawnDeck && y < yMaxDrawnDeck && y > yMinDrawnDeck) {
            if (game!!.board.deck.drawnCards.size > 0) {
                currentMovingPosition = positionSeen1
                currentMovingCard = game?.board?.deck?.drawnCards!!.peek()
                Log.e("CARD", "1 $currentMovingCard")
                Log.e("CARD", "1 " + game!!.board.deck.drawnCards)
            }
        } else if (x > xMinPile!![0] && x < xMaxPile!![0] && y < yMaxPile!![0] && y > yMinPile!![0]) {
            for (i in cardVisible[0].indices.reversed()) {
                if (y < yMaxPile!![0] - (cardVisible[0].size - 1 - i) * unity
                    && y > yMaxPile!![0] - (cardVisible[0].size - i) * unity
                ) {
                    currentMovingPosition = positionPile1
                    currentMovingCard = cardVisible[0][i]
                    cardsUnder =
                        game!!.board.piles[0]?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "2 $currentMovingCard")
                    Log.e("CARD", "2 " + cardVisible[0][i])
                    Log.e("CARD", "2 " + game!!.board.piles.get(0)?.visibleCards!!.peek())
                    break
                }
            }
        } else if (x > xMinPile!![1] && x < xMaxPile!![1] && y < yMaxPile!![1] && y > yMinPile!![1]) {
            for (i in cardVisible[1].indices.reversed()) {
                if (y < yMaxPile!![1] - (cardVisible[1].size - 1 - i) * unity
                    && y > yMaxPile!![1] - (cardVisible[1].size - i) * unity
                ) {
                    currentMovingPosition = positionPile2
                    currentMovingCard = cardVisible[1][i]
                    cardsUnder =
                        game!!.board.piles.get(1)?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "3 $currentMovingCard")
                    Log.e("CARD", "3 " + cardVisible[1][i])
                    break
                }
            }
        } else if (x > xMinPile!![2] && x < xMaxPile!![2] && y < yMaxPile!![2] && y > yMinPile!![2]) {
            for (i in cardVisible[2].indices.reversed()) {
                if (y < yMaxPile!![2] - (cardVisible[2].size - 1 - i) * unity
                    && y > yMaxPile!![2] - (cardVisible[2].size - i) * unity
                ) {
                    currentMovingPosition = positionPile3
                    currentMovingCard = cardVisible[2][i]
                    cardsUnder =
                        game!!.board.piles.get(2)?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "4 $currentMovingCard")
                    Log.e("CARD", "4 " + cardVisible[2][i])
                    break
                }
            }
        } else if (x > xMinPile!![3] && x < xMaxPile!![3] && y < yMaxPile!![3] && y > yMinPile!![3]) {
            for (i in cardVisible[3].indices.reversed()) {
                if (y < yMaxPile!![3] - (cardVisible[3].size - 1 - i) * unity
                    && y > yMaxPile!![3] - (cardVisible[3].size - i) * unity
                ) {
                    currentMovingPosition = positionPile4
                    currentMovingCard = cardVisible[3][i]
                    cardsUnder =
                        game!!.board.piles.get(3)?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "5 $currentMovingCard")
                    Log.e("CARD", "6 " + cardVisible[3][i])
                    break
                }
            }
        } else if (x > xMinPile!![4] && x < xMaxPile!![4] && y < yMaxPile!![4] && y > yMinPile!![4]) {
            for (i in cardVisible[4].indices.reversed()) {
                if (y < yMaxPile!![4] - (cardVisible[4].size - 1 - i) * unity
                    && y > yMaxPile!![4] - (cardVisible[4].size - i) * unity
                ) {
                    currentMovingPosition = positionPile5
                    currentMovingCard = cardVisible[4][i]
                    cardsUnder =
                        game!!.board.piles.get(4)?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "6 $currentMovingCard")
                    Log.e("CARD", "6 " + cardVisible[4][i])
                    break
                }
            }
        } else if (x > xMinPile!![5] && x < xMaxPile!![5] && y < yMaxPile!![5] && y > yMinPile!![5]) {
            for (i in cardVisible[5].indices.reversed()) {
                if (y < yMaxPile!![5] - (cardVisible[5].size - 1 - i) * unity
                    && y > yMaxPile!![5] - (cardVisible[5].size - i) * unity
                ) {
                    currentMovingPosition = positionPile6
                    currentMovingCard = cardVisible[5][i]
                    cardsUnder =
                        game!!.board.piles.get(5)?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "7 $currentMovingCard")
                    Log.e("CARD", "7 " + cardVisible[5][i])
                    break
                }
            }
        } else if (x > xMinPile!![6] && x < xMaxPile!![6] && y < yMaxPile!![6] && y > yMinPile!![6]) {
            for (i in cardVisible[6].indices.reversed()) {
                if (y < yMaxPile!![6] - (cardVisible[6].size - 1 - i) * unity
                    && y > yMaxPile!![6] - (cardVisible[6].size - i) * unity
                ) {
                    currentMovingPosition = positionPile7
                    currentMovingCard = cardVisible[6][i]
                    cardsUnder =
                        game!!.board.piles.get(6)?.getCardsUnderThisOne(currentMovingCard!!)
                    Log.e("CARD", "8 $currentMovingCard")
                    Log.e("CARD", "8 " + cardVisible[6][i])
                    break
                }
            }
        }
        Log.e("CARD", "Current moving card $currentMovingCard")
    }

    fun receiveMovingEvent(x: Float, y: Float) {
        var x = x
        var y = y
        if (currentMovingPosition != null) {
            currentMovingPosition!!.reset()
            val widthStep = 1.3f * realWidth / 10.0f
            x = x - widthStep / 2.0f
            y = y - widthStep
            val unityBase = (1.0f / 9.0f * realWidth) / bitmaps[1].width
            currentMovingPosition!!.postScale(unityBase, unityBase)
            currentMovingPosition!!.postScale(1.4f, 1.4f)
            currentMovingPosition!!.postTranslate(x, y)
        }
    }

    fun receiveStopEvent(x: Float, y: Float) {
        if (currentMovingCard != null) {
            if (x > xMinValidated && x < xMaxValidated && y < yMaxValidated && y > yMinValidated) {
                if (currentMovingCard!!.getSuit() === Card.Suit.SPADE) {
                    game = linker.spadeAction(game!!, currentMovingCard!!)
                } else if (currentMovingCard!!.getSuit() === Card.Suit.DIAMOND) {
                    game = linker.diamondAction(game!!, currentMovingCard!!)
                } else if (currentMovingCard!!.getSuit() === Card.Suit.CLUB) {
                    game = linker.clubAction(game!!, currentMovingCard!!)
                } else if (currentMovingCard!!.getSuit() === Card.Suit.HEART) {
                    game = linker.heartAction(game!!, currentMovingCard!!)
                }
            } else if (x > xMinPile!![0] && x < xMaxPile!![0] && y < yMaxPile!![0] && y > yMinPile!![0]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 0)
            } else if (x > xMinPile!![1] && x < xMaxPile!![1] && y < yMaxPile!![1] && y > yMinPile!![1]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 1)
            } else if (x > xMinPile!![2] && x < xMaxPile!![2] && y < yMaxPile!![2] && y > yMinPile!![2]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 2)
            } else if (x > xMinPile!![3] && x < xMaxPile!![3] && y < yMaxPile!![3] && y > yMinPile!![3]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 3)
            } else if (x > xMinPile!![4] && x < xMaxPile!![4] && y < yMaxPile!![4] && y > yMinPile!![4]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 4)
            } else if (x > xMinPile!![5] && x < xMaxPile!![5] && y < yMaxPile!![5] && y > yMinPile!![5]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 5)
            } else if (x > xMinPile!![6] && x < xMaxPile!![6] && y < yMaxPile!![6] && y > yMinPile!![6]) {
                game = linker.pileAction(game!!, currentMovingCard!!, 6)
            }
        }
        currentMovingCard = null
        currentMovingPosition = null
        cardsUnder = null
    }

    fun setGame(game: Game?) {
        this.game = game
    }

    fun getGame(): Game? {
        return game
    }
}