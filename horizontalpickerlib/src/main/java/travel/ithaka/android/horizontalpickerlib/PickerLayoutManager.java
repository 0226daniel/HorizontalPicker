package travel.ithaka.android.horizontalpickerlib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by adityagohad on 06/06/17.
 */

public class PickerLayoutManager extends LinearLayoutManager {

	private onSelectChangeListener onSelectChangeListener;
	private OnScrollStopListener onScrollStopListener;

	private View selectedView;

	public PickerLayoutManager(Context context, int orientation, boolean reverseLayout) {
		super(context, orientation, reverseLayout);
	}

	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		super.onLayoutChildren(recycler, state);
		scrollChange();
	}

	@Override
	public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
		int orientation = getOrientation();
		if (orientation == HORIZONTAL) {
			int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
			scrollChange();
			return scrolled;
		} else return 0;
	}

	private void scrollChange() {
		View centerView = centerView();

		if (selectedView != centerView) {
			selectedView = centerView;
			onSelectedChange(selectedView);
		}
	}

	@Override
	public void onScrollStateChanged(int state) {
		super.onScrollStateChanged(state);

		if (state == 0) {
			onScrollStop(centerView());
		}
	}

	public View centerView() {
		Log.e("com.kimjisub.log", getChildCount() + "");

		float mid = getWidth() / 2.0f;

		View selected = null;
		float min = 1000;
		for (int i = 0; i < getChildCount(); i++) {

			View child = getChildAt(i);
			float centerValue = Math.abs((getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f - mid);
			if (min > centerValue) {
				min = centerValue;
				selected = child;
			}
		}

		return selected;
	}

	private void onSelectedChange(View position) {
		if (onSelectChangeListener != null)
			onSelectChangeListener.onSelectedChange(position);
	}

	public void setOnSelectChangeListener(onSelectChangeListener onSelectChangeListener) {
		this.onSelectChangeListener = onSelectChangeListener;
	}

	public interface onSelectChangeListener {
		void onSelectedChange(View view);
	}

	private void onScrollStop(View position) {
		if (onScrollStopListener != null)
			onScrollStopListener.onScrollStop(position);
	}

	public void setOnScrollStopListener(OnScrollStopListener onScrollStopListener) {
		this.onScrollStopListener = onScrollStopListener;
	}

	public interface OnScrollStopListener {
		void onScrollStop(View view);
	}
}
