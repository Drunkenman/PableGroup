package hankin.pablegroup.pable.foot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hankin.pablegroup.R;
import hankin.pablegroup.pable.interf.ILoadMore;

/**
 * Created by Hankin on 2016/4/1.
 * @email hankin.huan@gmail.com
 */
public class LoadFView extends RelativeLayout implements ILoadMore {

    private Context context;
    private View mContentView;
    private TextView mLoadTv;
    private ImageView mLoadIv;
    private Animation mLoadingAnim;

    public LoadFView(Context context) {
        super(context);
        initView(context);
    }
    public LoadFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public LoadFView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.context = context;
        mContentView = LayoutInflater.from(context).inflate(R.layout.footer_load, null);
        addView(mContentView);
        mLoadTv = (TextView) mContentView.findViewById(R.id.tv_footer_load_more);
        mLoadIv = (ImageView) mContentView.findViewById(R.id.iv_footer_load_load);
        mLoadingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_rotate);
        //匀速
        LinearInterpolator lir = new LinearInterpolator();
        mLoadingAnim.setInterpolator(lir);
    }

    @Override
    public void onLoadMoreInit() {
        mLoadTv.setVisibility(View.VISIBLE);
        mLoadTv.setText(context.getString(R.string.chakangengduo));
        mLoadIv.clearAnimation();
        mLoadIv.setVisibility(View.GONE);
    }
    @Override
    public void onReleaseToLoad() {
        mLoadTv.setVisibility(View.VISIBLE);
        mLoadIv.clearAnimation();
        mLoadIv.setVisibility(View.GONE);
        mLoadTv.setText(context.getString(R.string.shifangjiazai));
    }
    @Override
    public void onLoading() {
        mLoadTv.setVisibility(View.GONE);
        mLoadIv.setVisibility(View.VISIBLE);
        mLoadIv.startAnimation(mLoadingAnim);
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
