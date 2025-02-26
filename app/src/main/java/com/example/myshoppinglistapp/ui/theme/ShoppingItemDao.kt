import androidx.room.*
import com.example.myshoppinglistapp.ui.theme.ShoppingItem

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shopping_items")
    suspend fun getAllItems(): List<ShoppingItem>

    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)
}
