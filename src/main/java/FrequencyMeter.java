import java.io.*;
import java.util.*;

public class FrequencyMeter {
    public static String WORDS_TXT = "words.txt";
    public static void measureFrequency(){
        List<String> wordsList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(WORDS_TXT))) {
            br.mark(4086);// встановлення максимального куска інформації для прочитання з файла
            String nextLine;
            String[] wordsNextLine;
            while((nextLine = br.readLine()) != null){
                wordsNextLine = nextLine.split("\\s+");
                for(String word: wordsNextLine){
                    wordsList.add(word);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Impossible to open file in measureFrequency()\t" + e.getStackTrace());
            return;
        }
        catch (IOException e){
            System.out.println("IO error while reading " + WORDS_TXT + " in measureFrequency()\t" + e.getStackTrace());
            return;
        }
        wordsList.sort(Comparator.naturalOrder());
        System.out.println("wordsList.size() = " + wordsList.size());
        Set<String> wordsSet = new LinkedHashSet<>(wordsList);
        List<Map.Entry<String, Integer>> wordsCollection = new ArrayList<>();
        int quantity;
        ListIterator<String> it = wordsList.listIterator();
        boolean collectetWordIsNext = false;
        String nextWord = "formally initialized"; // компілятор не розуміє що коли змінна collectetWordIsNext == false то ніхто не викликатиме у неініціалізованого стрінга метод іквалс
        for(String distinctWord: wordsSet){
            quantity = 0;
            while(true) {
                if(collectetWordIsNext == true){
                    quantity = 1;
                    collectetWordIsNext = false;
                }
                if (it.hasNext()) {
                    nextWord = it.next();
                    if (nextWord.equals(distinctWord)) {
                        quantity++;
                    }
                    else {
                        wordsCollection.add(new AbstractMap.SimpleEntry(distinctWord, quantity));
                        collectetWordIsNext = true;
                        break;
                    }
                }
                else {
                    wordsCollection.add(new AbstractMap.SimpleEntry(distinctWord, quantity));
                    break;
                }
            }
        }
        wordsCollection.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        System.out.println("wordsCollection.size() = " + wordsCollection.size());
        for(Map.Entry<String, Integer> entry: wordsCollection){
            System.out.printf("%-30s%d\n",entry.getKey(), entry.getValue());
            System.out.println("===============================");
        }
    }
}
