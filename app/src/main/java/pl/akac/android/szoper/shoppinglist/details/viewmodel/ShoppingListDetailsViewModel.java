package pl.akac.android.szoper.shoppinglist.details.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import pl.akac.android.szoper.database.AppDatabase;
import pl.akac.android.szoper.database.entities.ShoppingItem;
import pl.akac.android.szoper.shoppinglist.details.dao.ShoppingItemDao;

import static pl.akac.android.szoper.common.tmp.ThreadUtil.runInBackground;


public class ShoppingListDetailsViewModel extends ViewModel {

  private AppDatabase mDatabase;
  private ShoppingItemDao mShoppingItemDao;

  public void setDatabase(AppDatabase database) {
    mDatabase = database;
    mShoppingItemDao = database.shoppingItemDao();
  }

  private LiveData<List<ShoppingItem>> mShoppingItems;
  private long mShoppingListId;

  public long getShoppingListId() {
    return mShoppingListId;
  }

  public void setShoppingListId(long id) {
    mShoppingListId = id;
  }

  public LiveData<List<ShoppingItem>> getShoppingItems() {
    if (mShoppingItems == null) {
      mShoppingItems = mShoppingItemDao.getAll(mShoppingListId);
    }
    return mShoppingItems;
  }

  public void insert(@NonNull final String shoppingItemName) {
    runInBackground(new Runnable() {
      @Override
      public void run() {
        ShoppingItem shoppingItem = new ShoppingItem(shoppingItemName, System.currentTimeMillis(),
                                                     mShoppingListId);
        mShoppingItemDao.insert(shoppingItem);
      }
    });
  }

  public void update(@NonNull final ShoppingItem shoppingItem) {
    runInBackground(new Runnable() {
      @Override
      public void run() {
        mShoppingItemDao.update(shoppingItem);
      }
    });
  }

  public void delete(@NonNull final ShoppingItem shoppingItem) {
    runInBackground(new Runnable() {
      @Override
      public void run() {
        mShoppingItemDao.delete(shoppingItem);
      }
    });
  }
}
