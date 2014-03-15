package wondang.icehs.kr.whdghks913.wondanghighschool.schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class PreferenceData {

	public void copyDB(Context mContext, String packageName,
			String assetsFileName, boolean ifFileExistsDelete) {
		String PreferencePath = "/data/data/" + packageName + "/shared_prefs/";

		File mFilePath = new File(PreferencePath);
		if (!mFilePath.exists()) {
			mFilePath.mkdirs();
		}

		AssetManager mAssetManager = mContext.getAssets();
		File assetsFile = new File(PreferencePath + assetsFileName);

		if (ifFileExistsDelete)
			if (assetsFile.exists())
				assetsFile.delete();

		InputStream mInputStream = null;
		FileOutputStream mFileOutputStream = null;
		long filesize = 0;

		try {
			mInputStream = mAssetManager.open(assetsFileName,
					AssetManager.ACCESS_BUFFER);
			filesize = mInputStream.available();

			if (!assetsFile.exists()) {
				byte[] byteFile = new byte[(int) filesize];
				mInputStream.read(byteFile);
				mInputStream.close();
				assetsFile.createNewFile();
				mFileOutputStream = new FileOutputStream(assetsFile);
				mFileOutputStream.write(byteFile);
				mFileOutputStream.close();
			}
		} catch (IOException e) {

		}

	}
}
