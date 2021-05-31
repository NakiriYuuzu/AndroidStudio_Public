package tw.edu.pu.weather;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class VolleyApi_Search {

    private final Activity activity;
    private final String tempUrl;

    private String iconMain, city, country, state, temp, lowHighTemp, feelLikeTemp, humid, pressure, visibility, wind;
    private int condition, icDataMain, d5Condition1, d5Condition2, d5Condition3, firstDay = 0;

    private String d5Temp1, d5Temp2, d5Temp3, d5Date1, d5Date2, d5Date3, d5IconD1, d5IconD2, d5IconD3;

    public VolleyApi_Search(String tempUrl, Activity myActivity) {
        this.tempUrl = tempUrl;
        this.activity = myActivity;
    }

    public void getWeatherSearchD5() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Data Get
                try {
                    ArrayList<String> stateData = new ArrayList<>();

                    ArrayList<String> tempData = new ArrayList<>();
                    ArrayList<String> feelLikeData = new ArrayList<>();
                    ArrayList<String> highData = new ArrayList<>();
                    ArrayList<String> lowData = new ArrayList<>();

                    ArrayList<String> pressData = new ArrayList<>();
                    ArrayList<String> humidData = new ArrayList<>();
                    ArrayList<String> visiData = new ArrayList<>();
                    ArrayList<String> windData = new ArrayList<>();

                    ArrayList<String> iconData = new ArrayList<>();
                    ArrayList<String> dateData = new ArrayList<>();


                    JSONObject allData = new JSONObject(response);
                    JSONArray allList = allData.getJSONArray("list");
                    JSONObject getCity = allData.getJSONObject("city");

                    firstDay = updateTime();

                    for (int i = 0; i < allList.length(); i++) {
                        if (i == firstDay || i == 9 || i == 17 || i == 25) {
                            JSONObject getList = allList.getJSONObject(i);
                            JSONObject getMain = getList.getJSONObject("main");
                            JSONObject getWind = getList.getJSONObject("wind");
                            JSONArray getWeather = getList.getJSONArray("weather");

                            tempData.add(getMain.getString("temp"));
                            feelLikeData.add(getMain.getString("feels_like"));
                            lowData.add(getMain.getString("temp_min"));
                            highData.add(getMain.getString("temp_max"));

                            pressData.add(getMain.getString("pressure"));
                            humidData.add(getMain.getString("humidity"));
                            visiData.add(getList.getString("visibility"));
                            windData.add(getWind.getString("speed"));

                            dateData.add(getList.getString("dt_txt"));

                            for (int j = 0; j < getWeather.length(); j++) {
                                JSONObject getCondition = getWeather.getJSONObject(j);
                                iconData.add(getCondition.getString("id"));
                                stateData.add(getCondition.getString("main"));
                            }
                        }
                    }

                    //GetData to constructor
                    city = getCity.getString("name");
                    country = getCity.getString("country");
                    state = stateData.get(0);

                    humid = humidData.get(0) + "%";
                    pressure = pressData.get(0) + "mb";
                    visibility = visiData.get(0) + "km";
                    wind = windData.get(0) + "km/h";

                    d5Date1 = dateData.get(1);
                    d5Date2 = dateData.get(2);
                    d5Date3 = dateData.get(3);

                    condition = Integer.parseInt(iconData.get(0));
                    d5Condition1 = Integer.parseInt(iconData.get(1));
                    d5Condition2 = Integer.parseInt(iconData.get(2));
                    d5Condition3 = Integer.parseInt(iconData.get(3));

                    //IntegerValueExchange
                    temp = (int) Math.rint(Double.parseDouble(tempData.get(0)) - 273.15) + "°C";
                    d5Temp1 = (int) Math.rint(Double.parseDouble(tempData.get(1)) - 273.15) + "°C";
                    d5Temp2 = (int) Math.rint(Double.parseDouble(tempData.get(2)) - 273.15) + "°C";
                    d5Temp3 = (int) Math.rint(Double.parseDouble(tempData.get(3)) - 273.15) + "°C";

                    feelLikeTemp = (int) Math.rint(Double.parseDouble(feelLikeData.get(0)) - 273.15) + "°C";
                    lowHighTemp = (int) Math.rint(Double.parseDouble(lowData.get(0)) - 273.15) + "°C" + "/" + (int) Math.rint(Double.parseDouble(highData.get(0)) - 273.15) + "°C";

                    d5Date1 = d5Date1.substring(5, 10);
                    d5Date2 = d5Date2.substring(5, 10);
                    d5Date3 = d5Date3.substring(5, 10);

                    //Icon Setting
                    iconMain = updateWeatherIcon(condition);
                    d5IconD1 = updateWeatherIcon(d5Condition1);
                    d5IconD2 = updateWeatherIcon(d5Condition2);
                    d5IconD3 = updateWeatherIcon(d5Condition3);

                    Resources res = activity.getResources();
                    icDataMain = res.getIdentifier(iconMain, "drawable", activity.getPackageName());
                    d5Condition1 = res.getIdentifier(d5IconD1, "drawable", activity.getPackageName());
                    d5Condition2 = res.getIdentifier(d5IconD2, "drawable", activity.getPackageName());
                    d5Condition3 = res.getIdentifier(d5IconD3, "drawable", activity.getPackageName());

                    Toast.makeText(activity, "Success! Add to Favorites.", Toast.LENGTH_SHORT).show();

                    //Upload to firebase
                    fireBaseData();

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

    public void fireBaseData() {
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference fbData = fb.getReference().child("data");

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("city", city);
        dataMap.put("country", country);
        dataMap.put("temp", temp);
        dataMap.put("iconMain", String.valueOf(icDataMain));
        dataMap.put("state", state);
        dataMap.put("lowHigh", lowHighTemp);
        dataMap.put("feelLike", feelLikeTemp);
        dataMap.put("humid", humid);
        dataMap.put("pressure", pressure);
        dataMap.put("visibility", visibility);
        dataMap.put("wind", wind);
        dataMap.put("tempNow", temp);
        dataMap.put("d1Temp", d5Temp1);
        dataMap.put("d2Temp", d5Temp2);
        dataMap.put("d3Temp", d5Temp3);
        dataMap.put("iconD1", String.valueOf(d5Condition1));
        dataMap.put("iconD2", String.valueOf(d5Condition2));
        dataMap.put("iconD3", String.valueOf(d5Condition3));
        dataMap.put("d1Date", d5Date1);
        dataMap.put("d2Date", d5Date2);
        dataMap.put("d3Date", d5Date3);

        fbData.child(city).setValue(dataMap);
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

    private static int updateTime() {
        String one = "00:00:00";
        String two = "03:00:00";
        String three = "06:00:00";
        String four = "09:00:00";
        String five = "12:00:00";
        String six = "15:00:00";
        String seven = "18:00:00";
        String eight = "21:00:00";

        LocalTime time = LocalTime.now();
        Log.e("time", String.valueOf(time));

        if (time.isAfter(LocalTime.parse(one)) && time.isBefore(LocalTime.parse(two))) {
            return 0;
        }
        else if (time.isAfter(LocalTime.parse(two)) && time.isBefore(LocalTime.parse(three))) {
            return 1;
        }
        else if (time.isAfter(LocalTime.parse(three)) && time.isBefore(LocalTime.parse(four))) {
            return 2;
        }
        else if (time.isAfter(LocalTime.parse(four)) && time.isBefore(LocalTime.parse(five))) {
            return 3;
        }
        else if (time.isAfter(LocalTime.parse(five)) && time.isBefore(LocalTime.parse(six))) {
            return 3;
        }
        else if (time.isAfter(LocalTime.parse(six)) && time.isBefore(LocalTime.parse(seven))) {
            return 2;
        }
        else if (time.isAfter(LocalTime.parse(seven)) && time.isBefore(LocalTime.parse(eight))) {
            return 1;
        }
        else if (time.isAfter(LocalTime.parse(eight)) && time.isBefore(LocalTime.parse(one))) {
            return 0;
        }
        else
            return 0;
    }
}