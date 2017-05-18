package cn.hzw.graffiti.pop;

import android.app.Activity;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Admin on 2017/5/16 0016 13:35.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class PopUtils {
    static PopUtils popUtils = null;
    static WeakReference<Activity> mContext;

    private EraserPop eraserPop;
    private OnEraserListener onEraserListener;

    private PopUtils() {
    }

    public static PopUtils getInstance(Activity context) {
        if (popUtils == null) {
            synchronized (PopUtils.class) {
                if (popUtils == null) {
                    mContext = new WeakReference<Activity>(context);
                    popUtils = new PopUtils();
                }
            }
        }
        return popUtils;
    }

    private void initEraserPop() {
        synchronized (this) {
            if (eraserPop == null) {
                eraserPop = new EraserPop(mContext.get());
            }
            if (onEraserListener != null) {
                eraserPop.addLisener(this.onEraserListener);
            }
        }
    }

    public void showEraserPop(View ancher) {
        initEraserPop();
        eraserPop.showAs(new WeakReference<View>(ancher).get());
    }

    public void addEraserPopLinstener(OnEraserListener listener) {
        this.onEraserListener = listener;
    }

    public void destroy() {
        if (eraserPop != null) {
            eraserPop.removeLisener();
            eraserPop = null;
        }
        onEraserListener = null;
        mContext = null;
        popUtils = null;
    }
}
