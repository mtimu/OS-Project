import java.util.concurrent.Semaphore;

public class Patient extends Thread {
    private int id;
    private int arrivalTime;
    private SharedData sharedData;
    public Semaphore doctor;


    public Patient(int id , int arrivalTime , SharedData sharedData) {
        doctor = new Semaphore(0);
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        SharedData.sleep( (float) (arrivalTime + 0.01));

        SharedData.waitOn(sharedData.mutex);
        if (sharedData.isWaitingFull()) System.out.println(patientExitMsg());
        else {
            sharedData.addPatient(this);
            SharedData.signal(sharedData.patientSem);
        }
        SharedData.signal(sharedData.mutex);
        SharedData.waitOn(doctor);

    }

    private String patientExitMsg() {
        return identity() + " exit!";
    }


    public String identity() {
        return String.format("Patient %d" , id);
    }
}
