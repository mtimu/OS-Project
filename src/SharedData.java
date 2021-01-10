import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {
    public AtomicInteger time;
    public Semaphore patientSem;
    public Semaphore mutex;
    private final int m;

    private final Queue<Patient> patients;


    public SharedData(int m) {
        this.m = m;
        time = new AtomicInteger(0);
        patients = new ArrayDeque<>();
        patientSem = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public Patient getPatient() {
        return patients.poll();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }


    public boolean isWaitingFull() {
        return patients.size() == m;
    }

    public static void waitOn(Semaphore semaphore) {

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void signal(Semaphore semaphore) {
        semaphore.release();
    }

    public static void sleep(float sec) {
        try {
            Thread.sleep((long) (sec * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
