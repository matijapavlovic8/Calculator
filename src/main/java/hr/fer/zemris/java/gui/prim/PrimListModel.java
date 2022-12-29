package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * {@code PrimListModel} is an implementation of{@link ListModel}.
 * @author MatijaPav
 */
public class PrimListModel implements ListModel<Integer> {
    /**
     * List of primes.
     */
    private List<Integer> primes;

    /**
     * List of listners.
     */
    private List<ListDataListener> listeners;

    public PrimListModel(){
        primes = new ArrayList<>();
        listeners = new ArrayList<>();
        primes.add(1);
    }

    /**
     * Returns the length of the list.
     * @return the length of the list
     */
    @Override
    public int getSize() {
        return primes.size();
    }

    /**
     * Returns the value at the specified index.
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public Integer getElementAt(int index) {
        return primes.get(index);
    }

    /**
     * Adds a listener to the list that's notified each time a change
     * to the data model occurs.
     * @param l the <code>ListDataListener</code> to be added
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    /**
     * Removes a listener from the list that's notified each time a
     * change to the data model occurs.
     * @param l the <code>ListDataListener</code> to be removed
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public void next(){
        int nextPrime = this.primes.get(getSize() - 1) + 1;
        while(true){
            if(isPrime(nextPrime)){
                primes.add(nextPrime);
                break;
            }
            nextPrime++;
        }
        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1);
        listeners.forEach(l -> l.intervalAdded(event));
    }

    /**
     * Checks if given number is a prime number.
     * @param p checked number.
     * @return {@code true} if number is prime, {@code false} otherwise.
     */
    private boolean isPrime(int p){
        for(int i = 2; i <= p / 2; i++){
            if(p % i == 0)
                return false;
        }
        return true;
    }

}
