package fr.hexus.aprivate.mondayreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationsBroadcastReceiver extends BroadcastReceiver
{
    private NotificationService notificationService;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            notificationService = new NotificationService();
            notificationService.startService(intent);
        }
    }
}
