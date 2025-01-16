package polish_community_group_11.polish_community.information.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.models.SearchResult;
import polish_community_group_11.polish_community.information.models.ViewModel.DBInfoForm;

import java.sql.SQLException;
import java.util.List;

/**
 InformationService is an interface that defines the contract for services
 responsible for managing and retrieving information records.
 It abstracts the logic for fetching individual information items by ID
 and retrieving all available information records.
*/

public interface InformationService {
    DBInfo getInfomrationItemById(int id)
            throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException, SQLException;
    List<DBInfo> getAllItemsByCategoryId(int category_id);

    List<SearchResult> searchInfo(String userInput);
    DBInfo processDbInfoForm(DBInfoForm dbInfoForm);
    void addNewInfo(DBInfo newInfo) throws SQLException;
    int deleteInfoByList(List<Integer> deleteIdList) throws SQLException;
    int editInfo(DBInfo updatedInfo) throws SQLException;
    DBInfoForm processDbInfoFormForEdit(DBInfo selectedInfo);
}
