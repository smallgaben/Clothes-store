package parser.security;

import model.domain.security.Security;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public final class SecurityParser {
    public static final Logger LOGGER = Logger.getLogger(SecurityParser.class);
    private final String path;
    private Security security;

    public SecurityParser() {
        throw new UnsupportedOperationException("Immutable");
    }

    public SecurityParser(String path) {
        this.path = path;
    }

    public Security getSecurity() {
        return security;
    }

    public void parseXmlToObject() {
        File file = new File(path);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Security.class);

            Unmarshaller jaxbUnmarshaller;
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            security = (Security) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            LOGGER.warn("Can't unmarshalize file", e);
        }
    }
}
