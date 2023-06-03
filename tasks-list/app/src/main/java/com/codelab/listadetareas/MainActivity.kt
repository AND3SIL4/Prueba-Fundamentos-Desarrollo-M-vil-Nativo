import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var isComplete: Boolean = false
)

@Composable
fun TaskListScreen() {
    val tasks = remember { mutableStateListOf<Task>() }
    val newTaskTitle = remember { mutableStateOf("") }
    val newTaskDescription = remember { mutableStateOf("") }
    val searchQuery = remember { mutableStateOf("") }
    val showCompletedTasks = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SearchBar(searchQuery = searchQuery.value, onSearchQueryChange = { searchQuery.value = it })

        Spacer(modifier = Modifier.height(16.dp))

        TaskInput(
            newTaskTitle = newTaskTitle.value,
            newTaskDescription = newTaskDescription.value,
            onTitleChange = { newTaskTitle.value = it },
            onDescriptionChange = { newTaskDescription.value = it },
            onAddTask = {
                val task = Task(
                    id = tasks.size + 1,
                    title = newTaskTitle.value,
                    description = newTaskDescription.value
                )
                tasks.add(task)
                newTaskTitle.value = ""
                newTaskDescription.value = ""
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val completedTasks = tasks.filter { it.isComplete }
        val pendingTasks = tasks.filter { !it.isComplete }

        TaskList(
            tasks = pendingTasks,
            searchQuery = searchQuery.value,
            onTaskCompleteToggle = { taskId ->
                val task = tasks.find { it.id == taskId }
                task?.isComplete = !task?.isComplete!!
            },
            onTaskDelete = { taskId ->
                tasks.removeIf { it.id == taskId }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showCompletedTasks.value) {
            TaskList(
                tasks = completedTasks,
                searchQuery = searchQuery.value,
                onTaskCompleteToggle = { taskId ->
                    val task = tasks.find { it.id == taskId }
                    task?.isComplete = !task?.isComplete!!
                },
                onTaskDelete = { taskId ->
                    tasks.removeIf { it.id == taskId }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    tasks.removeAll { it.isComplete }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Delete Completed Tasks")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showCompletedTasks.value = false },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Hide Completed Tasks")
            }
        } else {
            Button(
                onClick = { showCompletedTasks.value = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Show Completed Tasks")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        label = { Text("Search for title") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TaskList(
    tasks: List<Task>,
    searchQuery: String,
    onTaskCompleteToggle: (Int) -> Unit,
    onTaskDelete: (Int) -> Unit
) {
    val filteredTasks = tasks.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn {
        items(items = filteredTasks) { task ->
            TaskItem(
                task = task,
                onTaskCompleteToggle = onTaskCompleteToggle,
                onTaskDelete = onTaskDelete
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskCompleteToggle: (Int) -> Unit,
    onTaskDelete: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isComplete,
            onCheckedChange = { isChecked ->
                onTaskCompleteToggle(task.id)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = task.title)
            Text(text = task.description)
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = { onTaskDelete(task.id) }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete Task"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInput(
    newTaskTitle: String,
    newTaskDescription: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddTask: () -> Unit
) {
    Column {
        TextField(
            value = newTaskTitle,
            onValueChange = onTitleChange,
            label = { Text("Tasks title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = newTaskDescription,
            onValueChange = onDescriptionChange,
            label = { Text("Task description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddTask,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Task")
        }
    }
}

@Preview
@Composable
fun TaskListScreenPreview() {
    TaskListScreen()
}
