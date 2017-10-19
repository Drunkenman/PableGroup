package hankin.pablegroup.pable.head;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import hankin.pablegroup.R;
import hankin.pablegroup.pable.interf.IRefresh;

/**
 * Created by Hankin on 2016/4/1.
 * @email hankin.huan@gmail.com
 */
public class EmptyHView extends RelativeLayout implements IRefresh {
    public EmptyHView(Context context) {
        super(context);
        initView(context);
    }
    public EmptyHView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public EmptyHView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = new View(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                context.getResources().getDimensionPixelSize(R.dimen.dp50));
        view.setLayoutParams(lp);
        addView(view);
    }

    @Override
    public void onRefreshInit() {

    }
    @Override
    public void onReleaseToRefresh() {

    }
    @Override
    public void onRefreshing() {

    }
    @Override
    public void onRefreshSuccess() {

    }
    @Override
    public void onRefreshFailed() {

    }
    @Override
    public void onRefreshDone() {

    }
}
