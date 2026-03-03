package friday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    protected String fileName;
    public static final int REQ_NUM_COMPONENTS_FILE = 3;

    private Ui ui = new Ui();

    public Storage(String fileName){
        this.fileName = fileName;
    }
    public void updateFile(TaskList tasks) {
        FileWriter fw;
        try {
            fw = new FileWriter(fileName);
            for (int i = 0; i < tasks.taskCount(); i++) {
                fw.write(tasks.get(i).printString() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            ui.showErrorMessage("io");
        }
    }

    public void createFile() {
        String path = fileName.split("/")[0];
        new File("./"+path).mkdirs();
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write("Testing writing new file");
            fw.close();
        } catch (IOException ex) {
            ui.showErrorMessage("io");
        }
    }

    public ArrayList<Task> loadData() throws FridayException {
        ArrayList<Task> fileTasks = new ArrayList<>();
        try {
            File f;
            f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String[] taskComponents = s.nextLine().split(" [|] ",REQ_NUM_COMPONENTS_FILE);
                String taskType = taskComponents[0];
                String taskDescription = taskComponents[2];
                String taskDoneStatus = taskComponents[1];

                addTaskFromFile(taskType, taskDescription, fileTasks);
                if (taskDoneStatus.equals("1")){
                    fileTasks.get(fileTasks.size()-1).markAsDone();
                }
            }
        } catch (FileNotFoundException e) {
            createFile();
        }
        if (fileTasks.isEmpty()){
            throw new FridayException();
        }
        return fileTasks;
    }

    private void addTaskFromFile(String taskType, String taskDescription, ArrayList<Task> fileTasks) {
        switch (taskType) {
        case "T":
            fileTasks.add(new Todo(taskDescription));
            break;

        case "D":
            String deadlineDescription;
            String dateTime;

            String[] deadlineDetailsFromFile = taskDescription.split(" [|] ");
            deadlineDescription = deadlineDetailsFromFile[0];
            dateTime = deadlineDetailsFromFile[1];

            fileTasks.add(new Deadline(deadlineDescription, dateTime));
            break;

        case "E":
            String eventDescription;
            String eventFrom;
            String eventTo;

            String[] eventDetailsFromFile = taskDescription.split(" [|] ");
            String[] eventTimeDetailsFromFile = eventDetailsFromFile[1].split("-");

            eventDescription = eventDetailsFromFile[0];
            eventFrom = eventTimeDetailsFromFile[0];
            eventTo = eventTimeDetailsFromFile[1];

            fileTasks.add(new Event(eventDescription, eventFrom, eventTo));
            break;
        }
    }
}
