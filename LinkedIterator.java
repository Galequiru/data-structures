import java.util.ListIterator;

public class LinkedIterator<T extends Comparable<T>> implements ListIterator<T>
{
    LinkedNode<T> next;
    int indexNext;

    public LinkedIterator(LinkedNode<T> next, int index)
    {
        this.next = next;  
        this.indexNext = index;
    }

    //Iterator interface
    @Override
    public boolean hasNext() 
    {
        return next != null;
    }

    @Override
    public T next() 
    {
        T item = next.getInfo(); 
        next = next.getNext();
        indexNext++;
        return item;
    }

    //ListIterator interface
    @Override
    public boolean hasPrevious() 
    {
        return next.getPrev() != null;
    }

    @Override
    public T previous() {
        next = next.getPrev();
        T item = next.getInfo();
        indexNext--;
        return item;
    }

    @Override
    public int nextIndex() 
    {
        return indexNext;
    }

    @Override
    public int previousIndex() 
    {
        return indexNext-1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("LinkedIterator.remove");
    }

    @Override
    public void set(T e) {
        throw new UnsupportedOperationException("LinkedIterator.set");
    }

    @Override
    public void add(T e) {
        throw new UnsupportedOperationException("LinkedIterator.add");
    }
}
