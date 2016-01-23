package com.example.tablayoutfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 若兰 on 2016/1/23.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class PageFragment extends Fragment {

    private View mView;

    /**
     * key值
     */
    private static final String KEY = "EXTRA";

    private String title;

    /**
     * 在这里我们提供一个静态的方法来实例化PageFragment
     * 在这里我们传入一个参数，用来得到title，然后我们拿到这个title设置给内容
     *
     * @param extra
     * @return
     */
    public static PageFragment  newInstance(String extra){
        //利用bundle传值
        Bundle bundle = new Bundle();
        bundle.putString(KEY,extra);
        //实例化
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(KEY);
        }
    }
*/
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(KEY);
        }
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment, container, false);
        }
        initView();
        return mView;
    }


    public void initView() {
        mTextView = (TextView) mView.findViewById(R.id.text_fragment);
        mTextView.setText(title);
    }
}
