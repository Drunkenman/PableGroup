package hankin.pablegroup.pable;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import hankin.pablegroup.pable.interf.ILoadMore;
import hankin.pablegroup.pable.interf.IPable;
import hankin.pablegroup.pable.interf.IRefresh;

/**
 * 自定义的布局，用来管理三个子控件，其中一个是下拉头，一个是包含内容的pullableView（可以是实现IPable接口的的任何View）， 还有一个上拉头
 * 如果头部样式需要修改，重写一个View继承IRefresh、底部样式继承ILoadMore
 * 使用时根据
 * {@link #allowPullDown}
 * {@link #allowPullUp}
 * {@link #needToRefresh}
 * {@link #needToLoadMore}
 * 四个方法配合实际页面效果编写
 * create by Hankin on 2016/4/1.
 * @email hankin.huan@gmail.com
 */
public class PableGroup extends FrameLayout {
	public static final int INIT = 0;
	public static final int RELEASE_TO_REFRESH = 1;
	public static final int REFRESHING = 2;
	public static final int RELEASE_TO_LOAD = 3;
	public static final int LOADING = 4;
	public static final int DONE = 5;
	private int state = INIT;
	private OnRefreshListener mListener;
	public static final int SUCCEED = 0;
	public static final int FAIL = 1;
	private float lastY;

	public float pullDownY = 0;
	private float pullUpY = 0;

	private float refreshDist = 200;
	private float loadmoreDist = 200;

	private LayoutTimer timer;
	public float MOVE_SPEED = 8;
	private boolean isLayout = false;
	private boolean isTouch = false;
	private float radio = 2;

	private View refreshView;

	private View loadmoreView;

	private View pullableView;
	private int mEvents;
	private boolean canPullDown = true;
	private boolean canPullUp = true;

	private boolean isAllowPullDown = true;
	private boolean isAllowPullUp = true;
	private boolean isNeedToRefresh = true;
	private boolean isNeedToLoadMore = true;

	private final int RECOVERRADIO = 3;

	private OnPullDownListener mOnPullDownListener;

	private LayoutHandler updateHandler;
	private Runnable doneR = new Runnable() {
		@Override
		public void run() {
			changeState(DONE);
			hide();
		}
	};
	private class LayoutHandler extends Handler {
		WeakReference<Context> weakReference ;
		public LayoutHandler(Context context){
			weakReference = new WeakReference<>(context);
		}
		@Override
		public void handleMessage(Message msg) {
			MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2
					/ getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
			if (!isTouch) {
				if (state == REFRESHING && pullDownY <= refreshDist&&isNeedToRefresh) {
					pullDownY = refreshDist;
					timer.cancel();
				} else if (state == LOADING && -pullUpY <= loadmoreDist&&isNeedToLoadMore) {
					pullUpY = -loadmoreDist;
					timer.cancel();
				}
				if ((state==REFRESHING&&!isNeedToRefresh)||(state==LOADING&&!isNeedToLoadMore))
					changeState(INIT);

			}
			if (pullDownY > 0)
				pullDownY -= MOVE_SPEED;
			else if (pullUpY < 0)
				pullUpY += MOVE_SPEED;
			if (pullDownY < 0) {
				pullDownY = 0;
				if (state != REFRESHING && state != LOADING)
					changeState(INIT);
				timer.cancel();
				requestLayout();
			}
			if (pullUpY > 0) {
				pullUpY = 0;
				if (state != REFRESHING && state != LOADING)
					changeState(INIT);
				timer.cancel();
				requestLayout();
			}
			requestLayout();
			if (pullDownY + Math.abs(pullUpY) == 0)
				timer.cancel();
		}
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		mListener = listener;
	}

	public PableGroup(Context context) {
		super(context);
		initView(context);
	}

