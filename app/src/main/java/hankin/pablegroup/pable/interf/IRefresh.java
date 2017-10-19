package hankin.pablegroup.pable.interf;

/**
 * Created by Hankin on 2016/4/1.
 * @email hankin.huan@gmail.com
 */
public interface IRefresh {
    /**
     * 下拉布局初始状态
     */
    void onRefreshInit();
    /**
     * 释放刷新状态
     */
    void onReleaseToRefresh();
    /**
     * 正在刷新状态
     */
    void onRefreshing();
    /**
     * 刷新成功
     */
    void onRefreshSuccess();
    /**
     * 刷新失败
     */
    void onRefreshFailed();
    /**
     * 刷新完毕
     */
    void onRefreshDone();
}
