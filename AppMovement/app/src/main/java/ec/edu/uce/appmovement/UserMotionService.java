
package ec.edu.uce.appmovement;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class UserMotionService extends IntentService {
    private static final String TAG = "UserMotionService";
    

    public interface OnActivityChangedListener{
         void onUserActivityChanged(int bestChoice, int bestConfidence,
                ActivityRecognitionResult newActivity);
    }
    

    private DetectedActivity mLastKnownActivity;

    private CallbackHandler mHandler;
    private static class CallbackHandler extends Handler {

        private OnActivityChangedListener mCallback;
        
        public void setCallback(OnActivityChangedListener callback) {
            mCallback = callback;
        }
        
        @Override
        public void handleMessage(Message msg) {
            if (mCallback != null) {

                ActivityRecognitionResult newActivity = (ActivityRecognitionResult) msg.obj;
                mCallback.onUserActivityChanged(msg.arg1, msg.arg2, newActivity);
            }
        }
    }
    
    public UserMotionService() {

        super("UserMotionService");
        mHandler = new CallbackHandler();
    }

    public void setOnActivityChangedListener(OnActivityChangedListener listener) {
        mHandler.setCallback(listener);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Service is stopping...");
    }
    

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {

            ActivityRecognitionResult result =
                    ActivityRecognitionResult.extractResult(intent);
            DetectedActivity activity = result.getMostProbableActivity();
            Log.v(TAG, "New User Activity Event");
            

            if (activity.getType() == DetectedActivity.UNKNOWN
                    && activity.getConfidence() < 60
                    && result.getProbableActivities().size() > 1) {

                activity = result.getProbableActivities().get(1);
            }
            

            if (mLastKnownActivity == null
                    || mLastKnownActivity.getType() != activity.getType()
                    || mLastKnownActivity.getConfidence() != activity.getConfidence()) {

                Message msg = Message.obtain(null,
                        0,                         //what
                        activity.getType(),        //arg1
                        activity.getConfidence(),  //arg2
                        result);
                mHandler.sendMessage(msg);
            }
            mLastKnownActivity = activity;
        }
    }
    

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    

    private LocalBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public UserMotionService getService() {
            return UserMotionService.this;
        }
    }
    

    public static String getActivityName(DetectedActivity activity) {
        switch(activity.getType()) {
            case DetectedActivity.IN_VEHICLE:
                return "Driving";
            case DetectedActivity.ON_BICYCLE:
                return "Biking";
            case DetectedActivity.ON_FOOT:
                return "Walking";
            case DetectedActivity.STILL:
                return "Not Moving";
            case DetectedActivity.TILTING:
                return "Tilting";
            case DetectedActivity.UNKNOWN:
            default:
                return "No Clue";
        }
    }
}
