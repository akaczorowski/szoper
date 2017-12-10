package pl.akac.android.szoper.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import pl.akac.android.szoper.MainActivity;

@MainActivityScope
@Subcomponent(modules = {
    MainActivityModule.class,
    FragmentBuilder.class})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<MainActivity> {
  }
}