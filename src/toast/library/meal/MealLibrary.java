package toast.library.meal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class MealLibrary {
	private static Source source;

	public static String[] getDate(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode, String schMmealScCode) {
		String[] date = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode;

		return getDateSub(date, url);
	}

	public static String[] getDate(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String schYmd) {
		String[] date = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + schYmd;

		return getDateSub(date, url);
	}

	public static String[] getDate(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String year, String month, String day) {
		String[] date = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + year + "." + month + "." + day;

		return getDateSub(date, url);
	}

	private static String[] getDateSub(String[] date, String url) {
		try {
			source = new Source(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		source.fullSequentialParse();
		List<?> table = source.getAllElements("table");
		for (int i = 0; i < table.size(); i++) {
			if (((Element) table.get(i)).getAttributeValue("class").equals(
					"tableType6")) {
				List<?> tr = ((Element) table.get(i)).getAllElements("tr");
				List<?> th = ((Element) tr.get(0)).getAllElements("th");
				date[0] = ((Element) th.get(1)).getContent().toString();
				date[1] = ((Element) th.get(2)).getContent().toString();
				date[2] = ((Element) th.get(3)).getContent().toString();
				date[3] = ((Element) th.get(4)).getContent().toString();
				date[4] = ((Element) th.get(5)).getContent().toString();
				date[5] = ((Element) th.get(6)).getContent().toString();
				date[6] = ((Element) th.get(7)).getContent().toString();
				break;
			}

		}

		return date;
	}

	public static String[] getMeal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode, String schMmealScCode) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode;

		return getMealSub(content, url);
	}

	/**
	 * 2014.03.16
	 */
	public static String[] getMeal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String schYmd) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + schYmd;

		return getMealSub(content, url);
	}

	public static String[] getMeal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String year, String month, String day) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + year + "." + month + "." + day;

		return getMealSub(content, url);
	}

	private static String[] getMealSub(String[] content, String url) {
		try {
			source = new Source(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		source.fullSequentialParse();
		List<?> table = source.getAllElements("table");
		for (int i = 0; i < table.size(); i++) {
			if (((Element) table.get(i)).getAttributeValue("class").equals(
					"tableType6")) {
				List<?> tbody = ((Element) table.get(i))
						.getAllElements("tbody");
				List<?> tr = ((Element) tbody.get(0)).getAllElements("tr");
				List<?> title = ((Element) tr.get(2)).getAllElements("th");
				if (((Element) title.get(0)).getContent().toString()
						.equals("식재료")) {
					List<?> tdMeal = ((Element) tr.get(1)).getAllElements("td");

					content[0] = ((Element) tdMeal.get(0)).getContent()
							.toString();

					content[0] = content[0].replace("<br />", "\n");
					content[1] = ((Element) tdMeal.get(1)).getContent()
							.toString();

					content[1] = content[1].replace("<br />", "\n");
					content[2] = ((Element) tdMeal.get(2)).getContent()
							.toString();

					content[2] = content[2].replace("<br />", "\n");
					content[3] = ((Element) tdMeal.get(3)).getContent()
							.toString();

					content[3] = content[3].replace("<br />", "\n");
					content[4] = ((Element) tdMeal.get(4)).getContent()
							.toString();

					content[4] = content[4].replace("<br />", "\n");
					content[5] = ((Element) tdMeal.get(5)).getContent()
							.toString();

					content[5] = content[5].replace("<br />", "\n");
					content[6] = ((Element) tdMeal.get(6)).getContent()
							.toString();

					content[6] = content[6].replace("<br />", "\n");
					break;
				}

				content[0] = null;
				content[1] = null;
				content[2] = null;
				content[3] = null;
				content[4] = null;
				content[5] = null;
				content[6] = null;
			}
		}
		return content;
	}

	/**
	 * schYm : 2014.03
	 */
	public static String[] getMonthMeal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String schYm) {

		String[] content = null;
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md00_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYm=" + schYm;

		return getMonthMealSub(content, schMmealScCode, url, "1");
	}

	public static String[] getMonthMeal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String year, String month) {

		String[] content = null;
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md00_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYm=" + year + "." + month;

		return getMonthMealSub(content, schMmealScCode, url, month);
	}

	private static String[] getMonthMealSub(String[] content,
			String schMmealScCode, String url, String month) {
		int dayChecker = 0;

		try {
			source = new Source(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		source.fullSequentialParse();
		List<?> table = source.getAllElements("table");
		for (int i = 0; i < table.size(); i++) {

			String tableType = ((Element) table.get(i))
					.getAttributeValue("class");

			if ("tableType2".equals(tableType)) {

				List<?> tbody = ((Element) table.get(i))
						.getAllElements("tbody");

				for (int ii = 0; ii < tbody.size(); ii++) {
					List<?> tr = ((Element) tbody.get(ii)).getAllElements("tr");
					content = new String[getMonthDays(month)];

					for (int a = 0; a < tr.size(); a++) {
						List<?> title = ((Element) tr.get(a))
								.getAllElements("td");

						for (int iiii = 0; iiii < title.size(); iiii++) {
							String Meal = ((Element) title.get(iiii))
									.getContent().toString();
							// iiii = 날짜

							if (isMealCheck(Meal)) {
								Meal = Meal.replace("<br />", "\n").trim();

								int morning = Meal.indexOf("[조식]");
								int lunch = Meal.indexOf("[중식]");
								int night = Meal.indexOf("[석식]");

								// schMmealScCode == 1 : 아침만
								// schMmealScCode == 2 : 점심만
								// schMmealScCode == 3 : 저녁만

								if ("1".equals(schMmealScCode)) {
									if (morning != -1 && lunch != -1)
										Meal = Meal.substring(morning + 4,
												lunch);
									else if (morning != -1)
										Meal = Meal.substring(morning + 4);
									else if (morning == -1)
										Meal = null;

								} else if ("2".equals(schMmealScCode)) {
									if (lunch != -1 && night != -1)
										Meal = Meal.substring(lunch + 4, night);
									else if (lunch != -1)
										Meal = Meal.substring(lunch + 4);
									else if (lunch == -1)
										Meal = null;

								} else if ("3".equals(schMmealScCode)) {
									if (night != -1)
										Meal = Meal.substring(night + 4);
									else if (night == -1)
										Meal = null;
								}
								content[dayChecker++] = Meal;
							}
						}
					}
				}
				break;
			}
		}

		return content;
	}

	private static boolean isMealCheck(String meal) {
		if ("".equals(meal) || " ".equals(meal) || meal == null)
			return false;
		else
			return true;
	}

	private static int getMonthDays(String CalenderMonth) {
		switch (Integer.parseInt(CalenderMonth)) {
		case 1:
			return 31;
		case 2:
			return 29;
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		}
		return 31;
	}

	public static String[] getKcal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode, String schMmealScCode) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode;

		return getKcalSub(content, url);
	}

	public static String[] getKcal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String schYmd) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + schYmd;

		return getKcalSub(content, url);
	}

	public static String[] getKcal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String year, String month, String day) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + year + "." + month + "." + day;

		return getKcalSub(content, url);
	}

	private static String[] getKcalSub(String[] content, String url) {
		try {
			source = new Source(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		source.fullSequentialParse();
		List<?> table = source.getAllElements("table");
		for (int i = 0; i < table.size(); i++) {
			if (((Element) table.get(i)).getAttributeValue("class").equals(
					"tableType6")) {
				List<?> tbody = ((Element) table.get(i))
						.getAllElements("tbody");
				List<?> __tr = ((Element) tbody.get(0)).getAllElements("tr");
				List<?> __th = ((Element) __tr.get(16)).getAllElements("th");
				if (((Element) __th.get(0)).getContent().toString()
						.equals("에너지(kcal)    ")) {
					List<?> td = ((Element) __tr.get(16)).getAllElements("td");
					content[0] = ((Element) td.get(0)).getContent().toString();
					content[1] = ((Element) td.get(1)).getContent().toString();
					content[2] = ((Element) td.get(2)).getContent().toString();
					content[3] = ((Element) td.get(3)).getContent().toString();
					content[4] = ((Element) td.get(4)).getContent().toString();
					content[5] = ((Element) td.get(5)).getContent().toString();
					content[6] = ((Element) td.get(6)).getContent().toString();
					break;
				}

				content[0] = null;
				content[1] = null;
				content[2] = null;
				content[3] = null;
				content[4] = null;
				content[5] = null;
				content[6] = null;
				break;
			}
		}

		return content;
	}

	public static String[] getPeople(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode, String schMmealScCode) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode;

		return getPeopleSub(content, url);
	}

	public static String[] getPeople(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String schYmd) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + schYmd;

		return getPeopleSub(content, url);
	}

	public static String[] getPeople(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String year, String month, String day) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYmd=" + year + "." + month + "." + day;

		return getPeopleSub(content, url);
	}

	private static String[] getPeopleSub(String[] content, String url) {
		try {
			source = new Source(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		source.fullSequentialParse();
		List<?> table = source.getAllElements("table");
		for (int i = 0; i < table.size(); i++) {
			if (((Element) table.get(i)).getAttributeValue("class").equals(
					"tableType6")) {
				List<?> tbody = ((Element) table.get(i))
						.getAllElements("tbody");
				List<?> __tr = ((Element) tbody.get(0)).getAllElements("tr");
				List<?> __th = ((Element) __tr.get(16)).getAllElements("th");
				if (((Element) __th.get(0)).getContent().toString()
						.equals("에너지(kcal)    ")) {
					List<?> td = ((Element) __tr.get(16)).getAllElements("td");
					content[0] = ((Element) td.get(0)).getContent().toString();
					content[1] = ((Element) td.get(1)).getContent().toString();
					content[2] = ((Element) td.get(2)).getContent().toString();
					content[3] = ((Element) td.get(3)).getContent().toString();
					content[4] = ((Element) td.get(4)).getContent().toString();
					content[5] = ((Element) td.get(5)).getContent().toString();
					content[6] = ((Element) td.get(6)).getContent().toString();
					break;
				}

				content[0] = null;
				content[1] = null;
				content[2] = null;
				content[3] = null;
				content[4] = null;
				content[5] = null;
				content[6] = null;
				break;
			}
		}

		return content;
	}
}