package com.example.recyclerviewpractice1;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // 한 줄에 들어가는 요소를 정하는 홀더
        public TextView TextView_Title;//3번 과정 -텍스트 2개에 이미지 1개만큼 추가해준다.
        public TextView TextView_Content; //Title은 제목,Content는 본문
        public SimpleDraweeView imageview;
        public MyViewHolder(View v) {//#5
            super(v);
            //받아온 자식들
            TextView_Title      =   v.findViewById(R.id.TextView_Title);//3번 과정 및, 2번 과정 #4
            TextView_Content    =   v.findViewById(R.id.TextView_Content);
            imageview           =   v.findViewById(R.id.imageview);




            //연결을 하는 과정은 레이아웃끼리 연결하는 setContent만 하는 기존의 방식과 다르다.
            //리사이클러뷰 내부에서 특정한 어떤 부분 부분을 바꾸는 것이기 때문에 #4부분에서 v.find id해준다.
            //why?액티비티에서 바로 찾아갈때는 액티비티 자체가 부모의 뷰이기 때문에 바로 찾아갈 수 있다.find id
            //but 여기서는 row_news가 부모이기 때문에 v.find id를 해주어야 한다.

        }
    }


    public MyAdapter(List<NewsData> myDataset, Context context) {//어댑터 최초에 세팅할 때 여기에 값을 넣어주어야 한다.(content)
        mDataset = myDataset;               //이 값을 꺼내와서 #5부분에서 보여준다.
        //String배열의 갯수만큼 반복한다.
        Fresco.initialize(context);//context를 받아올 수 있도록 RecyclerviewActivity의 #6부분의 인자값을 바꿔준다.
        //context를 받아오는 것은 메모리 누수가 날 수 있기 때문에 권장하진 않는다.
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 한 로고의 이미지 파일을 연결하는 부분.
        // 이 안에 들어가는 요소들은 위에 MyViewHolder
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                //3.여기서 원래 텍스트뷰지만 어댑터레이아웃의 최상위는 LinearLayout이기 때문에
                //LinearLayout으로 바꿔준다.
                .inflate(R.layout.row_news, parent, false);
        //1.레이아웃 이름 row_news로 변경 -> 레이아웃과 연결했고, findViewById를 통해서
        //뷰를 찾아가야한다. 해당 코드는 주석2번의 위치에서 친다.

        MyViewHolder vh = new MyViewHolder(v);//3번 과정을 수행하면 이 부분에 빨간줄이 생김
        //위에서 MyViewHolder에서 인자가 TextView로 설정되어 있기 때문에 그냥View로 바꿔준다.

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 뷰 홀더가 반복되면서 이 메소드에서 값을 셋팅한다.
        //내용 바꿔주는 거.
        NewsData news = mDataset.get(position);

        holder.TextView_Title.setText(news.getTitle());
        holder.TextView_Content.setText(news.getContent());
        Uri uri = Uri.parse(news.getUrlToImage());
        holder.imageview.setImageURI(uri);
        //이미지의 주소를 가져오기 위한 것이 fresco
    }




    @Override
    public int getItemCount() {
        return mDataset== null ? 0 : mDataset.size();//원래는 length지만 어레이리스트이기 때문에 size를 써준다.
        //에러가 날 수 있기 때문에 mDataset이 null일 경우 0을 출력해준다.
    }
}