package com.amanullah.myapplication98.ui.Learn;

import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amanullah.myapplication98.MainActivity;
import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.ui.Earn.EarnFragment;
import com.amanullah.myapplication98.ui.Profile.ProfileViewModel;
import com.amanullah.myapplication98.model.UserItem;
import com.google.firebase.auth.FirebaseAuth;


public class LearnFragment extends Fragment {
    private static final String TAG = "LearnFragment";

    private LearnViewModel mViewModel;
    private CardView cardLogout;
    private ConstraintLayout ratingHolder;
    private Button editProfileBtn;
    private TextView txtProfileName,txtSinceTime, saveChangesBtn;
    private EditText editProfileName;

    private UserItem mCurrentUserItem;
    private String isClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public static LearnFragment newInstance() {
        return new LearnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_learn, container, false);

//        initializeFields(root);

        CardView cardView = root.findViewById(R.id.card_header);
        cardView.setBackgroundResource(R.drawable.header);

        root.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChapterActivity.class);
                intent.putExtra("chapter", "1st");
                startActivity(intent);
            }
        });
        root.findViewById(R.id.cardView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChapterActivity.class);
                intent.putExtra("chapter", "2nd");
                startActivity(intent);
            }
        });

//        ((MainActivity)getActivity()).setActiveFragment();

        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(LearnViewModel.class);
        mViewModel.getmUserItem().observe(getViewLifecycleOwner(), new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem userItem) {
                Log.d(TAG, "onChanged:LearnViewModel "+userItem.getUsername());
            }
        });
    }

    private void goToNotificationFragment() {
//        Fragment notificationFragment = new testFragment();
//        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.add(R.id.nav_host_fragment,notificationFragment,"notificationFragment");
//        fragmentTransaction.commit();
    }



}
