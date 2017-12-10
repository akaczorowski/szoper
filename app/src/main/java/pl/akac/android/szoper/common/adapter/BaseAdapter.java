package pl.akac.android.szoper.common.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, S extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<S> {

  @NonNull
  private List<T> adapterData;

  public BaseAdapter() {
    this.adapterData = new ArrayList<>(1);
  }

  @Override
  public int getItemCount() {
    return adapterData.size();
  }


  @NonNull
  public T getItem(int index) {
    return adapterData.get(index);
  }


  @NonNull
  public List<T> getData() {
    return adapterData;
  }

  public void clear() {
    adapterData.clear();
  }

  public void addAll(@NonNull List<T> data) {
    this.adapterData.addAll(data);
  }
}
