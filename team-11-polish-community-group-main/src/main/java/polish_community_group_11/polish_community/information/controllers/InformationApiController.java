package polish_community_group_11.polish_community.information.controllers;

import org.springframework.web.bind.annotation.*;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.services.InformationService;

import java.sql.SQLException;
import java.util.List;


@RestController
public class InformationApiController {
    private final InformationService infoService;

    // Constructor for dependency injection of the InformationService
    public InformationApiController(InformationService infoService){
        this.infoService = infoService;
    }

    @GetMapping("getInfoByCategoryId/{category_id}")
    public List<DBInfo> getAllInformation(@PathVariable int category_id){
        return infoService.getAllItemsByCategoryId(category_id);
    }

    @DeleteMapping("api/info/delete")
    public int deleteInformation(@RequestParam List<Integer> deleteIdList) throws SQLException {
        return infoService.deleteInfoByList(deleteIdList);
    }
}
