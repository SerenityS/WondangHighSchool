package toast.library.meal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import net.htmlparser.jericho.*;

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
						.equals("�����")) {
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
						.equals("������(kcal)    ")) {
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
						.equals("������(kcal)    ")) {
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