package io.github.ddebree.game.ai.board.printer;

import com.google.common.base.Strings;
import io.github.ddebree.game.ai.board.geometry.Point;
import io.github.ddebree.game.ai.board.geometry.Region;

public abstract class AbstractToStringHelper<I> {

    private static final String PRE_STRING = "   ";
    private static final String NL = System.lineSeparator();

    protected abstract Region getRegion(I input);
    protected abstract char printPoint(I input, Point point);

    public String toString(final I input) {
        StringBuilder sb = new StringBuilder();

        final Region region = getRegion(input);

        //First line (Extra space to math the | below):
        sb.append(PRE_STRING).append(" ");

        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();
        region.getXRange().forEach( x -> {
            line1.append((int)(x / 10));
            line2.append(x % 10);
            line3.append("-");
        });

        sb.append(line1);
        sb.append(NL);

        //Second line (Extra space to match the | below)
        sb.append(PRE_STRING).append(" ");
        sb.append(line2);
        sb.append(NL);

        //Data lines:
        String topBottomBoarder = PRE_STRING + "|" + line3.toString() + "|\n";
        sb.append(topBottomBoarder);
        region.getYRange().forEach( y -> addFormattedRow(region, input, sb, y));

        sb.append(topBottomBoarder);
        return sb.toString();
    }

    private void addFormattedRow(Region region, I input, StringBuilder sb, int y) {
        sb.append(Strings.padStart(String.valueOf(y), 3, ' '));
        sb.append('|');
        region.getXRange().forEach( x -> {
            printPoint(input, Point.at(x, y));
        });
        sb.append('|').append(y).append(NL);
    }

}
