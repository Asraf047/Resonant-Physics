package com.amanullah.myapplication98.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.model.TransectionItem;
import com.amanullah.myapplication98.model.UserItem;
import com.amanullah.myapplication98.model.VideoItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataRepository {
    private static final String TAG = "DataRepository";
    private static String[] part1 = {
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
    private static String[] part2 = {
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
    private static int[] imgList1 = {
            R.drawable.c_1_1,
            R.drawable.c_1_2,
            R.drawable.c_1_3,
            R.drawable.c_1_4,
            R.drawable.c_1_5,
            R.drawable.c_1_6,
            R.drawable.c_1_7,
            R.drawable.c_1_8,
            R.drawable.c_1_9,
            R.drawable.c_1_10,
    };
    private static int[] imgList2 = {
            R.drawable.c_1_1,
            R.drawable.c_1_2,
            R.drawable.c_1_3,
            R.drawable.c_1_4,
            R.drawable.c_1_5,
            R.drawable.c_1_6,
            R.drawable.c_1_7,
            R.drawable.c_1_8,
            R.drawable.c_1_9,
            R.drawable.c_2_5,
            R.drawable.c_1_10
    };
    private static DataRepository instance;
    private static MutableLiveData<UserItem> mUserItem = new MutableLiveData<>();
    private static MutableLiveData<List<VideoItem>> mVideoItemList = new MutableLiveData<>();
    private static MutableLiveData<List<TransectionItem>> mTransectionItemList = new MutableLiveData<>();

    public static DataRepository getInstance(){
        if(instance == null){
            instance = new DataRepository();

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                setmUserItem();
                setVideoItemList();
            }
        }
        return instance;
    }


    public static void setmUserItem(){
        final DocumentReference docRef = FirebaseFirestore.getInstance().collection("user_list").document(FirebaseAuth.getInstance().getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites() ? "Local" : "Server";
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, source + " data from success");
                    UserItem userItem = snapshot.toObject(UserItem.class);
                    userItem.setUserID(FirebaseAuth.getInstance().getUid());
                    mUserItem.setValue(userItem);
                } else {
                    Log.d(TAG, source + " data: null");
                }
            }
        });
    }

    public static void setVideoItemList(){
        FirebaseFirestore.getInstance().collection("video_list")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        List<VideoItem> videoItemList = new ArrayList<>();
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                        } else {
                            for (QueryDocumentSnapshot doc : value) {
                                VideoItem videoItem = doc.toObject(VideoItem.class);
                                videoItemList.add(videoItem);
                            }
                            mVideoItemList.setValue(videoItemList);
                        }
                    }
                });
    }

    public static void setmTransectionItemList(String queryPhone){
        FirebaseFirestore.getInstance().collection("referral_history")
                .whereEqualTo("from", queryPhone)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        List<VideoItem> videoItemList = new ArrayList<>();
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                        } else {
                            for (QueryDocumentSnapshot doc : value) {
                                VideoItem videoItem = doc.toObject(VideoItem.class);
                                videoItemList.add(videoItem);
                            }
                            mVideoItemList.setValue(videoItemList);
                        }
                    }
                });
    }

    public static LiveData<UserItem> getmUserItem() {
        return mUserItem;
    }

    public static LiveData<List<VideoItem>> getmVideoItemList() {
        return mVideoItemList;
    }

    public static LiveData<List<TransectionItem>> getmTransectionItemList() {
        return mTransectionItemList;
    }

    public static void updateVideoItem(VideoItem videoItem){
        Log.d(TAG, "updateVideoItem: "+videoItem.getVid());
        FirebaseFirestore.getInstance().collection("video_list").document(videoItem.getVid())
                .set(videoItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public static List<String> getPart1() {
        return Arrays.asList(part1);
    }

    public static List<String> getPart2() {
        return Arrays.asList(part2);
    }

    public static int[] getImgList1() {
        return imgList1;
    }

    public static int[] getImgList2() {
        return imgList2;
    }
}
