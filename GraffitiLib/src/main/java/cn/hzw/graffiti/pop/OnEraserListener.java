package cn.hzw.graffiti.pop;

/**
 * Created by Admin on 2017/5/18 0018 13:53.
 * Author: kang
 * Email: kangsafe@163.com
 * 橡皮檫事件监听
 */
public interface OnEraserListener{
    /**
     * 橡皮檫大小改变
     * @param size
     */
    void onEraserSizeChanged(int size);

    /**
     * 清空画布
     */
    void onEraserCleared();
}