package pl.akac.android.szoper.shoppinglist.details.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.akac.android.szoper.database.entities.ShoppingItem;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ShoppingItemDao {
  @Query("SELECT * FROM ShoppingItem WHERE shoppingListId = :shoppingListId ORDER BY timestamp DESC")
  LiveData<List<ShoppingItem>> getAll(long shoppingListId);

  @Insert(onConflict = IGNORE)
  void insert(ShoppingItem shoppingItem);

  @Delete
  void delete(ShoppingItem shoppingItem);

  @Update
  int update(ShoppingItem shoppingItem);
}
