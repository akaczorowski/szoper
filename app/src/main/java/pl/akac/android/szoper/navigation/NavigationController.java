package pl.akac.android.szoper.navigation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.LinkedList;

import pl.akac.android.szoper.MainActivity;
import pl.akac.android.szoper.R;
import pl.akac.android.szoper.shoppinglist.details.ShoppingListDetailsFragment;
import pl.akac.android.szoper.shoppinglist.list.ShoppingListsFragment;

import static pl.akac.android.szoper.navigation.NavigationController.ScreenName.ARCHIVED_SHOPPING_LISTS;
import static pl.akac.android.szoper.navigation.NavigationController.ScreenName.SHOPPING_LISTS;
import static pl.akac.android.szoper.navigation.NavigationController.ScreenName.SHOPPING_LIST_DETAILS;

public class NavigationController implements NavigationListener {

  MainActivity mMainActivity;

  private LinkedList<ScreenName> mScreenNavigationStack;

  public NavigationController(MainActivity mainActivity) {
    mMainActivity = mainActivity;
    mScreenNavigationStack = new LinkedList<>();
  }

  public void navigateToShoppingListsScreen() {
    Bundle bundle = new Bundle();
    bundle.putBoolean(ShoppingListsFragment.BUNDLE_ARG_SHOW_ARCHIVED_LISTS, false);

    navigateToScreen(SHOPPING_LISTS, bundle);
    mMainActivity.onShowShoppingListsScreen();
  }

  public void navigateToArchivedShoppingListsScreen() {
    Bundle bundle = new Bundle();
    bundle.putBoolean(ShoppingListsFragment.BUNDLE_ARG_SHOW_ARCHIVED_LISTS, true);

    navigateToScreen(ARCHIVED_SHOPPING_LISTS, bundle);
    mMainActivity.onShowArchivedListsScreen();
  }

  public void navigateToShoppingListDetails(long shoppingListId, @NonNull String screenTitle, boolean readOnly) {
    Bundle bundle = new Bundle();
    bundle.putLong(ShoppingListDetailsFragment.BUNDLE_ARG_SHOPPING_LIST_ID, shoppingListId);
    bundle.putBoolean(ShoppingListDetailsFragment.BUNDLE_ARG_IS_READ_ONLY, readOnly);

    navigateToScreen(SHOPPING_LIST_DETAILS, bundle);
    mMainActivity.onShowShoppingListDetailsScreen(screenTitle);
  }

  public void goBack() {
    if (mScreenNavigationStack.getLast() == ScreenName.SHOPPING_LIST_DETAILS) {
      ScreenName previousScreenName = mScreenNavigationStack.get(0);
      if (previousScreenName == ScreenName.SHOPPING_LISTS) {
        mMainActivity.onShowShoppingListsScreen();
      } else if (previousScreenName == ScreenName.ARCHIVED_SHOPPING_LISTS) {
        mMainActivity.onShowArchivedListsScreen();
      }
      mScreenNavigationStack.remove(mScreenNavigationStack.getLast());
    }
  }

  @Override
  public void onShoppingListsNavigationClicked() {
    navigateToShoppingListsScreen();
  }

  @Override
  public void onArchivedShoppingListsNavigationClicked() {
    navigateToArchivedShoppingListsScreen();
  }


  public enum ScreenName {
    SHOPPING_LISTS, ARCHIVED_SHOPPING_LISTS, SHOPPING_LIST_DETAILS
  }

  private void navigateToScreen(@NonNull ScreenName screenName, @Nullable Bundle args) {
    if (screenName == SHOPPING_LISTS || screenName == ARCHIVED_SHOPPING_LISTS) {
      mScreenNavigationStack.clear();
      showShoppingListsFragment(args);
    } else if (screenName == SHOPPING_LIST_DETAILS) {
      showShoppingListDetailsFragment(args);
    }
    mScreenNavigationStack.addLast(screenName);
  }

  private void showShoppingListDetailsFragment(@Nullable Bundle args) {
    ShoppingListDetailsFragment shoppingListDetailsFragment = new ShoppingListDetailsFragment();
    shoppingListDetailsFragment.setArguments(args);

    showFragment(shoppingListDetailsFragment, true);
  }

  private void showShoppingListsFragment(@Nullable Bundle args) {
    ShoppingListsFragment shoppingListsFragment = new ShoppingListsFragment();
    shoppingListsFragment.setArguments(args);
    showFragment(shoppingListsFragment, false);
  }

  private void showFragment(@NonNull Fragment fragment, boolean animate) {
    FragmentTransaction transaction = mMainActivity.getSupportFragmentManager().beginTransaction();
    if (animate) {
      transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_enter,
                                      R.anim.pop_exit);
    }
    transaction.replace(R.id.fragmentViewHolder, fragment, null);
    if (!(fragment instanceof ShoppingListsFragment)) {
      transaction.addToBackStack(null);
    }
    transaction.commit();
  }

  private void clearFragmentsBackStack() {
    FragmentManager supportFragmentManager = mMainActivity.getSupportFragmentManager();
    FragmentManager.BackStackEntry entry = supportFragmentManager.getBackStackEntryAt(0);
    supportFragmentManager.popBackStack(entry.getId(),
                                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
    supportFragmentManager.executePendingTransactions();

  }
}
