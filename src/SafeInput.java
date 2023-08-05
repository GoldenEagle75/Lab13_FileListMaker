import java.util.Scanner;

public class SafeInput {
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString;  // Set this to zero length. Loop runs until it isn’t
        do {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);

        return retString;

    }

    public static int getInt(Scanner pipe, String prompt){
        int retInt = 0;
        String trash;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()){
                retInt = pipe.nextInt();
                done = true;
            }
            else{
                trash = pipe.nextLine();
                System.out.println("Enter a valid value, not " + trash + ".");
                //pipe.nextLine();
            }
        }while(!done);
        return retInt;
    }
    public static double getDouble(Scanner pipe, String prompt){
        double retDouble = 0;
        String trash;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()){
                retDouble = pipe.nextDouble();
                done = true;
            }
            else{
                System.out.println();
                trash = pipe.nextLine();
                System.out.print("Enter a valid value, not " + trash + ".\n");
            }
        }while(!done);
        return retDouble;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high){
        String trash;
        boolean done = false;
        int retInt = 0;

        do {
            System.out.print("\n" + prompt + " (in range " + low + "-" + high + ") : ");
            if (pipe.hasNextInt()){
                retInt = pipe.nextInt();
                if (retInt >= low && retInt <= high){
                    done = true;
                }
                else {
                    System.out.println("Enter a valid value in range " + low + "-" + high + ", not " + retInt + ".");
                    pipe.nextLine();
                }
            }
            else {
                System.out.println();
                trash = pipe.nextLine();
                System.out.println("Enter a valid value in range " + low + "-" + high + ", not " + trash + ".");
            }
        } while(!done);

        pipe.nextLine();

        return retInt;
    }


    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high){
        String trash;
        boolean done = false;
        double retDouble = 0;

        do {
            System.out.print("\n" + prompt + " (in range " + low + "-" + high + ") : ");
            if (pipe.hasNextDouble()){
                retDouble = pipe.nextDouble();
                if (retDouble >= low && retDouble <= high){
                    done = true;
                }
                else {
                    System.out.println("Enter a valid value in range " + low + "-" + high + ", not " + retDouble + ".");
                    pipe.nextLine();
                }
            }
            else {
                System.out.println();
                trash = pipe.nextLine();
                System.out.println("Enter a valid value in range " + low + "-" + high + ", not " + trash + ".");
            }
        } while(!done);

        pipe.nextLine();

        return retDouble;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt){
        String input;
        boolean confirmation = false;
        boolean done;

        do {
            System.out.print("\n" + prompt + " ");

            input = pipe.nextLine();

            if (input.equalsIgnoreCase("Y")){
                confirmation = true;
                done = true;
            }
            else if (input.equalsIgnoreCase("N")){
                done = true;
            }
            else {
                done = false;
                System.out.println();
                System.out.println("Enter either Y or N, not " + input + ".");
            }
        } while (!done);

        return confirmation;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx){
        String input;
        do {
            System.out.print("\n" +prompt + ": ");
            input = pipe.nextLine();
        }while (!input.matches(regEx));

        return input;
    }

    public static void prettyHeader(String msg){
        double numberOfSpaces;
        for (int i = 1; i <= 60; i ++){
            System.out.print("*");
        }

        System.out.println();

        for (int i = 1; i <= 3; i ++){
            System.out.print("*");
        }

        numberOfSpaces = 27 - (double) (msg.length()) /2;
        int remainder = msg.length() % 2;

        for (int i = 1; i <= numberOfSpaces; i++){
            System.out.print(" ");
        }

        if(remainder != 0) {
            numberOfSpaces += 1;
        }

        System.out.print(msg);

        for (int i = 1; i <= numberOfSpaces; i++){
            System.out.print(" ");
        }

        for (int i = 1; i <= 3; i ++){
            System.out.print("*");
        }
        System.out.println();

        for (int i = 1; i <= 60; i ++){
            System.out.print("*");
        }

        System.out.println();
    }

    public static double CtoF(double Celsius){
        double fahrenheitTemp;
        fahrenheitTemp = (Celsius*9/5)+32;
        return fahrenheitTemp;
    }
}