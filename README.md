
[![](https://jitpack.io/v/luck-fc/SwipeBackActivity.svg)](https://jitpack.io/#luck-fc/SwipeBackActivity)
[![GitHub release](https://img.shields.io/github/release/luck-fc/SwipeBackActivity.svg)](https://github.com/luck-fc/SwipeBackActivity/releases/tag/1.0.1)
# SwipeBackActivity
Right slide closure 右滑关闭

* 支持两种方式实现
* 支持开启关闭 右滑关闭功能
* 支持滑动关闭之后回调（可用于特殊机型需要activity销毁之前关闭键盘）

## Android引入library 
root build.gradle加入
```gradle
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
项目 build.gradle加入
```gradle
    compile 'com.github.luck-fc:SwipeBackActivity:1.0'
```
## 简要使用示例，详细请参考项目
### 方式1 继承SwipeBackActivity
~~~java
public class MainActivity extends SwipeBackActivity {
}
~~~
### 方式2 使用SwipeBackActivityUtil
~~~java
public class MainActivity extends AppCompatActivity {
   //top 1 使用SwipeBackActivityUtil
   protected SwipeBackActivityUtil mSwipeBackActivityUtil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //top 2 实例化，传入当前activity
        mSwipeBackActivityUtil = new SwipeBackActivityUtil(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //top 3 dispatchTouchEvent 调用SwipeBackActivityUtil.dispatchTouchEvent(MotionEvent ev)
        if(mSwipeBackActivityUtil.dispatchTouchEvent(ev)){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        //top 4 onDestroy 调用SwipeBackActivityUtil.onDestroy()
        mSwipeBackActivityUtil.onDestroy();
        super.onDestroy();
    }
}
~~~

开发者 (Developer)
----------------

* luck-fc - <xiaoorchao@gmail.com>

**License**
=======

    Copyright 2017 luck-fc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
