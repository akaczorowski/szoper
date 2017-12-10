package pl.akac.android.szoper.shoppinglist.details;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import pl.akac.android.szoper.R;
import pl.akac.android.szoper.common.callback.SimpleListUpdateCallback;
import pl.akac.android.szoper.database.entities.ShoppingItem;
import pl.akac.android.szoper.shoppinglist.details.adapter.ShoppingItemRowListener;
import pl.akac.android.szoper.shoppinglist.details.adapter.ShoppingItemsAdapter;
import pl.akac.android.szoper.shoppinglist.details.callback.DiffUtilCallback;
import pl.akac.android.szoper.shoppinglist.details.viewholder.ShoppingItemRowViewHolder;
import pl.akac.android.szoper.shoppinglist.details.viewmodel.ShoppingListDetailsViewModel;

public class ShoppingListDetailsFragment extends Fragment {

  public static final String BUNDLE_ARG_SHOPPING_LIST_ID = "BUNDLE_ARG_SHOPPING_LIST_ID";
  public static final String BUNDLE_ARG_IS_READ_ONLY = "BUNDLE_ARG_IS_READ_ONLY";

  @Inject
  ShoppingListDetailsViewModel mViewModel;

  @BindView(R.id.shoppingItemsRecyclerView)
  RecyclerView mShoppingItemsRecyclerView;

  @BindView(R.id.inputText)
  EditText mInputText;

  @BindView(R.id.addShoppingItemButton)
  Button mAddShoppingItemButton;

  private ShoppingItemsAdapter mShoppingItemsAdapter;
  private boolean mIsReadOnly;

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);

    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mIsReadOnly = getArguments().getBoolean(BUNDLE_ARG_IS_READ_ONLY);
    mShoppingItemsAdapter = new ShoppingItemsAdapter(new ShoppingItemRowListener() {
    });

    setupShoppingItemsObserver();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.shopping_list_details, container, false);
    ButterKnife.bind(this, view);

    setupShoppingItemsRecyclerView();

    if (mIsReadOnly) {
      mInputText.setVisibility(View.GONE);
      mAddShoppingItemButton.setVisibility(View.GONE);
    } else {
      setupInputTextListeners();
      setupAddButtonListeners();
    }

    return view;
  }

  private void setupShoppingItemsObserver() {
    long shoppingListId = getArguments().getLong(BUNDLE_ARG_SHOPPING_LIST_ID);
    mViewModel.setShoppingListId(shoppingListId);
    mViewModel.getShoppingItems().observe(this, new Observer<List<ShoppingItem>>() {
      @Override
      public void onChanged(@Nullable List<ShoppingItem> shoppingLists) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
            new DiffUtilCallback(shoppingLists, mShoppingItemsAdapter.getData()));
        mShoppingItemsAdapter.clear();
        mShoppingItemsAdapter.addAll(shoppingLists);

        diffResult.dispatchUpdatesTo(new SimpleListUpdateCallback(mShoppingItemsRecyclerView));
      }
    });
  }

  private void setupShoppingItemsRecyclerView() {
    mShoppingItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mShoppingItemsRecyclerView.setHasFixedSize(true);
    mShoppingItemsRecyclerView.setAdapter(mShoppingItemsAdapter);
//    mShoppingItemsRecyclerView.addItemDecoration(
//        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    if (!mIsReadOnly) {
      setupItemTouchHelper(mShoppingItemsRecyclerView);
    }
  }

  private void setupItemTouchHelper(@NonNull RecyclerView recyclerView) {
    ItemTouchHelper itemTouchHelper = createItemTouchHelper();
    itemTouchHelper.attachToRecyclerView(recyclerView);
  }

  @NonNull
  private ItemTouchHelper createItemTouchHelper() {
    return new ItemTouchHelper(new ItemTouchHelper.Callback() {
      @Override
      public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
      }

      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        final ShoppingItem shoppingItem = ((ShoppingItemRowViewHolder) viewHolder).getShoppingItem();
        mViewModel.delete(shoppingItem);
      }

      @Override
      public void onChildDraw(Canvas c, RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder,
                              float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View child = viewHolder.itemView;
        child.setAlpha(1 - Math.abs(dX) / child.getWidth());

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
      }
    });
  }

  private void setupAddButtonListeners() {
    mAddShoppingItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addList();
      }
    });
  }

  private void setupInputTextListeners() {
    mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          return addList();
        }
        return false;
      }
    });

    mInputText.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
          return addList();
        }
        return false;
      }
    });
  }

  private boolean addList() {

    final String shoppingItemName = mInputText.getText().toString().trim();
    if (!shoppingItemName.isEmpty()) {

      mViewModel.insert(shoppingItemName);

      mInputText.setText("");

      return true;
    }
    return false;
  }
}
