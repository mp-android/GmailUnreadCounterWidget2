package net.madroom.gucw2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ProviderChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, GmailUnreadCounterWidget.MyService.class));
    }
}
