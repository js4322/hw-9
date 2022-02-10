import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

public final class PhoneNumberReader {
    private static final String fileName = "file.txt";
    public static void phoneNumberPrinter(){
        Reader fileReader;
        try {
            fileReader = new FileReader(fileName);
        }
        catch(FileNotFoundException e){
            System.out.println("file " + fileName + " does not exist\n" + e.getStackTrace());
            return;
        }
        StringBuilder lines = new StringBuilder();
        int nextChar = 0;
        while (nextChar != -1) {
            try {
                nextChar = fileReader.read();
            }
            catch (IOException e){
                System.out.println("IO exception in " + fileName + " in method phoneNumberPrinter()");
            }
            if(nextChar == -1)
                break;
            lines.append((char) nextChar);
        }
        String[] linesArr = lines.toString().split("\n");
        for (String line: linesArr){
            Pattern pattern = Pattern.compile(
                    "([(][0123456789]{3}[)][ ][0123456789]{3}[\\-][0123456789]{4})" +
                    "|" +
                    "([0123456789]{3}[\\-][0123456789]{3}[\\-][0123456789]{4})");
            if(pattern.matcher(line.strip()).matches())
                System.out.println(line);
        }

        try {
            fileReader.close();
        }
        catch (IOException e){
            System.out.println("Data wasn't correctly wrote into " + fileName + "\t in phoneNumberPrinter()");
        }
    }
}
