import java.io.File;
import java.io.IOException;
import java.sql.Time;

public class Core {
    public static void main(String[] args) {
        //fileCreator("file.txt");
        //PhoneNumberReader.phoneNumberPrinter();
        //fileCreator("words.txt");
        //UserRewriter.reWrite();
        long t1 = System.currentTimeMillis();
        FrequencyMeter.measureFrequency();
        long t2 = System.currentTimeMillis();
        FrequencyMeterStreamApi.measureFrequency();
        long t3 = System.currentTimeMillis();
        System.out.println("FrequencyMeter.measureFrequency() working time = " + (t2 - t1) + "\nFrequencyMeterStreamApi.measureFrequency() working time = " + (t3-t2));
    }
    private static File fileCreator(String fileName){
        File testFile = new File(fileName);
        if(!testFile.exists()) {
            try {
                testFile.createNewFile();
            }
            catch (IOException ioException){
                System.out.println("IO exception while creating testFile " + fileName);
            }
            catch (SecurityException securityException){
                System.out.println("Security manager denies write to testFile " + fileName);
            }
        }
        return testFile;
    }
}
