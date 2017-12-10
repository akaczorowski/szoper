package pl.akac.android.szoper.di;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import pl.akac.android.szoper.MainApplication;

@AppScope
@Component(modules = {
    AndroidInjectionModule.class,
    AppModule.class,
    ActivityBuilder.class
})
public interface AppComponent {

  void inject(MainApplication app);

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }
}
