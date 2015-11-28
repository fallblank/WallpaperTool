package fallblank525;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Test {

	public static void main(String[] args) {
		FetchImage fetcher = new FetchImage();
		try {
			byte[] XMLBytes = fetcher.downloadBytesByUrl("http://cn.bing.com/HPImageArchive.aspx?format=xml&idx=0&n=1");
			String urlStr = fetcher.getImageURL(XMLBytes);
			String dateStr = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
			File file = new File("./BackgroundImage/", dateStr + ".png");
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			byte[] imageBytes = fetcher.downloadBytesByUrl(urlStr);
			fetcher.saveImage(file, imageBytes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

}
