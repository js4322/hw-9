import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FrequencyMeterStreamApi {
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
        System.out.println("wordsList.size() = " + wordsList.size());
        //wordsList.sort(Comparator.naturalOrder()); На відміну від першого метода не вдається зробити вторинне сортування за ключем (самим словом)
        Map<String, Integer> wordsCollectionNotOrdered = wordsList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(element -> 1)));
        Map<String, Integer> wordsCollection = wordsCollectionNotOrdered.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.
                                Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        Set<String> words = wordsCollection.keySet();
        Collection<Integer> quantities = wordsCollection.values();
        Iterator<String>itStr = words.iterator();
        Iterator<Integer> itInt = quantities.iterator();
        System.out.println("wordsCollection.size() = " + wordsCollection.size());
        while(itStr.hasNext()){
            System.out.printf("%-30s%d\n", itStr.next(), itInt.next());
            System.out.println("===============================");
        }
    }
}

