package repository;

import domain.Identifiable;

import java.util.List;

public interface IRepository <T extends Identifiable<ID>, ID>{
    T addNewEntity(T entity);
    T findById(ID id);
    List<T> findAll();
    T updateEntity(T entity) throws Exception;
    void deleteEntityById(ID id) throws Exception;
}
