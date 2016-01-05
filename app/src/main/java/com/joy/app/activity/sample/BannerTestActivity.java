package com.joy.app.activity.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.android.library.activity.BaseUiActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.view.banner.BannerAdapter;
import com.android.library.view.banner.BannerImage;
import com.android.library.view.banner.BannerWidget;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/17.
 */
public class BannerTestActivity extends BaseUiActivity {

    private BannerWidget mBannerWidget;

    public static void startActivity(Activity act) {

        act.startActivity(new Intent(act, BannerTestActivity.class));
    }

    @Override
    protected void onResume() {

        super.onResume();
        mBannerWidget.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mBannerWidget.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // local image
//        BannerAdapter<Integer> adapter = new BannerAdapter(new BannerImage<Integer>());
//        adapter.setData(getLocalImgs());

        // remote image
//        BannerAdapter<String> adapter = new BannerAdapter(new BannerImage<String>());
//        adapter.setData(getRemoteImgs());

        // obj
        BannerAdapter<TestEntry> adapter = new BannerAdapter<>(new BannerImage<TestEntry>() {

            @Override
            protected FrescoImageView onCreateView(Context context) {

                return (FrescoImageView) inflateLayout(R.layout.item_banner);
            }
        });
        adapter.setData(getEntries());
        adapter.setOnItemViewClickListener(new OnItemViewClickListener<TestEntry>() {

            @Override
            public void onItemViewClick(int position, View clickView, TestEntry testEntry) {

                showToast(testEntry.getCnname() + ": " + position);
            }
        });

        mBannerWidget = new BannerWidget(this, adapter);
        mBannerWidget.setPageTransformer(new AccordionTransformer());// page transformer animation
        setContentView(mBannerWidget.getContentView());
    }

    private List<Integer> getLocalImgs() {

        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.ic_def_large);
        imgs.add(R.drawable.ic_def_large);
        imgs.add(R.drawable.ic_def_large);
        imgs.add(R.drawable.ic_def_large);
        imgs.add(R.drawable.ic_def_large);
        imgs.add(R.drawable.ic_def_large);
        imgs.add(R.drawable.ic_def_large);
        return imgs;
    }

    private List<String> getRemoteImgs() {

        List<String> imgs = new ArrayList<>();
        imgs.add("http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg");
        imgs.add("http://img2.3lian.com/2014/f2/37/d/40.jpg");
        imgs.add("http://d.3987.com/sqmy_131219/001.jpg");
        imgs.add("http://img2.3lian.com/2014/f2/37/d/39.jpg");
        imgs.add("http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg");
        imgs.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg");
        imgs.add("http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg");
        return imgs;
    }

    private List<TestEntry> getEntries() {

        List<String> imgs = getRemoteImgs();

        List<TestEntry> entries = new ArrayList<>();
        TestEntry entry;
        for (int i = 0; i < imgs.size(); i++) {

            entry = new TestEntry();
            entry.setCnname("cnname" + i);
            entry.setEnname("enname" + i);
            entry.setPic_url(imgs.get(i));
            entries.add(entry);
        }
        return entries;
    }

    private class TestEntry {

        private String enname;
        private String cnname;
        private String pic_url;

        public String getEnname() {

            return enname;
        }

        public void setEnname(String enname) {

            this.enname = enname;
        }

        public String getCnname() {

            return cnname;
        }

        public void setCnname(String cnname) {

            this.cnname = cnname;
        }

        public String getPic_url() {

            return pic_url;
        }

        public void setPic_url(String pic_url) {

            this.pic_url = pic_url;
        }

        // must override
        @Override
        public String toString() {

            return this.pic_url;
        }
    }
}