package pl.akac.android.szoper;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import pl.akac.android.szoper.navigation.NavigationController;
import pl.akac.android.szoper.navigation.NavigationViewListener;
import pl.akac.android.szoper.util.ThemeUtils;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

  @Inject
  NavigationController mNavigationController;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.drawerLayout)
  DrawerLayout mDrawerLayout;
  @BindView(R.id.navigationView)
  NavigationView mNavigationView;

  private ActionBarDrawerToggle mDrawerToggle;

  public ActionBarDrawerToggle getDrawerToggle() {
    return mDrawerToggle;
  }

  public DrawerLayout getDrawerLayout() {
    return mDrawerLayout;
  }

  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    ThemeUtils.ensureRuntimeTheme(this);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(R.string.shopping_lists_header);

    mNavigationView.setNavigationItemSelectedListener(
        new NavigationViewListener(mNavigationController));
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                                              R.string.open_drawer_content_desc,
                                              R.string.close_drawer_content_desc);
    mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
    mDrawerLayout.addDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();

    if (savedInstanceState == null) {
      mNavigationController.navigateToShoppingListsScreen();
    } else {
      //todo navigation controller should know about this
    }

  }

  @Override
  public void onBackPressed() {
    mNavigationController.goBack();
    super.onBackPressed();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
//    else if (item.getItemId() == android.R.id.home) {
//      onBackPressed();
//      return true;
//    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  public void onShowShoppingListsScreen() {
    getSupportActionBar().setTitle(R.string.shopping_lists_header);
    mDrawerLayout.closeDrawers();
    mDrawerToggle.setDrawerIndicatorEnabled(true);
    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
  }

  public void onShowArchivedListsScreen() {
    getSupportActionBar().setTitle(R.string.archived_lists_header);
    mDrawerLayout.closeDrawers();
    mDrawerToggle.setDrawerIndicatorEnabled(true);
    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
  }

  public void onShowShoppingListDetailsScreen(String screenTitle) {
    getSupportActionBar().setTitle(screenTitle);
    mDrawerToggle.setDrawerIndicatorEnabled(false);
    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
  }
}
