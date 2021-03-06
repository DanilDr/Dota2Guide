package ru.russianbravedev.dotamania;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import ru.russianbravedev.dotamania.R;
import com.danildr.cacheimageview.CacheImageView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public class ShowWallpaper extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_wallpaper);
		final Context context = getApplicationContext();
		final String imageURL = getIntent().getStringExtra("imageURL");
		final CacheImageView wpMainImage = (CacheImageView) findViewById(R.id.wpMainImage);
		if (imageURL != null) {
			wpMainImage.setImageUrl(imageURL);
			RomulTextView attachTo = (RomulTextView) findViewById(R.id.attachTo);
			RomulTextView saveImage = (RomulTextView) findViewById(R.id.saveImage);
			final File fileImage = new File(wpMainImage.getAbsolutePath());
			final Uri uriImage = Uri.fromFile(fileImage);
			
			attachTo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
				    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				    intent.setDataAndType(uriImage, "image/*");
				    ShowWallpaper.this.startActivity(Intent.createChooser(intent, "Save as"));					
				}
			});
			saveImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					File pictFolder = new File(new File(Environment.getExternalStorageDirectory().toString()), Environment.DIRECTORY_PICTURES);
					String extension = MimeTypeMap.getFileExtensionFromUrl(imageURL); // расширение файла
					File outPicture = new File(pictFolder, wpMainImage.imageKey + "." + extension);
					if (fileImage.exists()) {
						FileChannel src = null;
						FileChannel dst = null;
			            try {
							src = new FileInputStream(fileImage).getChannel();
				            dst = new FileOutputStream(outPicture).getChannel();
				            dst.transferFrom(src, 0, src.size());
				            src.close();
				            dst.close();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							Toast.makeText(context, getString(R.string.galerysuccess), Toast.LENGTH_SHORT).show();
							sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + outPicture.toString())));
							try {
								src.close();
								dst.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
				}
			});
		}
	}
	
	public static String getMimeType(String url)
	{
	    String type = null;
	    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
	    if (extension != null) {
	        MimeTypeMap mime = MimeTypeMap.getSingleton();
	        type = mime.getMimeTypeFromExtension(extension);
	    }
	    return type;
	}
}
