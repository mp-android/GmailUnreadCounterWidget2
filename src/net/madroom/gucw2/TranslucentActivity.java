package net.madroom.gucw2;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;

import com.google.android.gm.contentprovider.GmailContract;

public class TranslucentActivity extends Activity {

    private static final String ACCOUNT_TYPE_GOOGLE = "com.google";
    private static final String GMAIL_PACKAGE_NAME = "com.google.android.gm";
    private static final String COLOR_NUM_ZERO = "\"#777777\"";
    private static final String COLOR_NAME_ZERO = "\"#777777\"";
    private static final String COLOR_NUM_NOT_ZERO = "\"#ff0000\"";
    private static final String COLOR_NAME_NOT_ZERO = "\"#000000\"";

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translucent_activity_layout);

        mContext = this;

        final AccountManager accountManager = AccountManager.get(mContext);
        final Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE_GOOGLE);
        final ArrayList<String> namesArray = new ArrayList<String>();
        final ArrayList<Integer> numsArray = new ArrayList<Integer>();

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
                        namesArray.add(account.name);
                        numsArray.add(c.getInt(c.getColumnIndexOrThrow(numUnreadConversations)));
                        break;
                    }
                } while(c.moveToNext());
            }
            c.close();
        }

        final CharSequence[] items = new CharSequence[namesArray.size()];
        for(int i=0; i<namesArray.size(); i++) {
            int num = numsArray.get(i);
            String colorNum;
            String colorName;
            if(num==0) {
                colorNum = COLOR_NUM_ZERO;
                colorName = COLOR_NAME_ZERO;
            } else {
                colorNum = COLOR_NUM_NOT_ZERO;
                colorName = COLOR_NAME_NOT_ZERO;
            }
            String html =
                "<font color=" + colorNum + ">" + numsArray.get(i) + "</font><br />" +
                "<font color=" + colorName + ">" +  namesArray.get(i) + "</font>";
            items[i] = Html.fromHtml(html);
        }

        final CharSequence[] names =(CharSequence[])namesArray.toArray(new CharSequence[0]);

        if(names.length==1) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setComponent(new ComponentName(GMAIL_PACKAGE_NAME, GMAIL_PACKAGE_NAME+".ConversationListActivityGmail"));
            i.setData(Uri.parse("content://gmail-ls/account/"+names[0]+"/label/^i"));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(i);
            finish();
            return;
        }

        new Builder(mContext).setTitle(R.string.select)
        .setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setComponent(new ComponentName(GMAIL_PACKAGE_NAME, GMAIL_PACKAGE_NAME+".ConversationListActivityGmail"));
                i.setData(Uri.parse("content://gmail-ls/account/"+names[which]+"/label/^i"));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(i);
                finish();
            }
        })
        .setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_BACK == keyCode) {
                    finish();
                }
                return false;
            }
        })
        .show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        startService(new Intent(this, GmailUnreadCounterWidget.MyService.class));
    }
}
