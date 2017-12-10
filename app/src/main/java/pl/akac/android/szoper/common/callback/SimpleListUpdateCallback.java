package pl.akac.android.szoper.common.callback;


import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;

public class SimpleListUpdateCallback implements ListUpdateCallback {

  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;

  public SimpleListUpdateCallback(RecyclerView recyclerView) {
    mRecyclerView = recyclerView;
    mAdapter = recyclerView.getAdapter();
  }


  @Override
  public void onInserted(int position, int count) {
    mAdapter.notifyItemRangeInserted(position, count);
    mRecyclerView.smoothScrollToPosition(position);
  }

  @Override
  public void onRemoved(int position, int count) {
    mAdapter.notifyItemRangeRemoved(position, count);
  }

  @Override
  public void onMoved(int fromPosition, int toPosition) {
    mAdapter.notifyItemMoved(fromPosition, toPosition);
  }

  @Override
  public void onChanged(int position, int count, Object payload) {
    mAdapter.notifyItemRangeChanged(position, count, payload);
  }
}
