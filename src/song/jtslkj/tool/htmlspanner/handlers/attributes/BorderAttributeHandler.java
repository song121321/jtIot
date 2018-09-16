package song.jtslkj.tool.htmlspanner.handlers.attributes;

import android.text.SpannableStringBuilder;
import android.util.Log;
import song.jtslkj.tool.htmlspanner.SpanStack;
import song.jtslkj.tool.htmlspanner.TagNodeHandler;
import song.jtslkj.tool.htmlspanner.handlers.StyledTextHandler;
import song.jtslkj.tool.htmlspanner.spans.BorderSpan;
import song.jtslkj.tool.htmlspanner.style.Style;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BorderAttributeHandler extends WrappingStyleHandler {

    public BorderAttributeHandler(StyledTextHandler handler) {
        super(handler);
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end,
                              Style useStyle, SpanStack spanStack) {

        if ( node.getAttributeByName("border") != null ) {
            Log.d("BorderAttributeHandler", "Adding BorderSpan from " + start + " to " + end);
            spanStack.pushSpan(new BorderSpan(useStyle, start, end, getSpanner().isUseColoursFromStyle() ), start, end);
        }

        super.handleTagNode(node, builder, start, end, useStyle, spanStack);

    }


}
