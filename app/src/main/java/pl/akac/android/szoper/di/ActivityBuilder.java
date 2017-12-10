package pl.akac.android.szoper.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import pl.akac.android.szoper.MainActivity;

@Module
public abstract class ActivityBuilder {

  @Binds
  @IntoMap
  @ActivityKey(MainActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(
      MainActivityComponent.Builder builder);


}
