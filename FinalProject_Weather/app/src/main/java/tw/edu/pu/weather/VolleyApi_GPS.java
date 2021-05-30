package tw.edu.pu.weather;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VolleyApi_GPS {

    private final Activity activity;
    private final String tempUrl;

    ShareViewModel_Home viewModel;

    private String iconMain, city, country, state, temp, lowHighTemp, feelLikeTemp, humid, pressure, visibility, wind;
    private int condition, icDataMain, d5Condition1, d5Condition2, d5Condition3;

    private String d5Temp1, d5Temp2, d5Temp3, d5Date1, d5Date2, d5Date3, d5IconD1, d5IconD2, d5IconD3;

    public VolleyApi_GPS(String tempUrl, Activity myActivity) {
        this.tempUrl = tempUrl;
        this.activity = myActivity;
    }

    public void getWeatherUrl() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Data Get
                try {
                    JSONObject allData = new JSONObject(response);

                    //Stage1
                    JSONArray allWeather = allData.getJSONArray("weather");
                    JSONObject allTemps = allData.getJSONObject("main");
                    JSONObject allWinds = allData.getJSONObject("wind");
                    JSONObject allSys = allData.getJSONObject("sys");

                    //Stage2
                    city = allData.getString("name");
                    String strVisibility = allData.getString("visibility");
                    country = allSys.getString("country");

                    //Stage3
                    double tempD = Double.parseDouble(allTemps.getString("temp")) - 273.15;
                    double minTempD = Double.parseDouble(allTemps.getString("temp_min")) - 273.15;
                    double maxTempD = Double.parseDouble(allTemps.getString("temp_max")) - 273.15;
                    double feelTempD = Double.parseDouble(allTemps.getString("feels_like")) - 273.15;
                    double humidD = Double.parseDouble(allTemps.getString("humidity"));
                    double pressD = Double.parseDouble(allTemps.getString("pressure"));
                    double windsD = Double.parseDouble(allWinds.getString("speed"));
                    double visiD = Double.parseDouble(strVisibility);

//                //Stage4 - temperature fix
                    int tempC = (int) Math.rint(tempD);
                    int minTempC = (int) Math.rint(minTempD);
                    int maxTempC = (int) Math.rint(maxTempD);
                    int feelTempC = (int) Math.rint(feelTempD);
                    int humidC = (int) Math.rint(humidD);

//                //Stage5 - Change value
                    temp = tempC + "°C";
                    lowHighTemp = minTempC + "°C" + "/" + maxTempC + "°C";
                    feelLikeTemp = feelTempC + "°C";
                    humid = humidC + "%";
                    pressure = pressD + "mb";
                    visibility = visiD + "km";
                    wind = windsD + "km/h";

                    //Special
                    ArrayList<String> getState = new ArrayList<>();

                    for (int i = 0; i < allWeather.length(); i++) {
                        JSONObject weatherData = allWeather.getJSONObject(i);
                        String wtState = weatherData.getString("main");
                        condition = weatherData.getInt("id");
                        getState.add(wtState);
                    }

                    //-------getState--------

                    StringBuilder builder = new StringBuilder();
                    for (String value : getState) {
                        builder.append(value);
                    }
                    state = builder.toString();

                    //Icon Setup
                    iconMain = updateWeatherIcon(condition);

                    Resources res = activity.getResources();
                    icDataMain = res.getIdentifier(iconMain, "drawable", activity.getPackageName());

                    //Setup
                    setupUIGpsMain();

                    //Toast
                    Toast.makeText(activity, "Success! Please go to Home", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Error", error.toString());
                Toast.makeText(activity, "Please input the correct City!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void getWeatherGPS() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Data Get
                try {
                    ArrayList<String> tempData = new ArrayList<>();
                    ArrayList<String> dateData = new ArrayList<>();
                    ArrayList<String> iconData = new ArrayList<>();

                    JSONObject allData = new JSONObject(response);
                    JSONArray allList = allData.getJSONArray("list");

                    for (int i = 0; i < allList.length(); i++) {
                        if (i == 8 || i == 16 || i == 24) {
                            JSONObject getList = allList.getJSONObject(i);
                            JSONObject getMain = getList.getJSONObject("main");
                            JSONArray getWeather = getList.getJSONArray("weather");

                            tempData.add(getMain.getString("temp"));
                            dateData.add(getList.getString("dt_txt"));

                            for (int j = 0; j < getWeather.length(); j++) {
                                JSONObject getCondition = getWeather.getJSONObject(j);
                                iconData.add(getCondition.getString("id"));
                            }
                        }
                    }

                    //GetData to constructor
                    d5Date1 = dateData.get(0);
                    d5Date2 = dateData.get(1);
                    d5Date3 = dateData.get(2);
                    d5Condition1 = Integer.parseInt(iconData.get(0));
                    d5Condition2 = Integer.parseInt(iconData.get(1));
                    d5Condition3 = Integer.parseInt(iconData.get(2));

                    //IntegerValueExchange
                    d5Temp1 = (int) Math.rint(Double.parseDouble(tempData.get(0)) - 273.15) + "°C";
                    d5Temp2 = (int) Math.rint(Double.parseDouble(tempData.get(1)) - 273.15) + "°C";
                    d5Temp3 = (int) Math.rint(Double.parseDouble(tempData.get(2)) - 273.15) + "°C";
                    d5Date1 = d5Date1.substring(5, 10);
                    d5Date2 = d5Date2.substring(5, 10);
                    d5Date3 = d5Date3.substring(5, 10);

                    //Icon Setting
                    d5IconD1 = updateWeatherIcon(d5Condition1);
                    d5IconD2 = updateWeatherIcon(d5Condition2);
                    d5IconD3 = updateWeatherIcon(d5Condition3);

                    Resources res = activity.getResources();
                    d5Condition1 = res.getIdentifier(d5IconD1, "drawable", activity.getPackageName());
                    d5Condition2 = res.getIdentifier(d5IconD2, "drawable", activity.getPackageName());
                    d5Condition3 = res.getIdentifier(d5IconD3, "drawable", activity.getPackageName());


                    //setup Ui
                    setupUID5Gps();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Error", error.toString());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void volleyWeatherRT_GPS() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Data Get
                try {
                    JSONObject allData = new JSONObject(response);

                    //Stage1
                    JSONArray allWeather = allData.getJSONArray("weather");
                    JSONObject allTemps = allData.getJSONObject("main");
                    JSONObject allWinds = allData.getJSONObject("wind");
                    JSONObject allSys = allData.getJSONObject("sys");

                    //Stage2
                    city = allData.getString("name");
                    String strVisibility = allData.getString("visibility");
                    country = allSys.getString("country");

                    //Stage3
                    double tempD = Double.parseDouble(allTemps.getString("temp")) - 273.15;
                    double minTempD = Double.parseDouble(allTemps.getString("temp_min")) - 273.15;
                    double maxTempD = Double.parseDouble(allTemps.getString("temp_max")) - 273.15;
                    double feelTempD = Double.parseDouble(allTemps.getString("feels_like")) - 273.15;
                    double humidD = Double.parseDouble(allTemps.getString("humidity"));
                    double pressD = Double.parseDouble(allTemps.getString("pressure"));
                    double windsD = Double.parseDouble(allWinds.getString("speed"));
                    double visiD = Double.parseDouble(strVisibility);

//                //Stage4 - temperature fix
                    int tempC = (int) Math.rint(tempD);
                    int minTempC = (int) Math.rint(minTempD);
                    int maxTempC = (int) Math.rint(maxTempD);
                    int feelTempC = (int) Math.rint(feelTempD);
                    int humidC = (int) Math.rint(humidD);

//                //Stage5 - Change value
                    temp = tempC + "°C";
                    lowHighTemp = minTempC + "°C" + "/" + maxTempC + "°C";
                    feelLikeTemp = feelTempC + "°C";
                    humid = humidC + "%";
                    pressure = pressD + "mb";
                    visibility = visiD + "km";
                    wind = windsD + "km/h";

                    //Special
                    ArrayList<String> getState = new ArrayList<>();

                    for (int i = 0; i < allWeather.length(); i++) {
                        JSONObject weatherData = allWeather.getJSONObject(i);
                        String wtState = weatherData.getString("main");
                        condition = weatherData.getInt("id");
                        getState.add(wtState);
                    }

                    //-------getState--------

                    StringBuilder builder = new StringBuilder();
                    for (String value : getState) {
                        builder.append(value);
                    }
                    state = builder.toString();

                    //Icon Setup
                    iconMain = updateWeatherIcon(condition);

                    Resources res = activity.getResources();
                    icDataMain = res.getIdentifier(iconMain, "drawable", activity.getPackageName());

                    //Setup
                    setupUIGpsMain();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Error", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void setupUIGpsMain() {
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ShareViewModel_Home.class);

        viewModel.setCity(city);
        viewModel.setCountry(country);
        viewModel.setState(state);
        viewModel.setTemp(temp);
        viewModel.setMinMaxTemp(lowHighTemp);
        viewModel.setFeelLike(feelLikeTemp);
        viewModel.setHumid(humid);
        viewModel.setPressure(pressure);
        viewModel.setVisibility(visibility);
        viewModel.setWind(wind);
        viewModel.setTempNow(temp);
        viewModel.setIcon(icDataMain);
        viewModel.setIconNow(icDataMain);
    }

    public void setupUID5Gps() {
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ShareViewModel_Home.class);
        viewModel.setTempD1(d5Temp1);
        viewModel.setTempD2(d5Temp2);
        viewModel.setTempD3(d5Temp3);
        viewModel.setIconD1(d5Condition1);
        viewModel.setIconD2(d5Condition2);
        viewModel.setIconD3(d5Condition3);
        viewModel.setDateD1(d5Date1);
        viewModel.setDateD2(d5Date2);
        viewModel.setDateD3(d5Date3);
    }

    private static String updateWeatherIcon(int condition) {
        if (condition >= 0 && condition <= 300) {
            return "thunder";
        } else if (condition >= 300 && condition < 500) {
            return "thunderstorm";
        } else if (condition >= 500 && condition < 600) {
            return "rain";
        } else if (condition >= 600 && condition < 700) {
            return "snow";
        } else if (condition >= 701 && condition < 771) {
            return "shower";
        } else if (condition >= 772 && condition < 800) {
            return "shower";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy";
        } else if (condition >= 900 && condition <= 902) {
            return "thunderstorm";
        }
        if (condition == 903) {
            return "snow1";
        }
        if (condition == 904) {
            return "sunny";
        }
        if (condition >= 905 && condition <= 1000) {
            return "thunderstorm2";
        }
        return "dunno";
    }
}