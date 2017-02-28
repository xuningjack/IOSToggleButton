package com.yang.togglebutton;

import android.view.View;

/**
 * IMToggleButton状态改变的回调接口
 * @author 徐宁
 * @date: 2014-10-30 上午8:42:21
 */
public interface IOnToggleStateChangeListener {

    /**
     * 当监听状态改变时的回调方法
     * @author 徐宁
     * @param view 操作的哪个ToggleButton
     * @param state 操作后的状态
     */
	void onToggleStateChange(View view, boolean state);
}
