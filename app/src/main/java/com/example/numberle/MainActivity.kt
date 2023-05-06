package com.example.numberle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.numberle.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var curLine = 0
    private var correctGuesses = 0
    private var curChar = 0
    private var numToGuess = ""
    private var record = 0
    private var recordBeaten = false
    private lateinit var list : MutableList<TextView>
    private lateinit var builder : AlertDialog.Builder
    private lateinit var binding : ActivityMainBinding
    private val Context.dataStore by preferencesDataStore(name = "record")

    private fun createApp(){
        curLine = 0
        curChar = 0
        for(text in list){
            text.setBackgroundColor(resources.getColor(R.color.white))
        }

        for(text in list){
            text.text = ""
        }
        numToGuess = ""
        for(i in 0..4){
            numToGuess += Random.nextInt(0,10)
        }
        binding.guesses.text = "Guesses: $correctGuesses"
        lifecycleScope.launch {
            record = loadRecord()?.toInt() ?: 0
            binding.record.text = "Your Record: $record"
        }
        Log.d("myLog", "Number to guess: $numToGuess")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        builder = AlertDialog.Builder(this)

        setContentView(binding.root)
        list = mutableListOf(binding.n00, binding.n01, binding.n02, binding.n03, binding.n04)
        list.addAll(mutableListOf(binding.n10, binding.n11, binding.n12, binding.n13, binding.n14))
        list.addAll(mutableListOf(binding.n20, binding.n21, binding.n22, binding.n23, binding.n24))
        list.addAll(mutableListOf(binding.n30, binding.n31, binding.n32, binding.n33, binding.n34))
        list.addAll(mutableListOf(binding.n40, binding.n41, binding.n42, binding.n43, binding.n44))
        addListeners()

        createApp()
    }

    private fun addListeners(){
        binding.btn0.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "0"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn1.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "1"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn2.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "2"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn3.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "3"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn4.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "4"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn5.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "5"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn6.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "6"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn7.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "7"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn8.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "8"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.btn9.setOnClickListener {
            if(curChar == 5 || curLine > 4) return@setOnClickListener
            for(text in list){
                if(text.text.isBlank()){
                    text.text = "9"
                    curChar++
                    return@setOnClickListener
                }
            }
        }
        binding.guessBtn.setOnClickListener{
            if(curChar != 5) return@setOnClickListener

            var guess = ""
            val guessChange : CharArray = CharArray(5)
            val possibleChars = numToGuess.toCharArray()
            lateinit var text : TextView

            for(i in 0..4){
                text = list[curLine*5+i]
                guess += text.text.toString()
                guessChange[i] = guess[i]
                if(possibleChars[i].toString() == text.text.toString()){
                    text.setBackgroundColor(resources.getColor(R.color.green))
                    possibleChars[i] = ' '
                    guessChange[i] = ' '
                }
                else{
                    text.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }

            for(i in 0..4){
                if(possibleChars.contains(guessChange[i]) && guessChange[i] != ' '){
                    val index = possibleChars.indexOfFirst{
                        guessChange[i] == it
                    }
                    if(index != -1){
                        possibleChars[index] = ' '
                        list[curLine*5+i].setBackgroundColor(resources.getColor(R.color.yellow))
                    }
                }
            }

            curLine++
            curChar = 0
            if(guess == numToGuess){
                builder.setTitle("Congratulations!").setMessage("You won!").setPositiveButton("YAY!"){
                        _, _ -> createApp()
                }.setCancelable(false).show()
                correctGuesses++
                recordBeaten = correctGuesses > record
                if(recordBeaten){
                    record++
                    lifecycleScope.launch { saveRecord(valueToSave = correctGuesses.toString()) }
                }

            }
            else if(curLine == 5){
                var message = ""
                when(recordBeaten){
                    true -> {
                        message = "Your New Record: $correctGuesses"
                        lifecycleScope.launch { saveRecord(valueToSave = correctGuesses.toString()) }
                    }
                    false -> message =  "Your Score: $correctGuesses"
                }
                builder.setTitle("Better luck next time!").setMessage("You lost!\n$message").setPositiveButton("NOO!"){
                        _,_ -> createApp()
                }.setCancelable(false).show()
                correctGuesses = 0
            }
        }
        binding.removeBtn.setOnClickListener{
            if(curChar == 0) return@setOnClickListener
            list[curLine*5+curChar-1].text = ""
            curChar--
        }
    }

    private suspend fun loadRecord() : String? {
        val prefsKey = stringPreferencesKey(name="record")
        val prefs = dataStore.data.first()
        return prefs[prefsKey]
    }

    private suspend fun saveRecord(valueToSave: String) = lifecycleScope.launch {
        val prefsKey = stringPreferencesKey(name="record")
        dataStore.edit { preferences ->
            preferences[prefsKey] = valueToSave
        }
    }

}