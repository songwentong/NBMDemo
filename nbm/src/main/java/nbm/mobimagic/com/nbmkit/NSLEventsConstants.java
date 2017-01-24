package nbm.mobimagic.com.nbmkit;

public class NSLEventsConstants {
    /**
     * 
     */
    public static final boolean DEBUG = false;
    /**
     * 功能支持的最低版本
     */
    public static final int SUPPORT_SDK_INT = 19;
    /**
     * 被收集通知文件
     */
    public static final String NSL_NOTIFICATION_LIST_FILE = "nsl_list_file.dat";
    /**
     * 通过v5下发的远端白名单文件
     */
    public static final String NSL_ROSTER_LIST_FILE = "nsl_roster_file.dat";
    /**
     * 此lib相关SharedPreferences name
     */
    public static final String NSL_SP_LIB = "notification_manager_sp_lib";
    /**
     * 客户端对于此功能的开关控制
     */
    public static final String SP_KEY_NSL_LOCAL_SWITCH = "sp_key_notification_manager_switch";
    /**
     * 首次收集某个应用的通知
     */
    public static final String SP_KEY_FRIST_DEL_NOTIFICATION = "sp_key_frist_del_notification";
    /**
     * 首次使用允许通知, 首次使用allow按钮是需要弹窗提示用户该功能的意义.
     */
    public static final String SP_KEY_FRIST_ALLOW = "sp_key_frist_allow";
    /**
     * 对应系统数据库中的开关key
     */
    public static final String ENABLED_NSL = "enabled_notification_listeners";
    /**
     * 打开nsl系统页面的action
     */
    public static final String ACTION_NSL_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    /**
     * 当nsl拦截通知时发出的广播
     */
    public static final String ACTION_NSL_CANCEL_NOTIFICATION = "com.qihoo.security.action.NSL_CANCEL_NOTIFICATION";

    /**
     * intent参数key
     */
    public static final String PKG_NAME = "pkg_name";

    public static final String OWN_PKG_NAME = "com.qihoo.security";
}