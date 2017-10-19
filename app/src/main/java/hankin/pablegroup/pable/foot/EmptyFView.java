package hankin.pablegroup.pable.foot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import hankin.pablegroup.R;
import hankin.pablegroup.pable.interf.ILoadMore;

/**
 *
 * Created by Hankin on 2016/4/25.
 * @email hankin.huan@gmail.com
 */
public class EmptyFView extends RelativeLayout implements ILoadMore {
    public EmptyFView(Context context) {
        this(context, null);
    }
    public EmptyFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public EmptyFView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void onLoadMoreInit() {
    }
    @Override
    public void onReleaseToLoad() {
    }
    @Override
    public void onLoading() {
    }
    @Override
    public void onLoadMoreSuccess() {
    }
    @Override
    public void onLoadMoreFailed() {
    }
    @Override
    public void onLoadMoreDone() {
    }
}
