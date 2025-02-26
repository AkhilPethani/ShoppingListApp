package com.example.myshoppinglistapp.ui.theme


import ShoppingItemDao
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ShoppingListApp(dao: ShoppingItemDao) {
    val scope = rememberCoroutineScope()
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var newName by remember { mutableStateOf("") }
    var newQuantity by remember { mutableStateOf("") }
    var editItemId by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        shoppingItems = dao.getAllItems()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Item Name") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = newQuantity,
                onValueChange = { newQuantity = it },
                label = { Text("Quantity") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                val quantity = newQuantity.toIntOrNull()
                if (newName.isNotBlank() && quantity != null) {
                    scope.launch {
                        if (editItemId == -1) {
                            val newItem = ShoppingItem(name = newName, quantity = quantity)
                            dao.insertItem(newItem)
                        } else {
                            val existingItem = shoppingItems.find { it.id == editItemId }
                            existingItem?.let {
                                it.name = newName
                                it.quantity = quantity
                                dao.updateItem(it)
                            }
                            editItemId = -1
                        }
                        shoppingItems = dao.getAllItems()
                        newName = ""
                        newQuantity = ""
                    }
                }
            }) {
                Text(if (editItemId == -1) "Add" else "Update")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(shoppingItems.size) { index ->
                val item = shoppingItems[index]
                ShoppingList(
                    item,
                    onEdit = {
                        editItemId = item.id
                        newName = item.name
                        newQuantity = item.quantity.toString()
                    },
                    onDelete = {
                        scope.launch {
                            dao.deleteItem(item)
                            shoppingItems = dao.getAllItems()
                        }
                    }
                )
            }
        }
    }
}

fun ShoppingList(item: ShoppingItem, onEdit: () -> Unit, onDelete: () -> Job) {


}




