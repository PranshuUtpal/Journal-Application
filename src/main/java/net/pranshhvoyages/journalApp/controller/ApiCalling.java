package net.pranshhvoyages.journalApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/journalapp")
public class ApiCalling {
    @GetMapping("/add-journal")
    public String showAddJournalPage() {
        return "add-journal"; // This will render the add-journal.html page from the templates folder
    }
    @GetMapping("/update-journal")
    public String showUpdateJournalPage() {
        return "update-journal"; // This will render the add-journal.html page from the templates folder
    }

    @GetMapping("/homepage")
    public String showHomepage() {
        return "homepage"; // This will render the add-journal.html page from the templates folder
    }

    @GetMapping("/my-journals")
    public String showMyJournalPage() {
        return "my-journals"; // This will render the add-journal.html page from the templates folder
    }

    @GetMapping("/view-profile")
    public String showViewProfilePage() {
        return "viewProfile"; // This will render the add-journal.html page from the templates folder
    }

    @GetMapping("/update-profile")
    public String showUpdateProfilePage() {
        return "updateProfile"; // This will render the add-journal.html page from the templates folder
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about"; // This will render the add-journal.html page from the templates folder
    }
}
