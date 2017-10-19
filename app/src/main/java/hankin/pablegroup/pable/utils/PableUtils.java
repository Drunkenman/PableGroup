package hankin.pablegroup.pable.utils;

import hankin.pablegroup.pable.PableGroup;
import hankin.pablegroup.pable.interf.imp.IPableImp;

/**
 * pullview工具类
 * Created by Hankin on 2016/4/2.
 * @email hankin.huan@gmail.com
 */
public class PableUtils {

    private static void finishRefresh(PableGroup pullLayout, IPableImp pullable, boolean isSuccess, boolean canAutoPullUp, boolean allowPullUp){
        pullLayout.refreshFinish(isSuccess ? PableGroup.SUCCEED : PableGroup.FAIL, isSuccess ? 800 : 0);
        setLoadMore(pullLayout, pullable, canAutoPullUp, allowPullUp);
    }
    private static void finishLoadMore(PableGroup pullLayout, IPableImp pullable, boolean isSuccess, boolean canAutoPullUp, boolean allowPullUp) {
        pullLayout.loadmoreFinish(isSuccess? PableGroup.SUCCEED: PableGroup.FAIL, 0);
        setLoadMore(pullLayout, pullable, canAutoPullUp, allowPullUp);
    }
    private static void setLoadMore(PableGroup pullLayout, IPableImp pullable, boolean canAutoPullUp, boolean allowPullUp){
        pullable.setIsPullUping(false);
        pullable.setAutoPullUp(pullLayout, canAutoPullUp);
        pullLayout.allowPullUp(allowPullUp);
    }

    /**
     * 刷新完成与加载完成不要同时调用，否则布局会立马还原
     * @param isRefresh 是否刷新操作
     * @param pullLayout
     * @param pullable
     * @param isSuccess 是否成功
     * @param canAutoPullUp 是否允许页面滑动至底部时，自动加载
     * @param allowPullUp 是否允许加载操作
     */
    public static void finish(boolean isRefresh, PableGroup pullLayout, IPableImp pullable, boolean isSuccess, boolean canAutoPullUp, boolean allowPullUp){
        if (isRefresh) finishRefresh(pullLayout, pullable, isSuccess, canAutoPullUp, allowPullUp);
         else finishLoadMore(pullLayout, pullable, isSuccess, canAutoPullUp, allowPullUp);
    }

}
