import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkFinder {
	ArrayList<String> linkList = new ArrayList<String>();
	Visitor visit;

	public LinkFinder(Visitor visit) {
		this.visit = visit;
	}

	public void processPage(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String pattern = "<\\s*[Aa]\\s+[Hh][Rr][Ee][Ff]\\s*=\\s*\"(?<Group1>[^\"]+)\"\\s*>*.*";
		Pattern p = Pattern.compile(pattern);

		try {
			while (reader.ready()) {
				String line = reader.readLine();
				visit.getLine(line);
				Matcher m = p.matcher(line);
				if (m.find()) {
					linkList.add(m.group("Group1"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Iterator<String> getLinks() {
		return linkList.iterator();
	}
}
