package com.babbel.games

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.babbel.games.viewmodel.GameViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.word.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private val viewModel: GameViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.getResult().observe(this, Observer {
            toolbar_title.text = getString(R.string.score_text, it.first, it.second)
        })

        viewModel.wordPlay.observe(this, Observer {
            text_word.text = it.wordGame.text_spa
            text_challenge.text = when (it.chooseTranslation) {
                true -> it.wordGame.text_eng
                false -> it.randomWord
            }
            startAnimation()
        })

        yes.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
            viewModel.answer(true)
        }

        no.setOnClickListener { view ->

            viewModel.answer(false)
        }
    }

    fun startAnimation() {

        val initialY = text_word.y

        container.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {

                val translateToY = resources.displayMetrics.heightPixels.toFloat() - text_word.height
                val limitToX = resources.displayMetrics.widthPixels - text_word.width


                val alphaAnimatorDisplay = ObjectAnimator.ofFloat(text_word, "alpha", 1f).apply {
                    duration = 2000
                }

                val translationAnimation =
                    ObjectAnimator.ofFloat(text_word, "translationY", translateToY).apply {
                        duration = 5000
                    }


                val animatorSet = AnimatorSet()

                animatorSet.playSequentially(alphaAnimatorDisplay, translationAnimation)
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        text_word.alpha = 0f
                        text_word.y = initialY
                        text_word.x = Random.nextInt(0, limitToX).toFloat()
                        animatorSet.start()
                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationStart(animation: Animator?) {}

                })
                container.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animatorSet.start()

            }
        })

    }

}
