package pl.akac.android.szoper.di;

import dagger.Module;
import dagger.Provides;
import pl.akac.android.szoper.MainActivity;
import pl.akac.android.szoper.shoppinglist.details.di.ShoppingListDetailsFragmentComponent;
import pl.akac.android.szoper.shoppinglist.list.di.ShoppingListsFragmentComponent;
import pl.akac.android.szoper.navigation.NavigationController;

@Module(subcomponents = {
        ShoppingListsFragmentComponent.class,
        ShoppingListDetailsFragmentComponent.class})
public class MainActivityModule {

    @MainActivityScope
    @Provides
    NavigationController provideNavigationController(MainActivity activity) {
        return new NavigationController(activity);
    }

}