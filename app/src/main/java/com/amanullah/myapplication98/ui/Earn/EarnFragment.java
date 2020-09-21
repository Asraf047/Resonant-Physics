package com.amanullah.myapplication98.ui.Earn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.ui.Profile.ProfileViewModel;
import com.amanullah.myapplication98.model.UserItem;
import com.google.firebase.auth.FirebaseAuth;


public class EarnFragment extends Fragment {
    private static final String TAG = "EarnFragment";

    private ProfileViewModel mViewModel;
    private CardView cardLogout;
    private ConstraintLayout ratingHolder;
    private Button editProfileBtn;
    private TextView txtProfileName,txtSinceTime, saveChangesBtn;
    private EditText editProfileName;

    private UserItem mCurrentUserItem;
    private String isClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public static EarnFragment newInstance() {
        return new EarnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_part1, container, false);

//        initializeFields(root);

        CardView cardView = root.findViewById(R.id.card_header);
        cardView.setBackgroundResource(R.drawable.header);

        root.findViewById(R.id.card_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UserRepository mRepo = UserRepository.getInstance();
//                mRepo.setTest(mRepo.getTest()+1);
//                Log.d(TAG, "onClick: "+mRepo.getTest());
            }
        });

        return root;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
//        mViewModel.getmNicePlaces().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                Log.d(TAG, "onClick onChanged: "+integer);
//            }
//        });
    }


}
