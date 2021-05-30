package tw.edu.pu.weather;

public class Fav_Constructor  {
    private String iconMain, iconNow, city, country, state, temp, lowHigh, feelLike, humid, pressure, visibility, wind;

    private String tempNow, d1Temp, d2Temp, d3Temp, d1Date, d2Date, d3Date, iconD1, iconD2, iconD3;
    private Boolean expanded;

    public Boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Fav_Constructor() {
        this.tempNow = tempNow;
        this.iconNow = iconNow;
        this.iconMain = iconMain;
        this.city = city;
        this.country = country;
        this.state = state;
        this.temp = temp;
        this.lowHigh = lowHigh;
        this.feelLike = feelLike;
        this.humid = humid;
        this.pressure = pressure;
        this.visibility = visibility;
        this.wind = wind;
        this.d1Temp = d1Temp;
        this.d2Temp = d2Temp;
        this.d3Temp = d3Temp;
        this.d1Date = d1Date;
        this.d2Date = d2Date;
        this.d3Date = d3Date;
        this.iconD1 = iconD1;
        this.iconD2 = iconD2;
        this.iconD3 = iconD3;
        this.expanded = false;
    }


    public Fav_Constructor(String temp, String city, String country, String state) {
        this.temp = temp;
        this.city = city;
        this.country = country;
        this.state = state;
        this.expanded = false;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconMain() {
        return iconMain;
    }

    public void setIconMain(String iconMain) {
        this.iconMain = iconMain;
    }

    public String getIconNow() {
        return iconNow;
    }

    public void setIconNow(String iconNow) {
        this.iconNow = iconNow;
    }

    public String getLowHigh() {
        return lowHigh;
    }

    public void setLowHigh(String lowHigh) {
        this.lowHigh = lowHigh;
    }

    public String getFeelLike() {
        return feelLike;
    }

    public void setFeelLike(String feelLike) {
        this.feelLike = feelLike;
    }

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTempNow() {
        return tempNow;
    }

    public void setTempNow(String tempNow) {
        this.tempNow = tempNow;
    }

    public String getD1Temp() {
        return d1Temp;
    }

    public void setD1Temp(String d1Temp) {
        this.d1Temp = d1Temp;
    }

    public String getD2Temp() {
        return d2Temp;
    }

    public void setD2Temp(String d2Temp) {
        this.d2Temp = d2Temp;
    }

    public String getD3Temp() {
        return d3Temp;
    }

    public void setD3Temp(String d3Temp) {
        this.d3Temp = d3Temp;
    }

    public String getD1Date() {
        return d1Date;
    }

    public void setD1Date(String d1Date) {
        this.d1Date = d1Date;
    }

    public String getD2Date() {
        return d2Date;
    }

    public void setD2Date(String d2Date) {
        this.d2Date = d2Date;
    }

    public String getD3Date() {
        return d3Date;
    }

    public void setD3Date(String d3Date) {
        this.d3Date = d3Date;
    }

    public String getIconD1() {
        return iconD1;
    }

    public void setIconD1(String iconD1) {
        this.iconD1 = iconD1;
    }

    public String getIconD2() {
        return iconD2;
    }

    public void setIconD2(String iconD2) {
        this.iconD2 = iconD2;
    }

    public String getIconD3() {
        return iconD3;
    }

    public void setIconD3(String iconD3) {
        this.iconD3 = iconD3;
    }

    @Override
    public String toString() {
        return "Fav_Constructor{" +
                "fav_Temp='" + temp + '\'' +
                ", fav_City='" + city + '\'' +
                ", fav_Country='" + country + '\'' +
                ", fav_State='" + state + '\'' +
                '}';
    }
}

