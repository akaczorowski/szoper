package pl.akac.android.szoper.di;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.akac.android.szoper.database.AppDatabase;

@Module(subcomponents = {MainActivityComponent.class})
public class AppModule {

  @Provides
  @AppScope
  Context provideContext(Application application) {
    return application;
  }

  @Provides
  @AppScope
  AppDatabase provideAppDatabase(Context context) {
    return Room.databaseBuilder(context,
                                AppDatabase.class,
                                "szoper-database")
               .build();
  }
}
