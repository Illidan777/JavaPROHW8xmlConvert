package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void addContact(Contact contact) {
        contactRepository.save(contact);
    }

    @Transactional
    public void addGroup(Group group) {
        groupRepository.save(group);
    }
    @Transactional
    public void deleteGroup(long[] ids) {
        for (long id : ids)
            groupRepository.delete(id);
    }
    @Transactional
    public void saveToXML(long[] ids,Pageable pageable){
        int temp = 1;
        List<Group> groupList = new ArrayList<>();
        List<Contact> contactRepositoryByGroup = null;
        List<ContactDTO>contactDTOList = new ArrayList<>();
        File xml = null;
        for (long id: ids) {
            groupList.add(groupRepository.findOne(id));
        }
        for (Group group: groupList){
            xml = new File("src/main/XMLGroup" + temp++ + ".txt");
           contactRepositoryByGroup = contactRepository.findByGroup(group, pageable);
        }
        System.out.println(contactRepositoryByGroup);
        for (Contact t:contactRepositoryByGroup) {
            if(t!=null){
                contactDTOList.add(t.toDTO());
            }
        }
       ContactXMLCatalog contactXMLCatalog = new ContactXMLCatalog();
        for (ContactDTO contact:contactDTOList) {
          contactXMLCatalog.addContact(contact);
            try {
                xml.createNewFile();
                JAXBWorker.saveToXML(contactXMLCatalog, xml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void deleteContacts(long[] idList) {
        for (long id : idList)
            contactRepository.delete(id);
    }

    @Transactional(readOnly=true)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Contact> findAll(Pageable pageable) {
        return contactRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly=true)
    public List<Contact> findByGroup(Group group, Pageable pageable) {
        return contactRepository.findByGroup(group, pageable);
    }

    @Transactional(readOnly = true)
    public long countByGroup(Group group) {
        return contactRepository.countByGroup(group);
    }

    @Transactional(readOnly=true)
    public List<Contact> findByPattern(String pattern, Pageable pageable) {
        return contactRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return contactRepository.count();
    }

    @Transactional(readOnly=true)
    public Group findGroup(long id) {
        return groupRepository.findOne(id);
    }

    @Transactional
    public void reset() {
        groupRepository.deleteAll();
        contactRepository.deleteAll();

        Group group = new Group("Test");
        Contact contact;

        addGroup(group);

        for (int i = 0; i < 13; i++) {
            contact = new Contact(null, "Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            addContact(contact);
        }
        for (int i = 0; i < 10; i++) {
            contact = new Contact(group, "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            addContact(contact);
        }
    }
}
