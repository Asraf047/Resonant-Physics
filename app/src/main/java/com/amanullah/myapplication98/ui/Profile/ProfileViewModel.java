package com.amanullah.myapplication98.ui.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amanullah.myapplication98.repository.DataRepository;

public class ProfileViewModel extends ViewModel {
//    private MutableLiveData<User> userData;
//    private DataRepository repository;
//    public ProfileViewModel() {
//        repository = new DataRepository();
//        userData = repository.getUserData();
//    }
//
//    public void updateUserName(String name)
//    {
//        repository.updateUserName(name);
//    }
//
//    public MutableLiveData<User> getUserData() {
//        return userData;
//    }

    private MutableLiveData<Integer> mNicePlaces;
    public ProfileViewModel(){
        if(mNicePlaces != null){
            return;
        }
        DataRepository mRepo = DataRepository.getInstance();
//        mNicePlaces = mRepo.getMtest();
    }

    public LiveData<Integer> getmNicePlaces() {
        return mNicePlaces;
    }

    public void setmNicePlaces(MutableLiveData<Integer> mNicePlaces) {
        this.mNicePlaces = mNicePlaces;
    }
}
