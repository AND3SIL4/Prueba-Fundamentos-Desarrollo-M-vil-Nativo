package com.codelab.pruebakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    var isComplete: Boolean
)
@Composable
fun TaskItem (task: Task) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

            ) {
        Checkbox(
            checked = task.isComplete,
            onCheckedChange = { isCheked ->
                task.isComplete = isCheked
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = task.title)
            Text(text = task.description)
        }
    }
}

@Preview
@Composable
fun TasklistPreview () {
    val tasks = remember { mutableSetOf(
        Task(1, "Hacer mercado", "Ir al ara a hacer mercado"),
        Task(2, "Limpiar la casa", "Hacer aseo general de la casa")
    ) }
    LazyColumn {
        items(items = tasks) { task ->
            TaskItem(task = task)
        }
    }
}




//class Task (
//    val id: Int,
//    val title: String,
//    val description: String,
//    var isComplete: Boolean = false
//) {
//    fun complete () {
//        isComplete = true
//    }
//    fun unComplete () {
//        isComplete = false
//    }
//    fun printTaskDetails() {
//        println("Task id: $id")
//        println("Task title: $title")
//        println("Task description: $description")
//        println("Task complete: $isComplete")
//    }
//}

