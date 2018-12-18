package ec.edu.uce.appperiodictask6_3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class JobSchedulerActivity extends Activity implements View.OnClickListener {


    private static final int JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        long interval = 5*1000;

        JobInfo info = new JobInfo.Builder(JOB_ID,
                new ComponentName(getPackageName(), WorkerService.class.getName()))
                .setPeriodic(interval)
                .build();

        switch (view.getId()) {
            case R.id.start:

                int result = scheduler.schedule(info);
                if (result <= 0) {
                    Toast.makeText(this, "Error Scheduling Job", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.stop:

                scheduler.cancel(JOB_ID);
                break;
            default:
                break;
        }
    }
}
