package cn.hzw.graffiti.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import java.lang.ref.WeakReference;

import cn.hzw.graffiti.R;
import cn.hzw.graffiti.ScreenUtils;

/**
 * Created by Admin on 2017/3/8 0008 16:04.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class EraserPop {
    private ImageView eraserImageView;
    private SeekBar eraserSeekBar;
    private Button eraserBtnClear;
    private PopupWindow popWindow;
    private WeakReference<Activity> activity;
    private int pupWindowsDPWidth = 300;//弹窗宽度，单位DP
    private int eraserPupWindowsDPHeight = 150;//橡皮擦弹窗高度，单位DP
    private int size = 50;

    public EraserPop(Activity context) {
        this.activity = new WeakReference<Activity>(context);
    }

    public void showAs(View parent) {
        //设置菜单显示的位置Gravity.VERTICAL_GRAVITY_MASK
        initView();
        popWindow.showAsDropDown(parent, -pupWindowsDPWidth / 2-20, 0);
    }

    private void initView() {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //橡皮擦弹窗布局
            View popupEraserLayout = layoutInflater.inflate(R.layout.graffiti_popup_sketch_eraser, null);
            eraserImageView = (ImageView) popupEraserLayout.findViewById(R.id.graffiti_stroke_circle);
            eraserSeekBar = (SeekBar) (popupEraserLayout.findViewById(R.id.graffiti_stroke_seekbar));
            eraserBtnClear = (Button) popupEraserLayout.findViewById(R.id.graffiti_stroke_clear);

            Drawable circleDrawable = activity.get().getResources().getDrawable(R.drawable.graffiti_circle);
            assert circleDrawable != null;
            size = circleDrawable.getIntrinsicWidth();
            //橡皮擦弹窗
            popWindow = new PopupWindow(activity.get());// new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(activity, 90), true);
            popWindow.setContentView(popupEraserLayout);//设置主体布局
            popWindow.setWidth(ScreenUtils.dip2px(activity.get(), pupWindowsDPWidth));//宽度200dp
//        eraserPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);//高度自适应
            popWindow.setHeight(ScreenUtils.dip2px(activity.get(), eraserPupWindowsDPHeight));//高度自适应
            popWindow.setFocusable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());//设置空白背景
            popWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//动画
            eraserBtnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEraserCleared();
                    }
                    popWindow.dismiss();
                }
            });
            //橡皮擦宽度拖动条
            eraserSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }


                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }


                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    setSeekBarProgress(progress);
                }
            });
            //SketchView.DEFAULT_ERASER_SIZE
            eraserSeekBar.setProgress(10);
        }
//
//
//            // 创建一个PopuWidow对象
//            popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(activity, 90), true);
//            // 重写onKeyListener
//            view.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        popWindow.dismiss();
//                        popWindow = null;
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        }
        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
//        popWindow.setBackgroundDrawable(new BitmapDrawable());
//        popWindow.setOutsideTouchable(true);
//         设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popWindow.setBackgroundDrawable(new BitmapDrawable());
//        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //软键盘不会挡着popupwindow
//        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void setSeekBarProgress(int progress) {
        int calcProgress = progress > 1 ? progress : 1;
        int newSize = Math.round((size / 100f) * calcProgress);
        int offset = Math.round((size - newSize) / 2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(newSize, newSize);
        lp.setMargins(offset, offset, offset, offset);
        eraserImageView.setLayoutParams(lp);
        if (listener != null) {
            listener.onEraserSizeChanged(newSize);
        }
    }

    public void addLisener(final OnEraserListener listener) {
        this.listener = listener;
    }

    public void removeLisener() {
        this.listener = null;
    }

    private OnEraserListener listener;
}
