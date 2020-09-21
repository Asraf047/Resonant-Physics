package com.amanullah.myapplication98.ui.Learn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amanullah.myapplication98.model.UserItem;
import com.amanullah.myapplication98.repository.DataRepository;

public class LearnViewModel extends ViewModel {
    private LiveData<UserItem> mUserItem;

    public LearnViewModel(){
        DataRepository mRepo = DataRepository.getInstance();
        mUserItem = mRepo.getmUserItem();
    }

    public LiveData<UserItem> getmUserItem() {
        return mUserItem;
    }
}
