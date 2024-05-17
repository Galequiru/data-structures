import java.util.*;

public class Stack<T extends Comparable<T>> implements SequencedCollection<T>
{
    private int size;
    private LinkedNode<T> top;

    public Stack()
    {
        this.size = 0;
        this.top = null;
    }

    public boolean push(T item)
    {
        try
        {
            LinkedNode<T> newNode = new LinkedNode<T>(top, item);
            if(!this.isEmpty()) top.setNext(newNode);
            top = newNode;
            size++;
            return true;
        }
        catch (Exception e)
        {   
            return false;
        }
    }

    public T pop()
    {
        if (this.isEmpty()) return null;

        T item = top.getInfo();
        top = top.getPrev();
        if (top != null) top.setNext(null);
        size--;
        return item;
    }

    //Iterable interface

    @Override
    public Iterator<T> iterator() 
    {
        if (this.isEmpty()) return new LinkedIterator<>(null, 0);

        LinkedNode<T> current = top;
        while (current.getPrev() != null) 
            current = current.getPrev();

        return new LinkedIterator<>(current, 0);
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
        return this.top == null;
    }

    @Override
    public boolean contains(Object o) 
    {
        var aux = new Stack<T>();
        boolean found = false;

        while (!this.isEmpty() && !found) 
        {
            T item = this.pop();
            if (item.equals(o)) found = true;
            aux.push(item);    
        }
        while (!aux.isEmpty()) this.push(aux.pop());

        return found;
    }

    @Override
    public Object[] toArray() 
    {
        Object[] ret = new Object[this.size];
        int index = 0;
        for (T item : this)
        {
            ret[index++] = item;
        }
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
        return this.push(e);
    }

    @Override
    public boolean remove(Object o) 
    {
        var aux = new Stack<T>();
        boolean changed = false;

        while (!this.isEmpty() && !changed) 
        {
            T item = this.pop();
            if (item.equals(o)) 
                changed = true;
            else
                aux.push(item);
        }
        while (!aux.isEmpty()) this.push(aux.pop());
            
        return changed;
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
        for (T item : c)
            if (this.push(item)) changed = true;
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) 
    {
        boolean changed = false;
        for(Object item : c)
            if (this.remove(item)) changed = true;
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
        this.size = 0;
        this.top = null;
    }

    //SequencedCollection interface

    @Override
    public SequencedCollection<T> reversed()
    {
        var aux = new Stack<T>();
        var ret = new Stack<T>();

        while (!this.isEmpty()) 
        {
            T item = this.pop();
            aux.push(item);
            ret.push(item);
        }
        while (!aux.isEmpty()) this.push(aux.pop());

        return ret;
    }

    @Override
    public void addFirst(T e) 
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLast(T e) 
    {
        this.push(e);
    }

    @Override
    public T getFirst() 
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getLast() 
    {
        return this.top.getInfo();  
    }

    @Override
    public T removeFirst() 
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeLast() 
    {
        return this.pop();
    }

    //Object inheritance

    @Override
    public String toString() {
        String ret = "Stack ("+size+") [ ";
        for (T item : this) {
            ret += item.toString() + ", ";
        }
        return ret + "] top";
    }
}

