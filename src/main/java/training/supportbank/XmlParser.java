package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger();

    public XmlParser(String filename) {
        try {
            List<String[]> newTable = new ArrayList<>();

            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            NodeList nList = doc.getElementsByTagName("SupportTransaction");

            for (int i = 0; i < nList.getLength(); i++) {
                Node currentTransaction = nList.item(i);
                Element currentTransactionElement = (Element) currentTransaction;
                String narrative = currentTransactionElement.getElementsByTagName("Description").item(0).getTextContent();
                String value = currentTransactionElement.getElementsByTagName("Value").item(0).getTextContent();

                Node parties = currentTransactionElement.getElementsByTagName("Parties").item(0);
                Element partiesElement = (Element) parties;
                String to = partiesElement.getElementsByTagName("To").item(0).getTextContent();
                String from = partiesElement.getElementsByTagName("From").item(0).getTextContent();

                System.out.println(narrative);
                System.out.println(value);
                System.out.println(to);
                System.out.println(from);

            }
            setTable(newTable);
            LOGGER.info("Parsed the file successfully!");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error("File I/O error - file \"" + filename + "\" might not exist");
        }
    }
}
