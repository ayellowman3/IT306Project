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
        User[] user = new User[1000];
        popUser(user, userPath);
        mainGUI(user);
        writeUser(user, userPath);
    }

    public static void mainGUI(User[] user){
        if(User.getCount() == 0){
            createAdmin(user);
            mainGUI(user);
        }
        else{
            loginMenu(user);
        }
    }

    public static void loginMenu(User[] user){

        int option = 0;
        String message = "Welcome\n" +
                        "[1] Login\n" +
                        "[2] Create new account\n" +
                        "[3] Exit";
        while(option != 3){
            option = getIntInput(message, 1, 3);
            switch(option){
                case 1:
                    login(user);
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

    public static void login(User[] user){
        String email;
        String password;

        email = stringInput("Enter email");
        password = stringInput("Enter password");

        for(int i = 0; i < User.getCount(); i++){
            if(user[i].getEmail().equals(email)){
                if(user[i].getPassword().equals(password)){
                    successLogin(user[i]);
                }else{
                    System.out.println("Invalid Password");
                }
            }
        }
        System.out.println("here");
    }

    public static void successLogin(User user){
        if(user instanceof Admin){
            System.out.println("Admin");
        }
        else if(user instanceof Employee){
            System.out.println("Employee");
        }else{
            System.out.println("User");
        }
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
}
