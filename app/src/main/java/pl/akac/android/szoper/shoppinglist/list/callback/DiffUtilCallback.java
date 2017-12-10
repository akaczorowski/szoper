package pl.akac.android.szoper.shoppinglist.list.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

import pl.akac.android.szoper.database.entities.ShoppingList;

public class DiffUtilCallback extends DiffUtil.Callback {

  private List<ShoppingList> newList;
  private List<ShoppingList> oldList;

  public DiffUtilCallback(List<ShoppingList> newList, List<ShoppingList> oldList) {
    this.newList = newList;
    this.oldList = oldList;
  }

  @Override
  public int getOldListSize() {
    return oldList.size();
  }

  @Override
  public int getNewListSize() {
    return newList.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    ShoppingList newItem = newList.get(newItemPosition);
    ShoppingList oldItem = oldList.get(oldItemPosition);

    return oldItem.getId() == newItem.getId();
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    ShoppingList newItem = newList.get(newItemPosition);
    ShoppingList oldItem = oldList.get(oldItemPosition);

    return oldItem.getName().equals(newItem.getId());
  }
}
