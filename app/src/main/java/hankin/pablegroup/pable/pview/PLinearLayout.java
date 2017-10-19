package hankin.pablegroup.pable.pview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import hankin.pablegroup.pable.PableGroup;
import hankin.pablegroup.pable.interf.imp.IPableImp;

/**
 *
 * Created by Hankin on 2017/10/15.
 * @email hankin.huan@gmail.com
 */

public class PLinearLayout extends LinearLayout implements IPableImp {

    public PLinearLayout(Context context) {
        super(context);
    }
    public PLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
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
