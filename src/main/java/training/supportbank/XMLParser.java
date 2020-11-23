package training.supportbank;

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
import java.util.List;
import java.util.ArrayList;

public class XMLParser extends Parser {
    NodeList readingData = null;

    public XMLParser(String filename) {
        setFilename(filename);
    }

    @Override
    public boolean checkIntermediaryDataAfterReading() {
        try {
            File xmlFile = new File(getFilename());
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            this.readingData = document.getElementsByTagName("SupportTransaction");
            return true;
        } catch (IOException e) {
            DialogueHandler.outputIOExceptionMessage(getFilename());
        } catch (ParserConfigurationException | SAXException e) {
            DialogueHandler.outputXMLParseExceptionMessage(getFilename());
        }
        return false;
    }

    @Override
    public boolean checkExtractedRawTransactionData() {
        List<RawTransaction> rawTransactions = new ArrayList<>();
        for (int i = 0; i < readingData.getLength(); i++) {
            Element transactionElement = (Element)readingData.item(i);
            if (transactionElement == null)
                continue;

            String date = transactionElement.getAttribute("Date");
            String narrative = getNodeTextOrEmptyString(transactionElement, "Description");
            String amount = getNodeTextOrEmptyString(transactionElement, "Value");
            String to = getNodeTextOrEmptyString(transactionElement, "To");
            String from = getNodeTextOrEmptyString(transactionElement, "From");

            RawTransaction newRawTransaction = new RawTransaction(date, from, to, narrative, amount);
            rawTransactions.add(newRawTransaction);
        }
        setRawTransactionData(rawTransactions.stream().toArray(RawTransaction[]::new));
        return true;
    }

    private String getNodeTextOrEmptyString(Element transactionElement, String nodeTagName) {
        Node node = transactionElement.getElementsByTagName(nodeTagName).item(0);
        return node == null ? "" : node.getTextContent();
    }
}
