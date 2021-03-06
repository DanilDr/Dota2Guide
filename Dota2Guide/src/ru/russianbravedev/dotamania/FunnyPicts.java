package ru.russianbravedev.dotamania;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.russianbravedev.dotamania.R;
import com.danildr.androidcomponents.LoadingJSONfromURL;
import com.danildr.cacheimageview.CacheImageView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FunnyPicts extends FullScreenActivity {
	private JSONObject fpJSON;
	private String status;
	private View funnyPictLayout;
	private RelativeLayout funnyPictsSwitcher;
    private float lastX;
    private JSONArray images = null;
	private int curindex = 0;
	private int lengthlist = 0;
	private CacheImageView funnypictpict;
//	private TextView funnypictdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_funny_picts);
		LayoutInflater factory = LayoutInflater.from(this);
		String fpURL = getString(R.string.servicehost) + "/funnypicts";
		funnyPictsSwitcher = (RelativeLayout) findViewById(R.id.funnyPictsSwitcher);
		try {
			fpJSON = new LoadingJSONfromURL().execute(fpURL).get(20, TimeUnit.SECONDS);
			if (fpJSON != null) {
				status = fpJSON.getString("result");
			}
		} catch (InterruptedException | ExecutionException | TimeoutException | JSONException e) {
			e.printStackTrace();
		}
		if (status != null && status.equals("success")) {
			funnyPictLayout = factory.inflate(R.layout.funnypictview, null);
			funnypictpict = (CacheImageView) funnyPictLayout.findViewById(R.id.funnypictpict);
//			funnypictdate = (TextView) funnyPictLayout.findViewById(R.id.funnypictdate);
			funnyPictsSwitcher.addView(funnyPictLayout);
			try {
				images = fpJSON.getJSONArray("images");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			lengthlist = images.length() - 1;
			setImageInfo(curindex);
		} else {
			Toast.makeText(this, getString(R.string.servicenotavailable), Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void shownext() {
		if (curindex < lengthlist - 1) {
			curindex += 1;
			setImageInfo(curindex);
		}
	}
	
	protected void showprevious() {
		if (curindex > 0) {
			curindex -= 1;
			setImageInfo(curindex);
		}
	}
	
	private String[] getImageURLDate(int index) {
//		funnyPictsSwitcher.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		String[] returnList = null;
		if (images != null) {
			JSONObject imageObject;
			try {
				imageObject = images.getJSONObject(index);
				String urlimage = imageObject.getString("image");
				String dateimage = imageObject.getString("date");
				returnList = new String[]{urlimage, dateimage};
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
//		funnyPictsSwitcher.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		return returnList;
	}
	
	private void setImageInfo(int index) {
		String[] imageInfo = getImageURLDate(index);
		funnypictpict.setImageUrl(imageInfo[0]);
	}
	
	public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
        	case MotionEvent.ACTION_DOWN: {
     			lastX = touchevent.getX();
     			break;
            }
         	case MotionEvent.ACTION_UP: {
         		float currentX = touchevent.getX();
         		if (lastX < currentX) {
         			showprevious();
			    }
         		if (lastX > currentX) {
         			shownext();
			    }
         		break;
			}
        }
        return false;
     }
}
