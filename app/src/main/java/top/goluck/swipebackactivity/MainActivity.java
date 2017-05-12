package top.goluck.swipebackactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import top.goluck.swipeback.SwipeBackActivity;

public class MainActivity extends SwipeBackActivity {

    private TextView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        close = (TextView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(close.getText().toString().equals("关闭滑动返回")) {
                    close.setText("开启滑动返回");
                    setEnabledSwipeBack(false);
                }else if(close.getText().toString().equals("开启滑动返回")) {
                    close.setText("关闭滑动返回");
                    setEnabledSwipeBack(true);
                }
            }
        });
        start();
    }

    public void start(){
        Intent m = new Intent(MainActivity.this,MainTwoActivity.class);
        startActivity(m);
    }
}
