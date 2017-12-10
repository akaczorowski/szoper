package pl.akac.android.szoper.shoppinglist.list.di;


import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import pl.akac.android.szoper.shoppinglist.list.ShoppingListsFragment;

@ShoppingListsFragmentScope
@Subcomponent(modules = ShoppingListsFragmentModule.class)
public interface ShoppingListsFragmentComponent extends AndroidInjector<ShoppingListsFragment> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ShoppingListsFragment> {
  }
}
