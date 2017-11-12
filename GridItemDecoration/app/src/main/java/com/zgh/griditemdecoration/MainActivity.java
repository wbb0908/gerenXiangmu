package com.zgh.griditemdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            datas.add("" + i);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //把数字数据赋给图片布局文件
        mAdapter = new RecyclerViewAdapter(R.layout.item, datas);
        recyclerView.setAdapter(mAdapter);

        int color = getResources().getColor(R.color.colorAccent);
        recyclerView.addItemDecoration(new GridItemDecoration(this, 3, color) {
            @Override
            public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
                //顺序:left, top, right, bottom
                boolean[] booleans = {false, false, false, false};
                switch (itemPosition % 3) {
                    case 0:
                        //每一行第一个只显示右边距和下边距
                        booleans[0] = true;
                        booleans[1] = true;
                        break;
                    case 1:
                        //每一行第二个只显示左边距和下边距
                        booleans[0] = true;
                        booleans[1] = true;
                        break;
                    case 2:
                        //每一行第二个只显示左边距和下边距
                        booleans[0] = true;
                        booleans[1] = true;
                        booleans[2] = true;
                        break;
                }
                return booleans;
            }
        });
    }


    private class RecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public RecyclerViewAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }


    }
}
