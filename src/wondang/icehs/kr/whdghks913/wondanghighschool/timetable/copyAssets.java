package wondang.icehs.kr.whdghks913.wondanghighschool.timetable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

public class copyAssets {

	private void assetsFileCopy(Context mContext, String mFilePath,
			String mFileName, int version) {
		File dbFile = new File(mFilePath + mFileName);
		SharedPreferences mPref = PreferenceManager
				.getDefaultSharedPreferences(mContext);

		if (!dbFile.exists()
				|| mPref.getInt(mFileName + "_dbVersion", 0) != version) {
			// 파일이 없거나 버전이 높아지면
			mPref.edit().putInt(mFileName + "_dbVersion", version).commit();

			File mFolder = new File(mFilePath);
			if (!mFolder.exists())
				mFolder.mkdirs();

			AssetManager mAssetM = mContext.getResources().getAssets();
			dbFile.delete();
			InputStream mInput = null;
			FileOutputStream mOutput = null;
			long filesize = 0;

			try {
				mInput = mAssetM.open(mFileName, AssetManager.ACCESS_BUFFER);
				filesize = mInput.available();

				if (dbFile.length() <= 0) {
					byte[] tmpbyte = new byte[(int) filesize];
					mInput.read(tmpbyte);
					dbFile.createNewFile();
					mOutput = new FileOutputStream(dbFile);
					mOutput.write(tmpbyte);
				}

			} catch (IOException e) {
				// error
			} finally {
				try {
					mInput.close();
					mOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
