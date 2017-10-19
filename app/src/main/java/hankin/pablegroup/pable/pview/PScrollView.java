package hankin.pablegroup.pable.pview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import hankin.pablegroup.pable.PableGroup;
import hankin.pablegroup.pable.interf.imp.IPableImp;

/**
 *
 * Created by Hankin on 2016/5/26.
 * @email hankin.huan@gmail.com
 */
public class PScrollView extends ScrollView implements IPableImp {
    public PScrollView(Context context) {
        this(context, null);
    }
    public PScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public PScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0) return true;
        else return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))  return true;
        else return false;
    }

    @Override
    public void consumeEvent(boolean isConsume) {
    }
    @Override
    public void setAutoPullUp(PableGroup pullLayout, boolean canAutoPullUp) {
    }
    @Override
    public void setIsPullUping(boolean isPullUping) {
    }
}
