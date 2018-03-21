package gui.util;

public class GraduallyIndexConverter {
    private final int numberElementsPerRow;

    public GraduallyIndexConverter(int numberElementsPerRow) {
        this.numberElementsPerRow = numberElementsPerRow;
    }

    public int convertToRow(int index) {
        return index / numberElementsPerRow;
    }

    public int convertToColumn(int index) {
        return index % numberElementsPerRow;
    }
}
