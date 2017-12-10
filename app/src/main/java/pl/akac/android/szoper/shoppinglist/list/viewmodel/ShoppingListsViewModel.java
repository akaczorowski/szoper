package pl.akac.android.szoper.shoppinglist.list.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import pl.akac.android.szoper.database.AppDatabase;
import pl.akac.android.szoper.database.entities.ShoppingList;
import pl.akac.android.szoper.shoppinglist.list.dao.ShoppingListDao;

import static pl.akac.android.szoper.common.tmp.ThreadUtil.runInBackground;

public class ShoppingListsViewModel extends ViewModel {

  private AppDatabase mDatabase;
  private ShoppingListDao mShoppingListDao;

  public void setDatabase(AppDatabase database) {
    mDatabase = database;
    mShoppingListDao = database.shoppingListDao();
  }

  private LiveData<List<ShoppingList>> mShoppingLists;
  private LiveData<List<ShoppingList>> mArchivedShoppingLists;

  public LiveData<List<ShoppingList>> getShoppingLists() {
    if (mShoppingLists == null) {
      mShoppingLists = mShoppingListDao.getAll();
    }
    return mShoppingLists;
  }

  public LiveData<List<ShoppingList>> getArchivedShoppingLists() {
    if (mArchivedShoppingLists == null) {
      mArchivedShoppingLists = mDatabase.shoppingListDao().getAllArchived();
    }
    return mArchivedShoppingLists;
  }

  public void insert(@NonNull final String shoppingListName) {
    runInBackground(new Runnable() {
      @Override
      public void run() {
        ShoppingList shoppingList = new ShoppingList(shoppingListName, System.currentTimeMillis());
        mShoppingListDao.insert(shoppingList);
      }
    });
  }

  public void update(@NonNull final ShoppingList shoppingList) {
    runInBackground(new Runnable() {
      @Override
      public void run() {
        mShoppingListDao.update(shoppingList);
      }
    });
  }

  public void delete(@NonNull final ShoppingList shoppingList) {
    runInBackground(new Runnable() {
      @Override
      public void run() {
        mShoppingListDao.delete(shoppingList);
      }
    });
  }
}
