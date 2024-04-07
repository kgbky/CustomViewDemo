package com.example.customviewapplication;

import static android.graphics.Paint.Style.FILL;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NestedSlidingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_sliding);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter listAdapter = new RecyclerView.Adapter<ViewHolder>() {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setLayoutParams(new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT, 300));
                tv.setBackgroundResource(R.color.colorPrimary);
                return new ViewHolder(tv);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                holder.tv.setText(position + "");
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        };
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            Paint paint = new Paint();
            private final Rect mBounds = new Rect();

            {
                paint.setStyle(FILL);
                paint.setAntiAlias(true);//抗锯齿
                paint.setColor(Color.YELLOW);
            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            }

            @Override
            public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                c.save();
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    final int top = bottom - 10;
                    mBounds.set(0, top, parent.getWidth(), bottom);
                    c.drawRect(mBounds, paint);
                }
                c.restore();
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(0, 0, 0, 10);
            }
        });
        recyclerView.setAdapter(listAdapter);
    }

    public void test(View view) {
        float dip = 400;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        //Translation参数；正数view会向下移动，负数向上移动
        findViewById(R.id.root).setTranslationY(-px);
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView tv;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = (TextView) itemView;
    }

}