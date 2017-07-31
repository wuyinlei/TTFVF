首先来看下实现的效果吧：
![这里写图片描述](http://img.blog.csdn.net/20160123201056225)
近期在项目中实现这个效果的时候，虽然自己磕磕绊绊的实现了，但是知识确实模模糊糊的，今天天气异常的冷，在加上这个知识不太熟练，实在是没有心情进行接下来的计划，干脆借着这个时间，好好的整理一下这个实现方式，也在次总结一下，记忆更加深刻。

####TabLayout简介
       
  *  在2015年的Google I/O大会上，Google发布的新的Android Support Design库，里面也包含了几个新的控件，那么TabLayout就是其中一个。使用该组件我们可以很轻松的实现TabPageIndicator效果，并且该为官方的，可以向下兼容很多版本而且可以更加统一Material Design效果。

使用的时候要在库依赖中加入
如果不太清楚，可以看下http://blog.csdn.net/wuyinlei/article/details/50570018
教你怎么在项目中加入依赖。
```
compile'com.android.support:design:23.1.1’
```
接下来，在TabLayout中加入

```
<android.support.design.widget.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        <!-- 下方滚动的下划线颜色  -->
        app:tabIndicatorColor="#33aa22"
        <!-- 下方指示条的高度  -->
        app:tabIndicatorHeight="5dp"
        <!-- tab被选中后，文字的颜色  -->
        app:tabSelectedTextColor="#33aa22"
        <!--可以改变tab中的字体的大小-->
        app:tabTextAppearance="@style/App_Theme"
        <!-- tab中字体的颜色  -->
        app:tabTextColor="#33aa22"/>
```
可以在这里设置字体大小，然后引用：
```
  <style name="App_Theme" parent="Theme.AppCompat">
        <item name="android:windowNoTitle">true</item>
        <item name="android:textSize">18dp</item>
        <item name="android:textAllCaps">false</item>
    </style>

```
**注意：**上面的这个style必须在承载他的activity中theme中加入，要不然会出现
```
Cause by ：java.lang.IllegalArgumentException: You need to use a Theme.AppCompat theme (or descendant) with the design library;（我没有加上之前各种错，加上正常运行，但是运行之后去掉，竟然也可以正常运行，不得解)
```

好了，接下来我们看下具体怎么实现的吧，里面有解释：
MainActivity.java：

```
package com.example.tablayoutfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

```
activity_main.xml:

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.tablayoutfragment.MainActivity">
    
        <FrameLayout
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            >
            <!--在这里直接添加fragment。并且制定fragment类名-->
            <fragment
                android:id="@+id/main_info_fragment"
                class="com.example.tablayoutfragment.MainFragment"
                android:layout_width="fill_parent"
                android:layout_height="match_parent"/>
        </FrameLayout>
</LinearLayout>

```
MAinFragment.java：

```
package com.example.tablayoutfragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //fragment存储器
    private List<Fragment> mFragments;
    //tab条目中的内容
    private String[]titles = {"第一个","第二个","第三个","第四个","第五个","第六个","第七个","第八个"};
    private FixedPagerAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mAdapter.setTitles(titles);//标题
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(PageFragment.newInstance(titles[i]));
        }
        //把要显示的fragment集合传给adapter
        mAdapter.setFragments(mFragments);
        /**
         * 设置tablayout的模式
         *  模式一：MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         *  模式二：MODE_FIXED Fixed tabs display all tabs concurrently   展示出所有的，适合三四个tab
         */
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //给viewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //TabLayout绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }
}

```
fragment_main.xml:

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:app="http://schemas.android.com/apk/res-auto"
              xmlns:tools="http://schemas.android.com/tools"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:background="#fff"
              android:orientation="vertical"
              tools:context=".MainFragment">

    <android.support.design.widget.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabIndicatorColor="#33aa22"
        app:tabIndicatorHeight="5dp"
        app:tabSelectedTextColor="#33aa22"
        app:tabTextAppearance="@style/App_Theme"
        app:tabTextColor="#33aa22"/>

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
    </android.support.v4.view.ViewPager>
</LinearLayout>

```
PageFragment.java:

```
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

```
这个fragment的布局里面就一个textview，我就不写上了。
下面看下这个adapter吧
FixedPagerAdapter.java:

```
package com.example.tablayoutfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 若兰 on 2016/1/23.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class FixedPagerAdapter extends FragmentStatePagerAdapter {


    private String[] titles;

    /**
     * 设置标题
     *
     * @param titles
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    private List<Fragment> mFragments;

    /**
     * 这个是在继承FragmentStatePagerAdapter会强制写入的
     *
     * @param fm
     */
    public FixedPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     * Return the number of views available.
     * 返回一个可以用的view的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * Create the page for the given position. The adapter is responsible for
     * adding the view to the container given here,
     * although it only must ensure this is done by the time it returns from finishUpdate(ViewGroup).
     * 这个同destroyItem（）相反，是对于给定的位置创建视图，适配器往container中添加
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        fragment = (Fragment) super.instantiateItem(container,position);
        return fragment;
    }


    public List<Fragment> getFragments() {
        return mFragments;
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    /**
     * Remove a page for the given position. The adapter is responsible for
     * removing the view from its container,
     * although it only must ensure this is done by the time it returns from finishUpdate(View).
     * 移除给定位置的数据，适配器负责从container（容器）中取出，但是这个必须保证是在finishUpdate（view）
     * 返回的时间内完成
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * 得到滑动页面的Title
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
```
好了，到此为止，运行就可以出现了开头的效果了。现在附上github该demo的地址：https://github.com/wuyinlei/TTFVF,  有疑问可以交流哈。多谢支持！！
