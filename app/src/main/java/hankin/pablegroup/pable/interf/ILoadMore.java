package hankin.pablegroup.pable.interf;

/**
 * Created by Hankin on 2016/4/1.
 * @email hankin.huan@gmail.com
 */
public interface ILoadMore {
    /**
     * 上拉布局初始状态
     */
    void onLoadMoreInit();
    /**
     * 释放加载状态
     */
    void onReleaseToLoad();
    /**
     * 正在加载状态
     */
    void onLoading();
    /**
     * 加载成功
     */
    void onLoadMoreSuccess();
    /**
     * 加载失败
     */
    void onLoadMoreFailed();
    /**
     * 加载完毕
     */
    void onLoadMoreDone();
}
