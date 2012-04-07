package net.madroom.gucw2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.android.gm.contentprovider.GmailContract;

public class GmailUnreadCounterWidget extends AppWidgetProvider {

    private static final String ACCOUNT_TYPE_GOOGLE = "com.google";
    private static final String ACTION_CLICK = "net.madroom.gucw.action.CLICK";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, MyService.class));
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public static class MyService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        Context mContext;
        private SharedPreferences mPref;
        ComponentName mComponentName;
        AppWidgetManager mManager;
        RemoteViews mRemoteViews;
        AccountManager mAccountManager;

        @Override
        public void onCreate() {
            /**
             * initialize.
             */
            mContext = this;
            mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
            mComponentName = new ComponentName(mContext, GmailUnreadCounterWidget.class);
            mManager = AppWidgetManager.getInstance(mContext);
            mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
            mAccountManager = AccountManager.get(mContext);
        }

        @Override
        public void onStart(Intent intent, int startId) {

            final int widgetCount = AppWidgetManager.getInstance(mContext).getAppWidgetIds(new ComponentName(mContext, GmailUnreadCounterWidget.class)).length;
            if(widgetCount==0) return;

            if(intent!=null && intent.getAction()!=null) {
                if(intent.getAction().equals(ACTION_CLICK)) {
                    Intent i = new Intent(mContext, TranslucentActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    stopSelf();
                    return;
                }
            }

            final Account[] accounts = mAccountManager.getAccountsByType(ACCOUNT_TYPE_GOOGLE);

            int total = 0;
            for(Account account : accounts) {
                final String numUnreadConversations = GmailContract.Labels.NUM_UNREAD_CONVERSATIONS;
                final String canonicalName = GmailContract.Labels.CANONICAL_NAME;
                String[] projection = {
                        numUnreadConversations,
                        canonicalName
                };

                Cursor c = getContentResolver().query(
                        GmailContract.Labels.getLabelsUri(account.name),
                        projection, null, null, null);

                if(c.moveToFirst()) {
                    final String inboxCanonicalName = GmailContract.Labels.LabelCanonicalNames.CANONICAL_NAME_INBOX;
                    final int canonicalNameIndex = c.getColumnIndexOrThrow(canonicalName);
                    do {
                        if (inboxCanonicalName.equals(c.getString(canonicalNameIndex))) {
                            total += c.getInt(c.getColumnIndexOrThrow(numUnreadConversations));
                            break;
                        }
                    } while(c.moveToNext());
                }
                c.close();

            }

            Intent clickIntent = new Intent();
            clickIntent.setAction(ACTION_CLICK);
            PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, clickIntent, 0);
            mRemoteViews.setOnClickPendingIntent(R.id.base_layout, pendingIntent);

            mRemoteViews.setTextViewText(R.id.count_text, ""+total);
            if(total==0) {
                int color = Color.argb(
                        mPref.getInt(ColorEditActivity.KEY_TEXT_ZERO_COLOR_A, ColorEditActivity.DEF_TEXT_ZERO_COLOR_A),
                        mPref.getInt(ColorEditActivity.KEY_TEXT_ZERO_COLOR_R, ColorEditActivity.DEF_TEXT_ZERO_COLOR_R),
                        mPref.getInt(ColorEditActivity.KEY_TEXT_ZERO_COLOR_G, ColorEditActivity.DEF_TEXT_ZERO_COLOR_G),
                        mPref.getInt(ColorEditActivity.KEY_TEXT_ZERO_COLOR_B, ColorEditActivity.DEF_TEXT_ZERO_COLOR_B));
                mRemoteViews.setTextColor(R.id.count_text, color);
            } else {
                int color = Color.argb(
                        mPref.getInt(ColorEditActivity.KEY_TEXT_NOT_ZERO_COLOR_A, ColorEditActivity.DEF_TEXT_NOT_ZERO_COLOR_A),
                        mPref.getInt(ColorEditActivity.KEY_TEXT_NOT_ZERO_COLOR_R, ColorEditActivity.DEF_TEXT_NOT_ZERO_COLOR_R),
                        mPref.getInt(ColorEditActivity.KEY_TEXT_NOT_ZERO_COLOR_G, ColorEditActivity.DEF_TEXT_NOT_ZERO_COLOR_G),
                        mPref.getInt(ColorEditActivity.KEY_TEXT_NOT_ZERO_COLOR_B, ColorEditActivity.DEF_TEXT_NOT_ZERO_COLOR_B));
                mRemoteViews.setTextColor(R.id.count_text, color);
            }
            int color = Color.argb(
                    mPref.getInt(ColorEditActivity.KEY_BG_COLOR_A, ColorEditActivity.DEF_BG_COLOR_A),
                    mPref.getInt(ColorEditActivity.KEY_BG_COLOR_R, ColorEditActivity.DEF_BG_COLOR_R),
                    mPref.getInt(ColorEditActivity.KEY_BG_COLOR_G, ColorEditActivity.DEF_BG_COLOR_G),
                    mPref.getInt(ColorEditActivity.KEY_BG_COLOR_B, ColorEditActivity.DEF_BG_COLOR_B));
            mRemoteViews.setInt(R.id.base_layout, "setBackgroundColor", color);

            mManager.updateAppWidget(mComponentName, mRemoteViews);

            stopSelf();

        }
    }

}
