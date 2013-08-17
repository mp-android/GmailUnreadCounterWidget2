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

        final Account[] accounts = AccountManager.get(mContext).getAccountsByType(ACCOUNT_TYPE_GOOGLE);

        final ArrayList<String> namesArray = new ArrayList<String>();
        final ArrayList<String> inboxesArray = new ArrayList<String>();
        final ArrayList<Integer> numsArray = new ArrayList<Integer>();

        final String[] projection = {
            GmailContract.Labels.NUM_UNREAD_CONVERSATIONS, GmailContract.Labels.CANONICAL_NAME};

        for(Account account : accounts) {
            Cursor c = getContentResolver().query(
                GmailContract.Labels.getLabelsUri(account.name), projection, null, null, null);

            if(c.moveToFirst()) {
                final String inboxName = GmailContract.Labels.LabelCanonicalNames.CANONICAL_NAME_INBOX_CATEGORY_PRIMARY;
                final String oldInboxName = GmailContract.Labels.LabelCanonicalNames.CANONICAL_NAME_INBOX;

                final String compare = c.getString(c.getColumnIndexOrThrow(GmailContract.Labels.CANONICAL_NAME));
                do {
                    if (inboxName.equals(compare) || oldInboxName.equals(compare)) {
                        namesArray.add(account.name);
                        inboxesArray.add(compare);
                        numsArray.add(c.getInt(c.getColumnIndexOrThrow(GmailContract.Labels.NUM_UNREAD_CONVERSATIONS)));
                        break;
                    }
                } while(c.moveToNext());
            }
            c.close();
        }

        final CharSequence[] items = new CharSequence[namesArray.size()];
        for(int i=0; i<namesArray.size(); i++) {
            final int num = numsArray.get(i);
            final String name = namesArray.get(i);

            final String colorNum = num == 0 ? COLOR_NUM_ZERO : COLOR_NUM_NOT_ZERO;
            final String colorName = num == 0 ? COLOR_NAME_ZERO : COLOR_NAME_NOT_ZERO;

            final String html =
                "<font color=" + colorNum + ">" + num + "</font><br />" +
                "<font color=" + colorName + ">" +  name + "</font>";

            items[i] = Html.fromHtml(html);
        }

        final CharSequence[] names =(CharSequence[])namesArray.toArray(new CharSequence[0]);
        final CharSequence[] inboxes =(CharSequence[])inboxesArray.toArray(new CharSequence[0]);

        if(names.length==1) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setComponent(new ComponentName(GMAIL_PACKAGE_NAME, GMAIL_PACKAGE_NAME+".ConversationListActivityGmail"));
            i.setData(Uri.parse("content://gmail-ls/account/"+names[0]+"/label/"+inboxes[0]));
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
                i.setData(Uri.parse("content://gmail-ls/account/"+names[which]+"/label/"+inboxes[which]));
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
