package galmart.filter;

public interface Filter<T> {

    public boolean accept(T object);
}
