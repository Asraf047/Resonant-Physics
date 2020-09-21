package com.amanullah.myapplication98.ui.Profile;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.model.UserItem;
import com.amanullah.myapplication98.model.VideoItem;
import com.amanullah.myapplication98.utils.HelperClasses;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel mViewModel;
    private Button save_btn;
    private EditText tv_title,tv_duration,tv_link,tv_details;
    private Spinner sp_part,sp_chapter,sp_isFree;
    private ProgressBar otp_progress_bar;

    private UserItem mCurrentUserItem;
    private int partPosition = 0;
    private int chapterPosition = 0;
    private int categoryPosition = 0;
    private List<String> partList = new ArrayList<String>();
    private List<String> chapterList = new ArrayList<String>();
    private List<String> categorieList = new ArrayList<String>();

    private String[] part1 = {
            "ভৌতজগৎ ও পরিমাপ",
            "ভেক্টর",
            "গতিবিদ্যা",
            "নিউটনিয়ান বলবিদ্যা",
            "কাজ শক্তি ও ক্ষমতা",
            "মহাকর্ষ ও অভিকর্ষ",
            "পদার্থের গাঠনিক ধর্ম",
            "পর্যাবৃত্ত গতি",
            "তরঙ্গ",
            "গ্যাসের গতি তত্ত্ব"
    };
    private String[] part2 = {
            "তাপ গতি বিদ্যা",
            "স্থির তড়িৎ",
            "চল তড়িৎ",
            "তড়িৎ প্রবাহের চৌম্বক ক্রিয়া ও চুম্বকত্ব",
            "তাড়িতচৌম্বকীয় অবেশ ও পরিবর্তী প্রবাহ",
            "জ্যামিতিক আলোকবিজ্ঞান",
            "ভৌত আলোকবিজ্ঞান",
            "আধুনিক পদার্থ বিজ্ঞান",
            "পরমাণুর মডেল এবং নিউক্লিয়ার",
            "সেমিকন্ডাক্টটর ও ইলেক্ট্রনিক্স",
            "জ্যোতি বিজ্ঞান"
    };


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeFields(root);

        return root;
    }

    public void initializeFields(View view){
        tv_title = view.findViewById(R.id.tv_title);
        tv_duration = view.findViewById(R.id.tv_duration);
        tv_link = view.findViewById(R.id.tv_link);
        tv_details = view.findViewById(R.id.tv_details);
        sp_part = view.findViewById(R.id.sp_part);
        sp_chapter = view.findViewById(R.id.sp_chapter);
        sp_isFree = view.findViewById(R.id.sp_isFree);
        save_btn = view.findViewById(R.id.save_btn);
        otp_progress_bar = view.findViewById(R.id.otp_progress_bar);

        sp_part.setOnItemSelectedListener(this);
        sp_chapter.setOnItemSelectedListener(this);
        sp_isFree.setOnItemSelectedListener(this);
        sp_part.post(new Runnable() {
            @Override
            public void run() {
                initializeSpinner();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(partPosition==0){
                    Toast.makeText(getContext(), "You did not select physics part", Toast.LENGTH_LONG).show();
                } else if(chapterPosition==0){
                    Toast.makeText(getContext(), "You did not select physics chapter", Toast.LENGTH_LONG).show();
                } else if(categoryPosition==0){
                    Toast.makeText(getContext(), "You did not select free or not", Toast.LENGTH_LONG).show();
                } else if(HelperClasses.isEmptyEditText(tv_title)){
                    Toast.makeText(getContext(), "You did not enter topic title", Toast.LENGTH_LONG).show();
                } else if(HelperClasses.isEmptyEditText(tv_duration)){
                    Toast.makeText(getContext(), "You did not enter video duration", Toast.LENGTH_LONG).show();
                } else if(HelperClasses.isEmptyEditText(tv_link)){
                    Toast.makeText(getContext(), "You did not enter video link", Toast.LENGTH_LONG).show();
                } else if(HelperClasses.isEmptyEditText(tv_details)){
                    Toast.makeText(getContext(), "You did not enter topic details", Toast.LENGTH_LONG).show();
                } else {
                    otp_progress_bar.setVisibility(View.VISIBLE);
                    VideoItem videoItem = new VideoItem();
                    videoItem.setPart(partPosition);
                    videoItem.setChapter(chapterPosition);
                    if(categoryPosition==1){
                        videoItem.setFree(true);
                    }else if(categoryPosition==2){
                        videoItem.setFree(false);
                    }
                    videoItem.setTitle(tv_title.getText().toString().trim());
                    videoItem.setTime(tv_duration.getText().toString().trim());
                    videoItem.setLink(tv_link.getText().toString().trim());
                    videoItem.setDatails(tv_details.getText().toString().trim());
                    String randomID = String.valueOf(Calendar.getInstance().getTimeInMillis());
                    videoItem.setVid(randomID);
                    FirebaseFirestore.getInstance().collection("video_list").document(randomID)
                            .set(videoItem)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: 100");
                                    otp_progress_bar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    otp_progress_bar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Upload failed : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

    }

    public void initializeSpinner(){
        partList.add("Select part");
        partList.add("1st part");
        partList.add("2nd part");

        chapterList.add("Select chapter");

        categorieList.add("Select category");
        categorieList.add("Free");
        categorieList.add("Premium");

        ArrayAdapter<String> sp_partAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, partList);
        sp_partAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_part.setAdapter(sp_partAdapter);

        ArrayAdapter<String> sp_chapterAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, chapterList);
        sp_chapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_chapter.setAdapter(sp_chapterAdapter);

        ArrayAdapter<String> sp_isFreeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categorieList);
        sp_isFreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_isFree.setAdapter(sp_isFreeAdapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.sp_part:
                partPosition = position;
                String part = adapterView.getItemAtPosition(position).toString();
                if(part.equals("1st part")){
                    chapterList.clear();
                    chapterList.add("Select chapter");
                    chapterList.addAll(Arrays.asList(part1));
                } else if(part.equals("2nd part")){
                    chapterList.clear();
                    chapterList.add("Select chapter");
                    chapterList.addAll(Arrays.asList(part2));
                }
                break;
            case R.id.sp_chapter:
                chapterPosition = position;
//                String chapter = adapterView.getItemAtPosition(position).toString();
//                Log.d("form","sp_chapter:"+chapter);
                break;
            case R.id.sp_isFree:
                categoryPosition = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
