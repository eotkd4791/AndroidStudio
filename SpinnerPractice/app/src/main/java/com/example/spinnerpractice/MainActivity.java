package com.example.spinnerpractice;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private Spinner spinnerCity, spinnerSigungu, spinnerDong, spinnerStadium;
    ArrayAdapter<String> arrayAdapter;

    private ArrayList<Stadium_compo> stList;
    private ArrayList<String> stListresult;
    private ArrayAdapter<String> stAdapterresult;

    String Selected_City        =   new String();
    String Selected_District    =   new String();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCity     = (Spinner)findViewById(R.id.Spinner_city);
        spinnerSigungu  = (Spinner)findViewById(R.id.Spinner_district);
        spinnerDong     = (Spinner)findViewById(R.id.Spinner_detail);
        spinnerStadium  = (Spinner)findViewById(R.id.Spinner_stadium);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.spinner_region));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(arrayAdapter);

        Selected_City=null;
        Selected_District=null;

        Log.d("주소셋팅","주소셋팅 들갔나" );
        initAddressSpinner();
        Log.d("주소셋팅","주소셋팅 나왔나" );



    }

    private String getJsonString() {
        Log.d("getJsonString","제이슨 스트링 받아오기함수 들어왔어");

        String json = "";

        try {
            InputStream is = getAssets().open("Stadium_info.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }

    public class Stadium_compo{
        private String CityOfStadium;
        private String DistrictOfStadium;
        private String StadiumOfStadium;


        public String getCity() {
            return CityOfStadium;
        }

        public String getDistrict() {
            return DistrictOfStadium;
        }


        public String getStadium() {
            return StadiumOfStadium;
        }


        public void setCity(String CityOfStadium) {
            this.CityOfStadium = CityOfStadium;
        }

        public void setDistrict(String DistrictOfStadium) {
           this.DistrictOfStadium = DistrictOfStadium;
        }

        public void setStadium(String StadiumOfStadium) {
            this.StadiumOfStadium = StadiumOfStadium;
        }
    }

    private void jsonParsing(String json)
    {
        Log.d("jsonParsing","파싱함수에 들어왔어");

        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray stadiumInfoArray = new JSONArray();

            if(Selected_City=="서울특별시") {
                stadiumInfoArray = jsonObject.getJSONArray("서울특별시");
            }
           else if(Selected_City=="부산광역시") {
                stadiumInfoArray = jsonObject.getJSONArray("부산광역시");
            }
           else if(Selected_City=="대구광역시") {
                stadiumInfoArray = jsonObject.getJSONArray("대구광역시");
            }
            else if(Selected_City=="인천광역시") {
                stadiumInfoArray = jsonObject.getJSONArray("인천광역시");
            }
            else if(Selected_City=="광주광역시") {
                stadiumInfoArray = jsonObject.getJSONArray("광주광역시");
            }
            else if(Selected_City=="대전광역시") {
                stadiumInfoArray = jsonObject.getJSONArray("대전광역시");
            }
            else if(Selected_City=="울산광역시") {
                stadiumInfoArray = jsonObject.getJSONArray("울산광역시");
            }
            else if(Selected_City=="세종특별자치시") {
                stadiumInfoArray = jsonObject.getJSONArray("세종특별자치시");
            }
            else if(Selected_City=="경기도") {
                stadiumInfoArray = jsonObject.getJSONArray("경기도");
            }
            else if(Selected_City=="강원도") {
                stadiumInfoArray = jsonObject.getJSONArray("강원도");
            }
            else if(Selected_City=="충청북도") {
                stadiumInfoArray = jsonObject.getJSONArray("충청북도");
            }
            else if(Selected_City=="충청남도") {
                stadiumInfoArray = jsonObject.getJSONArray("충청남도");
            }
            else if(Selected_City=="전라북도") {
                stadiumInfoArray = jsonObject.getJSONArray("전라북도");
            }
            else if(Selected_City=="전라남도") {
                stadiumInfoArray = jsonObject.getJSONArray("전라남도");
            }
            else if(Selected_City=="경상북도") {
                stadiumInfoArray = jsonObject.getJSONArray("경상북도");
            }
            else if(Selected_City=="경상남도") {
                stadiumInfoArray = jsonObject.getJSONArray("경상남도");
            }
            else if(Selected_City=="제주특별자치도") {
                stadiumInfoArray = jsonObject.getJSONArray("제주특별자치도");
            }

            stList = new ArrayList<>();
            stListresult = new ArrayList<>();

            for(int i=0; i<stadiumInfoArray.length(); i++)
            {
                Log.d("stadium_Object","반복문 일단 들어왔다.");
                JSONObject stObject = stadiumInfoArray.getJSONObject(i);
                Log.d("stadium_Object","JSON객체에서 배열값 가져왔어");


                Stadium_compo stInfo = new Stadium_compo();
                stInfo.setCity(stObject.getString("city"));
                stInfo.setDistrict(stObject.getString("district"));
                stInfo.setStadium(stObject.getString("stadium"));
                Log.d("stInfo.setCIty","도시값 " + stInfo.getCity());
                Log.d("stInfo.setDistrict","구값 " + stInfo.getDistrict());
                Log.d("stInfo.setStadium","경기장값 " + stInfo.getStadium());

//////////////////////////////////////////////////////////////////////////////////////////////////////////
                if(Selected_City==stInfo.getCity() && Selected_District==stInfo.getDistrict()){
                    Log.d("stListresult.add","추출한 경기장값 " + stInfo.getStadium());
                    String st_val= stObject.getString("stadium");
                    stListresult.add(st_val);
                }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
                Log.d("stadium_Object","배열리스트에 넣기 전에 값 셋팅");

                stList.add(stInfo);
                Log.d("stadium_Object","배열에 값 삽입했어");
            }

            Log.d("stadium_Object","JSON값 셋팅하는 for문 탈출");

            stAdapterresult = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,stListresult);
            //stAdapterresult.setDropDownViewResource();
            spinnerStadium.setAdapter(stAdapterresult);
            Log.d("String","Stadium 아답타 세팅");

        }catch (JSONException e) {
            Log.d("JSON_ERR","####################JSON 오류###########################");
            e.printStackTrace();
        }
    }

    private void initAddressSpinner() {
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 시군구, 동의 스피너를 초기화한다.

                switch (position) {
                    case 0:
                        Selected_City = null;
                        Selected_District=null;

                        spinnerSigungu.setAdapter(null);
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        break;
                    case 1:
                        spinnerDong.setEnabled(true);
                        spinnerDong.setClickable(true);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_seoul);
                        break;
                    case 2:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_busan);
                        break;
                    case 3:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daegu);
                        break;
                    case 4:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_incheon);
                        break;
                    case 5:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gwangju);
                        break;
                    case 6:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daejeon);
                        break;
                    case 7:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_ulsan);
                        break;
                    case 8:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_sejong);
                        break;
                    case 9:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeonggi);
                        break;
                    case 10:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gangwon);
                        break;
                    case 11:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_buk);
                        break;
                    case 12:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_nam);
                        break;
                    case 13:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_buk);
                        break;
                    case 14:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_nam);
                        break;
                    case 15:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_buk);
                        break;
                    case 16:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_nam);
                        break;
                    case 17:
                        spinnerDong.setEnabled(false);
                        spinnerDong.setClickable(false);
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeju);
                        break;

                }
                Selected_City       =   spinnerCity.getSelectedItem().toString();
                if(Selected_City!=null && position!=0) {
                    Log.d("주소셋팅", "도시선택 완료, 구선택 시작");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 서울특별시 선택시

                Selected_District   =   String.valueOf(spinnerSigungu.getSelectedItem()).toString();
                Log.d("Selected_city","선택된 도시 " + Selected_City);
                Log.d("Selected_district","선택된 구 " + Selected_District);

                if(Selected_City!=null && Selected_City !="" && Selected_District!=null && Selected_District!="") {
                    String jsonString = getJsonString();
                    jsonParsing(jsonString);
                }

                if(spinnerCity.getSelectedItemPosition() == 1 && spinnerSigungu.getSelectedItemPosition() > -1) {

                    switch(position) {
                        //25
                        case 0:
                            Selected_District=null;
                        case 1:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangnam);
                            break;
                        case 2:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangdong);
                            break;
                        case 3:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangbuk);
                            break;
                        case 4:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangseo);
                            break;
                        case 5:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gwanak);
                            break;
                        case 6:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gwangjin);
                            break;
                        case 7:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_guro);
                            break;
                        case 8:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_geumcheon);
                            break;
                        case 9:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_nowon);
                            break;
                        case 10:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dobong);
                            break;
                        case 11:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dongdaemun);
                            break;
                        case 12:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dongjag);
                            break;
                        case 13:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_mapo);
                            break;
                        case 14:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seodaemun);
                            break;
                        case 15:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seocho);
                            break;
                        case 16:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seongdong);
                            break;
                        case 17:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seongbuk);
                            break;
                        case 18:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_songpa);
                            break;
                        case 19:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yangcheon);
                            break;
                        case 20:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yeongdeungpo);
                            break;
                        case 21:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yongsan);
                            break;
                        case 22:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_eunpyeong);
                            break;
                        case 23:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jongno);
                            break;
                        case 24:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jung);
                            break;
                        case 25:
                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jungnanggu);
                            break;
                    }

                }
                else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




    private void setSigunguSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            spinnerSigungu.setAdapter(null);
            arrayAdapter = null;
        }

        if (spinnerCity.getSelectedItemPosition() > 1) {
            spinnerDong.setAdapter(null);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSigungu.setAdapter(arrayAdapter);
    }

    private void setDongSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            spinnerDong.setAdapter(null);
            arrayAdapter = null;
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDong.setAdapter(arrayAdapter);
    }
}
