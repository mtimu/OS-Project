public class Doctor extends Thread {
    private int id;
    private SharedData sharedData;

    private Patient patient;


    public Doctor(int id , SharedData sharedData) {
        this.id = id;
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        do {

            SharedData.waitOn(sharedData.patientSem);
            SharedData.waitOn(sharedData.mutex);
            patient = sharedData.getPatient();
            System.out.println(patientToDoctorMsg());
            SharedData.signal(sharedData.mutex);
            SharedData.sleep(2);
            System.out.println(report());
            SharedData.signal(patient.doctor);



        } while (true);
    }

    private String patientToDoctorMsg() {
        return String.format("%s -> %s: %d",patient.identity(),identity(),sharedData.time.get());
    }

    private String report() {
        return String.format("%s -> %s: %d" , identity() , patient.identity() , sharedData.time.get());
    }

    private String identity() {
        return String.format("Doctor %d" , id);
    }
}
