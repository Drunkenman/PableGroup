package hankin.pablegroup.pable.interf;

public interface IPable {
	/**
	 * 是否能下拉
	 * */
	boolean canPullDown();
	/**
	 * 是否能上拉
	 * */
	boolean canPullUp();
	/**
	 * 是否消费此次触碰事件
	 * */
	void consumeEvent(boolean isConsume);
}
