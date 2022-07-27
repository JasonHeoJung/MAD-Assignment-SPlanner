package sg.edu.np.mad.splanner;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class alarmreciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, ScheduleFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,i,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Splanner")
                .setSmallIcon(R.drawable.logo_png)
                .setContentText("Class Alarm")
                .setContentText("Click to view what class you have")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
