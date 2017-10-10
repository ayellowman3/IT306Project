import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class LibrarySystem{
    public static void main(String[] args){
        String userPath = "./user.txt";
        String resourcePath = "./resource.txt";
        User[] user = new User[1000];
        Resource[] resource = new Resource[1000];
        popUser(user, userPath);
        popResource(resource, resourcePath);
        mainGUI(user, resource);
        writeUser(user, userPath);
        writeResource(resource, resourcePath);
    }

    public static void mainGUI(User[] user, Resource[] resource){
        if(User.getCount() == 0){
            createAdmin(user);
            mainGUI(user, resource);
        }
        else{
            loginMenu(user, resource);
        }
    }

    public static void loginMenu(User[] user, Resource[] resource){

        int option = 0;
        String message = "Welcome\n" +
                        "[1] Login\n" +
                        "[2] Create new account\n" +
                        "[3] Exit";
        while(option != 3){
            option = getIntInput(message, 1, 3);
            switch(option){
                case 1:
                    login(user, resource);
                    break;
                case 2:
                    createNewUser(user);
                    break;
                case 3:
                    break;
                default:
                    option = 0;
                    break;
            }
        }
    }

    public static void login(User[] user, Resource[] resource){
        String email;
        String password;

        email = stringInput("Enter email");
        password = stringInput("Enter password");

        for(int i = 0; i < User.getCount(); i++){
            if(user[i].getEmail().equals(email)){
                if(user[i].getPassword().equals(password)){
                    successLogin(user[i], resource);
                }else{
                    System.out.println("Invalid Password");
                }
            }
        }
        System.out.println("here");
    }

    public static void successLogin(User user, Resource[] resource){
        if(user instanceof Admin){
            System.out.println("Admin");
        }
        else if(user instanceof Employee){
            System.out.println("Employee");
        }else{
            userGUI(resource);
        }
    }

    public static void userGUI(Resource[] resource){
        Resource[] tempResource = new Resource[1000];
        tempResource = copyResource(resource);

        String gui = "[1] Display resources\n" +
                    "[2] Sort by ID\n" +
                    "[3] Sort by Author\n" +
                    "[4] Sort by Category\n" +
                    "[5] Exit";
        int option = 0;
        while(option != 5){
            option = getIntInput(gui, 1, 5);
            switch(option){
                case 1:displayResources(tempResource);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    option = 0;
                    break;
            }
        }

    }

    public static void employeeGUI(){

    }

    public static void adminGUI(){

    }

    public static void displayResources(Resource[] resource){
        String display;

        display = "List of resources\n\n[1]Sort\n[2]Filter\n[3]Exit";
        for(int i = 0; i < Resource.getCount(); i++){
            display += "\n" + tempResource[i].getName() + " ";
            if(resource[i].getCheckedOut()){
                display += "Checked Out";
            }else{
                display += "In stock";
            }
        }

        int input = getIntInput(display, 1, 3);
        switch(input){
            case 1:
                System.out.println("sort");
                break;
            case 2:
                System.out.println("Filter");
                break;
        }
    }

    public static void sort(Resource[] resource){
        String message = "What would you like to sort by?\n\n" +
                        "[1] ID\n" +
                        "[2] Author\n" +
                        "[3] Genre";
    }

    public static void popUser(User[] user, String path){
        int counter = 0;
        File file = new File(path);

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String nextLine;

            String type;
            String email;
            String password;

            while((nextLine = br.readLine()) != null){
                type = stripType(nextLine);
                email = stripMid(nextLine, 1, 2);
                password = stripLast(nextLine);
                if(type.equals("Admin")){
                    user[counter] = new Admin(email, password);
                }
                else if(type.equals("Employee")){
                    user[counter] = new Employee(email, password);
                }
                else{
                    user[counter] = new User(email, password);
                }
                counter++;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void popResource(Resource[] re, String path){
        int counter = 0;
        File file = new File(path);

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String nextLine;
            String type;
            String name;
            Boolean checkedOut;

            while((nextLine = br.readLine()) != null){
                type = stripType(nextLine);
                name = stripMid(nextLine, 1, 2);
                checkedOut = stripLast(nextLine).equals("True");
                re[counter] = new Resource(type, name, checkedOut);
                counter++;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeUser(User[] user, String path){
        PrintWriter pw = null;
        String email;
        String password;
        String write;
        final String Space = " : ";
        try{
            pw = new PrintWriter(path);
            for(int i = 0; i < User.getCount(); i++){
                if(user[i] != null){
                    email = user[i].getEmail();
                    password = user[i].getPassword();
                    if(i > 0){
                        write = "\n";
                    }else{
                        write = "";
                    }
                    if(user[i] instanceof Admin){
                        write += "Admin" + Space;
                    }else if(user[i] instanceof Employee){
                        write += "Employee" + Space;
                    }else{
                        write += "User" + Space;
                    }
                    write += email + Space + password;
                    pw.write(write);
                }
            }
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			pw.close();
		}
    }

    public static void writeResource(Resource[] re, String path){
        PrintWriter pw = null;
        String write;
        String type;
        String name;
        String checkedOut;
        final String Space = " : ";
        try{
            pw = new PrintWriter(path);
            for(int i = 0; i < Resource.getCount(); i++){
                if(re[i] != null){
                    type = re[i].getType();
                    name = re[i].getName();
                    if(re[i].getCheckedOut()){
                        checkedOut = "True";
                    }else{
                        checkedOut = "False";
                    }
                    if(i > 0){
                        write = "\n";
                    }else{
                        write = "";
                    }
                    write += type + Space + name + Space + checkedOut;
                    pw.write(write);
                }
            }
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			pw.close();
		}
    }

    public static String stripType(String line){
        int first = 0;
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == ':'){
                return line.substring(0, i-1);
            }
        }
        return "";
    }

    public static String stripMid(String line, int uno, int dos){
        int counter = 0;
        int first = 0;
        int second = 0;

        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == ':'){
                counter++;
                if(counter == uno){
                    first = i;
                }
                if(counter == dos){
                    second = i;
                }
            }
        }
        return line.substring(first + 2, second - 1);
    }

    public static String stripLast(String line){
        int last = 0;
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == ':'){
                last = i;
            }
        }

        return line.substring(last+2, line.length());
    }

    public static void createAdmin(User[] user){
        String email;
        String password;

        email = stringInput("Enter Admin's email");
        password = stringInput("Enter Admin's password");

        user[0] = new Admin(email, password);
    }

    public static void createNewUser(User[] user){
        int index = User.getCount();
        String email;
        String password;

        email = stringInput("Enter user's email");
        password = stringInput("Enter user's password");

        user[index] = new User(email, password);
    }

    public static String stringInput(String message){
        String s = "";
        s = JOptionPane.showInputDialog(message);
        if(s.equals("")){
            JOptionPane.showMessageDialog(null, "Error! Invalid input!");
            s = stringInput(message);
        }
        return s;
    }

    public static int getIntInput(String message, int min, int max){
        int input = 0;
        try{
            input = Integer.parseInt(JOptionPane.showInputDialog(message));
        }catch(NumberFormatException e){
            input = 0;
        }
        if(input < min || input > max){
            JOptionPane.showMessageDialog(null, "Error! Invalid input entered!");
            input = getIntInput(message, min, max);
        }
        return input;
    }

    public static Resource[] copyResource(Resource[] resource){
        Resource[] tempResource = new Resource[1000];
        for(int i = 0; i < Resource.getCount(); i++){
            tempResource[i] = resource[i];
        }
        return tempResource;
    }
}
