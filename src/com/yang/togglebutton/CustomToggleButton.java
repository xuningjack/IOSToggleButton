package com.yang.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 仿ios圆形选择按钮的ToggleButton，左侧关闭、右侧开启
 * @author Jack
 * @date: 2014-10-30 上午8:45:02
 */
public class CustomToggleButton extends View {

	/** 选中状态的横条背景 */
	private Bitmap mBackgroundOn;
	/** 未选中状态的横条背景 */
	private Bitmap mBackgroundOff;
	/** 滑块的背景 */
	private Bitmap mSlideBarBackground;
	/** 当前x轴的偏移量 */
	private int mCurrentX;
	/** 开关当前所处的开关状态，true为开启状态，false为关闭状态 */
	private boolean mCurrentState;
	/** 是否正在滑动中 */
	private boolean mIsSliding;
	/** 用户设置的监听事件 */
	private IOnToggleStateChangeListener mIOnToggleStateChangeListener;

	public CustomToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initBitmap();
	}

	public CustomToggleButton(Context context) {
		super(context);
		initBitmap();
	}

	/**
	 * 初始化开关所需的图片
	 */
	private void initBitmap() {
		mBackgroundOn = BitmapFactory.decodeResource(getResources(), R.drawable.switch_on);
		mBackgroundOff = BitmapFactory.decodeResource(getResources(), R.drawable.switch_off);
		mSlideBarBackground = BitmapFactory.decodeResource(getResources(), R.drawable.switch_button);
	}

	/**
	 * 设置当前控件的宽和高
	 * @see android.view.View#onMeasure(int, int)
	 * @param widthMeasureSpec 预留的宽度
	 * @param heightMeasureSpec 预留的高度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mBackgroundOn != null) { // 设置开关的宽和高
			setMeasuredDimension(mBackgroundOn.getWidth(), mBackgroundOn.getHeight());
		}
	}

	/**
	 * 绘制当前的控件
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) { // 重新绘制不同位置的滑块
		// 初始化绘制背景
		if (!mCurrentState) {
			canvas.drawBitmap(mBackgroundOn, 0, 0, null);
		} else {
			canvas.drawBitmap(mBackgroundOff, 0, 0, null);
		}
		// 绘制滑动块
		if (mIsSliding) { // 滑块正在移动中

			int left = mCurrentX - mSlideBarBackground.getWidth() / 2;
			if (left < 0) {
				// 当前超出了左边界, 赋值为0
				left = 0;
			} else if (left > (mBackgroundOff.getWidth() - mSlideBarBackground.getWidth())) {
				// 当前超出了右边界, 赋值为: 背景的宽度 - 滑动块的宽度
				left = mBackgroundOff.getWidth() - mSlideBarBackground.getWidth();
			}
			canvas.drawBitmap(mSlideBarBackground, left, 0, null); // 绘制滑块
		} else { // 没有超界
			if (mCurrentState) { // 开启状态，绘制滑块开的状态
				canvas.drawBitmap(mBackgroundOn, 0, 0, null);
				canvas.drawBitmap(mSlideBarBackground, 0, 0, null);
			} else { // 绘制关的状态

				canvas.drawBitmap(mBackgroundOff, 0, 0, null);
				int left = mBackgroundOn.getWidth()
						- mSlideBarBackground.getWidth();
				canvas.drawBitmap(mSlideBarBackground, left, 0, null); // 绘制滑块
			}
		}
	}

	/**
	 * 捕获用户操作的事件
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mCurrentX = (int) event.getX();
				mIsSliding = true;
				break;
			case MotionEvent.ACTION_MOVE:
				mCurrentX = (int) event.getX();
				break;
			case MotionEvent.ACTION_UP:
				mIsSliding = false;
				mCurrentX = (int) event.getX();
				int center = mBackgroundOn.getWidth() / 2;
				// 当前最新的状态
				boolean state = mCurrentX < center;
				// 如果两个状态不一样并且监听事件不为null
				if (mCurrentState != state && mIOnToggleStateChangeListener != null) {
					// 调用用户的回调事件
					mIOnToggleStateChangeListener.onToggleStateChange(this, state);
				}
				mCurrentState = state;
				break;
			default:
				break;
		}
		invalidate(); // 调用onDraw方法重绘
		return true;
	}

	/**
	 * 设置开关的状态
	 * @author Jack
	 * @param state
	 */
	public void setToggleState(boolean state) {
		if (mIOnToggleStateChangeListener != null) {
			mIOnToggleStateChangeListener.onToggleStateChange(this, state);
		}
		mCurrentState = state;
		invalidate();
	}

	/**
	 * 获取当前的开关状态
	 * @author Jack
	 * @return
	 */
	public boolean getToggleState() {
		return mCurrentState;
	}

	/**
	 * 设置开关的状态监听器的相应处理
	 * @author Jack
	 * @param listener
	 */
	public void setIOnToggleStateChangeListener(IOnToggleStateChangeListener listener) {
		mIOnToggleStateChangeListener = listener;
	}
}