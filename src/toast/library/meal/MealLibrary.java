package toast.library.meal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import android.util.Log;

public class MealLibrary {
	static Source source;

	public static String[] getDate(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode, String schMmealScCode) {
		String[] date = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode;
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

	public static String[] getMonthMeal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode,
			String schMmealScCode, String schYm) {
		String[] content = null;
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md00_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode
				+ "&schYm=" + schYm;

		Log.d("url", url);
		Log.d("getMonthMeal", "Source 전");

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
					content = new String[tr.size() * 7];

					for (int a = 0; a < tr.size(); a++) {
						List<?> title = ((Element) tr.get(a))
								.getAllElements("td");

						// StringTokenizer st = new StringTokenizer(str," \n");
						// for(int i=0;st.hasMoreTokens();i++) {

						for (int iiii = 0; iiii < title.size(); iiii++) {
							String Meal = ((Element) title.get(iiii))
									.getContent().toString();

							if (isMealCheck(Meal)) {
//								String lunch = "[중식]";
//								String night = "[석식]";

/*								for (int day = 1; day < 7; day++) {
									// Meal = Meal.replace("<br />", "\n");
//									String dayString = Integer.toString(day);

//									if (Meal.matches(".*" + night + ".*")) {
//										// 석식이 있으면
//										int start = Meal.indexOf(dayString
//												+ "<br />[중식]<br />");
//										int end = Meal.indexOf(dayString
//												+ "<br />[석식]<br />");
//										Meal = Meal.substring(start, end);
//									} else {
//										// 석식이 없으면
//										int start = Meal.indexOf(dayString
//												+ "[중식]");
//										Meal = Meal.substring(start);
//									}
//									Log.e("Meal", Meal);
								}*/
								content[iiii] = Meal;
								content[iiii] = content[iiii].replace(
										"<br />", "\n");
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

	public static String[] getKcal(String CountryCode, String schulCode,
			String schulCrseScCode, String schulKndScCode, String schMmealScCode) {
		String[] content = new String[7];
		String url = "http://hes." + CountryCode
				+ "/sts_sci_md01_001.do?schulCode=" + schulCode
				+ "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode="
				+ schulKndScCode + "&schMmealScCode=" + schMmealScCode;
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