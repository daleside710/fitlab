package com.jq.app.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.jq.app.R;
import com.jq.app.data.content_helpers.BaseContentHelper;
import com.jq.app.data.content_helpers.ProfileHelper;
import com.jq.app.ui.MainActivity;
import com.jq.app.ui.base.BaseMainActivity;
import com.jq.app.util.base.BaseFragment;
import com.jq.chatsdk.Utils.helper.ChatSDKProfileHelper;
import com.jq.chatsdk.Utils.helper.ChatSDKUiHelper;
import com.jq.chatsdk.dao.BUser;
import com.jq.chatsdk.network.BNetworkManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends BaseFragment implements OnChartValueSelectedListener {
    private static final String TAG = ProfileFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected ChatSDKUiHelper chatSDKUiHelper;
    protected ChatSDKProfileHelper chatSDKProfileHelper;
    BUser currentUser;
    private CircleImageView profileImage;
    private TextView tvName, ageAndWeight;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private PieChart mChart;

    private AppCompatSeekBar seekbar_mobility, seekbar_exercise, seekbar_stretching, seekbar_pain_level, seekbar_weight;

    ProfileHelper profileHelper;

    private Typeface Caviar_Dreams_Bold;
    private Typeface Caviar_Dreams_Regular;
    private Typeface Cool_Vetica_Regular;

    private TextView tv_goals, tv_mobility, tv_exercise, tv_stretching, tv_pain_level, tv_weight, tv_work_out_time;
    private Button btnSetGoals;
    private Button editButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Caviar_Dreams_Bold = Typeface.createFromAsset((getActivity().getApplicationContext().getAssets()), String.format(Locale.US, "hasnain_fonts/%s", "Caviar_Dreams_Bold.ttf"));
        Caviar_Dreams_Regular = Typeface.createFromAsset((getActivity().getApplicationContext().getAssets()), String.format(Locale.US, "hasnain_fonts/%s", "CaviarDreams.ttf"));
        Cool_Vetica_Regular = Typeface.createFromAsset((getActivity().getApplicationContext().getAssets()), String.format(Locale.US, "hasnain_fonts/%s", "coolvetica_rg.ttf"));

        btnSetGoals = view.findViewById(R.id.btnSetGoals);
        btnSetGoals.setTypeface(Caviar_Dreams_Regular);

        btnSetGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetGoalActivity.class));
            }
        });

        profileImage = view.findViewById(R.id.profileImage);
        profileImage.setBorderWidth(2);
        profileImage.setBorderColor(Color.RED);


        editButton = view.findViewById(R.id.editButton);
        editButton.setTypeface(Caviar_Dreams_Regular);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        tvName = view.findViewById(R.id.tvName);
        tvName.setTypeface(Caviar_Dreams_Bold);

        tv_goals = view.findViewById(R.id.tv_goals);
        tv_goals.setTypeface(Caviar_Dreams_Bold);

        tv_mobility = view.findViewById(R.id.tv_mobility);
        tv_mobility.setTypeface(Caviar_Dreams_Regular);

        tv_exercise = view.findViewById(R.id.tv_exercise);
        tv_exercise.setTypeface(Caviar_Dreams_Regular);

        tv_stretching = view.findViewById(R.id.tv_stretching);
        tv_stretching.setTypeface(Caviar_Dreams_Regular);

        tv_pain_level = view.findViewById(R.id.tv_pain_level);
        tv_pain_level.setTypeface(Caviar_Dreams_Regular);

        tv_weight = view.findViewById(R.id.tv_weight);
        tv_weight.setTypeface(Caviar_Dreams_Regular);

        tv_work_out_time = view.findViewById(R.id.tv_work_out_time);
        tv_work_out_time.setTypeface(Caviar_Dreams_Bold);

        ageAndWeight = view.findViewById(R.id.ageAndWeight);
        ageAndWeight.setTypeface(Caviar_Dreams_Regular);

        profileHelper = new ProfileHelper(this);
        profileHelper.getProfile();
        profileHelper.getActivity();

        seekbar_mobility = view.findViewById(R.id.seekbar_mobility);
        seekbar_mobility.getThumb().mutate().setAlpha(0);
        seekbar_exercise = view.findViewById(R.id.seekbar_exercise);
        seekbar_exercise.getThumb().mutate().setAlpha(0);
        seekbar_stretching = view.findViewById(R.id.seekbar_stretching);
        seekbar_stretching.getThumb().mutate().setAlpha(0);
        seekbar_pain_level = view.findViewById(R.id.seekbar_pain_level);
        seekbar_pain_level.getThumb().mutate().setAlpha(0);
        seekbar_weight = view.findViewById(R.id.seekbar_weight);
        seekbar_weight.getThumb().mutate().setAlpha(0);

        seekbar_mobility.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        seekbar_exercise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        seekbar_stretching.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        seekbar_pain_level.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        seekbar_weight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        mChart = view.findViewById(R.id.chart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(mTfLight);
        //mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(48f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(-90);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(15f);

        ProgressBar progressBar = view.findViewById(com.jq.chatsdk.R.id.chat_sdk_progressbar);
        chatSDKUiHelper = ChatSDKUiHelper.getInstance().get(getActivity());
        chatSDKProfileHelper = new ChatSDKProfileHelper(getActivity(), profileImage, progressBar, chatSDKUiHelper, view);
        chatSDKProfileHelper.loadProfilePic();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentUser = BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel();
        if (currentUser != null) {
            tvName.setText(currentUser.getMetaName());

        } else {
            ((MainActivity) getActivity()).logout();
        }

        updateUI();
    }

    private void setData() {
        int sum = profileHelper.month_mobility + profileHelper.month_exercise + profileHelper.month_stretching;
        if (sum <= 0) return;

        float percentMobility = profileHelper.month_mobility * 100 / sum;
        float percentExercise = profileHelper.month_exercise * 100 / sum;
        float percentStretching = profileHelper.month_stretching * 100 / sum;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if (percentMobility > 0) {
            entries.add(new PieEntry(percentMobility, "Mobility", getResources().getDrawable(R.drawable.star)));
        }
        if (percentExercise > 0) {
            entries.add(new PieEntry(percentExercise, "Exercise", getResources().getDrawable(R.drawable.star)));
        }
        if (percentStretching > 0) {
            entries.add(new PieEntry(percentStretching, "Stretch", getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Exercise Results");

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(4, 244, 222));
        colors.add(Color.rgb(24, 95, 219));
        colors.add(Color.rgb(252, 0, 0));
        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void updateUI() {

        if (profileHelper.age != null && profileHelper.weight != null && profileHelper.height != null) {
            ageAndWeight.setText("Age: " + profileHelper.age + " / Wt " + profileHelper.weight + "lbs" + " / Ht " + profileHelper.height);
            tvName.setText(profileHelper.username);
        }

        seekbar_mobility.setMax(profileHelper.plan_mobility);
        seekbar_mobility.setProgress(profileHelper.month_mobility);
        seekbar_exercise.setMax(profileHelper.plan_exercise);
        seekbar_exercise.setProgress(profileHelper.month_exercise);
        seekbar_stretching.setMax(profileHelper.plan_stretching);
        seekbar_stretching.setProgress(profileHelper.month_stretching);
        seekbar_weight.setMax(profileHelper.plan_weight);
        seekbar_weight.setProgress(profileHelper.user_weight);
        seekbar_pain_level.setProgress(profileHelper.pain_level);

        setData();
    }

    @Override
    public void onFinishedAction(int action, int index, String errMsg) {
        hideProgressDialog();
        if (errMsg != null && !errMsg.isEmpty()) {
            Log.e(TAG," No Target set: ");
          //  showToast(errMsg);
        }

        switch (action) {
            case ProfileHelper.REQUEST_LIST:
                updateUI();
                break;

            case ProfileHelper.REQUEST_GET_ACTIVITY:
                updateUI();
                break;
        }
    }

}
