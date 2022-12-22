package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

public class CalcLayout implements LayoutManager2 {
    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp        the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        
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
        return null;
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
    public void invalidateLayout(Container target) {

    }

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

    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {

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
        return null;
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
        return null;
    }

    /**
     * Lays out the specified container.
     *
     * @param parent the container to be laid out
     */
    @Override
    public void layoutContainer(Container parent) {

    }
}
