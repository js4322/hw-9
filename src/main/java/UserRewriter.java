import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserRewriter {
    private static final String FILE_NAME = "file.txt";
    private static final String USER_JSON = "user.json";
    private static final String INCORRECT_NAME_EXAMPLE = "INCORRECT_NAME";
    public static void reWrite(){
        Reader fileReader;
        try {
            fileReader = new FileReader(FILE_NAME);
        }
        catch(FileNotFoundException e){
            System.out.println("file " + FILE_NAME + " does not exist\n" + e.getStackTrace());
            return;
        }
        Writer jsonWriter;
        try {
            jsonWriter = new FileWriter(USER_JSON);
        }
        catch (IOException e){
            System.out.println("IO exception while open " + USER_JSON);
            return;
        }
        Gson gson = new Gson();
        StringBuilder lines = new StringBuilder();
        int nextChar = 0;
        while (nextChar != -1) {
            try {
                nextChar = fileReader.read();
            }
            catch (IOException e){
                System.out.println("IO exception in " + FILE_NAME + " in method reWrite()");
            }
            if(nextChar == -1)
                break;
            lines.append((char) nextChar);
        }
        String fileString = lines.toString();
        Reader fileInput = new CharArrayReader(fileString.toCharArray());
        Collection<User> userList = new LinkedList<User>();
        Scanner scanner = new Scanner(fileInput);
        String name;
        int age;
        while(scanner.hasNext()){
            try{
                name = scanner.next("([a-z]|[A-Z])*");
            }
            catch(NoSuchElementException e){
                name = INCORRECT_NAME_EXAMPLE;
            }
            try {
                age = scanner.nextInt();
            }
            catch(NoSuchElementException e){
                age = -1;
            }
            if(name == INCORRECT_NAME_EXAMPLE || age == -1){
                //userList.add(new User(INCORRECT_NAME_EXAMPLE, -1));
                continue;
            }
            userList.add(new User(name, age));
        }
        Iterator<User> it = userList.iterator();
        while (it.hasNext()){
            try {
                jsonWriter.write(gson.toJson(it.next()));
            }
            catch(IOException e){
                System.out.println("IO exception while writing to " + USER_JSON + " in method reWrite()");
            }
        }
        try{
            jsonWriter.close();
        }
        catch (IOException e){
            System.out.println("IO exception while closing " + USER_JSON + "\t in reWrite()");
        }
        try {
            fileReader.close();
        }
        catch (IOException e){
            System.out.println("IO exception while closing " + FILE_NAME + "\t in reWrite()");
        }
    }
}

class User{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}