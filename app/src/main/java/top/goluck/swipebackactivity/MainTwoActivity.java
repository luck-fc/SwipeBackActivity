package top.goluck.swipebackactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import top.goluck.swipeback.SwipeBackActivityUtil;

public class MainTwoActivity extends AppCompatActivity {

    protected SwipeBackActivityUtil mSwipeBackActivityUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackActivityUtil = new SwipeBackActivityUtil(this);
        mSwipeBackActivityUtil.setIonFinish(new SwipeBackActivityUtil.IonFinish() {
            @Override
            public void onFinish() {
                Toast.makeText(MainTwoActivity.this,"监听到被滑动关闭了",Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(R.layout.activity_maintwo);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mSwipeBackActivityUtil.dispatchTouchEvent(ev)){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        mSwipeBackActivityUtil.onDestroy();
        super.onDestroy();
    }
}
