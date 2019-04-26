package travel.ithaka.android.horizontalpickerlib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by adityagohad on 06/06/17.
 */

public class PickerLayoutManager extends LinearLayoutManager {

	private onSelectChangeListener onSelectChangeListener;

	private int selected = 0;

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
		int position = checkPosition();

		if (selected != position) {
			selected = position;
			onSelectedChange(selected);
		}
	}

	@Override
	public void onScrollStateChanged(int state) {
		super.onScrollStateChanged(state);
	}

	public int checkPosition() {
		int selected = 0;
		float lastHeight = 0f;
		for (int i = 0; i < getChildCount(); i++) {
			if (lastHeight < getChildAt(i).getScaleY()) {
				lastHeight = getChildAt(i).getScaleY();
				selected = i;
			}
		}

		return selected;
	}

	private void onSelectedChange(int position) {
		if (onSelectChangeListener != null)
			onSelectChangeListener.onSelectedChange(position);
	}

	public void setOnSelectChangeListener(onSelectChangeListener onSelectChangeListener) {
		this.onSelectChangeListener = onSelectChangeListener;
	}

	public interface onSelectChangeListener {
		void onSelectedChange(int position);
	}
}
