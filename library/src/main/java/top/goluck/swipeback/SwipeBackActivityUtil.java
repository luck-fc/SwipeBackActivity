package top.goluck.swipeback;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * 作者：luck on 2017/5/12 11:29
 * 邮箱：fc_dream@163.com
 * VelocityTracker_2017_4_14
 */
public class SwipeBackActivityUtil {

    private Activity mActivity;
    private int mTouchDistance;
    private View mDecorView;
    private int mDecorViewWidth;
    private int startX, startY;
    private int DURATION = 300;
    private ValueAnimator mAnimator;
    private int mTouchSlop;
    private boolean mIsMoving;
    private ArgbEvaluator mEvaluator;
    private boolean ispost = false;

    public SwipeBackActivityUtil(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is null");
        }
        this.mActivity = activity;
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        mEvaluator = new ArgbEvaluator();
        mDecorView = mActivity.getWindow().getDecorView();
        mDecorView.post(new Runnable() {
            @Override
            public void run() {
                if (!ispost) {
                    if(mActivity!=null) {
                        ViewGroup rootViewGroup = ((ViewGroup) mActivity.findViewById(android.R.id.content));
                        if (rootViewGroup != null) {
                            ispost = true;
                            View rootView = rootViewGroup.getChildAt(0);
                            if (rootView != null && rootView.getBackground() == null) {
                                rootView.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }
                }
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDecorViewWidth = dm.widthPixels;
        mTouchSlop = ViewConfiguration.get(mActivity).getScaledTouchSlop();
        mTouchDistance = mDecorViewWidth / 25;
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(DURATION);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int x = (Integer) valueAnimator.getAnimatedValue();
                if (x >= mDecorViewWidth) {
                    if (mActivity!=null && !mActivity.isFinishing()) {
                        if (mIonFinish != null) {
                            mIonFinish.onFinish();
                        }
                        mActivity.onBackPressed();
                    }
                }
                handleView(x);
                handleBackgroundColor(x);
            }
        });
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        addMovement(ev);
        if (mAnimator.isRunning()) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startX < mTouchDistance) {
                    if (!mIsMoving) {
                        float dx = Math.abs(ev.getRawX() - startX);
                        float dy = Math.abs(ev.getRawY() - startY);
                        if (dx > dy && dx > mTouchSlop) {
                            mIsMoving = true;
                        }
                    }
                    if (mIsMoving) {
                        handleView((int) ev.getRawX());
                        handleBackgroundColor(ev.getRawX());
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsMoving) {
                    int distance = (int) (ev.getRawX() - startX);
                    mVelTracker.computeCurrentVelocity(1000);
                    //获取x方向上的速度
                    float velocityX = mVelTracker.getXVelocity();
                    if (mIsMoving && Math.abs(mDecorView.getScrollX()) >= 0) {
                        if (velocityX > 1000f || distance >= mDecorViewWidth / 2) {
                            mAnimator.setIntValues((int) ev.getRawX(), mDecorViewWidth);
                        } else {
                            mAnimator.setIntValues((int) ev.getRawX(), 0);
                        }
                        mAnimator.start();
                        mIsMoving = false;
                    }
                    startX = 0;
                    startY = 0;
                }
                recycleVelocityTracker();
                break;
        }
        return false;
    }

    /**
     * 控制activity 移动
     *
     * @param x
     */
    public void handleView(int x) {
        mDecorView.scrollTo(-x, 0);
    }

    /**
     * 控制背景颜色和透明度
     *
     * @param x
     */
    private void handleBackgroundColor(float x) {
        int colorValue = (int) mEvaluator.evaluate(x / mDecorViewWidth,
                Color.parseColor("#dd000000"), Color.parseColor("#00000000"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDecorView.setBackground(new ColorDrawable(colorValue));
        }else {
            mDecorView.setBackgroundDrawable(new ColorDrawable(colorValue));
        }
    }

    private VelocityTracker mVelTracker;

    /**
     * 获取速度追踪器
     *
     * @return
     */
    private void addMovement(MotionEvent event) {
        if (mVelTracker == null) {
            mVelTracker = VelocityTracker.obtain();
        }
        mVelTracker.addMovement(event);
    }

    /**
     * 回收速度追踪器
     */
    private void recycleVelocityTracker() {
        if (mVelTracker != null) {
            mVelTracker.clear();
            mVelTracker.recycle();
            mVelTracker = null;
        }
    }

    public void onDestroy() {
        recycleVelocityTracker();
        this.mIonFinish = null;
        this.mActivity = null;
    }

    private IonFinish mIonFinish;

    public void setIonFinish(IonFinish mIonFinish) {
        this.mIonFinish = mIonFinish;
    }

    public interface IonFinish {
        void onFinish();
    }
}
