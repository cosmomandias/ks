import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class WorkerHandler extends Thread {
    Socket connection;
    ObjectInputStream input;
    ObjectOutputStream output;
    static int workerid = 0;
    static int managerid = 0;
    int id;
    int type;// 1 for workers 2 for Managers
    static ArrayList<WorkerHandler> workerhandlers = new ArrayList<WorkerHandler>();
    static ArrayList<WorkerHandler> managerhandlers = new ArrayList<WorkerHandler>();

    public WorkerHandler(Socket connection) {
        try {
            this.connection = connection;
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
            // workerhandlers.add(this);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("This Worker has id :" + id);
            this.type = input.readInt();

            switch (type) {
                case 1:
                    this.id = workerid;
                    workerid++;
                    workerhandlers.add(this);
                    break;
                case 2:
                    this.id = managerid;
                    managerid++;
                    managerhandlers.add(this);
                    break;
                default:
                    break;
            }
            int i = 0;
            while (connection.isConnected()) {
                Accommodation acc1 = new Accommodation("Billy's rooms", 1, "Peristeri", 10, 300);
                System.out.println("Which id?");
                Random rand = new Random();
                int whichid = i;
                System.out.println("Good choice");
                output.writeInt(whichid);
                output.flush();
                output.writeObject(acc1);
                output.flush();
                i++;
                i = i % 3;
            }

            Accommodation acc1 = new Accommodation("Billy's rooms", 1, "Peristeri", 10, 300);
            output.writeObject(acc1);

            output.flush();
            System.out.println(workerhandlers.size());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public Accommodation createAccommodation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Accommodation name :");
        String roomName = scanner.nextLine();

        System.out.println("Number of people :");
        int nOfPeople = scanner.nextInt();

        System.out.println("Area :");
        String area = scanner.nextLine();

        System.out.println("Stars :");
        int stars = scanner.nextInt();

        System.out.println("Number of reviews :");
        int nOfReviews = scanner.nextInt();

        Accommodation acc = new Accommodation(roomName, nOfPeople, area, stars, nOfReviews);
        scanner.close();
        return acc;
    }

}
