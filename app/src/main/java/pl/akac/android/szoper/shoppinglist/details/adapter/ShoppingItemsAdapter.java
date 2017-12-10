package pl.akac.android.szoper.shoppinglist.details.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.akac.android.szoper.R;
import pl.akac.android.szoper.common.adapter.BaseAdapter;
import pl.akac.android.szoper.database.entities.ShoppingItem;
import pl.akac.android.szoper.shoppinglist.details.viewholder.ShoppingItemRowViewHolder;


public class ShoppingItemsAdapter extends
                                 BaseAdapter<ShoppingItem, ShoppingItemsAdapter.BaseViewHolder<ShoppingItem>> {

  private ShoppingItemRowListener mListener;

  public ShoppingItemsAdapter(@NonNull ShoppingItemRowListener listener) {
    mListener = listener;
    setHasStableIds(true);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).getId();
  }

  @Override
  public int getItemViewType(int position) {
    return R.layout.shopping_item_row;
  }

  @Override
  public BaseViewHolder<ShoppingItem> onCreateViewHolder(
      ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent,
                                                                 false);
    ShoppingItemRowViewHolder vh = new ShoppingItemRowViewHolder(view, mListener,
                                                                 ShoppingItemsAdapter.this);

    return vh;
  }

  @Override
  public void onBindViewHolder(BaseViewHolder<ShoppingItem> holder,
                               int position) {
    ShoppingItem item = getItem(position);
    holder.bindTo(item);
  }


  public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    public abstract void bindTo(@NonNull T item);
  }
}
