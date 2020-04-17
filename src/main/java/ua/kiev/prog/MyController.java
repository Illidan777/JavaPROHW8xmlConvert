package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class MyController {
    static final int DEFAULT_GROUP_ID = -1;
    static final int ITEMS_PER_PAGE = 6;

    //@Autowired
    private final ContactService contactService;

    public MyController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(required = false,
                                defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Contact> contacts = contactService
                .findAll(new PageRequest(page,
                        ITEMS_PER_PAGE,
                        Sort.Direction.DESC, "id"));

        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("allPages", getPageCount());

        return "index";
    }

    @RequestMapping("/reset")
    public String reset() {
        contactService.reset();
        return "redirect:/";
    }

    @RequestMapping("/contact_add_page")
    public String contactAddPage(Model model) {
        model.addAttribute("groups", contactService.findGroups());
        return "contact_add_page";
    }

    @RequestMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }

    @RequestMapping("/group/{id}")
    public String listGroup(
            @PathVariable(value = "id") long groupId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;
        if (page < 0) page = 0;

        List<Contact> contacts = contactService
                .findByGroup(group, new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("byGroupPages", getPageCount(group));
        model.addAttribute("groupId", groupId);

        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contactService.findByPattern(pattern, null));

        return "index";
    }

    @RequestMapping(value = "/contact/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(@RequestParam(value = "toDelete[]", required = false)
                                               long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            contactService.deleteContacts(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }
//ResponseEntity<Void>
    @RequestMapping(value = "/contact/toXML", method = RequestMethod.POST)
    public void toXMLContact(@RequestParam(value = "toXMLContact[]", required = false)
                                               long[] toXMLContact, HttpServletResponse response) {
        if (toXMLContact != null && toXMLContact.length > 0) {
             contactService.saveContactToXML(toXMLContact);
            System.out.println("File converted to XML");
            }
        }


    @RequestMapping(value = "/contact/toXML/download" , method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response){
        String name = "xmlContacts.xml";
        String filePathOnServer = "src/main/templateContactXMLStorage/" + name;
       // if(filename.indexOf(".txt")>-1)
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition","attachment; filename=" +name);
        response.setHeader("Content-Transfer-Encoding" , "binary");

        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(filePathOnServer);
            int len;
            byte[]buffer = new byte[1024];
            while ((len = fis.read(buffer))>0){
                bos.write(buffer,0,len);
            }
            response.flushBuffer();
            bos.close();


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/contact/add", method = RequestMethod.POST)
    public String contactAdd(@RequestParam(value = "group") long groupId,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String phone,
                             @RequestParam String email)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;

        Contact contact = new Contact(group, name, surname, phone, email);
        contactService.addContact(contact);

        return "redirect:/";
    }

    @RequestMapping(value="/group/add", method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name) {
        contactService.addGroup(new Group(name));
        return "redirect:/";
    }
    @RequestMapping("/group_delete_page")
    public String groupDeletePage(Model model) {
        model.addAttribute("groups", contactService.findGroups());
        return "group_delete_page";
    }
    @RequestMapping(value = "/group_xml_page")
    public String groupXMLPage(Model model){
        model.addAttribute("groups", contactService.findGroups());
        return "group_xml_page";
    }


    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    public String deleteGroup(
            @RequestParam(value = "toDeleteGroup[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            contactService.deleteGroup(toDelete);
        return "redirect:/";
    }

    @RequestMapping(value = "/group/toXML", method = RequestMethod.POST)
    public String toXMLGroup(
            @RequestParam(value = "toXMLGroup[]", required = false) long[] toXML) {
        if (toXML != null && toXML.length > 0)
            contactService.saveGroupToXML(toXML,null);
        return "redirect:/";
    }

    private long getPageCount() {
        long totalCount = contactService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Group group) {
        long totalCount = contactService.countByGroup(group);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
