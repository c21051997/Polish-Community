package polish_community_group_11.polish_community.information.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import polish_community_group_11.polish_community.information.common.Utility;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.models.ViewModel.DBInfoForm;
import polish_community_group_11.polish_community.information.services.InformationService;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

@Controller
public class InformationController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InformationService infoService;

    // Constructor for dependency injection of the InformationService
    @Autowired
    public InformationController(InformationService infoService){
        this.infoService = infoService;
    }

    // Method to handle GET requests for retrieving a specific information entry by ID
    @GetMapping("info/{id}")
    public ModelAndView getInformationById(@PathVariable int id)
            throws NullPointerException, EmptyResultDataAccessException, IncorrectResultSizeDataAccessException, SQLException
    {
        ModelAndView modelAndView = new ModelAndView("information/information");
        DBInfo selectedDbInfo = infoService.getInfomrationItemById(id);
        if(selectedDbInfo!=null){
            // Convert the information description from Markdown to HTML
            String html = Utility.markdownToHtml(selectedDbInfo.getInfoDescription());
            modelAndView.addObject("selectedInfo", selectedDbInfo);
            modelAndView.addObject("html",html);
        }
        else{
            throw new NullPointerException();
        }
        return modelAndView;
    }

    @GetMapping("info/add/{categoryId}")
    public ModelAndView addInformation(@PathVariable int categoryId){
        ModelAndView modelAndView=new ModelAndView("information/addInformation");
        DBInfoForm newInfo = new DBInfoForm();
        newInfo.setCategoryId(categoryId);
        modelAndView.addObject("newInfo",newInfo);
        modelAndView.addObject("formAction", "/info/add");
        return modelAndView;
    }

    @PostMapping("/info/add")
    public ModelAndView addInformation(@Valid @ModelAttribute("newInfo") DBInfoForm newInfo,
                                       BindingResult bindingResult, Model model) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("information/addInformation");
        if(bindingResult.hasErrors()){
            modelAndView.addObject(model.asMap());
            modelAndView.addObject("formAction", "/info/add");
        }
        else{
            DBInfo addInfo = infoService.processDbInfoForm(newInfo);
            infoService.addNewInfo(addInfo);
            modelAndView = new ModelAndView(
                    String.format("redirect:../categories/%d", addInfo.getCategoryId()));
        }
        return modelAndView;
    }

    @GetMapping("/info/edit/{info_id}")
    public ModelAndView editInformation(@PathVariable int info_id) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("information/addInformation");
        DBInfo selectedInfo = infoService.getInfomrationItemById(info_id);
        DBInfoForm newInfo = infoService.processDbInfoFormForEdit(selectedInfo);
        modelAndView.addObject("newInfo", newInfo);
        modelAndView.addObject("formAction", "/info/edit");
        return modelAndView;
    }

    @PostMapping("/info/edit")
    public ModelAndView editInformation(@Valid @ModelAttribute("newInfo") DBInfoForm newInfo,
                                        BindingResult bindingResult, Model model) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("information/addInformation");
        if(bindingResult.hasErrors()){
            modelAndView.addObject(model.asMap());
            modelAndView.addObject("formAction", "/info/edit");
        }
        else{
            DBInfo updatedInfo = infoService.processDbInfoForm(newInfo);
            infoService.editInfo(updatedInfo);
            modelAndView = new ModelAndView(
                    String.format("redirect:../categories/%d", updatedInfo.getCategoryId()));
        }
        return modelAndView;
    }
}
