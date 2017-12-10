package pl.akac.android.szoper.shoppinglist.details.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

import pl.akac.android.szoper.database.entities.ShoppingItem;

public class DiffUtilCallback extends DiffUtil.Callback {

  private List<ShoppingItem> newList;
  private List<ShoppingItem> oldList;

  public DiffUtilCallback(List<ShoppingItem> newList, List<ShoppingItem> oldList) {
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
    ShoppingItem newItem = newList.get(newItemPosition);
    ShoppingItem oldItem = oldList.get(oldItemPosition);

    return oldItem.getId() == newItem.getId();
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    ShoppingItem newItem = newList.get(newItemPosition);
    ShoppingItem oldItem = oldList.get(oldItemPosition);

    return oldItem.getName().equals(newItem.getId());
  }
}
