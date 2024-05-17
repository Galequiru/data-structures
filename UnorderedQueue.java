import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class UnorderedQueue<T extends Comparable<T>> implements Queue<T>
{
    private int size;
    private LinkedNode<T> end, head;

    public boolean enqueue(T item)
    {
        if (this.isEmpty()) 
        {
            this.end = this.head = new LinkedNode<T>(item);
        }
        else
        {
            var newItem = new LinkedNode<T>(item, this.end);
            this.end.setPrev(newItem);
            this.end = newItem;
        }
        this.size++;
        return true;
    }

    public T dequeue()
    {
        if (this.isEmpty()) return null;

        T item = this.head.getInfo();

        this.head = this.head.getPrev();
        if (!this.isEmpty()) this.head.setNext(null);
        if (this.size == 1) this.end = null;
        this.size--;

        return item;
    }

    //Iterable interface
    @Override
    public Iterator<T> iterator() 
    {
        return new LinkedIterator<>(end, 0);
    }

    //Collection interface
    @Override
    public int size() 
    {
        return this.size;
    }

    @Override
    public boolean isEmpty() 
    {
        return this.head == null;
    }

    @Override
    public boolean contains(Object o) 
    {
        for (T item : this)
            if (item.equals(o)) return true;

        return false;
    }

    @Override
    public Object[] toArray() 
    {
        var ret = new Object[this.size];
        int index = 0;
        for (T item : this)
            ret[index++] = item;
        return ret;
    }

    @Override
    public <E> E[] toArray(E[] a) 
    {        
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean add(T e) 
    {
        return this.enqueue(e);
    }

    @Override
    public boolean remove(Object o) 
    {
        if (this.isEmpty()) return false;

        var current = this.end;
        while (!current.getInfo().equals(o))
        {
            current = current.getNext();
            if (current == null) return false;
        }

        if (current.equals(this.head))
            this.dequeue();
        else if (current.equals(this.end)) 
        {
            this.end = this.end.getNext();
            if (this.end != null) this.end.setPrev(null);
            if (this.size == 1) this.head = null;
            this.size--;
        }
        else
        {
            current.getNext().setPrev(current.getPrev());
            current.getPrev().setNext(current.getNext());
            this.size--;
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) 
    {
        for (Object item : c) 
            if (!this.contains(item)) return false;
        return true;    
    }

    @Override
    public boolean addAll(Collection<? extends T> c) 
    {
        boolean changed = false;
        for(T item : c)
            if (this.enqueue(item)) changed = true;
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) 
    {
        boolean changed = false;
        for(var item : c)
            if (this.remove(item)) 
                changed = true;
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) 
    {
        boolean changed = false;
        for (var item : this.toArray()) 
            if (!c.contains(item)) 
            {
                this.remove(item);
                changed = true;
            }
        return changed;
    }

    @Override
    public void clear() 
    {
        this.end = this.head = null;
        this.size = 0;
    }

    //Queue interface
    @Override
    public boolean offer(T e) 
    {
        return this.add(e);
    }

    @Override
    public T remove() 
    {
        if (this.isEmpty()) throw new NoSuchElementException();

        return this.dequeue();
    }

    @Override
    public T poll() 
    {
        return this.dequeue();
    }

    @Override
    public T element() 
    {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.head.getInfo();
    }

    @Override
    public T peek() 
    {
        return this.isEmpty()? null : this.head.getInfo();
    }
}
