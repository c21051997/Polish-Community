package polish_community_group_11.polish_community.information.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.information.dao.InformationRepository;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.models.DBInfoImpl;
import polish_community_group_11.polish_community.information.models.SearchResult;
import polish_community_group_11.polish_community.information.models.ViewModel.DBInfoForm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 InformationServiceImpl is the concrete implementation of the InformationService interface.
 This service is responsible for handling business logic related to information retrieval.
 It uses Dependency Injection to receive an instance of the InformationRepository, which it delegates data access tasks to.
*/

@Service
public class InformationServiceImpl implements InformationService{

    // The InformationRepository is injected by Spring using constructor injection
    private final InformationRepository informationRepository;

    // Spring automatically injects an instance of InformationRepository into this service at runtime.
    public InformationServiceImpl(InformationRepository informationRepository){
        this.informationRepository = informationRepository;
    }

    public DBInfo getInfomrationItemById(int id) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException, SQLException {
        return informationRepository.getInfomrationById(id);
    }

    public List<DBInfo> getAllItemsByCategoryId(int category_id) {
        return informationRepository.getDbInfoListByCategory(category_id);
    }
     public List<SearchResult> searchInfo(String userInput) {
        return informationRepository.findDbInfo(userInput)
                .stream()
                .map(dbinfo -> SearchResult.builder()
                        .withInfoTitle(dbinfo.getInfoTitle())
                        .withInfoID(dbinfo.getInformationId())
                        .withLastUpdated(dbinfo.getUpdatedDate())
                        .build())
                .toList();
     }

    public void addNewInfo(DBInfo newInfo) throws SQLException {
        informationRepository.addNewInfo(newInfo);
    }

    public int deleteInfoByList(List<Integer> deleteIdList) throws SQLException {
        return informationRepository.deleteInfoList(deleteIdList);
    }

    public int editInfo(DBInfo updatedInfo) throws SQLException {
        return informationRepository.editInfo(updatedInfo);
    }

    public DBInfo processDbInfoForm(DBInfoForm dbInfoForm) {
        if (dbInfoForm == null) {
            throw new IllegalArgumentException("dbInfoForm cannot be null");
        }
        DBInfo newInfo = new DBInfoImpl();
        newInfo.setCategoryId(dbInfoForm.getCategoryId());
        newInfo.setInfoTitle(dbInfoForm.getInfoTitle());
        newInfo.setShortDescription(dbInfoForm.getShortDescription());
        newInfo.setInfoDescription(dbInfoForm.getInfoDescription());
        newInfo.setInformationId(dbInfoForm.getInformationId());
        newInfo.setCreatedBy(dbInfoForm.getCreatedBy());
        newInfo.setCreatedDate(dbInfoForm.getCreatedDate());
        newInfo.setTagId(dbInfoForm.getTagId());
        return newInfo;
    }

    public DBInfoForm processDbInfoFormForEdit(DBInfo selectedInfo) {
        if (selectedInfo == null) {
            throw new IllegalArgumentException("dbInfoForm cannot be null");
        }
        DBInfoForm selectedInfoForm = new DBInfoForm();
        selectedInfoForm.setCategoryId(selectedInfo.getCategoryId());
        selectedInfoForm.setInfoTitle(selectedInfo.getInfoTitle());
        selectedInfoForm.setInformationId(selectedInfo.getInformationId());
        selectedInfoForm.setShortDescription(selectedInfo.getShortDescription());
        selectedInfoForm.setInfoDescription(selectedInfo.getInfoDescription());
        selectedInfoForm.setCreatedBy(selectedInfo.getCreatedBy());
        selectedInfoForm.setCreatedDate(selectedInfo.getCreatedDate());
        selectedInfoForm.setTagId(selectedInfo.getTagId());
        return selectedInfoForm;
    }
}
