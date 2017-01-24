package nbm.mobimagic.com.nbmkit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by songwentong on 24/01/2017.
 */

public class SharedPref {
    /**
     * 状态栏开关
     */
    public static final String SP_KEY_NOTIFICATION = "notification";


    //用户
    private static String sharedPrefUser = "nbm.mobimagic.com.nbms";
    public static final void setBoolean(Context context, String key, boolean value) {
        SharedPreferences spf = context.getSharedPreferences(sharedPrefUser,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static final boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences spf = context.getSharedPreferences(sharedPrefUser,Context.MODE_PRIVATE);
        return spf.getBoolean(key,defValue);
    }


}
