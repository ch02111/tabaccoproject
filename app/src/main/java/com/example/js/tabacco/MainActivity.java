package com.example.js.tabacco;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.js.tabacco.R.drawable.bar_icon_menu;


public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar=null;
    private ListView listView= null;
    private ListViewAdapter listViewAdapter= null;
    private userData user = null;
    DrawerLayout drawer=null;
    int layarr[]={R.layout.content_login,R.layout.content_map,R.layout.content_tabacco,R.layout.content_lawfine,R.layout.content_notice};
    int drawarr[]={R.drawable.bar_icon_login,R.drawable.menu_icon_map,R.drawable.menu_icon_tabacco,R.drawable.menu_icon_law_fine,R.drawable.menu_icon_notice};
    String strarr[]={"로그인 하세요.","MAP","TABACCO\nINFORMATION","LAW&FINE","NOTICE"};


    public boolean IsLogin()//로그인 확인
    {
        if(user==null)
            return false;
        else
            return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //톨바 추가
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        setSupportActionBar(toolbar);

        //액션바 토글 버튼 - Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        //메뉴 리스트 초기화

        //list_view   참고 링크 :    http://itmir.tistory.com/477
        listView = (ListView)findViewById(R.id.bar_menu);   //list_view bar_menu 가져오기
        listViewAdapter = new ListViewAdapter(this); //list_view_adapter 만들기
        listView.setAdapter(listViewAdapter); //listview 와 adapter 연결

        // 로그인 메뉴구성
        // 로그인이 되어있지 않을 경우
        if(IsLogin())
            ;
        else
            listViewAdapter.addItem(getResources().getDrawable(R.drawable.bar_icon_login),"로그인 하세요.");
        //그외 메뉴 구성
        for(int i=1;i<5;i++)
            listViewAdapter.addItem(getResources().getDrawable(drawarr[i]),strarr[i]);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){ //position = 몇번째 추가 아이템인지
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                FrameLayout frame = (FrameLayout) findViewById(R.id.frame) ; //Framelayout객체의 frame에 id가 frame으로 선언된 FrameLayout을 대입.(activity_main 에 선언되어 있음)
                v=inflater.inflate(layarr[position],frame,false); //v=layarr 배열의 position 번째 layout 정보를 담음
                ChangeScreen(frame,v); //frame layout 변경
            }
        });

    }
    public void onClickedLogin(View v)//app_bar.xml 에 있는 Image_view 와 연결 되어 있음.우측 마이페이지.
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame) ; //Framelayout객체의 frame에 id가 frame으로 선언된 FrameLayout을 대입.(activity_main 에 선언되어 있음)
        v=inflater.inflate(R.layout.content_login,frame,false);
        ChangeScreen(frame,v);
    }


    public void ChangeScreen(FrameLayout frame,View v)
    {

        if(v!=null) {
            if (frame.getChildCount() > 0) {
                // FrameLayout에서 뷰 삭제.
                frame.removeViewAt(0);
            }
            frame.addView(v);
        }
    }


    public class bar_menu_Horder
    {
        public ImageView menu_icon;
        public TextView menu_text;
    }



    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            bar_menu_Horder holder;
            if (convertView == null) {
                holder = new bar_menu_Horder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_view_item, null);

                holder.menu_icon = (ImageView) convertView.findViewById(R.id.menu_Image);
                holder.menu_text = (TextView) convertView.findViewById(R.id.menu_text);

                convertView.setTag(holder);
            }else{
                holder = (bar_menu_Horder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.menu_icon.setVisibility(View.VISIBLE);
                holder.menu_icon.setImageDrawable(mData.mIcon);
            }else{
                holder.menu_icon.setVisibility(View.GONE);
            }

            holder.menu_text.setText(mData.mTitle);

            return convertView;
        }

        public void addItem(Drawable icon, String mTitle){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            mListData.add(addInfo);
        }
    }
}
