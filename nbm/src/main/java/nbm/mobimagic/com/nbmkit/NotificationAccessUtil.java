package nbm.mobimagic.com.nbmkit;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class NotificationAccessUtil {

    private static Object lock = new Object();

    //1.通知防打扰	是否应该弹窗
    public static boolean shouldNotificationManagementTip(Context mContext){
        if (!NotificationAccessUtil.isEnabled(mContext) || !NotificationAccessUtil.getNotificationManagerSwitch(mContext)) {
            return true;
        }
        return false;
    }


    //1.通知防打扰  弹窗
    public static NotifyGuideDialog getNotifyGuideDialog(Activity activity,View.OnClickListener listener){
        NotifyGuideDialog mGuideDialog = new NotifyGuideDialog(activity);
        mGuideDialog.setCancelable(true);
        mGuideDialog.setCanceledOnTouchOutside(true);
        mGuideDialog.setDialogContentClickListener(listener);
        return mGuideDialog;
    }

    /**
     * 设置客户端通知栏管理本地开关
     * 
     * @param c
     * @param b
     */
    public static void setNotificationManagerSwitch(Context c, boolean b) {
        //        SharedPreferences mySharedPreferences = c.getSharedPreferences(NSLEventsConstants.NSL_SP_LIB,
        //                Activity.MODE_PRIVATE);
        //        mySharedPreferences.edit().putBoolean(NSLEventsConstants.SP_KEY_NSL_LOCAL_SWITCH, b).commit();
        SharedPreferences sp = c.getSharedPreferences("SP",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("SP_KEY_NSL_LOCAL_SWITCH",b);
        editor.commit();
    }

    /**
     * 获得客户端通知栏管理本地开关
     * 
     * @param c
     * @return
     */
    public static boolean getNotificationManagerSwitch(Context c) {
        SharedPreferences sp = c.getSharedPreferences("SP",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        return sp.getBoolean("SP_KEY_NSL_LOCAL_SWITCH",false);
    }

    /**
     * 获得已经被拦截过通知的pkg, 用于toast提示时不重复show同一个pkg的.
     * 
     * @param c
     * @return
     */
    public static Set<String> getFristDelNotification(Context c) {
        SharedPreferences mySharedPreferences = c.getSharedPreferences(NSLEventsConstants.NSL_SP_LIB,
                Activity.MODE_PRIVATE);
        return mySharedPreferences
                .getStringSet(NSLEventsConstants.SP_KEY_FRIST_DEL_NOTIFICATION, new HashSet<String>());
    }

    /**
     * 设置已经被拦截过通知的pkg, 用于toast提示时不重复show同一个pkg的.
     * 
     * @param c
     * @return
     */
    public static void setFristDelNotification(Context c, Set<String> set) {
        SharedPreferences mySharedPreferences = c.getSharedPreferences(NSLEventsConstants.NSL_SP_LIB,
                Activity.MODE_PRIVATE);
        mySharedPreferences.edit().putStringSet(NSLEventsConstants.SP_KEY_FRIST_DEL_NOTIFICATION, set).commit();
    }

    /**
     * 用于设置首次点击allow button的标记
     * 
     * @param c
     * @param b
     */
    public static void setFristAllow(Context c, boolean b) {
        SharedPreferences mySharedPreferences = c.getSharedPreferences(NSLEventsConstants.NSL_SP_LIB,
                Activity.MODE_PRIVATE);
        mySharedPreferences.edit().putBoolean(NSLEventsConstants.SP_KEY_FRIST_ALLOW, b).commit();

    }

    /**
     * 用于获得首次点击allow button的标记
     * 
     * @param c
     *
     */
    public static boolean getFristAllow(Context c) {
        SharedPreferences mySharedPreferences = c.getSharedPreferences(NSLEventsConstants.NSL_SP_LIB,
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getBoolean(NSLEventsConstants.SP_KEY_FRIST_ALLOW, true);
    }

    /**
     * 保存白名单
     * 
     * @param c
     * @param bean
     */
    public static void saveRosterList(Context c, RosterBeanLocal bean) {
        saveObject(c, bean, NSLEventsConstants.NSL_ROSTER_LIST_FILE);
    }

    /**
     * 获得用户白名单
     *
     * 1. 用户白名单为用户主动行为生成的白名单, 当首次使用或无主动行为触发时此名单为空, 此时使用v5下发的推荐白名单来填补用户白名单.
     * 2.
     *
     * @param c
     * @return
     */
    public static RosterBeanLocal getRosterList(Context c) {
        // 1. 获得用户白名单, 用户白名单为用户在通知栏管理设置页面进行勾选后产生的记录.
        RosterBeanLocal bean = (RosterBeanLocal) getObject(c, NSLEventsConstants.NSL_ROSTER_LIST_FILE);
        if (bean == null) {
            // 2. 当首次使用时用户白名单为空, 此时使用v5下发的推荐白名单来填补用户白名单, 并生成用户白名单.
            bean = new RosterBeanLocal();
            Set<String> pkgList = new HashSet<String>();
            List<RosterBeanRemote> mRosterBeanRemoteList = RosterReadTool.getInfoFromFile(c);
            if (mRosterBeanRemoteList != null && !mRosterBeanRemoteList.isEmpty()) {
                for (RosterBeanRemote info : mRosterBeanRemoteList) {
                    pkgList.add(info.getPkgName());
                }
            } else {
                // 3. 当v5下发的文件不存在或不可用时, 此时使用本地名单(随包发出的assets下的配置文件)来填补用户白名单.(这是一定存在的)
                Set<String> pkgs = nbm.mobimagic.com.nbmkit.NotificationAccessUtil.getLocalRosterList(c);
                pkgList.addAll(pkgs);
            }
            bean.setList(pkgList);
            saveRosterList(c, bean); // (在首次使用时bean==null, 完成了用户白名单填充后 保存一次)
        }
        return bean;
    }

    /**
     * 得到本地默认白名单
     * 首次使用时在用户没有生成自己的白名单时使用此名单作为默认白名单,在用户手动保存过一次白名单之后都使用用户行为生成的白名单.
     *
     * @param c
     * @return
     */
    public static Set<String> getLocalRosterList(Context c) {
        return RosterReadTool.ReadDefRosterFile(c);
    }

    /**
     * 得到v5下发的运营数据
     *
     * @param c
     * @return
     */
    public static List<String> getRemoteRosterList(Context c) {
        List<String> list = new ArrayList<String>();
        List<RosterBeanRemote> mRosterBeanRemoteList = RosterReadTool.getInfoFromFile(c);
        if (mRosterBeanRemoteList != null && !mRosterBeanRemoteList.isEmpty()) {
            for (RosterBeanRemote info : mRosterBeanRemoteList) {
                list.add(info.getPkgName());
            }
        }
        return list;
    }

    /**
     * 判断NotificationAccess是否被打开
     */
    public static boolean isEnabled(Context c) {
        String pkgName = c.getPackageName();
        final String flat = Settings.Secure.getString(c.getContentResolver(), NSLEventsConstants.ENABLED_NSL);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 进入NotificationAccess开关页面
     */
    public static void openNotificationAccess(Context c) {
        Intent mIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(mIntent);
    }

    /**
     * 得到被收集的通知列表
     *
     * @param c
     * @return
     */
    public synchronized static LocalNotificationBeanS getNotificationList(Context c) {
        LocalNotificationBeanS bean = (LocalNotificationBeanS) getObject(c,
                NSLEventsConstants.NSL_NOTIFICATION_LIST_FILE);
        if (bean == null) {
            bean = new LocalNotificationBeanS();
        }
        return bean;
    }

    /**
     * 保存通知列表
     * 
     * @param c
     * @param bean
     */
    public synchronized static void saveNotificationList(Context c, LocalNotificationBeanS bean) {
        saveObject(c, bean, NSLEventsConstants.NSL_NOTIFICATION_LIST_FILE);
    }

    private synchronized static void saveObject(Context c, Object sod, String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        synchronized (lock) {
            try {
                fos = c.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(sod);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static Object getObject(Context c, String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        synchronized (lock) {
            try {
                fis = c.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                return ois.readObject();
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static String getApplicationName(String pkgName, Context c) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        String applicationName = null;
        try {
            packageManager = c.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(pkgName, 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return applicationName;
    }
}