package pl.akac.android.szoper.shoppinglist.list;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import pl.akac.android.szoper.R;
import pl.akac.android.szoper.shoppinglist.list.callback.DiffUtilCallback;
import pl.akac.android.szoper.common.callback.SimpleListUpdateCallback;
import pl.akac.android.szoper.database.entities.ShoppingList;
import pl.akac.android.szoper.common.listener.RecyclerViewItemClickSupport;
import pl.akac.android.szoper.shoppinglist.list.adapter.ShoppingListRowListener;
import pl.akac.android.szoper.shoppinglist.list.adapter.ShoppingListsAdapter;
import pl.akac.android.szoper.shoppinglist.list.viewholder.ShoppingListRowViewHolder;
import pl.akac.android.szoper.shoppinglist.list.viewmodel.ShoppingListsViewModel;
import pl.akac.android.szoper.navigation.NavigationController;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class ShoppingListsFragment extends Fragment
    implements RecyclerViewItemClickSupport.OnItemClickListener {

  public static final String BUNDLE_ARG_SHOW_ARCHIVED_LISTS = "BUNDLE_ARG_SHOW_ARCHIVED_LISTS";

  @Inject
  ShoppingListsViewModel mViewModel;

  @Inject
  NavigationController mNavigationController;

  @BindView(R.id.shoppingListsRecyclerView)
  RecyclerView mShoppingListsRecyclerView;

  @BindView(R.id.inputText)
  EditText mInputText;

  @BindView(R.id.addShoppingListButton)
  Button mAddShoppingListButton;

  private ShoppingListsAdapter mShoppingListsAdapter;
  private boolean mIsShowArchivedShoppingListsRequested;

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);

    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mIsShowArchivedShoppingListsRequested = getArguments().getBoolean(BUNDLE_ARG_SHOW_ARCHIVED_LISTS);

    mShoppingListsAdapter = new ShoppingListsAdapter(new ShoppingListRowListener() {
    });

    if (mIsShowArchivedShoppingListsRequested) {
      setupArchivedShoppingListsObserver();
    } else {
      setupShoppingListsObserver();
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.shopping_lists, container, false);
    ButterKnife.bind(this, view);

    setupShoppingListsRecyclerView();

    if(mIsShowArchivedShoppingListsRequested) {
      mInputText.setVisibility(View.GONE);
      mAddShoppingListButton.setVisibility(View.GONE);
    }else{
      setupInputTextListeners();
      setupAddButtonListeners();
    }

    return view;
  }

  @Override
  public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    ShoppingList shoppingList = mShoppingListsAdapter.getItem(position);
    long shoppingListId = shoppingList.getId();
    String shoppintListName = shoppingList.getName();
    mNavigationController.navigateToShoppingListDetails(shoppingListId, shoppintListName, mIsShowArchivedShoppingListsRequested);
  }

  private void setupShoppingListsObserver() {
    mViewModel.getShoppingLists().observe(this, new Observer<List<ShoppingList>>() {
      @Override
      public void onChanged(@Nullable List<ShoppingList> shoppingLists) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
            new DiffUtilCallback(shoppingLists, mShoppingListsAdapter.getData()));
        mShoppingListsAdapter.clear();
        mShoppingListsAdapter.addAll(shoppingLists);

        diffResult.dispatchUpdatesTo(new SimpleListUpdateCallback(mShoppingListsRecyclerView));

      }
    });
  }

  private void setupArchivedShoppingListsObserver() {
    mViewModel.getArchivedShoppingLists().observe(this, new Observer<List<ShoppingList>>() {
      @Override
      public void onChanged(@Nullable List<ShoppingList> shoppingLists) {
        mShoppingListsAdapter.clear();
        mShoppingListsAdapter.addAll(shoppingLists);
        mShoppingListsAdapter.notifyDataSetChanged();
      }
    });
  }

  private void setupShoppingListsRecyclerView() {
    mShoppingListsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mShoppingListsRecyclerView.setHasFixedSize(true);
    mShoppingListsRecyclerView.setAdapter(mShoppingListsAdapter);
//    mShoppingListsRecyclerView.addItemDecoration(
//        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    if (!mIsShowArchivedShoppingListsRequested) {
      setupItemTouchHelper(mShoppingListsRecyclerView);
    }
    RecyclerViewItemClickSupport.addTo(mShoppingListsRecyclerView).setOnItemClickListener(this);
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
        final ShoppingList shoppingList = ((ShoppingListRowViewHolder) viewHolder)
            .getShoppingList();
        shoppingList.setArchived(true);
        mViewModel.update(shoppingList);
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
    mAddShoppingListButton.setOnClickListener(new View.OnClickListener() {
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

    final String listName = mInputText.getText().toString().trim();
    if (!listName.isEmpty()) {

      mViewModel.insert(listName);

      mInputText.setText("");
      hideSoftKeyboard();

      return true;
    }

    return false;

  }

  private void hideSoftKeyboard() {
    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
        INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(mInputText.getWindowToken(), 0);
  }

}
