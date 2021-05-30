package tw.edu.pu.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel_Search extends ViewModel {
    private final MutableLiveData<String> ioCity = new MutableLiveData<>();


    public void setIoCity(String s) {
        ioCity.setValue(s);
    }

    public LiveData<String> getIoCity() {
        return ioCity;
    }
}
