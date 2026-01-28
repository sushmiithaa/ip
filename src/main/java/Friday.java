import java.util.Arrays;
import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String line = "\t____________________________________________________________";
        System.out.println(line +"\n\t Hello! I'm Friday\n\t What can I do for you?\n" + line + "\n");
        String[] inputs = new String[100];
        int commandCount = 0;
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (! command.equals("bye")) {
            System.out.println(line);
            if (command.equals("list")){
                for (int i = 0;i < commandCount;i++){
                    System.out.println("\t" +(i+1) + ". " + inputs[i]);
                }
            }
            else if (command.isBlank()){
                System.out.println("\tNothing has been entered");
            }
            else{
                System.out.println("\t added: " + command);
                inputs[commandCount] = command;
                commandCount++;
            }
            System.out.println(line+ "\n");
            command = in.nextLine();

        }
        System.out.println(line + "\n\tBye. Hope to see you again soon!\n" + line);
    }
}
