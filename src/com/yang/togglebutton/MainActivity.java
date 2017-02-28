package com.yang.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private CustomToggleButton mToggleButton;
    private TextView mTextView;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToggleButton = (CustomToggleButton) findViewById(R.id.toggle2);
        mToggleButton.setIOnToggleStateChangeListener(new IOnToggleStateChangeListener() {

            @Override
            public void onToggleStateChange(View view, boolean state) {
                //TODO 使用者重写此方法监听开关的相应处理
                if (state) { //开启状态

                    Toast.makeText(MainActivity.this, "开关开启", 0).show();
                } else { //关闭状态

                    Toast.makeText(MainActivity.this, "开关关闭", 0).show();
                }
            }
        });
        mToggleButton.setToggleState(false); //初始设置关闭状态

        mLayout = (RelativeLayout) findViewById(R.id.layout);
        mLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mToggleButton.setToggleState(!mToggleButton.getToggleState());
            }
        });

        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() { //单击其他view来控制开关

            @Override
            public void onClick(View v) {

                mToggleButton.setToggleState(!mToggleButton.getToggleState());
            }
        });
    }
}
