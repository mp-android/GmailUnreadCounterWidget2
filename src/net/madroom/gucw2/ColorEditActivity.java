package net.madroom.gucw2;

import net.madroom.common.CommonUtil;
import net.madroom.gucw2.GmailUnreadCounterWidget.MyService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ColorEditActivity extends Activity {
    private static final int COLOR_MAX = 255;
    public static final int DEF_BG_COLOR_A = 128;
    public static final int DEF_BG_COLOR_R = 0;
    public static final int DEF_BG_COLOR_G = 0;
    public static final int DEF_BG_COLOR_B = 0;

    public static final int DEF_TEXT_NOT_ZERO_COLOR_A = 255;
    public static final int DEF_TEXT_NOT_ZERO_COLOR_R = 255;
    public static final int DEF_TEXT_NOT_ZERO_COLOR_G = 255;
    public static final int DEF_TEXT_NOT_ZERO_COLOR_B = 0;

    public static final int DEF_TEXT_ZERO_COLOR_A = 255;
    public static final int DEF_TEXT_ZERO_COLOR_R = 128;
    public static final int DEF_TEXT_ZERO_COLOR_G = 128;
    public static final int DEF_TEXT_ZERO_COLOR_B = 128;

    public static final String KEY_BG_COLOR_A = "key_bg_color_a";
    public static final String KEY_BG_COLOR_R = "key_bg_color_r";
    public static final String KEY_BG_COLOR_G = "key_bg_color_g";
    public static final String KEY_BG_COLOR_B = "key_bg_color_b";

    public static final String KEY_TEXT_NOT_ZERO_COLOR_A = "key_text_not_zero_color_a";
    public static final String KEY_TEXT_NOT_ZERO_COLOR_R = "key_text_not_zero_color_r";
    public static final String KEY_TEXT_NOT_ZERO_COLOR_G = "key_text_not_zero_color_g";
    public static final String KEY_TEXT_NOT_ZERO_COLOR_B = "key_text_not_zero_color_b";

    public static final String KEY_TEXT_ZERO_COLOR_A = "key_text_zero_color_a";
    public static final String KEY_TEXT_ZERO_COLOR_R = "key_text_zero_color_r";
    public static final String KEY_TEXT_ZERO_COLOR_G = "key_text_zero_color_g";
    public static final String KEY_TEXT_ZERO_COLOR_B = "key_text_zero_color_b";

    private static final int STR_NUM = 3;

    private Context mContext;
    private SharedPreferences mPref;
    private TextView mBgSeekbarTitleA;
    private TextView mBgSeekbarTitleR;
    private TextView mBgSeekbarTitleG;
    private TextView mBgSeekbarTitleB;
    private SeekBar mBgSeekbarA;
    private SeekBar mBgSeekbarR;
    private SeekBar mBgSeekbarG;
    private SeekBar mBgSeekbarB;

    private TextView mTextNotZeroSeekbarTitle;
    private TextView mTextNotZeroSeekbarTitleA;
    private TextView mTextNotZeroSeekbarTitleR;
    private TextView mTextNotZeroSeekbarTitleG;
    private TextView mTextNotZeroSeekbarTitleB;
    private SeekBar mTextNotZeroSeekbarA;
    private SeekBar mTextNotZeroSeekbarR;
    private SeekBar mTextNotZeroSeekbarG;
    private SeekBar mTextNotZeroSeekbarB;

    private TextView mTextZeroSeekbarTitle;
    private TextView mTextZeroSeekbarTitleA;
    private TextView mTextZeroSeekbarTitleR;
    private TextView mTextZeroSeekbarTitleG;
    private TextView mTextZeroSeekbarTitleB;
    private SeekBar mTextZeroSeekbarA;
    private SeekBar mTextZeroSeekbarR;
    private SeekBar mTextZeroSeekbarG;
    private SeekBar mTextZeroSeekbarB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_edit_activity_layout);
        mContext = this;
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext);

        /**
         * background
         */
        mBgSeekbarTitleA = (TextView)findViewById(R.id.bg_seekbar_title_a);
        mBgSeekbarTitleR = (TextView)findViewById(R.id.bg_seekbar_title_r);
        mBgSeekbarTitleG = (TextView)findViewById(R.id.bg_seekbar_title_g);
        mBgSeekbarTitleB = (TextView)findViewById(R.id.bg_seekbar_title_b);
        mBgSeekbarA = (SeekBar)findViewById(R.id.bg_seekbar_a);
        mBgSeekbarR = (SeekBar)findViewById(R.id.bg_seekbar_r);
        mBgSeekbarG = (SeekBar)findViewById(R.id.bg_seekbar_g);
        mBgSeekbarB = (SeekBar)findViewById(R.id.bg_seekbar_b);
        mBgSeekbarA.setMax(COLOR_MAX);
        mBgSeekbarR.setMax(COLOR_MAX);
        mBgSeekbarG.setMax(COLOR_MAX);
        mBgSeekbarB.setMax(COLOR_MAX);
        mBgSeekbarA.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeBGColor();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mBgSeekbarR.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeBGColor();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mBgSeekbarG.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeBGColor();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mBgSeekbarB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeBGColor();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * not zero text
         */
        mTextNotZeroSeekbarTitle  = (TextView)findViewById(R.id.not_zero_text_seekbar_title);
        mTextNotZeroSeekbarTitleA = (TextView)findViewById(R.id.not_zero_text_seekbar_title_a);
        mTextNotZeroSeekbarTitleR = (TextView)findViewById(R.id.not_zero_text_seekbar_title_r);
        mTextNotZeroSeekbarTitleG = (TextView)findViewById(R.id.not_zero_text_seekbar_title_g);
        mTextNotZeroSeekbarTitleB = (TextView)findViewById(R.id.not_zero_text_seekbar_title_b);

        mTextNotZeroSeekbarA = (SeekBar)findViewById(R.id.not_zero_text_seekbar_a);
        mTextNotZeroSeekbarR = (SeekBar)findViewById(R.id.not_zero_text_seekbar_r);
        mTextNotZeroSeekbarG = (SeekBar)findViewById(R.id.not_zero_text_seekbar_g);
        mTextNotZeroSeekbarB = (SeekBar)findViewById(R.id.not_zero_text_seekbar_b);
        mTextNotZeroSeekbarA.setMax(COLOR_MAX);
        mTextNotZeroSeekbarR.setMax(COLOR_MAX);
        mTextNotZeroSeekbarG.setMax(COLOR_MAX);
        mTextNotZeroSeekbarB.setMax(COLOR_MAX);

        mTextNotZeroSeekbarA.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextNotZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextNotZeroSeekbarR.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextNotZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextNotZeroSeekbarG.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextNotZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextNotZeroSeekbarB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextNotZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        /**
         * zero text
         */
        mTextZeroSeekbarTitle  = (TextView)findViewById(R.id.zero_text_seekbar_title);
        mTextZeroSeekbarTitleA = (TextView)findViewById(R.id.zero_text_seekbar_title_a);
        mTextZeroSeekbarTitleR = (TextView)findViewById(R.id.zero_text_seekbar_title_r);
        mTextZeroSeekbarTitleG = (TextView)findViewById(R.id.zero_text_seekbar_title_g);
        mTextZeroSeekbarTitleB = (TextView)findViewById(R.id.zero_text_seekbar_title_b);

        mTextZeroSeekbarA = (SeekBar)findViewById(R.id.zero_text_seekbar_a);
        mTextZeroSeekbarR = (SeekBar)findViewById(R.id.zero_text_seekbar_r);
        mTextZeroSeekbarG = (SeekBar)findViewById(R.id.zero_text_seekbar_g);
        mTextZeroSeekbarB = (SeekBar)findViewById(R.id.zero_text_seekbar_b);
        mTextZeroSeekbarA.setMax(COLOR_MAX);
        mTextZeroSeekbarR.setMax(COLOR_MAX);
        mTextZeroSeekbarG.setMax(COLOR_MAX);
        mTextZeroSeekbarB.setMax(COLOR_MAX);

        mTextZeroSeekbarA.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextZeroSeekbarR.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextZeroSeekbarG.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextZeroSeekbarB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                changeTextZero();
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final Button defaultButton = (Button)findViewById(R.id.default_button);
        defaultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDefault();
            }
        });

        final Button okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = mPref.edit();
                editor.putInt(KEY_BG_COLOR_A, mBgSeekbarA.getProgress());
                editor.putInt(KEY_BG_COLOR_R, mBgSeekbarR.getProgress());
                editor.putInt(KEY_BG_COLOR_G, mBgSeekbarG.getProgress());
                editor.putInt(KEY_BG_COLOR_B, mBgSeekbarB.getProgress());
                editor.putInt(KEY_TEXT_NOT_ZERO_COLOR_A, mTextNotZeroSeekbarA.getProgress());
                editor.putInt(KEY_TEXT_NOT_ZERO_COLOR_R, mTextNotZeroSeekbarR.getProgress());
                editor.putInt(KEY_TEXT_NOT_ZERO_COLOR_G, mTextNotZeroSeekbarG.getProgress());
                editor.putInt(KEY_TEXT_NOT_ZERO_COLOR_B, mTextNotZeroSeekbarB.getProgress());
                editor.putInt(KEY_TEXT_ZERO_COLOR_A, mTextZeroSeekbarA.getProgress());
                editor.putInt(KEY_TEXT_ZERO_COLOR_R, mTextZeroSeekbarR.getProgress());
                editor.putInt(KEY_TEXT_ZERO_COLOR_G, mTextZeroSeekbarG.getProgress());
                editor.putInt(KEY_TEXT_ZERO_COLOR_B, mTextZeroSeekbarB.getProgress());
                editor.commit();
                startService(new Intent(mContext, MyService.class));
                finish();
            }
        });
        setCurrent();
        changeBGColor();
        changeTextNotZero();
        changeTextZero();
    }
    /***************************************************************************
     * setCurrent / setDefault
     ***************************************************************************/
    private void setCurrent() {
        mBgSeekbarA.setProgress(mPref.getInt(KEY_BG_COLOR_A, DEF_BG_COLOR_A));
        mBgSeekbarR.setProgress(mPref.getInt(KEY_BG_COLOR_R, DEF_BG_COLOR_R));
        mBgSeekbarG.setProgress(mPref.getInt(KEY_BG_COLOR_G, DEF_BG_COLOR_G));
        mBgSeekbarB.setProgress(mPref.getInt(KEY_BG_COLOR_B, DEF_BG_COLOR_B));
        mTextNotZeroSeekbarA.setProgress(mPref.getInt(KEY_TEXT_NOT_ZERO_COLOR_A, DEF_TEXT_NOT_ZERO_COLOR_A));
        mTextNotZeroSeekbarR.setProgress(mPref.getInt(KEY_TEXT_NOT_ZERO_COLOR_R, DEF_TEXT_NOT_ZERO_COLOR_R));
        mTextNotZeroSeekbarG.setProgress(mPref.getInt(KEY_TEXT_NOT_ZERO_COLOR_G, DEF_TEXT_NOT_ZERO_COLOR_G));
        mTextNotZeroSeekbarB.setProgress(mPref.getInt(KEY_TEXT_NOT_ZERO_COLOR_B, DEF_TEXT_NOT_ZERO_COLOR_B));
        mTextZeroSeekbarA.setProgress(mPref.getInt(KEY_TEXT_ZERO_COLOR_A, DEF_TEXT_ZERO_COLOR_A));
        mTextZeroSeekbarR.setProgress(mPref.getInt(KEY_TEXT_ZERO_COLOR_R, DEF_TEXT_ZERO_COLOR_R));
        mTextZeroSeekbarG.setProgress(mPref.getInt(KEY_TEXT_ZERO_COLOR_G, DEF_TEXT_ZERO_COLOR_G));
        mTextZeroSeekbarB.setProgress(mPref.getInt(KEY_TEXT_ZERO_COLOR_B, DEF_TEXT_ZERO_COLOR_B));
    }
    private void setDefault() {
        mBgSeekbarA.setProgress(DEF_BG_COLOR_A);
        mBgSeekbarR.setProgress(DEF_BG_COLOR_R);
        mBgSeekbarG.setProgress(DEF_BG_COLOR_G);
        mBgSeekbarB.setProgress(DEF_BG_COLOR_B);
        mTextNotZeroSeekbarA.setProgress(DEF_TEXT_NOT_ZERO_COLOR_A);
        mTextNotZeroSeekbarR.setProgress(DEF_TEXT_NOT_ZERO_COLOR_R);
        mTextNotZeroSeekbarG.setProgress(DEF_TEXT_NOT_ZERO_COLOR_G);
        mTextNotZeroSeekbarB.setProgress(DEF_TEXT_NOT_ZERO_COLOR_B);
        mTextZeroSeekbarA.setProgress(DEF_TEXT_ZERO_COLOR_A);
        mTextZeroSeekbarR.setProgress(DEF_TEXT_ZERO_COLOR_R);
        mTextZeroSeekbarG.setProgress(DEF_TEXT_ZERO_COLOR_G);
        mTextZeroSeekbarB.setProgress(DEF_TEXT_ZERO_COLOR_B);
    }
    /***************************************************************************
     * bg
     ***************************************************************************/
    private void changeBGColor() {
        int color = Color.argb(
                mBgSeekbarA.getProgress(),
                mBgSeekbarR.getProgress(),
                mBgSeekbarG.getProgress(),
                mBgSeekbarB.getProgress());

        PaintDrawable d = new PaintDrawable(color);
        getWindow().setBackgroundDrawable(d);

        mBgSeekbarTitleA.setText(getString(R.string.alpha) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mBgSeekbarA.getProgress()));
        mBgSeekbarTitleR.setText(getString(R.string.red) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mBgSeekbarR.getProgress()));
        mBgSeekbarTitleG.setText(getString(R.string.green) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mBgSeekbarG.getProgress()));
        mBgSeekbarTitleB.setText(getString(R.string.blue) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mBgSeekbarB.getProgress()));
    }
    /***************************************************************************
     * text - not zero
     ***************************************************************************/
    private void changeTextNotZero() {
        int color = Color.argb(
                mTextNotZeroSeekbarA.getProgress(),
                mTextNotZeroSeekbarR.getProgress(),
                mTextNotZeroSeekbarG.getProgress(),
                mTextNotZeroSeekbarB.getProgress());

        mTextNotZeroSeekbarTitle.setTextColor(color);
        mTextNotZeroSeekbarTitleA.setTextColor(color);
        mTextNotZeroSeekbarTitleR.setTextColor(color);
        mTextNotZeroSeekbarTitleG.setTextColor(color);
        mTextNotZeroSeekbarTitleB.setTextColor(color);

        mTextNotZeroSeekbarTitleA.setText(getString(R.string.alpha) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextNotZeroSeekbarA.getProgress()));
        mTextNotZeroSeekbarTitleR.setText(getString(R.string.red) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextNotZeroSeekbarR.getProgress()));
        mTextNotZeroSeekbarTitleG.setText(getString(R.string.green) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextNotZeroSeekbarG.getProgress()));
        mTextNotZeroSeekbarTitleB.setText(getString(R.string.blue) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextNotZeroSeekbarB.getProgress()));
    }
    /***************************************************************************
     * text - zero
     ***************************************************************************/
    private void changeTextZero() {
        int color = Color.argb(
                mTextZeroSeekbarA.getProgress(),
                mTextZeroSeekbarR.getProgress(),
                mTextZeroSeekbarG.getProgress(),
                mTextZeroSeekbarB.getProgress());

        mTextZeroSeekbarTitle.setTextColor(color);
        mTextZeroSeekbarTitleA.setTextColor(color);
        mTextZeroSeekbarTitleR.setTextColor(color);
        mTextZeroSeekbarTitleG.setTextColor(color);
        mTextZeroSeekbarTitleB.setTextColor(color);

        mTextZeroSeekbarTitleA.setText(getString(R.string.alpha) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextZeroSeekbarA.getProgress()));
        mTextZeroSeekbarTitleR.setText(getString(R.string.red) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextZeroSeekbarR.getProgress()));
        mTextZeroSeekbarTitleG.setText(getString(R.string.green) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextZeroSeekbarG.getProgress()));
        mTextZeroSeekbarTitleB.setText(getString(R.string.blue) + ":" +
                CommonUtil.zeroPadding(STR_NUM, ""+mTextZeroSeekbarB.getProgress()));
    }
}
