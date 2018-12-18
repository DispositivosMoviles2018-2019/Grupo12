package ec.edu.uce.appperiodictask6_3;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WorkerService extends JobService {

    private static final int MSG_JOB = 1;


    private Handler mJobProcessor = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            JobParameters params = (JobParameters) msg.obj;
            Log.i("WorkerService", "Executing Job "+params.getJobId());

            doWork();
            jobFinished(params, false);

            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("WorkerService", "Start Job "+jobParameters.getJobId());

        mJobProcessor.sendMessageDelayed(
                Message.obtain(mJobProcessor, MSG_JOB, jobParameters),
                7500
        );


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.w("WorkerService", "Stop Job "+jobParameters.getJobId());

        mJobProcessor.removeMessages(MSG_JOB);


        return false;
    }

    private void doWork() {

        Calendar now = Calendar.getInstance();
        DateFormat formatter = SimpleDateFormat.getTimeInstance();
        Toast.makeText(this, formatter.format(now.getTime()), Toast.LENGTH_SHORT).show();
    }
}
