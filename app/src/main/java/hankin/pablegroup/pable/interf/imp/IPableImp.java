package hankin.pablegroup.pable.interf.imp;

import hankin.pablegroup.pable.PableGroup;
import hankin.pablegroup.pable.interf.IPable;

/**
 *
 * Created by Hankin on 2016/4/11.
 * @email hankin.huan@gmail.com
 */
public interface IPableImp extends IPable {
    /**
     * 设置当内容页面滚动到底部时，是否自动加载
     * */
    void setAutoPullUp(PableGroup pullLayout, boolean canAutoPullUp);
    /**
     * 设置是否在加载中
     * */
    void setIsPullUping(boolean isPullUping);
}
