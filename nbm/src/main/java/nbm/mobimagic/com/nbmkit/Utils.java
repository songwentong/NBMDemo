package nbm.mobimagic.com.nbmkit;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

/**
 * Created by songwentong on 24/01/2017.
 */

public class Utils {
    public static void bindService(Context context, Class<?> serviceClazz, String action, ServiceConnection conn,
                                   int flags) {
        try {
            Context context2 = context.getApplicationContext();
            Intent serviceIntent = new Intent(context2, serviceClazz).setAction(action);
            context2.bindService(serviceIntent, conn, flags);
        } catch (Exception e) {
        }
    }
    public static void unbindService(String tag, Context context, ServiceConnection conn) {
        try {
            context.getApplicationContext().unbindService(conn);
        } catch (Exception ex) {

        }
    }
}
