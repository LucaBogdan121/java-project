package repository;
import domain.Identifiable;
import repository.IRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryRepository <T extends Identifiable<ID>, ID> implements IRepository<T, ID> {
    protected Map<ID, T> storage;
    public MemoryRepository(){
        this.storage=new HashMap<>();
    }
    @Override
    public T addNewEntity(T entity){
        if (entity==null){
            return null;
        }
        if(storage.containsKey(entity.getId())){
            throw new IllegalStateException("Entity with ID" + entity.getId() + " already exists");
        }
        storage.put(entity.getId(), entity);
        return entity;
    }
    @Override
    public T findById(ID id){
        return storage.get(id);
    }

    @Override
    public List<T> findAll(){
        return new ArrayList<>(storage.values());
    }

    @Override
    public T updateEntity(T entity) throws Exception{
        if(entity==null){
            return null;
        }
        if(!storage.containsKey(entity.getId())){
            throw new IllegalStateException("Entity with ID" + entity.getId() + " does not exist");
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteEntityById(ID id) throws Exception {
        if(!storage.containsKey(id)){
            throw new IllegalStateException("Entity with ID" + id + " does not exist");
        }
        storage.remove(id);
    }

}
