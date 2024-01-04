import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateQuestionButton.setOnClickListener {
            generateQuestion()
        }

        checkAnswerButton.setOnClickListener {
            checkAnswer()
        }
    }

    private fun generateQuestion() {
        val random = Random(System.currentTimeMillis())

        val selectedLevelRadioButtonId = levelRadioGroup.checkedRadioButtonId
        val selectedLevelRadioButton = findViewById<RadioButton>(selectedLevelRadioButtonId)

        val maxDigits = when (selectedLevelRadioButton.text) {
            "i3" -> 1
            "i7" -> 3
            else -> 1 
        }

        val firstNumber = random.nextInt(10.0.pow(maxDigits).toInt())
        val secondNumber = random.nextInt(10.0.pow(maxDigits).toInt())
        val operator = random.nextInt(4)

        val operatorSymbol = when (operator) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> "+" 
        }

        questionTextView.text = "$firstNumber $operatorSymbol $secondNumber"
    }

    private fun checkAnswer() {
        val userAnswer = userAnswerEditText.text.toString().toDoubleOrNull()

        if (userAnswer == null) {
            Toast.makeText(this, "Invalid answer", Toast.LENGTH_SHORT).show()
            return
        }

        val questionParts = questionTextView.text.split(" ")
        val firstNumber = questionParts[0].toDouble()
        val secondNumber = questionParts[2].toDouble()
        val operatorSymbol = questionParts[1]

        val correctAnswer = when (operatorSymbol) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            else -> firstNumber + secondNumber 
        }

        if (userAnswer == correctAnswer) {
            points++
            Toast.makeText(this, "Correct! Points: $points", Toast.LENGTH_SHORT).show()
        } else {
            points--
            Toast.makeText(this, "Incorrect. Points: $points", Toast.LENGTH_SHORT).show()
        }

        generateQuestion()
    }
}
