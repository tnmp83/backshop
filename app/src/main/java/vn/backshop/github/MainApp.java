package vn.backshop.github;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 *
 * @author PhuongTNM
 * @version 1.0
 */
public class MainApp extends Application {
    private static MainApp instance;
    public static synchronized MainApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize instance
        instance = this;
        Fresco.initialize(this);
    }
}
