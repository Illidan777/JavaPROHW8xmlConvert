package ua.kiev.prog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class JAXBWorker {
    public static void saveToXML(ContactXMLCatalog contactXMLCatalog, File file){
        try {
            JAXBContext jaxbC = JAXBContext.newInstance(ContactXMLCatalog.class);
            Marshaller marSh = jaxbC.createMarshaller();
            marSh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marSh.marshal(contactXMLCatalog, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
