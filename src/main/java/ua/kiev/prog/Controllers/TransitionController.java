package ua.kiev.prog.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kiev.prog.Models.Group;
import ua.kiev.prog.Servise.ContactService;

@Controller
public class TransitionController {

    //@Autowired
    private final ContactService contactService;

    public TransitionController(ContactService contactService) {
        this.contactService = contactService;
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
    @RequestMapping("/verification_download")
    public String verificationDownload() {
        return "verification_download";
    }

    @RequestMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
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
}
