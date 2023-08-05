import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class Main {

    static ArrayList<String> list = new ArrayList<>();
    static Path saveFile;
    static boolean needsToBeSaved;

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        final String menu = "A - Add  D - Delete  V - View  Q - Quit\nO - Open  S - Save  C - Clear";
        boolean done = false;
        String cmd;
        boolean firstTime = true;

        do {
            //displays list
            displayList();
            //displays menu options then gets choice
            cmd = SafeInput.getRegExString(in, menu, "[AaDdVvQqOoSsCc]");
            cmd = cmd.toUpperCase();

            //executes choice
            switch (cmd) {
                case "A":
                    addItem(SafeInput.getNonZeroLenString(in, "Enter the item you wish to add"));
                    break;
                case "D":
                    if (list.size()>0){
                        deleteItem(SafeInput.getRangedInt(in, "Enter the number of the item you wish to delete", 1, list.size()));
                        System.out.println("Item successfully removed!");
                    }
                    else {
                        System.out.println("Nothing to delete!");
                    }
                    break;
                case "V":
                    break;
                case "Q":
                    if (SafeInput.getYNConfirm(in, "Are you sure you would like to quit?")) {
                        if (needsToBeSaved && SafeInput.getYNConfirm(in,"Would you like to save your list?")){
                            if (saveFile==null){
                                getSaveFile();
                            }
                            else {
                                saveList();
                            }
                        }
                        done = true;
                    }
                    break;
                case "O":
                    if (needsToBeSaved){
                        if (SafeInput.getYNConfirm(in,"Would you like to save your list?")){
                            if (saveFile==null){
                                getSaveFile();
                            }
                            else {
                                saveList();
                            }
                        }
                    }
                    openList();

                    break;
                case "S":
                    if (needsToBeSaved){
                        saveList();
                        System.out.println("List saved to disk!");
                    }
                    else {
                        System.out.println("List does not need to be saved.");
                    }
                    break;
                case "C":
                    clearList();
                    break;
            }
        } while (!done);
    }

    private static void displayList() {

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%3d%35s%n", i + 1, list.get(i));
                //System.out.printf("%3s%n", list.get(i));
            }
        } else {
            System.out.println("+++            List is empty             +++");
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
    }

    private static void addItem(String item) {
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem(int item) {
        list.remove(item - 1);
        needsToBeSaved = true;
    }

    private static void openList() {
        list.clear();

        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        try{
            File workingDirectory = new File(System.getProperty("user.dir")+"/src");
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while (reader.ready()) {
                    rec = reader.readLine();
                    list.add(rec);
                }
                reader.close();
                System.out.println("\n\nList imported!");
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        needsToBeSaved = false;
    }

    private static void clearList() {
        Scanner in = new Scanner(System.in);
        if (list.size() != 0) {
            if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list?")) {
                int size = list.size();
                for (int i = 0; i < size; i++){
                    deleteItem(1);
                }
            }
        }
        else {
            System.out.println("\nList is already empty!\n");
        }
    }

    private static void saveList() throws IOException {
        if (saveFile==null){
            getSaveFile();
        }

        //entirely rewrite file

        //clear the file
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path oldFile = Paths.get(workingDirectory.getPath() + "/src/list.txt");
        File newFile = new File(oldFile.toUri());
        if(newFile.delete()) {
            newFile.createNewFile();
        }

        try {
            //writer
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(saveFile, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
            
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        needsToBeSaved = false;
    }
    private static void getSaveFile() throws IOException {
        Scanner in = new Scanner(System.in);
        if (!SafeInput.getYNConfirm(in,"Would you like to create a new save file?")){
            JFileChooser chooser = new JFileChooser();
            File selectedFile;

            File workingDirectory = new File(System.getProperty("user.dir") + "/src");
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                saveFile = selectedFile.toPath();
                //saveList();
            }
        }
        else{
            boolean completed;
            do {
                String FileName = SafeInput.getNonZeroLenString(in, "Enter the name of the file you wish to save as");
                if (FileName.contains(".")||FileName.contains("/")){
                    System.out.println("Invalid filename. Filename must not contain a period or slash.");
                    completed = false;
                }
                else {
                    completed = true;
                }

                saveFile = new File(String.valueOf(Path.of(System.getProperty("user.dir") + "/src/" + FileName + ".txt"))).toPath();
                //System.out.println(saveFile);
            }while(!completed);
        }
    }
}