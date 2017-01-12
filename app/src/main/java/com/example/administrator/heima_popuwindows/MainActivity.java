package com.example.administrator.heima_popuwindows;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ListView.OnItemClickListener{

    private ImageView ib_dropdown;
    private List<String> list;
    private ListView listView;
    private EditText et_input;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        initData();
    }

    private void initData() {

    }

    private void initUi() {
        ib_dropdown = (ImageView) findViewById(R.id.ib_dropdown);
        et_input = (EditText) findViewById(R.id.et_input);
        ib_dropdown.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_dropdown:
                showPopupWindow();
                break;

        }

    }

    private void showPopupWindow() {
        //在PopupWindow内添加点击事件，需要将显示item属性descendantFocusability设置为true
        initListview();
        //为PopupWindow添加显示内容及宽度大小
        popupWindow = new PopupWindow(listView, et_input.getWidth(), 400);
        //非popupwindow区域点击取消popupWindow显示方法：
        //1.设置PopupWindow外部的点击事件
        popupWindow.setOutsideTouchable(true);
        //2.设置popupWindow的背景为透明
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //3.设置PopupWindow可以获取焦点
        popupWindow.setFocusable(true);
        //设置PopupWindow显示的位置
        popupWindow.showAsDropDown(et_input, 5, -5);


        //设置PopupWindow内item的点击事件
    }

    private void initListview() {
        //添加一个list数组集合，
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add((10000 + i) + "");
        }
        listView = new ListView(this);
        //去掉listview条目的分割线
        listView.setDividerHeight(0);
        //给listview设置边界
        listView.setBackgroundResource(R.drawable.listview_background);
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        et_input.setText(list.get(position));

        popupWindow.dismiss();
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.popupwindow_item, null);
            } else {
                view = convertView;
            }
            TextView tv_qq = (TextView) view.findViewById(R.id.tv_qq);
            ImageButton iv_delete = (ImageButton) view.findViewById(R.id.ib_delete);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                    if (list.size() ==0){
                        popupWindow.dismiss();
                    }
                }
            });
            tv_qq.setText(list.get(position));
            return view;
        }
    }
}
