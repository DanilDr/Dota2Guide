package ru.russianbravedev.dotamania;

import java.util.List;
import ru.russianbravedev.dotamania.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapterCrepsParams extends BaseAdapter {
	private Context context;
	private List<String> gridcontent;

	public GridAdapterCrepsParams(Context context, List<String> gridcontent){
		this.context = context;
		this.gridcontent = gridcontent;
	}
	
	@Override
	public int getCount() {
		return gridcontent.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String curElement = gridcontent.get(position);
		TextView elementLay;
		if (convertView == null) {
			elementLay = new TextView(context);
		} else {
			elementLay = (TextView)convertView;
		}
		
		Resources res = context.getResources();
		
		elementLay.setText(curElement);
		elementLay.setTextColor(context.getResources().getColor(R.color.mainbuttontextcolor));
		elementLay.setGravity(Gravity.CENTER_VERTICAL);
		elementLay.setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(R.dimen.accordfontsize));
		elementLay.setHeight(res.getDimensionPixelSize(R.dimen.gridtextcellheight));
		
		return (View)elementLay;
	}

}
