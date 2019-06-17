package com.jq.app.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;

import com.jq.app.R;
import com.jq.app.ui.views.CustomVideoView;
import com.jq.app.util.Utility;
import com.squareup.picasso.Picasso;

import org.slf4j.Marker;

import at.grabner.circleprogress.CircleProgressView;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
//import com.jq.app.ui.timer.MyCountDownTimer;
import android.os.CountDownTimer;
import android.os.SystemClock;




public class ClockTime extends AppCompatActivity {
    private static final String TAG = ClockTime.class.getSimpleName();
    private CircleProgressView circleView;
    private Handler customHandler = new Handler();
    TextView hint_txt;
    int lap = 0;
    TextView lap_txt;
    private long lastTime = 0;
    long lessTime = 0;
    private int maxTime;
    ImageView pause;
    private long pauseTime = 0;
    ImageView play;
    TextView plus_txt;
    int round = 0;
    TextView round_cn_txt;
    private RelativeLayout bottom_ly;
    private RelativeLayout round_ly;
    TextView round_txt;
    long secsOld = 0;
    int selectedRound = 0;
    private long startTime = 0;
    boolean started = false;
    SwitchCompat swich_lap_round;
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    TextView timer;
    String timerName = "";
    long timerProgressEMOM = 0;
    private Runnable updateTimerThread = new C15865();
    long updatedTime = 0;
    String video_url;
    String thumbnail;
    private CustomVideoView mCustomVideoView;
    private Spinner before_spinner;
    private Spinner after_spinner;
    private Button t1, t2, t3, t4;
	    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;

	      //  final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60*1000, 1000);
		//	 myCountDownTimer.setSourceActivity(this);


