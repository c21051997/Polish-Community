package polish_community_group_11.polish_community.information.common;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
Utility class to perform common operations
*/

public class Utility {

    //This method converts Markdown text to html formatted output text
    public static String markdownToHtml(String markdown) throws IllegalArgumentException{
        if (markdown == null || markdown.trim().isEmpty()) {
            throw new IllegalArgumentException("Input markdown text cannot be null or empty.");
        }
        else{
            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            Node document = parser.parse(markdown);
            return renderer.render(document);
        }
    }
}
