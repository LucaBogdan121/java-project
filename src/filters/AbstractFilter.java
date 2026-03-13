package filters;

import java.util.List;

public interface AbstractFilter<T> {
    boolean matches(T entity);
}
