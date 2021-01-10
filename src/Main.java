import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {



        // get number of doctors and waiting capacity
        System.out.println("Enter m n");
        System.out.print("->");
        String[] rawInput = readLine().split(" ");

        int m = Integer.parseInt(rawInput[0]);
        int n = Integer.parseInt(rawInput[1]);

        // get patients
        System.out.println("Enter Patients Arrival time in one line, separated by space");
        System.out.print("->");
        String arrivalTimes = readLine();

        SharedData sharedData = new SharedData(m);

        Stream<Doctor> doctors = getDoctors(sharedData , n);
        Stream<Patient> patients = getPatients(arrivalTimes , sharedData);
        Thread time = new Thread(() -> {
            do {
                SharedData.sleep(1);
                sharedData.time.getAndIncrement();
            } while (true);
        });

        doctors.forEach(Thread::start);
        patients.forEach(Thread::start);
        time.start();
    }


    public static Stream<Patient> getPatients(String arrivalTimes, SharedData sharedData) {
        AtomicInteger id = new AtomicInteger();
        return Stream.of(arrivalTimes.split(" "))
                .map(Integer::parseInt)
                .sorted()
                .map(arrivalTime -> new Patient(id.getAndIncrement() , arrivalTime, sharedData));
    }

    public static Stream<Doctor> getDoctors(SharedData sharedData, int n) {
        AtomicInteger id = new AtomicInteger();
        return IntStream.range(0 , n).mapToObj(value -> new Doctor(id.getAndIncrement() , sharedData));
    }

    public static String readLine() {
        return input.nextLine();
    }

}