    /* renamed from: com.jq.app.ui.timer.ClockTime$1 */
    class C15821 implements OnCheckedChangeListener {
        C15821() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                ClockTime.this.round_cn_txt.setText("Lap");
                ClockTime.this.round = 0;
                ClockTime.this.plus_txt.setText(Marker.ANY_NON_NULL_MARKER);
                return;
            }
            ClockTime.this.round_cn_txt.setText("Rounds");
            ClockTime.this.round = 0;
            ClockTime.this.plus_txt.setText(Marker.ANY_NON_NULL_MARKER);
        }
    }

    /* renamed from: com.jq.app.ui.timer.ClockTime$2 */
    class C15832 implements OnItemSelectedListener {
        C15832() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            Utility.setIntegerSharedPreference(ClockTime.this, "before", position);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.jq.app.ui.timer.ClockTime$3 */
    class C15843 implements OnItemSelectedListener {
        C15843() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            Utility.setIntegerSharedPreference(ClockTime.this, "after", position);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.jq.app.ui.timer.ClockTime$4 */
    class C15854 implements OnClickListener {
        C15854() {
        }

        public void onClick(View v) {
            ClockTime clockTime = ClockTime.this;
            clockTime.selectedRound++;
            TextView textView = (TextView) ClockTime.this.findViewById(R.id.header_name);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ClockTime.this.timerName);
            stringBuilder.append(" ");
            stringBuilder.append(ClockTime.this.lap);
            stringBuilder.append(" of ");
            stringBuilder.append(ClockTime.this.selectedRound);
            textView.setText(stringBuilder.toString());
        }
    }

    /* renamed from: com.jq.app.ui.timer.ClockTime$5 */
    class C15865 implements Runnable {
        C15865() {
        }

        public void run() {
			 int secs=0;
           MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;
			MilliSeconds = (int) (UpdateTime % 1000);

			Log.e(TAG, "Mili sec Timer : "+MilliSeconds);
            ClockTime clockTime = ClockTime.this;
            clockTime.timeInMilliseconds++;
			
						Log.e(TAG, "Mili sec only : "+clockTime.timeInMilliseconds);
						
if (ClockTime.this.timerName.equalsIgnoreCase("STOPWATCH")){
            ClockTime.this.circleView.setValue((float) (ClockTime.this.timeInMilliseconds - ClockTime.this.lessTime));
}else 
			{
            ClockTime.this.circleView.setValue((float) (ClockTime.this.timeInMilliseconds - ClockTime.this.lessTime));
			}
           //Log.e(TAG, "TABATA timer increase: "+ClockTime.this.timeInMilliseconds  );
            int localRound = ClockTime.this.selectedRound;

            if (ClockTime.this.getIntent().getStringExtra("name").equalsIgnoreCase("EMOM")) {
                localRound = 1;
                if (((long) ((ClockTime.this.maxTime * 60) / ClockTime.this.selectedRound)) <= ClockTime.this.timeInMilliseconds - ClockTime.this.lessTime && ((long) (ClockTime.this.maxTime * 60)) > ClockTime.this.timeInMilliseconds) {
                    long as = (long) ((ClockTime.this.maxTime * 60) / ClockTime.this.selectedRound);
                    long asa = ClockTime.this.timeInMilliseconds - ClockTime.this.lessTime;
					Log.e(TAG, "Less Time : "+asa);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" : ");
                    stringBuilder.append(as);
                    stringBuilder.append(" ");
                    stringBuilder.append(asa);
                    Log.e("maxTime", stringBuilder.toString());
                    ClockTime.this.timerProgressEMOM = ClockTime.this.timeInMilliseconds;
                    ClockTime.this.lessTime = ClockTime.this.timeInMilliseconds;
                    TextView textView = ClockTime.this.findViewById(R.id.header_title);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(ClockTime.this.lap + 1);
                    stringBuilder.append("/");
                    stringBuilder.append(getIntent().getExtras().getString("round"));
                    textView.setText(stringBuilder.toString());
                    ClockTime.this.started = false;
                    ClockTime.this.timer.setVisibility(View.GONE);
                    ClockTime.this.play.setVisibility(View.VISIBLE);
                    ClockTime.this.stopTime();
                    ClockTime.this.hint_txt.setText("tap to resume");
                    return;
                }
            }
            localRound = ClockTime.this.selectedRound;
            if (((long) (ClockTime.this.getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP") ? ClockTime.this.maxTime * 60 : (ClockTime.this.maxTime * 60) / localRound)) > ClockTime.this.timeInMilliseconds) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" : ");
                stringBuilder2.append(ClockTime.this.timerName);

                /**
                 * check video is playing or not
                 */
                //Log.e(TAG, stringBuilder2.toString());
                if (video_url != null && !video_url.equalsIgnoreCase("")) {

                    mCustomVideoView.replayTextView.setVisibility(View.GONE);
                    if (!JZMediaManager.isPlaying()) {
                        mCustomVideoView.playPause();
                    }

                }

                if (ClockTime.this.timerName.equalsIgnoreCase("TABATA")) {
                   if (ClockTime.this.updatedTime==2)
					{
                        rest();
					   Log.e(TAG, "Timer 0");
		//			    myCountDownTimer.start();
					}
else{
	               
					Log.e(TAG, "TABATA Pause-1: "+ClockTime.this.updatedTime );	  
                    ClockTime.this.updatedTime = ((long) ((ClockTime.this.maxTime * 60) / ClockTime.this.selectedRound)) - ClockTime.this.timeInMilliseconds;
					
}

                } else {
                    if (!ClockTime.this.timerName.equalsIgnoreCase("TIMER")) {
                        if (!ClockTime.this.timerName.equalsIgnoreCase("STOPWATCH")) {
                            ClockTime.this.updatedTime = ((long) (ClockTime.this.maxTime * 60)) - ClockTime.this.timeInMilliseconds;
                        }
                    }
                    ClockTime.this.updatedTime = ClockTime.this.timeInMilliseconds;
                }
               
				 if (ClockTime.this.timerName.equalsIgnoreCase("STOPWATCH")){
			     secs = (int) ClockTime.this.updatedTime/1000;
				  MilliSeconds = (int) (UpdateTime % 1000);

				 }
				 else {
					   secs = (int) ClockTime.this.updatedTime; //actual
				 }
                int mins = secs / 60;
                secs %= 60;
                TextView textView2 = ClockTime.this.timer;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(String.format("%02d", new Object[]{Integer.valueOf(mins)}));
                stringBuilder.append(":");
                stringBuilder.append(String.format("%02d", new Object[]{Integer.valueOf(secs)}));
				 if (ClockTime.this.timerName.equalsIgnoreCase("STOPWATCH")){
				stringBuilder.append(":");//
				 stringBuilder.append(String.format("%03d", new Object[]{Integer.valueOf(MilliSeconds)}));//
				 }
				// Log.e(TAG, "Mili seconds count down "+milliseconds);

                textView2.setText(stringBuilder.toString());
				if (ClockTime.this.timerName.equalsIgnoreCase("STOPWATCH")){
                ClockTime.this.customHandler.postDelayed(this, 100);
				}
				else {
				ClockTime.this.customHandler.postDelayed(this, 1000);
				}
            } else if (ClockTime.this.lap < ClockTime.this.selectedRound) {
                ClockTime.this.started = false;
                ClockTime.this.timer.setVisibility(View.GONE);
                ClockTime.this.play.setVisibility(View.VISIBLE);
                ClockTime.this.stopTime();
                ClockTime.this.hint_txt.setText("tap to resume");
                ClockTime.this.timeInMilliseconds = 0;
            } else {
                ClockTime.this.completed();
                ClockTime.this.started = false;
                /**
                 * release media player
                 */
                if (video_url != null && !video_url.equalsIgnoreCase("")) {
                    mCustomVideoView.release();
                    JZMediaManager.instance().releaseMediaPlayer();
                }


                Intent intent = new Intent(ClockTime.this, ThankYou.class);
                intent.putExtra("name", getIntent().getExtras().getString("name"));
                intent.putExtra("video_id", getIntent().getExtras().getString("video_id"));
                intent.putExtra("time", getIntent().getExtras().getString("time"));
                intent.putExtra("before_pain", before_spinner.getSelectedItem().toString());
                intent.putExtra("after_pain", after_spinner.getSelectedItem().toString());
                intent.putExtra("sets", getIntent().getExtras().getString("sets"));
                intent.putExtra("reps", getIntent().getExtras().getString("reps"));
                ClockTime.this.startActivity(intent);
                ClockTime.this.finish();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_time);
        bottom_ly = findViewById(R.id.bottom_ly);
        round_ly = findViewById(R.id.round_ly);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        this.circleView = (CircleProgressView) findViewById(R.id.circleView);
        this.circleView.setRimWidth(4);
        this.circleView.setBarWidth(16);
        this.circleView.setBarColor(new int[]{getResources().getColor(R.color.purple)});
        this.circleView.setRimColor(getResources().getColor(R.color.gray_rim));
        this.timerName = getIntent().getStringExtra("name");
        ((TextView) findViewById(R.id.titleView)).setText(this.timerName);
        ((TextView) findViewById(R.id.header_name)).setText(this.timerName);
        TextView textView = findViewById(R.id.header_title);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time Cap : ");
        stringBuilder.append(getIntent().getExtras().getString("time"));
        stringBuilder.append(" minutes");
        textView.setText(stringBuilder.toString());
        this.hint_txt = (TextView) findViewById(R.id.hint_txt);
        this.timer = (TextView) findViewById(R.id.timer);
        this.pause = (ImageView) findViewById(R.id.pause);
        this.play = (ImageView) findViewById(R.id.play);
        this.plus_txt = (TextView) findViewById(R.id.plus_txt);
        this.round_cn_txt = (TextView) findViewById(R.id.round_cn_txt);
        this.round_txt = (TextView) findViewById(R.id.round_txt);
        this.lap_txt = (TextView) findViewById(R.id.lap_txt);
        this.swich_lap_round = (SwitchCompat) findViewById(R.id.swich_lap_round);
        this.maxTime = Integer.parseInt(getIntent().getExtras().getString("time"));
        this.selectedRound = Integer.parseInt(getIntent().getExtras().getString("round"));
        if (!getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP")) {
            findViewById(R.id.add_bt).setVisibility(View.GONE);
        }
        if (!(getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP") || getIntent().getStringExtra("name").equalsIgnoreCase("TIMER") || getIntent().getStringExtra("name").equalsIgnoreCase("STOPWATCH"))) {
            TextView textView2 = findViewById(R.id.header_title);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("1/");
			Log.e(TAG, "TABATA Pause: "+ selectedRound);
            stringBuilder2.append(this.selectedRound);
            textView2.setText(stringBuilder2.toString());
            round_ly.setVisibility(View.GONE);
        }
        if (getIntent().getStringExtra("name").equalsIgnoreCase("STOPWATCH")) {
            findViewById(R.id.botton_scales).setVisibility(View.GONE);
            this.round_txt.setVisibility(View.VISIBLE);
            this.lap_txt.setVisibility(View.VISIBLE);
            this.swich_lap_round.setVisibility(View.VISIBLE);
            this.round_cn_txt.setText("Lap");
        }
        this.swich_lap_round.setOnCheckedChangeListener(new C15821());
        if (getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP")) {
            this.circleView.setBarColor(new int[]{getResources().getColor(R.color.amrap)});
            this.timer.setTextColor(getResources().getColor(R.color.amrap));
            this.play.setImageResource(R.drawable.ic_play_purple);
            this.pause.setImageResource(R.drawable.ic_pause_purple);
            this.plus_txt.setBackgroundResource(R.drawable.round_counter_pink);

            /**
             * change option button
             */
            setButtonText("EMOM", "STOPWATCH", "TABATA", "TIMER");

        } else if (getIntent().getStringExtra("name").equalsIgnoreCase("EMOM")) {
            this.circleView.setBarColor(new int[]{getResources().getColor(R.color.emom)});
            this.timer.setTextColor(getResources().getColor(R.color.emom));
            this.play.setImageResource(R.drawable.ic_play_emom);
            this.pause.setImageResource(R.drawable.ic_pause_emom);

            /**
             * change option button
             */
            setButtonText("AMRAP", "STOPWATCH", "TABATA", "TIMER");


        } else if (getIntent().getStringExtra("name").equalsIgnoreCase("STOPWATCH")) {
            this.circleView.setBarColor(new int[]{getResources().getColor(R.color.stopwatch)});
            this.timer.setTextColor(getResources().getColor(R.color.stopwatch));
            this.play.setImageResource(R.drawable.ic_play_stop);
            this.pause.setImageResource(R.drawable.ic_pause_stop);
            this.plus_txt.setBackgroundResource(R.drawable.round_counter_stop_watch);

            /**
             * change option button
             */
            setButtonText("EMOM", "AMRAP", "TABATA", "TIMER");


        } else if (getIntent().getStringExtra("name").equalsIgnoreCase("TABATA")) {
Log.e(TAG, "SELECTED_WORK - 4: " );
            this.circleView.setBarColor(new int[]{getResources().getColor(R.color.tabata)});
            this.timer.setTextColor(getResources().getColor(R.color.tabata));
            this.play.setImageResource(R.drawable.ic_play_tabata);
            this.pause.setImageResource(R.drawable.ic_pause_tabata);

            /**
             * change option button
             */
            setButtonText("EMOM", "STOPWATCH", "AMRAP", "TIMER");


        } else if (getIntent().getStringExtra("name").equalsIgnoreCase("TIMER")) {
            this.circleView.setBarColor(new int[]{getResources().getColor(R.color.timer)});
            this.timer.setTextColor(getResources().getColor(R.color.timer));
            this.play.setImageResource(R.drawable.ic_play_timer);
            this.pause.setImageResource(R.drawable.ic_pause_timer);
            this.plus_txt.setBackgroundResource(R.drawable.round_counter_timer);

            /**
             * change option button
             */
            setButtonText("EMOM", "STOPWATCH", "TABATA", "AMRAP");


        }

        after_spinner = findViewById(R.id.after_spinner);
        after_spinner.setSelection(Utility.getIntegerSharedPreferences(this, "after"));
        after_spinner.setOnItemSelectedListener(new C15843());

        before_spinner = findViewById(R.id.before_spinner);
        before_spinner.setSelection(Utility.getIntegerSharedPreferences(this, "before"));
        before_spinner.setOnItemSelectedListener(new C15832());


        if (getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP")) {
            TextView textView3 = (TextView) findViewById(R.id.header_name);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(this.timerName);
            stringBuilder3.append(" ");
            stringBuilder3.append(this.lap + 1);
            stringBuilder3.append(" of ");
            stringBuilder3.append(this.selectedRound);
            textView3.setText(stringBuilder3.toString());
        }
        findViewById(R.id.add_bt).setOnClickListener(new C15854());

        /**
         * hilde bottom timer
         */
//        findViewById(R.id.bottom_ly).setVisibility(View.GONE);

        video_url = getIntent().getExtras().getString("video_url");
        thumbnail = getIntent().getExtras().getString("thumbnail");

        mCustomVideoView = findViewById(R.id.videoplayer);
        mCustomVideoView.batteryLevel.setVisibility(View.GONE);
        mCustomVideoView.hideSkip();
        if (video_url != null && !video_url.equalsIgnoreCase("")) {
            mCustomVideoView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(thumbnail).into(this.mCustomVideoView.thumbImageView);

            bottom_ly.setVisibility(View.GONE);

        } else {
            mCustomVideoView.setVisibility(View.GONE);
            bottom_ly.setVisibility(View.VISIBLE);
        }

//        videoplayer

    }

    private void setButtonText(String textT1, String textT2, String textT3, String textT4) {
        t1.setText(textT1);
        t2.setText(textT2);
        t3.setText(textT3);
        t4.setText(textT4);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            onBackPressed();
        } else if (v.getId() == R.id.play) {
            this.started = true;
			Log.e(TAG, "TABATA Pause: " );
            v.setVisibility(View.GONE);
            this.timer.setVisibility(View.VISIBLE);
            this.hint_txt.setText("tap to pause");
            startTime();

            /**
             * play video
             */
            if (video_url != null && !video_url.equalsIgnoreCase("")) {
                setAndStartVideo();
            }


        } else if (v.getId() == R.id.pause) {
            this.started = true;
			Log.e(TAG, "TABATA Pause-1: " );
            v.setVisibility(View.GONE);
            this.timer.setVisibility(View.VISIBLE);
		    TimeBuff += MillisecondTime;

            resume();
            this.hint_txt.setText("tap to pause");

            /**
             * Play Pause
             */
            goOnPlayOnPause();

        } else if (v.getId() == R.id.timer) {
            this.started = false;
            v.setVisibility(View.GONE);
			Log.e(TAG, "TABATA Pause-2: " );
            this.pause.setVisibility(View.VISIBLE);
            stopTime();
            this.hint_txt.setText("tap to resume");
        } else if (v.getId() == R.id.round_ly) {
            if (this.started) {
                long last_round = this.timeInMilliseconds - this.lastTime;
                this.lastTime += last_round;

                int secs = 0;

                int mins = ((int) last_round) / 60;

                TextView textView = findViewById(R.id.header_title);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(getIntent().getIntExtra("time", 0));
                stringBuilder.append(" minutes / Last round : ");
                stringBuilder.append(String.format("%02d", new Object[]{Integer.valueOf(mins)}));
                stringBuilder.append(":");
                stringBuilder.append(String.format("%02d", new Object[]{Integer.valueOf(secs)}));
                textView.setText(stringBuilder.toString());
                this.round++;
                TextView textView2 = this.plus_txt;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.round);
                stringBuilder2.append("");
                textView2.setText(stringBuilder2.toString());
            }
        } else if (v.getId() == R.id.t1) {

            startSelectTime(t1.getText().toString());

        } else if (v.getId() == R.id.t2) {

            startSelectTime(t2.getText().toString());

        } else if (v.getId() == R.id.t3) {

            startSelectTime(t3.getText().toString());

        } else if (v.getId() == R.id.t4) {

            startSelectTime(t4.getText().toString());

        }
    }

    private void startSelectTime(String buttonName) {
        Intent intent = new Intent(this, SelectTime.class);
        intent.putExtra("name", buttonName);
        startActivity(intent);
        finish();

    }

    private void startTime() {
        this.circleView.setValue(0.0f);
        if (getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP")) {

            this.circleView.setMaxValue((float) (this.maxTime * 60));

        } else {

            this.circleView.setMaxValue((float) ((this.maxTime * 60) / this.selectedRound));

        }
        if (this.timerName.equalsIgnoreCase("TABATA")) {
            this.updatedTime = (long) ((this.maxTime * 60) / this.selectedRound);
			Log.e(TAG, "TABATA : " + updatedTime);

			Log.e(TAG, "TABATA - maxTime : " + maxTime);

        } else if (this.timerName.equalsIgnoreCase("TIMER") && this.timerName.equalsIgnoreCase("STOPWATCH")) {
            this.updatedTime = 0;
                            StartTime = SystemClock.uptimeMillis();// added for mili second calculation

        } else {
            this.updatedTime = (long) (this.maxTime * 60);
            if (this.timeInMilliseconds > 0) {
                this.updatedTime -= this.timeInMilliseconds;
            }
        }
        //long miliSec=timeInMilliseconds++;
        int secs = (int) this.updatedTime;
		//int miliSec=(int) (timeInMilliseconds % 1000);;
		//int milliseconds = (int) (secs / 1000);
        int mins = secs / 60;
        secs %= 60;
		// int milliseconds = (int) (secs % 1000);
        TextView textView = this.timer;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(String.format("%02d", new Object[]{Integer.valueOf(mins)}));
        stringBuilder.append(":");
        stringBuilder.append(String.format("%02d", new Object[]{Integer.valueOf(secs)}));

		//stringBuilder.append(":");
        //stringBuilder.append(String.format("%03d", new Object[]{Integer.valueOf(miliSec)}));
		// Log.e(TAG, "Mili seconds count down Next Level"+miliSec);


        textView.setText(stringBuilder.toString());
        this.lap++;
        if (!(getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP") || getIntent().getStringExtra("name").equalsIgnoreCase("TIMER") || getIntent().getStringExtra("name").equalsIgnoreCase("STOPWATCH"))) {
            textView = findViewById(R.id.header_title);
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.lap);
            stringBuilder.append("/");
            stringBuilder.append(getIntent().getExtras().getString("round"));
            textView.setText(stringBuilder.toString());
        }
        this.customHandler.postDelayed(this.updateTimerThread, 1000);
		Log.e(TAG, "TABATA updateTimerThread: "+updateTimerThread );
        if (getIntent().getStringExtra("name").equalsIgnoreCase("AMRAP")) {
            textView = (TextView) findViewById(R.id.header_name);
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.timerName);
            stringBuilder.append(" ");
            stringBuilder.append(this.lap);
            stringBuilder.append(" of ");
            stringBuilder.append(this.selectedRound);
            textView.setText(stringBuilder.toString());
        }
    }

    private void completed() {
		Log.e(TAG, "TABATA updateTimerThread 577 : "+updateTimerThread );
        this.customHandler.removeCallbacks(this.updateTimerThread);

    }

    private void stopTime() {
        this.pauseTime = SystemClock.uptimeMillis();
        this.customHandler.removeCallbacks(this.updateTimerThread);
    }

    private void resume() {
        this.customHandler.postDelayed(this.updateTimerThread, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.customHandler.removeCallbacks(this.updateTimerThread);
    }


    public void setAndStartVideo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" mVideo.url : ");
        stringBuilder.append(video_url);
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mVideo.thumbnail_url : ");
        stringBuilder.append(thumbnail);

        this.mCustomVideoView.setUp(video_url, 0, new Object[]{" "});
        setTitle(" ");
        mCustomVideoView.playPause();

        Log.e(TAG, "video_url : " + video_url);
        Log.e(TAG, "thumbnail : " + thumbnail);
    }

    protected void goOnPlayOnPause() {
        JZVideoPlayer.goOnPlayOnPause();
    }


	/* here This method will be invoked when CountDownTimer finish. */
	/*
    public void onCountDownTimerFinishEvent()
    {
      //  this.buttonSendVerifyCode.setEnabled(true);
    }

    //This method will be invoked when CountDownTimer tick event happened./
    public void onCountDownTimerTickEvent(long millisUntilFinished)
    {
        // Calculate left seconds.
        long leftSeconds = millisUntilFinished / 1000;

       // String sendButtonText = "Left " + leftSeconds + " (s)";

        if(leftSeconds==0)
        {
           // sendButtonText = "Send Verification Code";
		   Log.e(TAG, " Rest Finished : " );
		  startTime();
        }

		else{
			Log.e(TAG, " Rest Start : "+leftSeconds );
			AlertDialog alertDialog = new AlertDialog.Builder(ClockTime.this).create();
                alertDialog.setMessage("Left Second "+leftSeconds);
                alertDialog.show();
		//startTime();
		}

        // Show left seconds in send button.
        //this.buttonSendVerifyCode.setText(sendButtonText);
    }
	*/

	private void rest()
	{
	final	AlertDialog alertDialog = new AlertDialog.Builder(this).create();
alertDialog.setTitle("Rest Start");  
alertDialog.setMessage("00:10");
alertDialog.show();   // 

new CountDownTimer(10000, 1000) {
    @Override
    public void onTick(long millisUntilFinished) {
      

	   long restTime=millisUntilFinished/1000;
	   if(restTime==1)
		{
		   alertDialog.cancel();
		   startTime();
		}
		else {
			 goOnPlayOnPause();
		 alertDialog.setMessage("00:"+ (millisUntilFinished/1000));
		}
    }

    @Override
    public void onFinish() {
       //this.sourceActivity.onCountDownTimerFinishEvent();
    }
}.start();
	}



}
