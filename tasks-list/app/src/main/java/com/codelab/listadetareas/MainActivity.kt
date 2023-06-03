import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var isComplete: Boolean
)

@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn {
        items(items = tasks) { task ->
            TaskItem(task = task)
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Row {
        Checkbox(
            checked = task.isComplete,
            onCheckedChange = { isChecked ->
                task.isComplete = isChecked
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
fun TaskListPreview() {
    val tasks = listOf(
        Task(1, "Hacer mercado", "Ir al ara a hacer mercado", false),
        Task(2, "Limpiar la casa", "Hacer aseo general de la casa", false)
    )
    TaskList(tasks = tasks)
}
