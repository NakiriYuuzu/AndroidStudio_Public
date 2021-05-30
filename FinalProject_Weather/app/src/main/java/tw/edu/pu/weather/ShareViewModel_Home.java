package tw.edu.pu.weather;

//import android.content.ClipData;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel_Home extends ViewModel {
//    private final MutableLiveData<ClipData.Item> selected = new MutableLiveData<>();

    private final MutableLiveData<Integer> iconMain = new MutableLiveData<>();
    private final MutableLiveData<String> state = new MutableLiveData<>();
    private final MutableLiveData<String> country = new MutableLiveData<>();
    private final MutableLiveData<String> city = new MutableLiveData<>();
    private final MutableLiveData<String> temp = new MutableLiveData<>();
    private final MutableLiveData<String> feelLike = new MutableLiveData<>();
    private final MutableLiveData<String> minMaxTemp = new MutableLiveData<>();
    private final MutableLiveData<String> humid = new MutableLiveData<>();
    private final MutableLiveData<String> press = new MutableLiveData<>();
    private final MutableLiveData<String> visi = new MutableLiveData<>();
    private final MutableLiveData<String> wind = new MutableLiveData<>();
    private final MutableLiveData<String> tempNow = new MutableLiveData<>();
    private final MutableLiveData<String> tempD1 = new MutableLiveData<>();
    private final MutableLiveData<String> tempD2 = new MutableLiveData<>();
    private final MutableLiveData<String> tempD3 = new MutableLiveData<>();
    private final MutableLiveData<String> dateD1 = new MutableLiveData<>();
    private final MutableLiveData<String> dateD2 = new MutableLiveData<>();
    private final MutableLiveData<String> dateD3 = new MutableLiveData<>();
    private final MutableLiveData<Integer> iconNow = new MutableLiveData<>();
    private final MutableLiveData<Integer> iconD1 = new MutableLiveData<>();
    private final MutableLiveData<Integer> iconD2 = new MutableLiveData<>();
    private final MutableLiveData<Integer> iconD3 = new MutableLiveData<>();


//    public void select(ClipData.Item item) {
//        selected.setValue(item);
//    }
//
//    public LiveData<ClipData.Item> getSelected() {
//        return selected;
//    }

    public void setState(String s) {
        state.setValue(s);
    }

    public LiveData<String> getState() {
        return state;
    }

    public void setCity(String s) {
        city.setValue(s);
    }

    public LiveData<String> getCity() {
        return city;
    }

    public void setCountry(String s) {
        country.setValue(s);
    }

    public LiveData<String> getCountry() {
        return country;
    }

    public void setTemp(String s) {
        temp.setValue(s);
    }

    public LiveData<String> getTemp() {
        return temp;
    }

    public void setFeelLike(String s) {
        feelLike.setValue(s);
    }

    public LiveData<String> getFeelLike() {
        return feelLike;
    }

    public void setMinMaxTemp(String s) {
        minMaxTemp.setValue(s);
    }

    public LiveData<String> getMinMaxTemp() {
        return minMaxTemp;
    }

    public void setHumid(String s) {
        humid.setValue(s);
    }

    public LiveData<String> getHumid() {
        return humid;
    }

    public void setPressure(String s) {
        press.setValue(s);
    }

    public LiveData<String> getPressure() {
        return press;
    }

    public void setVisibility(String s) {
        visi.setValue(s);
    }

    public LiveData<String> getVisibility() {
        return visi;
    }

    public void setWind(String s) {
        wind.setValue(s);
    }

    public LiveData<String> getWind() {
        return wind;
    }

    public void setIcon(Integer resource) {
        iconMain.setValue(resource);
    }

    public LiveData<Integer> getIcon() {
        return iconMain;
    }

    public void setTempNow(String s) {
        tempNow.setValue(s);
    }

    public LiveData<String> getTempNow() {
        return tempNow;
    }

    public void setTempD1(String s) {
        tempD1.setValue(s);
    }

    public LiveData<String> getTempD1() {
        return tempD1;
    }

    public void setTempD2(String s) {
        tempD2.setValue(s);
    }

    public LiveData<String> getTempD2() {
        return tempD2;
    }

    public void setTempD3(String s) {
        tempD3.setValue(s);
    }

    public LiveData<String> getTempD3() {
        return tempD3;
    }

    public void setIconNow(Integer resource) {
        iconNow.setValue(resource);
    }

    public LiveData<Integer> getIconNow() {
        return iconNow;
    }

    public void setIconD1(Integer resource) {
        iconD1.setValue(resource);
    }

    public LiveData<Integer> getIconD1() {
        return iconD1;
    }

    public void setIconD2(Integer resource) {
        iconD2.setValue(resource);
    }

    public LiveData<Integer> getIconD2() {
        return iconD2;
    }

    public void setIconD3(Integer resource) {
        iconD3.setValue(resource);
    }

    public LiveData<Integer> getIconD3() {
        return iconD3;
    }

    public void setDateD1(String s) {dateD1.setValue(s);}

    public LiveData<String> getDateD1() {return dateD1;}

    public void setDateD2(String s) {dateD2.setValue(s);}

    public LiveData<String> getDateD2() {return dateD2;}

    public void setDateD3(String s) {dateD3.setValue(s);}

    public LiveData<String> getDateD3() {return dateD3;}
}
