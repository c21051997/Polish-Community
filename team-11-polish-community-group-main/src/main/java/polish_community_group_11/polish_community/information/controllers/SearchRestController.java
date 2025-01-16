package polish_community_group_11.polish_community.information.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.models.SearchResult;
import polish_community_group_11.polish_community.information.services.InformationService;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class SearchRestController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final InformationService infoService;

    // Constructor for dependency injection of the InformationService
    public SearchRestController(InformationService infoService) {
        this.infoService = infoService;
    }


    @GetMapping("/searchresults")
    public List<SearchResult> findAll(@RequestParam(value = "q", defaultValue = "") String userInput) {
        LOG.info("user input: {}", userInput);
        return infoService.searchInfo(userInput);
    }

}
