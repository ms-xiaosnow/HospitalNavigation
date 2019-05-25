package com.hospitalnavigation.fregment;

import android.view.View;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hospitalnavigation.Adapter.InfoAdapter;
import com.hospitalnavigation.InfoDatas;
import com.hospitalnavigation.R;
import com.itheima.pulltorefreshlib.PullToRefreshBase;
import com.itheima.pulltorefreshlib.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends BaseFragment {

    private InfoAdapter mInfoAdapter;
    private PullToRefreshListView mPullToRefreshListView;
    private List<InfoDatas> infoDatas;

    @Override
    public int bindLayout() {
        return R.layout.fg_info;
    }

    @Override
    public void initView(View view) {
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh_list_view);
        getData();
        mInfoAdapter = new InfoAdapter(getContext(), infoDatas);
        mPullToRefreshListView.setAdapter(mInfoAdapter);
        //设置刷新监听


        PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2<ListView>() {

            /**
             * 下拉刷新回调
             * @param refreshView
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //模拟延时三秒刷新
                mPullToRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        mInfoAdapter.notifyDataSetChanged();
                        mPullToRefreshListView.onRefreshComplete();//下拉刷新结束，下拉刷新头复位

                    }
                }, 3000);
            }

            /**
             * 上拉加载更多回调
             * @param refreshView
             */
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //模拟延时三秒加载更多数据
                mPullToRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        mInfoAdapter.notifyDataSetChanged();
                        mPullToRefreshListView.onRefreshComplete();//上拉加载更多结束，上拉加载头复位
                    }
                }, 3000);
            }
        };
        mPullToRefreshListView.setOnRefreshListener(mListViewOnRefreshListener2);
    }

    private void getData() {
        infoDatas = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>("UserRegistration");
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {

                    for (int i = 0; i < list.size(); i++) {
                        InfoDatas datas = new InfoDatas();
                        datas.setAddress(list.get(i).get("hospital").toString());
                        datas.setService(list.get(i).get("service").toString());
                        datas.setType(list.get(i).get("department").toString() + "|" + list.get(i).get("doctor").toString());
                        infoDatas.add(datas);
                    }

                } else {
                    e.printStackTrace();
                }

            }

        });
    }

    @Override
    public void initData(View view) {

    }
}
