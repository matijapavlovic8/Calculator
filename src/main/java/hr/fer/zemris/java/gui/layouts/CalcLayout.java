package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * CalcLayout class represents a Layout manager.
 *
 * @author MatijaPav
 */

public class CalcLayout implements LayoutManager2 {

    /**
     * Number of rows it {@code CalcLayout}
     */
    public static final int ROWS = 5;

    /**
     * Number of columns in {@code CalcLayout}
     */
    public static final int COLUMNS = 7;

    /**
     * Size of the gap between two components in a layout.
     */
    private int componentGap;

    private Map<RCPosition, Component> components;

    /**
     * Creates a {@code CalcLayout} with no gaps between two components.
     */
    public CalcLayout(){
        this(0);
    }

    /**
     * Creates a {@code CalcLayout}
     * @param componentGap size of the gap between two components.
     */
    public CalcLayout(int componentGap){
        if(componentGap < 0)
            throw new CalcLayoutException("Component gap can't be negative!");
        this.componentGap = componentGap;
        this.components = new HashMap<>();
    }
    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp        the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        Objects.requireNonNull(comp, "Component can't be null!");
        Objects.requireNonNull(constraints, "Constraints can't be null!");
        RCPosition pos;
        if(constraints instanceof RCPosition){
            pos = (RCPosition) constraints;
        } else if(constraints instanceof String){
            pos = RCPosition.parse((String) constraints);
        } else {
            throw new CalcLayoutException("Constraints must be instances of RCPosition or String class!");
        }
        int r = pos.getRow();
        int s = pos.getColumn();
        if(components.containsKey(pos))
            throw new CalcLayoutException("Component with these constraints already exists!");
        if((r < 1 || r > ROWS) || (s < 1 || s > COLUMNS) || (r == 1 && s > 1 && s < 6))
            throw new CalcLayoutException("Can't create component with these constraints!");

        this.components.put(pos, comp);

    }

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @param target the target container
     *
     * @return the maximum size of the container
     *
     * @see Component#getMaximumSize
     * @see LayoutManager
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return layoutSize(target, Component::getMaximumSize);
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * @param target the target container
     *
     * @return the x-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * @param target the target container
     *
     * @return the y-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     *
     * @param target the target container
     */
    @Override
    public void invalidateLayout(Container target) {}

    /**
     * If the layout manager uses a per-component string,
     * adds the component {@code comp} to the layout,
     * associating it
     * with the string specified by {@code name}.
     *
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        Objects.requireNonNull(comp, "Can't remove null!");
        if(!this.components.containsValue(comp))
            throw new CalcLayoutException("Layout doesn't contain given component!");
        this.components.values().remove(comp);
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the container to be laid out
     *
     * @return the preferred dimension for the container
     *
     * @see #minimumLayoutSize
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return layoutSize(parent, Component::getPreferredSize);

    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the component to be laid out
     *
     * @return the minimum dimension for the container
     *
     * @see #preferredLayoutSize
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return layoutSize(parent, Component::getMinimumSize);
    }

    /**
     * Lays out the specified container.
     *
     * @param parent the container to be laid out
     */
    @Override
    public void layoutContainer(Container parent) {

    }

    public Dimension layoutSize(Container parent, Function<Component, Dimension> function){
        int height;
        int width;

        height = this.components.values().stream().map(function).mapToInt(comp -> comp.height).max().orElseThrow();
        width = this.components.entrySet().stream().mapToInt(entry ->{
            if(entry.getKey().getColumn() == 1 && entry.getKey().getRow() == 1)
                return (function.apply(entry.getValue()).width - (ROWS - 1) * componentGap) / ROWS;
            return function.apply(entry.getValue()).width;
        }).max().orElseThrow();

        Insets insets = parent.getInsets();

        height = height * ROWS + componentGap * (ROWS - 1) + insets.top + insets.bottom;
        width = width * COLUMNS + componentGap * (COLUMNS - 1) + insets.left + insets.right;

        return new Dimension(width, height);

    }

}
