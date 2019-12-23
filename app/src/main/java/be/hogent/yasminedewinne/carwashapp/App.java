package be.hogent.yasminedewinne.carwashapp;

import android.app.Application;
import android.content.Context;

import be.hogent.yasminedewinne.carwashapp.data.UserHelper;

/** Custom override voor de Application class, wordt gebruikt om de context statisch te verkrijgen */
// Source: https://stackoverflow.com/questions/2002288/static-way-to-get-context-in-android
public class App extends Application {

    private static Application _application;
    private static UserHelper _userHelper;

    public static Application getApplication() {
        return _application;
    }

    /**
     * Methode om de context statisch op te vragen.
     * Gebruik enkel wanneer deze niet via een Activity/Fragment beschikbaar is
     * @return Retourneert de applicatie context
     */
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    /**
     * Methode om de userhelper statisch op te vragen.
     * Gebruik enkel in geval dat context niet beschikbaar is
     * @return Retourneert een userhelper instantie
     */
    public static UserHelper getUserHelper() {
        return _userHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _application = this;
        _userHelper = new UserHelper(getContext());
        //createNotificationChannel();
    }

    /*private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_reservatie_name);
            String description = getString(R.string.channel_reservatie_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_RESERVATIES_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
