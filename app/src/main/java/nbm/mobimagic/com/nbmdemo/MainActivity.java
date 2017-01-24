package nbm.mobimagic.com.nbmdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import nbm.mobimagic.com.nbmkit.*;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context mContext = getApplicationContext();
        final MainActivity activity = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //1.
                boolean shouldNotificationManagementTip = NotificationAccessUtil.shouldNotificationManagementTip(getApplicationContext());
                Log.i("MainActivity","shouldNotificationManagmentTip:"+shouldNotificationManagementTip);
                //1.
                activity.showNotifyGuideDialog();
            }
        });
    }

    //1.引导弹窗
    NotifyGuideDialog getNotifyGuideDialog = null;
    public void showNotifyGuideDialog(){
        getNotifyGuideDialog = NotificationAccessUtil.getNotifyGuideDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotifyGuideDialog.dismiss();
            }
        });
        getNotifyGuideDialog.topTextView.setText("Top 可定义文案");
        getNotifyGuideDialog.contentTextView.setText("中间可定义的文案");
        getNotifyGuideDialog.cleanButtonTextView.setText("底部可定义的文案");
        getNotifyGuideDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
