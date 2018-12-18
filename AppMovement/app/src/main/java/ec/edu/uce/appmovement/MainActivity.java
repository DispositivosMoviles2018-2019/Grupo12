
package ec.edu.uce.appmovement;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import ec.edu.uce.appmovement.UserMotionService.LocalBinder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SupportErrorDialogFragment;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class MainActivity extends AppCompatActivity implements
        ServiceConnection,
        UserMotionService.OnActivityChangedListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    private static final String TAG = "UserActivity";
    
    private Intent mServiceIntent;
    private PendingIntent mCallbackIntent;
    private UserMotionService mService;
    
    private ActivityRecognitionClient mRecognitionClient;

    private ActivityAdapter mListAdapter;
    
    private View mBlockingView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mBlockingView = findViewById(R.id.blocker);
        

        ListView list = (ListView) findViewById(R.id.list);
        mListAdapter = new ActivityAdapter(this);
        list.setAdapter(mListAdapter);
        

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                showDetails(mListAdapter.getItem(position));
            }
        });


        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        switch (resultCode) {
            case ConnectionResult.SUCCESS:
                Log.d(TAG, "Google Play Services is ready to go!");
                break;
            default:
                showPlayServicesError(resultCode);
                return;
        }


        mRecognitionClient = new ActivityRecognitionClient(this, this, this);

        mServiceIntent = new Intent(this, UserMotionService.class);

        mCallbackIntent = PendingIntent.getService(this, 0,
                mServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRecognitionClient.connect();
        bindService(mServiceIntent, this, BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        mRecognitionClient.removeActivityUpdates(mCallbackIntent);
        mRecognitionClient.disconnect();
        
        disconnectService();
        unbindService(this);
    }
    

    
    public void onServiceConnected(ComponentName name, IBinder service) {

        mService = ((LocalBinder) service).getService();
        mService.setOnActivityChangedListener(this);
    }
    
    @Override
    public void onServiceDisconnected(ComponentName name) {
        disconnectService();
    }
    
    private void disconnectService() {
        if (mService != null) {
            mService.setOnActivityChangedListener(null);
        }
        mService = null;
    }
    

    
    @Override
    public void onConnected(Bundle connectionHint) {

        mRecognitionClient.requestActivityUpdates(5000, mCallbackIntent);
    }
    
    @Override
    public void onDisconnected() {
        Log.w(TAG, "Google Services Disconnected");
    }
    
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.w(TAG, "Google Services Connection Failure");
    }
    

    
    @Override
    public void onUserActivityChanged(int bestChoice, int bestConfidence,
            ActivityRecognitionResult newActivity) {

        mListAdapter.add(newActivity);
        mListAdapter.notifyDataSetChanged();
        

        switch (bestChoice) {
            case DetectedActivity.IN_VEHICLE:
            case DetectedActivity.ON_BICYCLE:
                mBlockingView.setVisibility(View.VISIBLE);
                break;
            case DetectedActivity.ON_FOOT:
            case DetectedActivity.STILL:
                mBlockingView.setVisibility(View.GONE);
                break;
            default:

                break;
        }
    }
    

    private void showDetails(ActivityRecognitionResult activity) {
        StringBuilder sb = new StringBuilder();
        sb.append("Details:");
        for(DetectedActivity element : activity.getProbableActivities()) {
            sb.append("\n"+UserMotionService.getActivityName(element)
                    + ", " + element.getConfidence() + "% sure");
        }
        
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
    }
    

    private static class ActivityAdapter extends ArrayAdapter<ActivityRecognitionResult> {
        
        public ActivityAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView tv = (TextView) convertView;
            ActivityRecognitionResult result = getItem(position);
            DetectedActivity newActivity = result.getMostProbableActivity();
            String entry = DateFormat.format("hh:mm:ss", result.getTime())
                    + ": " + UserMotionService.getActivityName(newActivity) + ", "
                    + newActivity.getConfidence() + "% confidence";
            tv.setText(entry);
            
            return convertView;
        }
    }



    private void showPlayServicesError(int errorCode) {

        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                1000 /* RequestCode */);

        if (errorDialog != null) {

            SupportErrorDialogFragment errorFragment = SupportErrorDialogFragment.newInstance(errorDialog);

            errorFragment.show(
                    getSupportFragmentManager(),
                    "Activity Tracker");
        }
    }
}
