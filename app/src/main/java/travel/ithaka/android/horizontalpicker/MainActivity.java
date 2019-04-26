package travel.ithaka.android.horizontalpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

public class MainActivity extends AppCompatActivity {
	RecyclerView rv;
	PickerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rv = findViewById(R.id.rv);

		PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);

		adapter = new PickerAdapter(this, getData(100), rv);
		SnapHelper snapHelper = new LinearSnapHelper();
		snapHelper.attachToRecyclerView(rv);
		rv.setLayoutManager(pickerLayoutManager);
		rv.setAdapter(adapter);

		pickerLayoutManager.setOnSelectChangeListener(position ->
				Toast.makeText(MainActivity.this, "Selected value : " + position, Toast.LENGTH_SHORT).show()
		);
	}

	public List<String> getData(int count) {
		List<String> data = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			data.add(String.valueOf(i));
		}
		return data;
	}
}
