package pl.akac.android.szoper.shoppinglist.details.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import pl.akac.android.szoper.shoppinglist.details.ShoppingListDetailsFragment;

@ShoppingListDetailsFragmentScope
@Subcomponent(modules = ShoppingListDetailsFragmentModule.class)
public interface ShoppingListDetailsFragmentComponent
    extends AndroidInjector<ShoppingListDetailsFragment> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<ShoppingListDetailsFragment> {
  }
}