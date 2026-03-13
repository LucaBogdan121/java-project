package repository;

import domain.Car;


public abstract class CarFileRepository extends MemoryRepository<Car,String> {
    protected String fileName;

    public CarFileRepository(String fileName){
        this.fileName=fileName;
        readFromFile();
    }

    protected abstract void readFromFile();
    protected abstract void writeToFile();

    @Override
    public Car addNewEntity(Car carToAdd){
        super.addNewEntity(carToAdd);
        writeToFile();
        return carToAdd;
    }

    @Override
    public void deleteEntityById(String id) throws Exception{
        super.deleteEntityById(id);
        writeToFile();
    }

    @Override
    public Car updateEntity(Car carToUpdate) throws Exception{
        super.updateEntity(carToUpdate);
        writeToFile();
        return carToUpdate;
    }
}
