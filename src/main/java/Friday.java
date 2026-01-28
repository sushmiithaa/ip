import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String line = "\t____________________________________________________________";
        System.out.println(line +"\n\t Hello! I'm Friday\n\t What can I do for you?\n" + line + "\n");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (! command.equals("bye")) {
            System.out.println(line + "\n\t" + command + "\n" + line);
            System.out.println();
            command = in.nextLine();
        }
        System.out.println(line + "\n\tBye. Hope to see you again soon!\n" + line);
    }
}
