package pl.akac.android.szoper.navigation;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import pl.akac.android.szoper.R;


public class NavigationViewListener
    implements NavigationView.OnNavigationItemSelectedListener {

  private NavigationListener mNavigationListener;

  public NavigationViewListener(NavigationListener navigationListener) {
    mNavigationListener = navigationListener;
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    item.setChecked(true);
    int itemId = item.getItemId();
    switch (itemId) {
      case R.id.currentLists:
        mNavigationListener.onShoppingListsNavigationClicked();
        break;
      case R.id.archivedLists:
        mNavigationListener.onArchivedShoppingListsNavigationClicked();
        break;
      default:
        break;

    }
    return true;
  }
}