	public PableGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PableGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		updateHandler = new LayoutHandler(context);
		timer = new LayoutTimer(updateHandler);
	}

	private void hide() {
		timer.schedule(RECOVERRADIO);
	}

	public void refreshFinish(int refreshResult, int delay) {
		switch (refreshResult) {
			case SUCCEED:
				((IRefresh)refreshView).onRefreshSuccess();
				break;
			case FAIL:
			default:
				((IRefresh)refreshView).onRefreshFailed();
				break;
		}
		if (pullDownY > 0) {
			updateHandler.postDelayed(doneR, delay);
		} else {
			updateHandler.post(doneR);
		}
	}

	public void loadmoreFinish(int refreshResult, int delay) {
		switch (refreshResult) {
			case SUCCEED:
				((ILoadMore)loadmoreView).onLoadMoreSuccess();
				break;
			case FAIL:
			default:
				((ILoadMore)loadmoreView).onLoadMoreFailed();
				break;
		}
		if (pullUpY < 0) {
			updateHandler.postDelayed(doneR, delay);
		} else {
			updateHandler.post(doneR);
		}
	}

	private void changeState(int to) {
		state = to;
		switch (state) {
			case INIT:
				((IRefresh)refreshView).onRefreshInit();
				((ILoadMore)loadmoreView).onLoadMoreInit();
				break;
			case RELEASE_TO_REFRESH:
				((IRefresh)refreshView).onReleaseToRefresh();
				break;
			case REFRESHING:
				((IRefresh)refreshView).onRefreshing();
				break;
			case RELEASE_TO_LOAD:
				((ILoadMore)loadmoreView).onReleaseToLoad();
				break;
			case LOADING:
				((ILoadMore)loadmoreView).onLoading();
				break;
			case DONE:
				((IRefresh)refreshView).onRefreshDone();
				((ILoadMore)loadmoreView).onLoadMoreDone();
				break;
		}
	}

	private void releasePull() {
		canPullDown = true;
		canPullUp = true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				lastY = ev.getY();
				timer.cancel();
				mEvents = 0;
				releasePull();
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
			case MotionEvent.ACTION_POINTER_UP:
				mEvents = -1;
				break;
			case MotionEvent.ACTION_MOVE:
				if (mEvents == 0) {
					if ((pullDownY > 0 || (((IPable) pullableView).canPullDown() && canPullDown && state != LOADING))&&isAllowPullDown) {
						pullDownY = pullDownY + (ev.getY() - lastY) / radio;
						if (pullDownY < 0) {
							pullDownY = 0;
							canPullDown = false;
							canPullUp = true;
						}
						if (pullDownY > getMeasuredHeight())
							pullDownY = getMeasuredHeight();
						if (state == REFRESHING) {
							isTouch = true;
						}
					} else if ((pullUpY < 0 || (((IPable) pullableView).canPullUp() && canPullUp && state != REFRESHING)) && isAllowPullUp) {
						pullUpY = pullUpY + (ev.getY() - lastY) / radio;
						if (pullUpY > 0) {
							pullUpY = 0;
							canPullDown = true;
							canPullUp = false;
						}
						if (pullUpY < -getMeasuredHeight())
							pullUpY = -getMeasuredHeight();
						if (state == LOADING) {
							isTouch = true;
						}
					} else
						releasePull();
				} else
					mEvents = 0;
				lastY = ev.getY();
				radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
						* (pullDownY + Math.abs(pullUpY))));
				if (pullDownY > 0 || pullUpY < 0)
					requestLayout();
				if (pullDownY > 0) {
					if (pullDownY <= refreshDist
							&& (state == RELEASE_TO_REFRESH || state == DONE)) {
						changeState(INIT);
					}
					if (pullDownY >= refreshDist && state == INIT) {
						changeState(RELEASE_TO_REFRESH);
					}
				} else if (pullUpY < 0) {
					if (-pullUpY <= loadmoreDist
							&& (state == RELEASE_TO_LOAD || state == DONE)) {
						changeState(INIT);
					}
					if (-pullUpY >= loadmoreDist && state == INIT) {
						changeState(RELEASE_TO_LOAD);
					}

				}
				if ((pullDownY + Math.abs(pullUpY)) > 0) {
//					ev.setAction(MotionEvent.ACTION_DOWN);
					ev.setAction(MotionEvent.ACTION_CANCEL);
					((IPable)pullableView).consumeEvent(true);
				} else {
					((IPable)pullableView).consumeEvent(false);
				}

				if (mOnPullDownListener!=null) mOnPullDownListener.onPullDown(pullDownY);

				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (pullDownY > refreshDist || -pullUpY > loadmoreDist) {
					isTouch = false;
				}
				if (state == RELEASE_TO_REFRESH) {
					changeState(REFRESHING);
					if (mListener != null)
						mListener.onRefresh(this);
				} else if (state == RELEASE_TO_LOAD) {
					changeState(LOADING);
					if (mListener != null)
						mListener.onLoadMore(this);
				}
				hide();
			default:
				break;
		}
		super.dispatchTouchEvent(ev);
		return true;
	}

	private class AutoRefreshAndLoadTask extends AsyncTask<Integer, Float, String> {

		@Override
		protected String doInBackground(Integer... params) {
			while (pullDownY < 4 / 3 * refreshDist) {
				pullDownY += MOVE_SPEED;
				publishProgress(pullDownY);
				try {
					Thread.sleep(params[0]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			changeState(REFRESHING);
			if (mListener != null)
				mListener.onRefresh(PableGroup.this);
			hide();
		}

		@Override
		protected void onProgressUpdate(Float... values) {
			if (pullDownY > refreshDist)
				changeState(RELEASE_TO_REFRESH);
			requestLayout();
		}

	}

	public void autoRefresh() {
		if (isAllowPullDown) {
			AutoRefreshAndLoadTask task = new AutoRefreshAndLoadTask();
			task.execute(5);
		}
	}

	public void autoLoad() {
		if (isAllowPullUp) {
			pullUpY = -loadmoreDist;
			requestLayout();
			changeState(LOADING);
			if (mListener != null)
				mListener.onLoadMore(this);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (!isLayout) {
			refreshView = getChildAt(0);
			if (!(refreshView instanceof IRefresh))
				throw new IllegalArgumentException("the header view must implement hankin.pablegroup.pable.interf.IRefresh.");
			pullableView = getChildAt(1);
			loadmoreView = getChildAt(2);
			if (!(loadmoreView instanceof ILoadMore))
				throw new IllegalArgumentException("the footer view must implement hankin.pablegroup.pable.interf.ILoadMore.");
			isLayout = true;
			refreshDist = ((ViewGroup) refreshView).getChildAt(0)
					.getMeasuredHeight();
			loadmoreDist = ((ViewGroup) loadmoreView).getChildAt(0)
					.getMeasuredHeight();
		}
		refreshView.layout(0,
				(int) (pullDownY + pullUpY) - refreshView.getMeasuredHeight(),
				refreshView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
		pullableView.layout(0, (int) (pullDownY + pullUpY),
				pullableView.getMeasuredWidth(), (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight());
		loadmoreView.layout(0,
				(int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight(),
				loadmoreView.getMeasuredWidth(),
				(int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight()
						+ loadmoreView.getMeasuredHeight());
	}

	public void allowPullDown(boolean isAllowPullDown){
		this.isAllowPullDown = isAllowPullDown;
	}
	public void allowPullUp(boolean isAllowPullUp){
		this.isAllowPullUp = isAllowPullUp;
		if (loadmoreView!=null) loadmoreView.setVisibility(isAllowPullUp? View.VISIBLE: View.INVISIBLE);
	}
	public void needToRefresh(boolean isNeedToRefresh){
		this.isNeedToRefresh = isNeedToRefresh;
	}
	public void needToLoadMore(boolean isNeedToLoadMore){
		this.isNeedToLoadMore = isNeedToLoadMore;
	}

	private class LayoutTimer {
		private Handler handler;
		private Timer timer;
		private LayoutTask mTask;

		public LayoutTimer(Handler handler) {
			this.handler = handler;
			timer = new Timer();
		}

		public void schedule(long period) {
			cancel();
			mTask = new LayoutTask(handler);
			timer.schedule(mTask, 0, period);
		}

		public void cancel() {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
		}

		private class LayoutTask extends TimerTask {
			private Handler handler;

			public LayoutTask(Handler handler)
			{
				this.handler = handler;
			}

			@Override
			public void run()
			{
				handler.obtainMessage().sendToTarget();
			}

		}
	}

	public interface OnRefreshListener {
		void onRefresh(PableGroup pableGroup);
		void onLoadMore(PableGroup pableGroup);
	}

	public void setOnPullDownListener(OnPullDownListener onPullDownListener){
		this.mOnPullDownListener = onPullDownListener;
	}

	public interface OnPullDownListener{
		void onPullDown(float pullDown);
	}

}
