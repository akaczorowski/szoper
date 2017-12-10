package pl.akac.android.szoper.shoppinglist.details.di;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import pl.akac.android.szoper.database.AppDatabase;
import pl.akac.android.szoper.shoppinglist.details.ShoppingListDetailsFragment;
import pl.akac.android.szoper.shoppinglist.details.viewmodel.ShoppingListDetailsViewModel;

@Module
public class ShoppingListDetailsFragmentModule {

  @Provides
  @ShoppingListDetailsFragmentScope
  ShoppingListDetailsViewModel provideViewModel(AppDatabase database,
                                                ShoppingListDetailsFragment fragment) {
    ShoppingListDetailsViewModel vm = ViewModelProviders.of(fragment)
                                                        .get(ShoppingListDetailsViewModel.class);
    vm.setDatabase(database);

    return vm;
  }
}
