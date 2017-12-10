package pl.akac.android.szoper.shoppinglist.list.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.akac.android.szoper.R;
import pl.akac.android.szoper.common.adapter.BaseAdapter;
import pl.akac.android.szoper.database.entities.ShoppingList;
import pl.akac.android.szoper.shoppinglist.list.viewholder.ShoppingListRowViewHolder;

public class ShoppingListsAdapter
    extends BaseAdapter<ShoppingList, ShoppingListsAdapter.BaseViewHolder<ShoppingList>> {

  private ShoppingListRowListener mListener;

  public ShoppingListsAdapter(@NonNull ShoppingListRowListener listener) {
    mListener = listener;
    setHasStableIds(true);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).getId();
  }

  @Override
  public int getItemViewType(int position) {
    return R.layout.shopping_list_name_row;
  }

  @Override
  public BaseViewHolder<ShoppingList> onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent,
                                                                 false);
    ShoppingListRowViewHolder vh = new ShoppingListRowViewHolder(view, mListener,
                                                                 ShoppingListsAdapter.this);

    return vh;
  }

  @Override
  public void onBindViewHolder(BaseViewHolder<ShoppingList> holder, int position) {
    ShoppingList item = getItem(position);
    holder.bindTo(item);
  }


  public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    public abstract void bindTo(@NonNull T item);
  }
}
