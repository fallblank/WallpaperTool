package fallblank525;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FetchImage {
	
	private static final String BING_ENDPOINT = "http://s.cn.bing.net";
	
	
	public byte[] downloadBytesByUrl(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
				return null;

			int byteRead = 0;
			byte[] buffer = new byte[1024];
			while ((byteRead = (in.read(buffer))) > 0) {
				out.write(buffer, 0, byteRead);
			}
			out.close();
			return out.toByteArray();
		} finally {
			connection.disconnect();
		}
	}

	public String downloadStringByUrl(String url) throws IOException {
		return new String(downloadBytesByUrl(url));
	}

	public String getImageURL(byte[] xmlStr) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputStream in = new ByteArrayInputStream(xmlStr);
		Document doc = db.parse(in);
		NodeList root = doc.getElementsByTagName("url");
		Element urlElement = (Element) root.item(0);
		String URLStr = urlElement.getChildNodes().item(0).getNodeValue();
		return BING_ENDPOINT+URLStr;

	}

	public boolean saveImage(File DestFile, byte[] imageBytes) throws FileNotFoundException {
		OutputStream out = new FileOutputStream(DestFile);
		try {
			out.write(imageBytes, 0, imageBytes.length);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
