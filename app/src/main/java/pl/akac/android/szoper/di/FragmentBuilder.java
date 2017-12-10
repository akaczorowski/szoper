package pl.akac.android.szoper.di;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import pl.akac.android.szoper.shoppinglist.details.di.ShoppingListDetailsFragmentComponent;
import pl.akac.android.szoper.shoppinglist.list.di.ShoppingListsFragmentComponent;
import pl.akac.android.szoper.shoppinglist.details.ShoppingListDetailsFragment;
import pl.akac.android.szoper.shoppinglist.list.ShoppingListsFragment;

@Module
public abstract class FragmentBuilder {

  @Binds
  @IntoMap
  @FragmentKey(ShoppingListsFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindShoppingListsFragment(
      ShoppingListsFragmentComponent.Builder builder);

  @Binds
  @IntoMap
  @FragmentKey(ShoppingListDetailsFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindShoppingListDetailsFragment(
      ShoppingListDetailsFragmentComponent.Builder builder);
}
