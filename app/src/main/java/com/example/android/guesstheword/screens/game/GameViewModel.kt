package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){

    // The current word
     private val _word = MutableLiveData<String>()
     val word : LiveData<String>
         get() {
             return _word
         }

    // The current score
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished : LiveData<Boolean>
        get() {
            return _gameFinished
        }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private lateinit var _timer: CountDownTimer

    private val _time = MutableLiveData<String>()
    val time : LiveData<String>
        get() = _time

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    init {
        Log.i("gameViewModel", "gameViewModel created")

        _score.value = 0;
        _word.value = ""
        _gameFinished.value = false
        resetList()
        nextWord()

        _timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
               // _time.value = DateUtils.formatElapsedTime(millisUntilFinished)
                _time.value = millisUntilFinished.toString()
            }

            override fun onFinish() {
                _gameFinished.value = true
            }
        }

        _timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("gameViewModel", "gameViewModel destroyed")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball"/*,
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"*/
        )
        wordList.shuffle()
    }


    /**
     * Moves to the next word in the list
     */
    fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //_gameFinished.value = true
            resetList()
        } else {
            _word.value = wordList.removeAt(0)
        }

    }

    /** Methods for buttons presses **/

     fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    fun finishGame() {
        _gameFinished.value = false
    }

/*    override fun onCleared() {
        super.onCleared()
        _timer.cancel()
    }*/

}