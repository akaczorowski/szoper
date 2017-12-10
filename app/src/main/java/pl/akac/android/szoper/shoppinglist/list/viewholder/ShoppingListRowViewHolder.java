package pl.akac.android.szoper.shoppinglist.list.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.akac.android.szoper.R;
import pl.akac.android.szoper.database.entities.ShoppingList;
import pl.akac.android.szoper.shoppinglist.list.adapter.ShoppingListRowListener;
import pl.akac.android.szoper.shoppinglist.list.adapter.ShoppingListsAdapter;

public class ShoppingListRowViewHolder extends ShoppingListsAdapter.BaseViewHolder<ShoppingList> {

  private final ShoppingListRowListener mListener;
  private final ShoppingListsAdapter mAdapter;

  @BindView(R.id.listName)
  TextView listName;

  private ShoppingList shoppingList;

  public ShoppingListRowViewHolder(@NonNull View view, @NonNull ShoppingListRowListener listener,
                                   @NonNull ShoppingListsAdapter adapter) {
    super(view);
    ButterKnife.bind(this, view);
    mListener = listener;
    mAdapter = adapter;
  }

  public void bindTo(@NonNull ShoppingList shoppingList) {
    this.shoppingList = shoppingList;
    listName.setText(shoppingList.getName());
  }

  public ShoppingList getShoppingList() {
    return shoppingList;
  }
}

