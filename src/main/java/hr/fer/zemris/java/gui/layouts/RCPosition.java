package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Class {@code RCPosition} represents constraints on the layout created by {@code CalcLayout}.
 * @author MatijaPav
 */
public class RCPosition {
    /**
     * Row index.
     */
    private final int row;

    /**
     * Column index.
     */
    private final int column;

    public RCPosition(int row, int column){
        if(row < 0 || column < 0)
            throw new CalcLayoutException("Indexes of rows and columns must be non negative!");
        this.row = row;
        this.column = column;
    }

    /**
     * @return Column index.
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return Row index.
     */
    public int getRow() {
        return row;
    }

    public static RCPosition parse(String text){
        Objects.requireNonNull(text, "Can't parse RCPosition from null!");

        String[] splits = text.split(",");
        try {
            int rows = Integer.parseInt(splits[0]);
            int columns = Integer.parseInt(splits[1]);
            return new RCPosition(rows, columns);
        } catch (NumberFormatException e){
            throw new CalcLayoutException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RCPosition)) {
            return false;
        }
        RCPosition that = (RCPosition) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
