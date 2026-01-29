import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String line = "\t____________________________________________________________";
        System.out.println(line + "\n\t Hello! I'm Friday\n\t What can I do for you?\n" + line + "\n");
        Task[] tasks = new Task[100];
        int commandCount = 0;
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (! command.equals("bye")) {
            System.out.println(line);
            if (command.equals("list")){
                System.out.println("\tHere are the tasks in your list:");
                for (int i = 0; i < commandCount; i++){
                    System.out.println("\t" + (i + 1) + ". [" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
            } else if (command.substring(0,4).equals("mark") || command.substring(0,6).equals("unmark")) {
                int taskIndex = Integer.parseInt(command.split(" ")[1]);
                if (command.substring(0, 4).equals("mark")) {
                    tasks[taskIndex - 1].markAsDone();
                    System.out.println("\tNice! I've marked this task as done:");
                } else {
                    tasks[taskIndex - 1].setDone(false);
                    System.out.println("\tOK, I've marked this task as not done yet:");
                }
                System.out.println("\t" + "  [" + tasks[taskIndex - 1].getStatusIcon() + "] " + tasks[taskIndex - 1].getDescription());

            } else {
                Task t = new Task(command);
                tasks[commandCount] = t;
                System.out.println("\t added: " + command);
                commandCount++;
            }
            System.out.println(line + "\n");
            command = in.nextLine();

        }
        System.out.println(line + "\n\tBye. Hope to see you again soon!\n" + line);
    }
}
