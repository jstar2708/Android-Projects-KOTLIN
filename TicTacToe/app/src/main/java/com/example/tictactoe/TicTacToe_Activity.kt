 package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class TicTacToe_Activity : AppCompatActivity() {

    //Tic tac toe board reference
    private var boardNum : Array<Array<Int>> = arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 9))
    //Last position clicked by player 1 or 2
    private var posClicked = 0
    //Total steps till now (At most 9 steps are possible)
    private var step = 0
    //Player 1 name
    private var p1Name : String? = ""
    //Player 2 name
    private var p2Name : String? = ""

    //Respective textView references for the tic tac board
    private var pos1 : TextView? = null
    private var pos2 : TextView? = null
    private var pos3 : TextView? = null
    private var pos4 : TextView? = null
    private var pos5 : TextView? = null
    private var pos6 : TextView? = null
    private var pos7 : TextView? = null
    private var pos8 : TextView? = null
    private var pos9 : TextView? = null
    private var p1chance : ImageView? = null
    private var p2chance : ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        val intent : Intent = intent

        p1Name = intent.getStringExtra("p1name")
        p2Name = intent.getStringExtra("p2name")

        val p1TextView = findViewById<TextView>(R.id.p1_name)
        val p2TextView = findViewById<TextView>(R.id.p2_name)

        p1TextView.text = p1Name
        p2TextView.text = p2Name

        pos1 = findViewById<TextView>(R.id.pos1)
        pos2 = findViewById<TextView>(R.id.pos2)
        pos3 = findViewById<TextView>(R.id.pos3)
        pos4 = findViewById<TextView>(R.id.pos4)
        pos5 = findViewById<TextView>(R.id.pos5)
        pos6 = findViewById<TextView>(R.id.pos6)
        pos7 = findViewById<TextView>(R.id.pos7)
        pos8 = findViewById<TextView>(R.id.pos8)
        pos9 = findViewById<TextView>(R.id.pos9)
        p1chance = findViewById(R.id.p1_chance)
        p2chance = findViewById(R.id.p2_chance)
        p1chance?.setImageResource(R.drawable.circle2)


        //Reset Button to reset the game
        val resetButton = findViewById<Button>(R.id.reset)
        resetButton.setOnClickListener{
            reset()
        }
    }

    //This fun checks whether the position clicked by the player is valid or not i.e is already occupied of not
    private fun isValid(position : Int) : Boolean{
        return when (position){
            1-> pos1?.text == "--"
            2-> pos2?.text == "--"
            3-> pos3?.text == "--"
            4-> pos4?.text == "--"
            5-> pos5?.text == "--"
            6-> pos6?.text == "--"
            7-> pos7?.text == "--"
            8-> pos8?.text == "--"
            9-> pos9?.text == "--"
            else-> true
        }
    }

    //This fun resets the game
    private fun reset(){
        boardNum = arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 9))
        pos1?.text = "--"
        pos2?.text = "--"
        pos3?.text = "--"
        pos4?.text = "--"
        pos5?.text = "--"
        pos6?.text = "--"
        pos7?.text = "--"
        pos8?.text = "--"
        pos9?.text = "--"
        step = 0
        posClicked = 0
        pos1?.setTextColor(resources.getColor(R.color.black))
        pos2?.setTextColor(resources.getColor(R.color.black))
        pos3?.setTextColor(resources.getColor(R.color.black))
        pos4?.setTextColor(resources.getColor(R.color.black))
        pos5?.setTextColor(resources.getColor(R.color.black))
        pos6?.setTextColor(resources.getColor(R.color.black))
        pos7?.setTextColor(resources.getColor(R.color.black))
        pos8?.setTextColor(resources.getColor(R.color.black))
        pos9?.setTextColor(resources.getColor(R.color.black))


    }


    //Checks if there any winner till now (invokes after every move by any player)
    private fun isWinner() : Boolean{
        if(boardNum[0][0] == boardNum[0][1] && boardNum[0][2] == boardNum[0][0]){
            return true;
        }
        if(boardNum[0][0] == boardNum[1][0] && boardNum[0][0] == boardNum[2][0]){
            return true;
        }
        if(boardNum[0][0] == boardNum[1][1] && boardNum[0][0] == boardNum[2][2]){
            return true;
        }
        if(boardNum[1][0] == boardNum[1][1] && boardNum[1][0] == boardNum[1][2]){
            return true;
        }
        if(boardNum[2][0] == boardNum[2][1] && boardNum[2][0] == boardNum[2][2]){
            return true;
        }
        if(boardNum[2][0] == boardNum[1][1] && boardNum[2][0] == boardNum[0][2]){
            return true;
        }
        if(boardNum[0][1] == boardNum[1][1] && boardNum[0][1] == boardNum[2][1]){
            return true;
        }
        if(boardNum[0][2] == boardNum[1][2] && boardNum[0][2] == boardNum[2][2]){
            return true;
        }
        if(boardNum[1][0] == boardNum[1][1] && boardNum[1][0] == boardNum[1][2]){
            return true;
        }
        return false
    }

    //Checks if the match is drawn i.e 9 steps are completed
    private fun isMatchDrawn() : Boolean{
        if(step >= 9){
            return true;
        }
        return false;
    }

    /*
    This fun is used to swap the names, chance marker of the players.
    This is done so that after a game finishes the player that moved first now moves second and
    the player who moved second moves first.
     */
    private fun swapAll(){
        var temp = p1Name
        p1Name = p2Name
        p2Name = temp

        var temp2 = p1chance;
        p1chance = p2chance;
        p2chance = temp2
    }


    //This defines what happens when any of the position is clicked
    fun pos1(view: android.view.View) {
        if(isValid(1)){
            posClicked = 1
            pos1?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos1?.setTextColor(resources.getColor(R.color.black))
                boardNum[0][0] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos1?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[0][0] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_SHORT).show()
                }
                swapAll()
                reset()

                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos2(view: android.view.View) {
        if(isValid(2)){
            posClicked = 2
            pos2?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos2?.setTextColor(resources.getColor(R.color.black))
                boardNum[0][1] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos2?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[0][1] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
            step++
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos3(view: android.view.View) {
        if(isValid(3)){
            posClicked = 3
            pos3?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos3?.setTextColor(resources.getColor(R.color.black))
                boardNum[0][2] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos3?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[0][2] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos4(view: android.view.View) {
        if(isValid(4)){
            posClicked = 4
            pos4?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos4?.setTextColor(resources.getColor(R.color.black))
                boardNum[1][0] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos4?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[1][0] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos5(view: android.view.View) {
        if(isValid(5)){
            posClicked = 5
            pos5?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos5?.setTextColor(resources.getColor(R.color.black))
                boardNum[1][1] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos5?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[1][1] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos6(view: android.view.View) {
        if(isValid(6)){
            posClicked = 6
            pos6?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos6?.setTextColor(resources.getColor(R.color.black))
                boardNum[1][2] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos6?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[1][2] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos7(view: android.view.View) {
        if(isValid(7)){
            posClicked = 7
            pos7?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos7?.setTextColor(resources.getColor(R.color.black))
                boardNum[2][0] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos7?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[2][0] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos8(view: android.view.View) {
        if(isValid(8)){
            posClicked = 8
            pos8?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos8?.setTextColor(resources.getColor(R.color.black))
                boardNum[2][1] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos8?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[2][1] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++
            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }
    fun pos9(view: android.view.View) {
        if(isValid(9)){
            posClicked = 9
            pos9?.text = if(step % 2 == 0){
                p1chance?.setImageResource(0)
                p2chance?.setImageResource(R.drawable.circle2)
                pos9?.setTextColor(resources.getColor(R.color.black))
                boardNum[2][2] = -1
                "X"
            } else{
                p1chance?.setImageResource(R.drawable.circle2)
                p2chance?.setImageResource(0)
                pos9?.setTextColor(resources.getColor(R.color.redA400))
                boardNum[2][2] = -2
                "O"
            }
            if(isWinner()){
                if(step % 2 == 0){
                    Toast.makeText(this, "$p1Name is the winner!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "$p2Name is the winner!", Toast.LENGTH_LONG).show()
                }
                swapAll()
                reset()
                return
            }
            step++

            if(isMatchDrawn()){
                Toast.makeText(this, "Match is Drawn!", Toast.LENGTH_SHORT).show()
                reset()
                return
            }
        }
        else{
            Toast.makeText(this, "Position already occupied", Toast.LENGTH_SHORT).show()
        }
    }


}