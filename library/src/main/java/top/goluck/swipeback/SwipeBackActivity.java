package top.goluck.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * 作者：luck on 2017/5/12 14:23
 * 邮箱：fc_dream@163.com
 * SwipeBackActivity
 */
public class SwipeBackActivity extends AppCompatActivity {

    protected SwipeBackActivityUtil mSwipeBackActivityUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackActivityUtil = new SwipeBackActivityUtil(this);
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
