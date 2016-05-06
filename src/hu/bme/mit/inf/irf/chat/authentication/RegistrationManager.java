package hu.bme.mit.inf.irf.chat.authentication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RegistrationManager {

    private final Map<String, String> registeredUsers = new HashMap<>();

    public void loadUsers(final File file) throws Exception {
        try {
            // read and normalise XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            // validate root element
            Element rootElement = document.getDocumentElement();
            if ("config".equals(rootElement.getNodeName())) {
                // get users
                NodeList users = rootElement.getElementsByTagName("user");

                if (users.getLength() > 0) {
                    for (int i = 0; i < users.getLength(); ++i) {
                        Element user = (Element) users.item(i);
                        Element userNameElement = (Element) user
                                .getElementsByTagName("name").item(0);
                        Element passwordElement = (Element) user
                                .getElementsByTagName("password").item(0);

                        if (userNameElement == null || passwordElement == null) {
                            throw new Exception(
                                    "Configuration file: name, password must be defined for each user.");
                        }

                        String userName = userNameElement.getTextContent();
                        String password = passwordElement.getTextContent();
                        registeredUsers.put(userName, password);
                    }
                } else {
                    throw new Exception(
                            "At least one user is required!");
                }
            } else {
                throw new Exception(
                        "Configuration file: the root element should be <config>");
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            throw new Exception("Unexpected exception occured when parsing"
                    + " the configuration file (see details)", ex);
        }
    }

    public boolean isAuthenticated(final String user, final String rawPassword) {
        String encodedPassword = new String(Base64.encodeBase64(
                rawPassword.getBytes()));
        String storedPassword = registeredUsers.get(user);
        return encodedPassword.equals(storedPassword);
    }
}
