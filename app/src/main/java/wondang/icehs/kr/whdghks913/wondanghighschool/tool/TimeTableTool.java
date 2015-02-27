package wondang.icehs.kr.whdghks913.wondanghighschool.tool;

import android.os.Environment;

/**
 * Created by 종환 on 2015-02-17.
 */
public class TimeTableTool {
    public static final String TimeTableDBName = "WondangTimeTable.db";
    public static final String tableName = "WondangTimeTable";

    public final static String mFilePath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/WondangHS/";
    public final static int dbVersion = 3;

    public final static String[] mDisplayName = {"월요일", "화요일", "수요일", "목요일", "금요일"};
}
