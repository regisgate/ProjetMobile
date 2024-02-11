package com.example.projetmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val quizId = intent.getIntExtra("quizId", -1)
        setContent {

            MaterialTheme(
                colorScheme = AppTheme,
                typography = AppTypography
            ) {
                QuizScreen(quizId)
            }
        }
    }
}


@Composable
fun QuizScreen(quizId: Int) {
    // Supposons que cette fonction retourne une liste de questions pour le quizId donné
    val questions = getQuizQuestions(quizId)
    // Utiliser le premier élément de la liste pour cet exemple
    val question = questions.firstOrNull()

    // Gérer l'état de l'index de l'option sélectionnée et du feedback
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    val showFeedback = remember { mutableStateOf(false) }

    // Afficher la question et les options de réponse
    Column {
        question?.let {
            Text(it.questionText, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))
            it.options.forEachIndexed { index, option ->
                Button(
                    onClick = {
                        // Mettre à jour l'état lorsqu'une option est sélectionnée
                        selectedOptionIndex = index
                        showFeedback.value = true
                        onAnswerSelected(index, it.correctAnswerIndex)
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (index == selectedOptionIndex) Color.LightGray else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(option)
                }
            }

            // Afficher le feedback si une option a été sélectionnée
            if (showFeedback.value && selectedOptionIndex != null) {
                Text(
                    text = if (selectedOptionIndex == question.correctAnswerIndex) "Bonne réponse!" else "Mauvaise réponse!",
                    color = if (selectedOptionIndex == question.correctAnswerIndex) Color.Green else Color.Red,
                    style = MaterialTheme.typography.titleMedium, // Utilisez le style approprié de Material 3 ici
                    modifier = Modifier.padding(top = 16.dp)
                )

            }
        } ?: Text("Aucune question trouvée pour le quiz ID $quizId")
    }
}
private val AppTheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    // Définissez d'autres couleurs du thème ici si nécessaire
)
private val AppTypography = Typography(
    // Spécifiez ici les styles de typographie que vous souhaitez utiliser dans l'application
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
)


fun onAnswerSelected(selectedIndex: Int, correctIndex: Int) {
    if (selectedIndex == correctIndex) {
        println("Bonne réponse!")
    } else {
        println("Mauvaise réponse!")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuizScreens() {
    QuizScreen(quizId = 1)
}
