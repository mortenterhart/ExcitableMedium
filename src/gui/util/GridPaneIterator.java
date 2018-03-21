package gui.util;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class GridPaneIterator implements Iterator<Rectangle> {
    private final ObservableList<Node> gridChildren;
    private int currentChildIndex = 0;

    public GridPaneIterator(GridPane grid) {
        Objects.requireNonNull(grid);
        this.gridChildren = grid.getChildren();
        removeChildGroups();
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    public boolean hasNext() {
        return currentChildIndex < gridChildren.size();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    public Rectangle next() {
        if (!hasNext()) {
            throw new NoSuchElementException("no more rectangles in this grid pane");
        }

        return (Rectangle) gridChildren.get(currentChildIndex++);
    }

    private void removeChildGroups() {
        gridChildren.removeIf(child -> child instanceof Group);
    }
}
