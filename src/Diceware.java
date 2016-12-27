import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by rotarydialer on 12/21/16.
 */
public class Diceware {

    public static Hashtable<String, String> codeWordTable = new Hashtable<String, String>();
    static Random rng = new Random();

    public static void main(String args[]) throws IOException {
        readFromFile("wordlist.txt");

        System.out.println("Suggested passphrase:");
        System.out.println();
        System.out.println(generatePassPhrase(7));
    }

    private static String generatePassPhrase(int numWords) {
        String phrase="";

        for (int i=0; i <numWords; i++) {
            if (phrase.equals("")) {
                phrase = codeWordTable.get( concatDieRolls(6, 5) );
            } else {
                phrase = phrase + " " + codeWordTable.get( concatDieRolls(6, 5) );
            }
        }

        return phrase;
    }

    private static String concatDieRolls(int numSides, int numDigits) {
        int result;
        String concatResult="";

        for (int i=0; i <numDigits; i++) {
            result = rng.nextInt(numSides - 1) + 1;

            concatResult = concatResult + result;
        }

        return concatResult;
    }

    private static void readFromFile(String fileName) {
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            InputStream res = Diceware.class.getClassLoader().getResourceAsStream(fileName);

            BufferedReader fileReader =	new BufferedReader(new InputStreamReader(res));

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            try {
                while((line = bufferedReader.readLine()) != null) {

                    if (line.trim() != "" && !line.trim().isEmpty()) {

                        codeWordTable.put(line.substring(0, 5), line.substring(6, line.length()));

                    } else {
                        // ignore empty lines
                    }

                }
            } catch (Exception e) {
                System.out.println(fileName + " ERROR: " + e.getLocalizedMessage() );
                e.printStackTrace();
            } finally {
                // Always close files.
                bufferedReader.close();
            }

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '" + fileName + "'");
        }
    }

}