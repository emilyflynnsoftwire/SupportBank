package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XmlParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger();

    public XmlParser(String filename) {
        try {
            List<Transaction> newTable = new ArrayList<>();

            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            NodeList nList = doc.getElementsByTagName("SupportTransaction");

            for (int i = 0; i < nList.getLength(); i++) {
                try {
                    Element curTranEl = (Element) nList.item(i);

                    String date = curTranEl.getAttribute("Date");
                    String narrative = curTranEl.getElementsByTagName("Description").item(0).getTextContent();

                    String value = curTranEl.getElementsByTagName("Value").item(0).getTextContent();
                    BigDecimal amount = new BigDecimal(value);

                    Element partiesEl = (Element) curTranEl.getElementsByTagName("Parties").item(0);
                    String to = partiesEl.getElementsByTagName("To").item(0).getTextContent();
                    String from = partiesEl.getElementsByTagName("From").item(0).getTextContent();

                    Transaction newTransaction = new Transaction(date, from, to, narrative, amount);
                    newTransaction.setup();
                    newTable.add(newTransaction);
                }
                catch (Exception e) {
                    if(i!=0){//probably title line
                        LOGGER.error("Error with node " + i);
                    }
                }
            }
            setTable(newTable);
            LOGGER.info("Parsed the file successfully!");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error("File I/O error - file \"" + filename + "\" might not exist");
        }
    }
}
