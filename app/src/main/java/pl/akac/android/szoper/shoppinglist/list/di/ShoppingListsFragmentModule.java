package pl.akac.android.szoper.shoppinglist.list.di;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import pl.akac.android.szoper.database.AppDatabase;
import pl.akac.android.szoper.shoppinglist.list.ShoppingListsFragment;
import pl.akac.android.szoper.shoppinglist.list.viewmodel.ShoppingListsViewModel;

@Module
public class ShoppingListsFragmentModule {

    @Provides
    @ShoppingListsFragmentScope
    ShoppingListsViewModel provideViewModel(AppDatabase database, ShoppingListsFragment fragment) {
        ShoppingListsViewModel vm = ViewModelProviders.of(fragment)
                .get(ShoppingListsViewModel.class);
        vm.setDatabase(database);

        return vm;
    }
}
