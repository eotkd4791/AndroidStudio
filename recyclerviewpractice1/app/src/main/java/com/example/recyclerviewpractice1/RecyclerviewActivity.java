package com.example.recyclerviewpractice1;

    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;

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
    import java.util.List;

public class RecyclerviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;//전역변수로 바꾼다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        queue =  Volley.newRequestQueue(this);
        getNews();
    }

    public void getNews() {
        String url ="https://newsapi.org/v2/top-headlines?country=kr&category=sports&apiKey=c8e9be2d28fb414189aa462890158209";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray arrayArticles =  jsonObj.getJSONArray("articles");
                            //가져오려는 뉴스API의 아티클이 배열구조이기떄문에 ..

                            List<NewsData> news = new ArrayList<>();
                            for (int i=0, j=arrayArticles.length(); i<j; i++)  {
                                JSONObject obj = arrayArticles.getJSONObject(i);

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("description"));
                                news.add(newsData);
                            }

                            //데이터를 NewsData에서 분류한다.
                            mAdapter = new MyAdapter(news, RecyclerviewActivity.this);//받아온 입력값을 MyAdapter자바 파일의 #5부분으로 전달하는 코드
                            //fresco설정 시에 RecyclerviewActivity.this를 추가하여 준다. #6

                            recyclerView.setAdapter(mAdapter);// 그래서 이 부분에 초기값을 세팅해야한다.

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}