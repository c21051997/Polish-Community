package polish_community_group_11.polish_community.information.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import polish_community_group_11.polish_community.information.models.DBInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * This Information Repository interface acts as a layer to hide the main implementations of the
 information repository methods and make only the methods available.

 It defines methods to retrieve specific data
items and lists of database information from the data source.
**/

public interface InformationRepository {
    public DBInfo getInfomrationById(int id)
            throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException, SQLException;
    public List<DBInfo> getDbInfoListByCategory(int category_id);

    List<DBInfo> findDbInfo(String userInput);
    void addNewInfo(DBInfo newInfo) throws SQLException;
    int deleteInfoList(List<Integer> deleteIdList) throws SQLException;
    int editInfo(DBInfo updatedInfo) throws SQLException;
}
