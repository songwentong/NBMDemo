package nbm.mobimagic.com.nbmkit;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import nbm.mobimagic.com.nbmkit.R;


/**
 * Created by Administrator on 2016/11/23.
 */
public class NotifyGuideDialog extends Dialog {
    public static final boolean DEBUG = false;
    private static final String TAG = DEBUG ? "NotifyGuideDialog" : NotifyGuideDialog.class.getSimpleName();


    public static final int TYPE_NOTITY_GUID = 0;
    public static final int TYPE_NOTITY_LOADING = 1;
    private int mType = TYPE_NOTITY_GUID;
    private final Context mContext;
    private View mContentview;
    private View mLoadingview;

    //自定义的文字
    public TextView topTextView = null;
    public TextView contentTextView = null;
    public TextView cleanButtonTextView = null;
    public NotifyGuideDialog(Context context) {
        super(context, R.style.App_Dialog);
        mContext = context;
        init(context);
    }

    public NotifyGuideDialog(Context context, int type) {
        super(context, R.style.App_Dialog);
        mContext = context;
        mType = type;
        init(context);
    }

    private void init(Context context) {
        if (mType == TYPE_NOTITY_LOADING) {
            mContentview = LayoutInflater.from(context).inflate(R.layout.notifymanager_loading_dialog, null, false);
            try {
                startAnimation();
            } catch (Exception e){
            }
        } else {
            mContentview = LayoutInflater.from(context).inflate(R.layout.notifymanager_guide_dialog, null, false);
            topTextView = (TextView)mContentview.findViewById(R.id.nbm_top_textView_id);
            contentTextView = (TextView)mContentview.findViewById(R.id.nbm_contentTextView);
            cleanButtonTextView = (TextView)mContentview.findViewById(R.id.nbm_clean_now_id);
        }
        setContentView(mContentview);
    }

    public void setDialogContentClickListener(View.OnClickListener clickListener) {
        if (mContentview != null) {
            mContentview.setOnClickListener(clickListener);
        }
    }

    private void startAnimation(){
        final View loading = mContentview.findViewById(R.id.notify_loading_root);
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 150f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        RotateAnimation rotateAnimation1 = new RotateAnimation(0f, 360f*3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation1.setDuration(2300);
        rotateAnimation1.setStartOffset(2000);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(rotateAnimation1);
        rotateAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ScaleAnimation outAnimation = new ScaleAnimation(1f, 0.0f, 1f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                outAnimation.setDuration(700);
                outAnimation.setFillAfter(true);
                loading.startAnimation(outAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLoadingview = mContentview.findViewById(R.id.notify_loading_bg);
        mLoadingview.startAnimation(animationSet);
    }
}
