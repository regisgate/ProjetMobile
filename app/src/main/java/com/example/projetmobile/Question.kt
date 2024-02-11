package com.example.projetmobile

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
fun getQuizQuestions(quizId: Int): List<Question> {
    return listOf(
        Question(
            questionText = "Quelle est la capitale de la France ?",
            options = listOf("Paris", "Lyon", "Marseille", "Bordeaux"),
            correctAnswerIndex = 0
        ),
        Question(
            questionText = "Qui est le créateur de Kotlin ?",
            options = listOf("Google", "Microsoft", "JetBrains", "Facebook"),
            correctAnswerIndex = 2
        )
        // Ajoutez d'autres questions ici
    )
}
@Composable
fun QuizQuestionScreen(question: Question, onFinishQuiz: () -> Unit) {
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(question.questionText, modifier = Modifier.padding(bottom = 8.dp))

        question.options.forEachIndexed { index, option ->
            Button(
                onClick = { selectedOptionIndex = index },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(option)
            }
        }

        selectedOptionIndex?.let {
            val feedbackText = if (it == question.correctAnswerIndex) "Correct !" else "Incorrect. La bonne réponse est ${question.options[question.correctAnswerIndex]}."
            Text(feedbackText, modifier = Modifier.padding(top = 8.dp))
        }

        Button(onClick = onFinishQuiz, modifier = Modifier.padding(top = 8.dp)) {
            Text("Suivant")
        }
    }
}


object QuizUI {
    @Composable
    fun QuizScreen(quizId: Int) {
        val questions = getQuizQuestions(quizId)
        var currentQuestionIndex by remember { mutableStateOf(0) }
        val context = LocalContext.current

        QuizQuestionScreen(question = questions[currentQuestionIndex], onFinishQuiz = {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
            } else {
                (context as? Activity)?.finish() // Termine l'activité et retourne à l'accueil
            }
        })
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewQuizScreen() {
    QuizUI.QuizScreen(1)
}
