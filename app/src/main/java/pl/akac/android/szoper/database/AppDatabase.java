package pl.akac.android.szoper.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import pl.akac.android.szoper.database.entities.ShoppingItem;
import pl.akac.android.szoper.database.entities.ShoppingList;
import pl.akac.android.szoper.shoppinglist.details.dao.ShoppingItemDao;
import pl.akac.android.szoper.shoppinglist.list.dao.ShoppingListDao;

@Database(entities = {ShoppingList.class, ShoppingItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public abstract ShoppingListDao shoppingListDao();

  public abstract ShoppingItemDao shoppingItemDao();
}
