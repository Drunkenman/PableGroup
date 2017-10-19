package hankin.pablegroup.pable.head;

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
import hankin.pablegroup.pable.interf.IRefresh;

/**
 *
 * Created by Hankin on 2016/4/19.
 * @email hankin.huan@gmail.com
 */
public class ArrowHView extends RelativeLayout implements IRefresh {

    private Context context;
    private View mContainerView;
    private ImageView mArrowIv;
    private ImageView mRefreshingIv;
    private ImageView mSuccessIv;
    private TextView mRefreshTv;
    private Animation mArrowAnim;
    private Animation mRefreshingAnim;

    public ArrowHView(Context context) {
        this(context, null);
    }
    public ArrowHView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public ArrowHView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context){
        this.context = context;
        mContainerView = LayoutInflater.from(context).inflate(R.layout.header_arrow, null);
        addView(mContainerView);
        mArrowIv = (ImageView) mContainerView.findViewById(R.id.iv_header_arrow_arrow);
        mRefreshingIv = (ImageView) mContainerView.findViewById(R.id.iv_header_arrow_refreshing);
        mSuccessIv = (ImageView) mContainerView.findViewById(R.id.iv_header_arrow_success);
        mRefreshTv = (TextView) mContainerView.findViewById(R.id.tv_header_arrow_refresh);

        mArrowAnim = AnimationUtils.loadAnimation(context, R.anim.reverse_anim);
        mRefreshingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_rotate);
        LinearInterpolator lir = new LinearInterpolator();
        mArrowAnim.setInterpolator(lir);
        mRefreshingAnim.setInterpolator(lir);
    }

    @Override
    public void onRefreshInit() {
        mArrowIv.clearAnimation();
        mArrowIv.setVisibility(View.VISIBLE);
        mRefreshingIv.clearAnimation();
        mRefreshingIv.setVisibility(View.GONE);
        mSuccessIv.setVisibility(View.GONE);
        mRefreshTv.setText(context.getString(R.string.xialashuaxin));
    }
    @Override
    public void onReleaseToRefresh() {
        mArrowIv.setVisibility(View.VISIBLE);
        mArrowIv.startAnimation(mArrowAnim);
        mRefreshingIv.clearAnimation();
        mRefreshingIv.setVisibility(View.GONE);
        mSuccessIv.setVisibility(View.GONE);
        mRefreshTv.setText(context.getString(R.string.shifangshuaxin));
    }
    @Override
    public void onRefreshing() {
        mArrowIv.clearAnimation();
        mArrowIv.setVisibility(View.GONE);
        mRefreshingIv.setVisibility(View.VISIBLE);
        mRefreshingIv.startAnimation(mRefreshingAnim);
        mSuccessIv.setVisibility(View.GONE);
        mRefreshTv.setText(context.getString(R.string.shuaxinzhong));
    }
    @Override
    public void onRefreshSuccess() {
        mArrowIv.clearAnimation();
        mArrowIv.setVisibility(View.GONE);
        mRefreshingIv.clearAnimation();
        mRefreshingIv.setVisibility(View.GONE);
        mSuccessIv.setVisibility(View.VISIBLE);
        mRefreshTv.setText(context.getString(R.string.shuaxinchenggong));
    }
    @Override
    public void onRefreshFailed() {
        mArrowIv.clearAnimation();
        mArrowIv.setVisibility(View.GONE);
        mRefreshingIv.clearAnimation();
        mRefreshingIv.setVisibility(View.GONE);
        mSuccessIv.setVisibility(View.GONE);
        mRefreshTv.setText(context.getString(R.string.shuaxinshibai));
    }
    @Override
    public void onRefreshDone() {
    }
}
