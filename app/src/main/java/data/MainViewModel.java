package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {



    private MutableLiveData<String> editString = new MutableLiveData<>();

    private MutableLiveData<Boolean> isSelected = new MutableLiveData<>();




}
