package com.hospitalnavigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fengmap.android.map.marker.FMModel;
import com.hospitalnavigation.utils.AnalysisUtils;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<FMModel> searchList;
    ListView mListView;
    ArrayList<String> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 初始化Toolbar控件
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("查询地图信息");
        setSupportActionBar(toolbar);

        EditText keywordText = (EditText) findViewById(R.id.keyword_text);
        final String keyword = keywordText.getText().toString();

        mListView = (ListView) findViewById(R.id.list_view);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索请求
//                searchList = AnalysisUtils.queryModelByKeyword(MainActivity.mFMMap, MainActivity.mSearchAnalyser, keyword);

                for (FMModel model : searchList) {
                    displayList.add(model.getName());
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,displayList);
                mListView.setAdapter(adapter);
            }
        });

    }
}
