import java.util.*;

public class LinkedList<T extends Comparable<T>> implements List<T>
{
    private LinkedNode<T> first, last;
    private int size;

    //Iterable interfaace

    @Override
    public Iterator<T> iterator() 
    {
        return new LinkedIterator<>(this.first, 0);
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
        return this.first == null;
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
        if (this.isEmpty()) 
        {
            this.first = this.last = new LinkedNode<T>(e);
        }
        else
        {
            var newItem = new LinkedNode<>(this.last, e);
            this.last.setNext(newItem);
            this.last = newItem;
        }
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) 
    {
        int index = this.indexOf(o);
        if (index < 0) return false;

        if (index == 0) 
            this.removeFirst();
        else if (index == this.size-1)
            this.removeLast();
        else
            this.remove(index);
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
        boolean changed = true;
        for(T item : c)
            if (this.add(item))
                changed = true;
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
        this.first = this.last = null;
        this.size = 0;
    } 
    
    //SequencedCollection interface

    @Override
    public void addFirst(T e) 
    {
        if (this.isEmpty()) {
            this.add(e);
            return;
        }
        var newItem = new LinkedNode<>(e, first);
        first.setPrev(newItem);
        first = newItem;
        this.size++;
    }

    @Override
    public void addLast(T e) 
    {
        this.add(e);    
    }

    @Override
    public T getFirst() 
    {
        if (this.isEmpty())
            throw new NoSuchElementException(); 
        return first.getInfo();    
    }

    @Override
    public T getLast() 
    {
        if (this.isEmpty())
            throw new NoSuchElementException();         
        return last.getInfo();
    }

    @Override
    public T removeFirst() 
    {
        if (this.isEmpty())
            throw new NoSuchElementException(); 

        T item = first.getInfo();

        this.first = this.first.getNext();
        if (this.size == 1)
            this.last = null;
        else
            this.first.setPrev(null);
        this.size--;

        return item;
    }

    @Override
    public T removeLast() 
    {
        if (this.isEmpty())
            throw new NoSuchElementException(); 

        T item = last.getInfo();

        this.last = this.last.getPrev();
        if (this.size == 1)
            this.first = null;
        else
            this.last.setNext(null);
        this.size--;

        return item;
    } 
    
    //List interface

    @Override
    public List<T> reversed() 
    {
        var ret = new LinkedList<T>();
        var current = last;
        for (int i = 0; i < this.size; i++)
        {
            ret.add(current.getInfo());
            current = current.getPrev();
        }
        return ret;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) 
    {
        if (index > this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        var aux = new LinkedList<T>();
        for (T item : c) {
            aux.add(item);
        }
        if (aux.isEmpty()) return false;

        if (index == 0)
        {
            this.first.setPrev(aux.last);
            aux.last.setNext(this.first);
            this.first = aux.first;
        }
        else if (index == this.size)
        {
            this.last.setNext(aux.first);
            aux.first.setPrev(this.last);
            this.last = aux.last;
        }
        else 
        {
            var before = first;
            for (int i = 0; i != index-1; i++)
                before = before.getNext();
            var after = before.getNext();

            aux.first.setPrev(before);
            before.setNext(aux.first);
            aux.last.setNext(after);
            after.setPrev(aux.last);
        }
        this.size += aux.size;
        return true;
    }

    @Override
    public T get(int index) 
    {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);
        if (index == 0) return this.first.getInfo();
        if (index == this.size-1) return this.last.getInfo();

        var current = first.getNext();
        for (int i = 1; i != index; i++)
            current = current.getNext();

        return current.getInfo();
    }

    @Override
    public T set(int index, T element) 
    {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        T item;
        if (index == 0)
        {
            item = first.getInfo();
            first.setInfo(element);
            return item;
        }
        if (index == this.size-1)
        {
            item = last.getInfo();
            last.setInfo(element);
            return item;
        }

        var current = first.getNext();
        for (int i = 1; i != index; i++)
            current = current.getNext();

        item = current.getInfo();
        current.setInfo(element);
        return item;
    }

    @Override
    public void add(int index, T element) 
    {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        if (index == 0) this.addFirst(element);
        else if (index == this.size-1) this.add(element);
        else 
        {
            var current = first.getNext();
            for (int i = 1; i != index; i++)
                current = current.getNext();
            var newItem = new LinkedNode<>(current.getPrev(), element, current);
            current.getPrev().setNext(newItem);
            current.setPrev(newItem);
            this.size++;
        }
    }

    @Override
    public T remove(int index) 
    {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        if (index == 0) return this.removeFirst();
        if (index == this.size-1) return this.removeLast(); 

        var current = first.getNext();
        for (int i = 1; i != index; i++)
            current = current.getNext();

        current.getNext().setPrev(current.getPrev());
        current.getPrev().setNext(current.getNext());
        this.size--;
        return current.getInfo();
    }

    @Override
    public int indexOf(Object o) 
    {
        int index = 0;
        for (T item : this)
        {
            if (item.equals(o)) return index;
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) 
    {
        int index = -1;
        for (var iterator = new LinkedIterator<T>(first, 0); iterator.hasNext();)
        {
            if (iterator.next().equals(o)) index = iterator.previousIndex();
        }
        return index;
    }

    @Override
    public ListIterator<T> listIterator() 
    {
        return new LinkedIterator<>(first, 0);
    }

    @Override
    public ListIterator<T> listIterator(int index) 
    {
        if (index >= this.size || index < 0)
            throw new IndexOutOfBoundsException(index);

        var current = first;
        for (int i = 0; i != index; i++) 
            current = current.getNext();
        
        return new LinkedIterator<>(current, index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) 
    {
        if (fromIndex < 0) throw new IndexOutOfBoundsException(fromIndex);
        if (fromIndex > toIndex || toIndex > this.size)
            throw new IndexOutOfBoundsException(toIndex);

        var ret = new LinkedList<T>();

        var iterator = this.listIterator(fromIndex);
        while (iterator.nextIndex() != toIndex) 
            ret.add(iterator.next());
        
        return ret;
    }
}
