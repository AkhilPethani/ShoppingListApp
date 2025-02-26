package com.example.myshoppinglistapp.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var quantity: Int
)
