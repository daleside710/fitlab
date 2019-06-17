package com.jq.app.ui.home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jq.app.R;
import com.jq.app.data.model.MainCategoryModel;
import com.jq.app.ui.MainActivity;
import com.jq.app.ui.auth.LoginActivity;
import com.jq.app.ui.createplanner.SelectPlannerTypeActivity;
import com.jq.app.ui.search.SearchActivity;
import com.jq.app.ui.search.VideosActivity;
import com.jq.app.util.base.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jq.app.ui.sport.SportFragment;
import com.jq.app.ui.my_workout.MyWorkoutFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements MainCategoryAdapter.OnListFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EasyRecyclerView recyclerView;
    MainCategoryAdapter dataAdapter;
    private TextView tv_title;
    private Typeface Caviar_Dreams_Regular;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Caviar_Dreams_Regular = Typeface.createFromAsset((getActivity().getApplicationContext().getAssets()), String.format(Locale.US, "hasnain_fonts/%s", "CaviarDreams.ttf"));

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dataAdapter = new MainCategoryAdapter(Caviar_Dreams_Regular, getMainCategories(), this);
        recyclerView.setAdapter(dataAdapter);

        mProgressView = view.findViewById(R.id.view_progress);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setTypeface(Caviar_Dreams_Regular);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(" ");
    }

    public List<MainCategoryModel> getMainCategories() {
        ArrayList<MainCategoryModel> categories = new ArrayList<>();
        categories.add(new MainCategoryModel(getString(R.string.category_exercise), R.mipmap.exercise_videos));
        categories.add(new MainCategoryModel(getString(R.string.mobility_videos), R.mipmap.roller_videos));
        categories.add(new MainCategoryModel(getString(R.string.category_stretching), R.mipmap.stretching_videos));

        categories.add(new MainCategoryModel(getString(R.string.category_sport), R.mipmap.sport_video));
        categories.add(new MainCategoryModel(getString(R.string.category_cardio), R.mipmap.cardio_video));
        categories.add(new MainCategoryModel(getString(R.string.stop_watch), R.mipmap.timer));
        categories.add(new MainCategoryModel(getString(R.string.category_my_tracker), R.mipmap.my_tracker));
        categories.add(new MainCategoryModel(getString(R.string.category_my_workout), R.mipmap.my_workout));

        //categories.add(new MainCategoryModel(getString(R.string.category_logout), R.mipmap.logout));
        return categories;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListFragmentInteraction(MainCategoryModel item) {
        Intent myIntent = new Intent(getActivity(), SearchActivity.class);
        switch (item.image_resource) {

            case R.mipmap.exercise_videos:
                myIntent = new Intent(getContext(), SelectPlannerTypeActivity.class);
                myIntent.putExtra("tab", 0);
//                startActivity(exerciseIntent);

//                i.putExtra(VideosActivity.KEY_WORKOUT_CODE, VideosActivity.CODE_WORKOUT_EXERCISE_VIDEOS);
                break;

            case R.mipmap.roller_videos:
                myIntent = new Intent(getContext(), SelectPlannerTypeActivity.class);
                myIntent.putExtra("tab", 1);
//                startActivity(rollerIntent);
                // i.putExtra(VideosActivity.KEY_WORKOUT_CODE, VideosActivity.CODE_WORKOUT_MOBILITY_VIDEOS);
                break;

            case R.mipmap.stretching_videos:

                myIntent = new Intent(getContext(), SelectPlannerTypeActivity.class);
                myIntent.putExtra("tab", 2);
//                startActivity(stretchingIntent);

//                i.putExtra(VideosActivity.KEY_WORKOUT_CODE, VideosActivity.CODE_WORKOUT_STRETCHING_VIDEOS);

                break;

            case R.mipmap.cardio_video:

                return;

            case R.mipmap.timer:


                return;

            case R.mipmap.sport_video:

                SportFragment sportFragment = new SportFragment();
                moveToFragment(sportFragment);
                //i.putExtra("AppTheme", R.color.seekbar_exercise);

                return;


            case R.mipmap.my_workout:

                MyWorkoutFragment myWorkoutFragment = new MyWorkoutFragment();
                moveToFragment(myWorkoutFragment);

                return;


            case R.mipmap.my_tracker:
                ((MainActivity) getActivity()).showProfilePage();
                return;

            case R.mipmap.logout:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return;
        }
        startActivity(myIntent);
    }

    private void moveToFragment(Fragment fragment) {
        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frLayout, fragment).commit();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
