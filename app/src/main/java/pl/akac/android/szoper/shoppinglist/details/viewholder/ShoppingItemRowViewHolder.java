package pl.akac.android.szoper.shoppinglist.details.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.akac.android.szoper.R;
import pl.akac.android.szoper.database.entities.ShoppingItem;
import pl.akac.android.szoper.shoppinglist.details.adapter.ShoppingItemsAdapter;
import pl.akac.android.szoper.shoppinglist.details.adapter.ShoppingItemRowListener;


public class ShoppingItemRowViewHolder extends ShoppingItemsAdapter.BaseViewHolder<ShoppingItem> {


  private final ShoppingItemRowListener mListener;
  private final ShoppingItemsAdapter mAdapter;

  @BindView(R.id.shoppingItemName)
  TextView shoppingItemName;

  private ShoppingItem msShoppingItem;

  public ShoppingItemRowViewHolder(@NonNull View view, @NonNull ShoppingItemRowListener listener,
                                   @NonNull ShoppingItemsAdapter adapter) {
    super(view);
    ButterKnife.bind(this, view);
    mListener = listener;
    mAdapter = adapter;
  }

  public void bindTo(@NonNull ShoppingItem shoppingItem) {
    msShoppingItem = shoppingItem;
    shoppingItemName.setText(shoppingItem.getName());
  }

  public ShoppingItem getShoppingItem() {
    return msShoppingItem;
  }
}
