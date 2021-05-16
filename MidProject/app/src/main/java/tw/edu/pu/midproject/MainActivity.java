package tw.edu.pu.midproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton iBtn1;
    private ImageButton iBtn2;
    private ImageButton iBtn3;
    private ListView lv;
    private boolean checkBtn1 = false;
    private boolean checkBtn2 = false;
    private boolean checkBtn3 = false;


    private void findView() {
        iBtn1 = findViewById(R.id.ibtn1);
        iBtn2 = findViewById(R.id.ibtn2);
        iBtn3 = findViewById(R.id.ibtn3);
        lv = findViewById(R.id.listView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        iBtn1.setOnClickListener(this);
        iBtn2.setOnClickListener(this);
        iBtn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn1:
                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
                downloadAsyncTask.execute("https://datacenter.taichung.gov.tw/swagger/OpenData/34c2aa94-7924-40cc-96aa-b8d090f0ab69");
                checkBtn1 = true;
                break;

            case R.id.ibtn2:
                DownloadAsyncTask downloadAsyncTask1 = new DownloadAsyncTask();
                downloadAsyncTask1.execute("https://datacenter.taichung.gov.tw/swagger/OpenData/34c2aa94-7924-40cc-96aa-b8d090f0ab69");
                checkBtn2 = true;
                break;

            case R.id.ibtn3:
                DownloadAsyncTask1 downloadAsyncTask2 = new DownloadAsyncTask1();
                downloadAsyncTask2.execute("https://datacenter.taichung.gov.tw/swagger/OpenData/c60986c5-03fb-49b9-b24f-6656e1de02aa");
                checkBtn3 = true;
                break;

            default:
                throw new RuntimeException("Unknown button ID");
        }
    }


    class DownloadAsyncTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                InputStream inputStream = connection.getInputStream();
                int status = connection.getResponseCode();
                Log.d("JSON parser", String.valueOf(status));
                String result = "";
                if (inputStream != null) {
                    InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader in = new BufferedReader(reader);

                    String line = "";
                    while ((line = in.readLine()) != null) {
                        result += (line + "\n");
                    }
                } else {
                    result = "Did not work!";
                }
                //
                connection.disconnect();
                return result;
                //
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON", s);
            try {
                JSONObject allData = new JSONObject(s);
                int status = allData.getInt("retCode");
                if (status == 1) {
                    JSONObject allSites = allData.getJSONObject("retVal");
                    Iterator<String> it = allSites.keys();

                    ArrayList<String> mySite1 = new ArrayList<>();
                    ArrayList<String> mySite2 = new ArrayList<>();
                    ArrayList<String> mySiteBike2 = new ArrayList<>();

                    while (it.hasNext()) {
                        String id = it.next();
                        JSONObject siteData = allSites.getJSONObject(id);

                        //臺中市
                        String areaNames = siteData.getString("sarea");
                        String siteNames = siteData.getString("sna");
                        String loadNames = siteData.getString("ar");
                        int liveBikes = Integer.parseInt(siteData.getString("sbi"));

                        Log.i("JSON", id + ":" + siteNames + "," + liveBikes);

                        //btn1
                        mySite1.add(areaNames + " : " + siteNames);

                        //btn2
                        if (areaNames.equals("沙鹿區")) {
                            mySite2.add(siteNames + " : " + loadNames);
                            mySiteBike2.add(String.valueOf(liveBikes));
                        }
                    }

                    if (checkBtn1) {
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                mySite1
                        );
                        lv.setAdapter(adapter1);
                        checkBtn1 = false;
                    }

                    if (checkBtn2) {
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                mySite2
                        );

                        lv.setAdapter(adapter2);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(MainActivity.this, "場站: "
                                        + adapter2.getItem(position) + ", 剩餘 " + mySiteBike2.get(position) + "台脚踏車可以租借", Toast.LENGTH_SHORT).show();
                            }
                        });
                        checkBtn2 = false;
                    }



                } else {
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class DownloadAsyncTask1 extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                InputStream inputStream = connection.getInputStream();
                int status = connection.getResponseCode();
                Log.d("JSON parser", String.valueOf(status));
                String result = "";
                if (inputStream != null) {
                    InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader in = new BufferedReader(reader);

                    String line = "";
                    while ((line = in.readLine()) != null) {
                        result += (line + "\n");
                    }
                } else {
                    result = "Did not work!";
                }
                //
                connection.disconnect();
                return result;
                //
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON", s);
            try {
                JSONArray allSpot = new JSONArray(s);
                ArrayList<String> getNames = new ArrayList<>();
                ArrayList<String> getAddress = new ArrayList<>();
                ArrayList<String> getLocationX = new ArrayList<>();
                ArrayList<String> getLocationY = new ArrayList<>();

                for (int i = 0; i < allSpot.length(); i++) {
                    JSONObject getSpot = allSpot.getJSONObject(i);
                    String spotNames = getSpot.getString("名稱");
                    String spotArea = getSpot.getString("鄉鎮市區");
                    String spotAddress = getSpot.getString("地址");
                    String spotLocationX = getSpot.getString("東經");
                    String spotLocationY = getSpot.getString("北緯");

                    if (spotArea.equals("龍井區") || spotArea.equals("梧棲區") || spotArea.equals("清水區") || spotArea.equals("沙鹿區")) {
                        getNames.add(spotNames);
                        getAddress.add(spotAddress);
                        getLocationX.add(spotLocationX);
                        getLocationY.add(spotLocationY);

                        Log.i("JSON", "名稱: " + spotNames + " 地址: " + spotAddress + " 東經: " + spotLocationX + " 北緯: " + spotLocationY);
                    }
                }

                if (checkBtn3) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            getNames
                    );
                    lv.setAdapter(adapter);
                    checkBtn3 = false;

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent ii = new Intent(getApplicationContext(), Map.class);

                            Bundle forName = new Bundle();
                            Bundle forAddress = new Bundle();
                            Bundle forLocation = new Bundle();

                            String name = getNames.get(position);
                            String address = getAddress.get(position);
                            String location = getLocationX.get(position) + ", " + getLocationY.get(position);

                            forName.putString("name", name);
                            forAddress.putString("address" ,address);
                            forLocation.putString("location", location);

                            ii.putExtras(forName);
                            ii.putExtras(forAddress);
                            ii.putExtras(forLocation);

                            startActivity(ii);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}