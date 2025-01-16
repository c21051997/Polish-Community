package polish_community_group_11.polish_community.information.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.models.DBInfoImpl;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 Implementation of the InformationRepository interface.
 Provides methods for interacting with the "information" database table using JdbcTemplate.
*/

@Repository
public class InformationRepositoryImpl implements InformationRepository {
    private JdbcTemplate jdbc; // JdbcTemplate instance for performing database operations
    private RowMapper<DBInfo> infoItemMapper; // RowMapper to map rows of the database result set to DBInfo objects
    public InformationRepositoryImpl(JdbcTemplate aJdbc){
        this.jdbc = aJdbc;
        setInfoItemMapper();
    }

    /**
     Sets mapping rows from the database to instances of the DBInfo.
     */
    public void setInfoItemMapper(){
        infoItemMapper = (rs, i) ->{
            try {
                // Check if any required fields are missing or null
                if (rs.getInt("category_id") == 0
                        || rs.getInt("info_id") == 0
                        || rs.getString("info_title") == null) {
                    throw new NullPointerException("Required News fields cannot be empty");
                }
                return new DBInfoImpl(
                        rs.getInt("category_id"),
                        rs.getInt("tag_id"),
                        rs.getInt("info_id"),
                        rs.getString("info_title"),
                        rs.getString("created_by"),
                        rs.getDate("created_date").toLocalDate(),
                        rs.getDate("updated_date").toLocalDate(),
                        rs.getString("short_info"),
                        rs.getString("info_article")
                );
            }
            catch (NullPointerException ex) {
                // Handle NullPointerException if any required fields are missing or null
                throw new NullPointerException("Error in mapping News data: " + ex.getMessage());
            } catch (DateTimeException ex) {
                // Handle DateTimeException if the date format is invalid
                throw new RuntimeException("Invalid date format for News Upload Date");
            } catch (Exception ex) {
                // Handle any other unexpected exceptions
                throw new RuntimeException("Unexpected error during mapping: " + ex.getMessage(), ex);
            }
        };
    }

    // Retrieves a single DBInfo item from the database based on the provided info id.
    public DBInfo getInfomrationById(int id) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException, SQLException {
        try{
            String sql = "select * from information where info_id=?";
            return jdbc.queryForObject(sql, infoItemMapper, id);
        }
        catch(EmptyResultDataAccessException ex){
            // Handle case where no records were found
            throw new EmptyResultDataAccessException("Did not find any records with selected id", 0);
        } catch (IncorrectResultSizeDataAccessException ex) {
            // Handle case where multiple results were found
            throw new IncorrectResultSizeDataAccessException("Multiple records generated, only one record expected",1);
        } catch (DataAccessException e) {
            // Handle other database-related exceptions
            throw new SQLException("Database error occurred", e);
        }
    }

    // Retrieves a list of all DBInfo items from the database.
    public List<DBInfo> getDbInfoListByCategory(int category_id){
        String sql = "select * from information where category_id=?";
//        return jdbc.query(sql,(rs, rowNum) -> rs.getString("info_title"),category_id);
        return jdbc.query(sql,infoItemMapper,category_id);
    }

    public List<DBInfo> findDbInfo(String userInput) {
        String sql = "SELECT * FROM information WHERE LOWER(info_title) LIKE ?";
        // from https://www.geeksforgeeks.org/spring-prepared-statement-jdbc-template/
        return jdbc.query(sql, ps -> ps.setString(1, "%" + userInput.toLowerCase() + "%"), infoItemMapper);
    }

    // Inserts new database of information record to the information table
    public void addNewInfo(DBInfo newInfo) throws SQLException {
        String dbInsertSql =
                "insert into information " +
                        "(category_id, info_title, created_by, created_date, " +
                        "updated_date, tag_id, short_info, info_article)" +
                " values (?,?,?,?,?,?,?,?)";
        try {
            jdbc.update(dbInsertSql,
                    newInfo.getCategoryId(),
                    newInfo.getInfoTitle(),
                    "Nitish Marnuri", //Mocking the user for now
                    LocalDate.now(),
                    LocalDate.now(),
                    1, // Mocking the tag id for now
                    newInfo.getShortDescription(),
                    newInfo.getInfoDescription()
            );
        } catch (DataAccessException e) {
            throw new SQLException("Failed to insert new information record", e);
        }
    }

    // Deletes a list of records from information table with list of Information Ids
    public int deleteInfoList(List<Integer> deleteIdList) throws SQLException {
        // Prepare query for deletion
        String deleteSql = "delete from information where info_id in (" +
                String.join(",", deleteIdList.stream().map(info_id -> "?").toArray(String[]::new)) + ")";

        Object[] deleteParams = deleteIdList.toArray();
        try {
            return jdbc.update(deleteSql, deleteParams);
        } catch (DataAccessException e) {
            throw new SQLException("Failed to delete information records", e);
        }
    }

    // Updates selected record with new updated information
    public int editInfo(DBInfo updatedInfo) throws SQLException {
        String updateSql = "update information " +
                "set info_title=?, short_info=?, info_article=? " +
                "where info_id=?";

        try {
            return jdbc.update(updateSql,
                    updatedInfo.getInfoTitle(),
                    updatedInfo.getShortDescription(),
                    updatedInfo.getInfoDescription(),
                    updatedInfo.getInformationId()
            );
        } catch (DataAccessException e) {
            throw new SQLException("Failed to update information record", e);
        }

    }
}
