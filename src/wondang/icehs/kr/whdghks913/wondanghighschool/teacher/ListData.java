package wondang.icehs.kr.whdghks913.wondanghighschool.teacher;

import java.text.Collator;
import java.util.Comparator;

public class ListData {
	/**
	 * ����Ʈ ������ ��� ���� ��ü ����
	 */
	public String mName;
	public String mRoom;
	public String mRoomNum;
	public String mSubject;

	/**
	 * ���ĺ� �̸����� ����
	 */
	public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
		private final Collator sCollator = Collator.getInstance();

		@Override
		public int compare(ListData mListDate_1, ListData mListDate_2) {
			return sCollator.compare(mListDate_1.mName, mListDate_2.mName);
		}
	};
}
