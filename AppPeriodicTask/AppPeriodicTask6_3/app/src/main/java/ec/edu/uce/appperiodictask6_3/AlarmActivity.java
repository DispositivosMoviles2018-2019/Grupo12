package ec.edu.uce.appperiodictask6_3;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends Activity implements View.OnClickListener {
    
    private PendingIntent mAlarmIntent;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);

        Intent launchIntent = new Intent(this, AlarmService.class);
        mAlarmIntent = PendingIntent.getService(this, 0, launchIntent, 0);
    }

    @Override
    public void onClick(View v) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long interval = 5*1000;
        
        switch(v.getId()) {
        case R.id.start:
            Toast.makeText(this, "Scheduled", Toast.LENGTH_SHORT).show();
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime()+interval,
                    interval,
                    mAlarmIntent);
            break;
        case R.id.stop:
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            manager.cancel(mAlarmIntent);
            break;
        default:
            break;
        }
    }
    

    private long nextStartTime() {

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 10);
        startTime.set(Calendar.MINUTE, 37);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        

        Calendar now = Calendar.getInstance();
        
        if(now.before(startTime)) {

            return startTime.getTimeInMillis();
        } else {

            startTime.add(Calendar.DATE, 1);
            return startTime.getTimeInMillis();
        }
    }
}