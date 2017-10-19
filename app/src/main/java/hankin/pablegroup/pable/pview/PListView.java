package hankin.pablegroup.pable.pview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import hankin.pablegroup.pable.PableGroup;
import hankin.pablegroup.pable.interf.imp.IPableImp;

/**
 *
 * @email hankin.huan@gmail.com
 */
public class PListView extends ListView implements IPableImp {

	/*是否正上拉加载总*/
	private boolean isPullUping = false;
	private PableGroup pullLayout;
	/*能否自动上拉加载*/
	private boolean canAutoPullUp = false;

	public PListView(Context context) {
		super(context);
	}
	public PListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public PListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp() {
		if (getCount() == 0) {
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}

	@Override
	public void setAutoPullUp(PableGroup pullLayout, boolean canAutoPullUp){
		if (pullLayout==null) return;
		this.pullLayout = pullLayout;
		this.canAutoPullUp = canAutoPullUp;
	}
	@Override
	public void setIsPullUping(boolean isPullUping) {
		this.isPullUping = isPullUping;
	}
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (!isPullUping &&canAutoPullUp&&canPullUp()&&pullLayout!=null){
			isPullUping = true;
			pullLayout.autoLoad();
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}
	@Override
	public void consumeEvent(boolean isConsume) {
		this.isConsume = isConsume;
	}
	private boolean isConsume = false;

	@Override
	public boolean performItemClick(View view, int position, long id) {
		if (isConsume){
			isConsume = false;
			return  isConsume;
		}
		return super.performItemClick(view, position, id);
	}

}
