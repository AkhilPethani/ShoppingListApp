package com.example.myshoppinglistapp.ui.theme

import ShoppingItemDao
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.myshoppinglistapp.ui.theme.MyShoppingListAPPTheme
import com.example.myshoppinglistapp.ui.theme.ShoppingDatabase
import com.example.myshoppinglistapp.ui.theme.ShoppingListApp

class MainActivity : ComponentActivity() {
    lateinit var database: ShoppingDatabase
    lateinit var dao: ShoppingItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            ShoppingDatabase::class.java,
            "shopping_database"
        ).build()
        dao = database.shoppingItemDao()

        setContent {
            MyShoppingListAPPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingListApp(dao)
                }
            }
        }
    }
}
