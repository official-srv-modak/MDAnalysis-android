package com.souravmodak.mdanalysis.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewProductViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewProductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is New Product fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}