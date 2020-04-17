package ua.kiev.prog.Controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kiev.prog.Servise.ContactService;
import ua.kiev.prog.Models.Contact;
import ua.kiev.prog.Models.Group;

import java.util.List;
@Controller
public class GroupController {

    static final int DEFAULT_GROUP_ID = -1;
    static final int ITEMS_PER_PAGE = 6;

    //@Autowired
    private final ContactService contactService;

    public GroupController(ContactService contactService) {
        this.contactService = contactService;
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
            contactService.saveGroupToXML(toXML);
        return "redirect:/";
    }

    private long getPageCount(Group group) {
        long totalCount = contactService.countByGroup(group);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
