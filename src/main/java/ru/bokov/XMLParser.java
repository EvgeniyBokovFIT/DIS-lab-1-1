package ru.bokov;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    private final String NODE_TAG_NAME = "node";
    private final String TAG_TAG_NAME = "tag";
    private final String USER_ATTRIBUTE_NAME = "user";
    private final String KEY_ATTRIBUTE_NAME = "k";

    Map<String, Integer> userFrequencyMap = new HashMap<>();

    Map<String, Integer> keyFrequencyMap = new HashMap<>();

    public Map<String, Integer> getUserFrequencyMap() {
        return new HashMap<>(userFrequencyMap);
    }

    public Map<String, Integer> getKeyFrequencyMap() {
        return new HashMap<>(keyFrequencyMap);
    }

    public void calculateFrequencyOfAttributes(
            InputStream inputStream) throws XMLStreamException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(inputStream);

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (isEventEquals(event, TAG_TAG_NAME)) {
                var attribute = getAttributeValue(event, KEY_ATTRIBUTE_NAME);
                keyFrequencyMap.merge(attribute, 1, Integer::sum);
            }

            if (isEventEquals(event, NODE_TAG_NAME)) {
                var attribute = getAttributeValue(event, USER_ATTRIBUTE_NAME);
                userFrequencyMap.merge(attribute, 1, Integer::sum);
            }
        }
    }

    private boolean isEventEquals(XMLEvent event, String name) {
        if(!event.isStartElement()) {
            return false;
        }
        return event.asStartElement().getName().getLocalPart().equals(name);
    }

    private String getAttributeValue(XMLEvent event, String attributeName) {
        return event.asStartElement()
                .getAttributeByName(new QName(attributeName)).getValue();
    }
}
