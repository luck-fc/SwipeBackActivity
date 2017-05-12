package top.goluck.swipebackactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import top.goluck.swipeback.SwipeBackActivity;

public class MainActivity extends SwipeBackActivity {

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
        start();
    }

    public void start(){
        Intent m = new Intent(MainActivity.this,MainTwoActivity.class);
        startActivity(m);
    }
}
