package com.example.projetmobile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

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
fun QuizScreenQuestions(quizId: Int) {
    val questions = getQuizQuestions(quizId)
    // Implémentation...
}

object QuizUI{
    @Composable
    fun QuizScreen(quizId: Int) {
        val questions = getQuizQuestions(quizId)
        // Pour simplifier, affichez uniquement la première question
        val question = questions.firstOrNull()

        Column {
            if (question != null) {
                Text(question.questionText)
                OptionsList(question)
            }
        }
    }
}

@Composable
fun OptionsList(question: Question) {
    var selectedOptionIndex by remember { mutableStateOf(-1) }
    Column {
        question.options.forEachIndexed { index, option ->
            Button(onClick = {
                selectedOptionIndex = index
            }) {
                Text(option)
            }
        }
        if (selectedOptionIndex >= 0) {
            Text(if (selectedOptionIndex == question.correctAnswerIndex) "Bonne réponse!" else "Mauvaise réponse!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuizScreen() {
    QuizUI.QuizScreen(1)
}
