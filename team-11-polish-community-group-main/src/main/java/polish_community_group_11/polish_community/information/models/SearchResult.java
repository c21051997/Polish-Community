package polish_community_group_11.polish_community.information.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Represents the summary of a single article in a search result.
 */
@Builder(setterPrefix = "with")
@Data
@AllArgsConstructor
public class SearchResult {
    /** The information id. */
    private final int infoID;
    /** The title or the article. */
    private final String infoTitle;
    /** The category the article was filed under. */
    private final String category;
    /** The date the article was last updated. */
    private final LocalDate lastUpdated;
}
