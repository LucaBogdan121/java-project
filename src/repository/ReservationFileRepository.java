package repository;
import domain.Car;
import domain.Reservation;

public abstract class ReservationFileRepository extends MemoryRepository<Reservation,String>{
    protected String fileName;

    public ReservationFileRepository(String fileName){
        this.fileName=fileName;
        readFromFile();
    }

    public abstract void readFromFile();
    public abstract void  writeToFile();

    @Override
    public Reservation addNewEntity(Reservation reservationToAdd){
        super.addNewEntity(reservationToAdd);
        writeToFile();
        return reservationToAdd;
    }

    @Override
    public void deleteEntityById(String id) throws Exception{
        super.deleteEntityById(id);
        writeToFile();
    }

    @Override
    public Reservation updateEntity(Reservation reservationToUpdate) throws Exception{
        super.updateEntity(reservationToUpdate);
        writeToFile();
        return reservationToUpdate;
    }
}

