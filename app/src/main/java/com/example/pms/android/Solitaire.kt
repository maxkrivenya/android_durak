package com.example.pms.android

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.FragmentManager
import com.example.pms.R
import com.example.pms.model.Game
import com.example.pms.view.Panel
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException

class Solitaire : AppCompatActivity() {
    private var x = 0f
    private var y = 0f
    private var panel: Panel? = null
    private var game: Game? = null

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action: Int = MotionEventCompat.getActionMasked(e)

        if (action == MotionEvent.ACTION_DOWN) {
            x = e.x
            y = e.y

            //compute touch param
            val width: Float = panel!!.realWidth
            val height: Float = panel!!.realHeight
            val xMinNew = width - height / 8.0f
            val yMinNew = height - height / 8.0f
            if (x > xMinNew && y > yMinNew) {
                createAndShowAlertDialog()
                return false
            }

            panel!!.receiveTouchEvent(x, y)
            setContentView(panel)
            return false
        } else if (action == MotionEvent.ACTION_MOVE) {
            x = e.x
            y = e.y

            val rect = Rect()
            val win: Window = getWindow()
            win.decorView.getWindowVisibleDisplayFrame(rect)
            val statusBarHeight = rect.top
            val contentViewTop = win.findViewById<View>(Window.ID_ANDROID_CONTENT).top
            val titleBarHeight = contentViewTop - statusBarHeight

            panel!!.receiveMovingEvent(x, y - titleBarHeight - statusBarHeight)
            setContentView(panel)
            return false
        } else if (action == MotionEvent.ACTION_UP) {
            panel!!.receiveStopEvent(x, y)
            setContentView(panel)
            return false
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        //initialize game
        game = Game()
        game!!.initGame()

        //draw the game
        panel = Panel(getApplicationContext(), null)
        panel!!.setGame(game)
        setContentView(panel)
    }

    override fun onPause() {
        super.onPause()
        if (panel != null) {
            val gameToSave: Game = panel!!.getGame()!!
            write(gameToSave)
        }
    }

    override fun onResume() {
        super.onResume()
        val gameLoaded: Game? = read()
        if (gameLoaded != null) {
            panel!!.setGame(gameLoaded)
            setContentView(panel)
        }
    }

    private fun createAndShowAlertDialog() {
        val fragmentManager: FragmentManager = getSupportFragmentManager()
        val customDialog: CustomDialogBox = CustomDialogBox()
        customDialog.setCancelable(false)
        customDialog.show(fragmentManager, "Custom Dialog")
    }

    fun customDialogYes() {
        game = Game()
        game!!.initGame()
        panel!!.setGame(game)
        setContentView(panel)
    }

    private fun write(game: Game) {
        if (panel != null) {
            val filename = "savingGame.srl"
            var out: ObjectOutput? = null

            try {
                out = ObjectOutputStream(
                    FileOutputStream(
                        File(
                            getFilesDir(),
                            ""
                        ).toString() + File.separator + filename
                    )
                )
                out.writeObject(game)
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun read(): Game? {
        val input: ObjectInputStream
        val filename = "savingGame.srl"

        try {
            input = ObjectInputStream(
                FileInputStream(
                    File(
                        File(
                            getFilesDir(),
                            ""
                        ).toString() + File.separator + filename
                    )
                )
            )
            val game: Game = input.readObject() as Game
            input.close()
            return game
        } catch (e: StreamCorruptedException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }
}