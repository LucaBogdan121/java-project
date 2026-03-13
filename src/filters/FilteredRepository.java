package filters;
import domain.Identifiable;
import repository.IRepository;
import repository.MemoryRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
public class FilteredRepository<ID, T extends Identifiable<ID>> implements IRepository<T, ID> {
    private IRepository<T, ID> repository;
    private AbstractFilter<T> filter;

    public FilteredRepository(IRepository<T, ID> repository, AbstractFilter<T> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    @Override
    public T addNewEntity(T entity) {
        return repository.addNewEntity(entity);
    }

    @Override
    public T findById(ID id) {
        T entity = repository.findById(id);
        if (entity != null && filter.matches(entity))
            return entity;
        return null;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll().stream().filter(entity -> filter.matches(entity)).toList();
    }

    @Override
    public T updateEntity(T entity) throws Exception {
        return repository.updateEntity(entity);
    }

    @Override
    public void deleteEntityById(ID id) throws Exception {
        repository.deleteEntityById(id);
    }

    public Iterable<T> findFiltered(AbstractFilter<T> filter) {
        return StreamSupport.stream(findAll().spliterator(), false)
                .filter(filter::matches)
                .collect(Collectors.toList());
    }

}
