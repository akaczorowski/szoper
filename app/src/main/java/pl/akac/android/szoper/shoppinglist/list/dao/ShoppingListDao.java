package pl.akac.android.szoper.shoppinglist.list.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.akac.android.szoper.database.entities.ShoppingList;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ShoppingListDao {

  @Query("SELECT * FROM ShoppingList WHERE isArchived = 0 ORDER BY timestamp DESC")
  LiveData<List<ShoppingList>> getAll();

  @Query("SELECT * FROM ShoppingList  WHERE isArchived = 1 ORDER BY timestamp DESC")
  LiveData<List<ShoppingList>> getAllArchived();

  @Insert(onConflict = IGNORE)
  void insert(ShoppingList shoppingList);

  @Delete
  void delete(ShoppingList shoppingList);

  @Update
  int update(ShoppingList shoppingList);
}
