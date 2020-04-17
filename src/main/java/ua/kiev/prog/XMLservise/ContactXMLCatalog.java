package ua.kiev.prog.XMLservise;

import ua.kiev.prog.DTO.ContactDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class ContactXMLCatalog {
    @XmlElement(name = "contact")
    private ArrayList<ContactDTO> listContact = new ArrayList<>();
    public ContactXMLCatalog() {
        super();
    }
    public void addContact(ContactDTO contactDTO) {
       listContact.add(contactDTO);
    }
    public  ArrayList<ContactDTO> getListContact() {
        return new ArrayList<>(listContact);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        listContact.stream()
                .forEachOrdered(n -> sb.append(n).append(System.lineSeparator()));
        return sb.toString();
    }
}
