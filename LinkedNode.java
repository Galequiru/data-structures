public class LinkedNode<T extends Comparable<T>> implements Comparable<LinkedNode<T>>
{
    private T info;
    private LinkedNode<T> next, prev;

    public LinkedNode(LinkedNode<T> prev, T info, LinkedNode<T> next) throws NullPointerException
    {
        if (info == null) throw new NullPointerException("info must have value");
        this.info = info;
        this.prev = prev;
        this.next = next;
    }

    public LinkedNode(T info, LinkedNode<T> next) throws NullPointerException
    {
        this(null, info, next);
    }

    public LinkedNode(LinkedNode<T> prev, T info) throws NullPointerException
    {
        this(prev, info, null);
    }

    public LinkedNode(T info) throws NullPointerException
    {
        this(null, info, null);
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) 
    {
        if (info == null) throw new NullPointerException();
        this.info = info;
    }

    public LinkedNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }

    public LinkedNode<T> getPrev() {
        return prev;
    }

    public void setPrev(LinkedNode<T> prev) {
        this.prev = prev;
    }

    @Override
    public int compareTo(LinkedNode<T> o) {
        return this.info.compareTo(o.info);
    }

    @Override
    public int hashCode() {
        return this.info.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LinkedNode<?> other = (LinkedNode<?>) obj;
        return this.info.equals(other.info);
    }

    @Override
    public String toString() {
        return "LinkedNode [" + 
            (this.prev==null? null:prev.info) + 
            " -> " + this.info + " -> " + 
            (this.next==null? null:next.info) + 
        "]";
    }
}