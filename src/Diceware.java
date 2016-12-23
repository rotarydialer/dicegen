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
        System.out.println("START ___");

        readFromFile("wordlist.txt");

        System.out.println(String.format("Phrase: \"%1$s\"", generatePassPhrase(7) ));

        System.out.println("___   END");
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

        System.out.println(">>> " + concatResult);

        return concatResult;
    }

    private static void readFromFile(String fileName) {
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            InputStream res = Diceware.class.getClassLoader().getResourceAsStream(fileName);

            BufferedReader fileReader =	new BufferedReader(new InputStreamReader(res));

            //FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            try {
                while((line = bufferedReader.readLine()) != null) {

                    if (line.trim() != "" && !line.trim().isEmpty()) {

                        codeWordTable.put(line.substring(0, 5), line.substring(6, line.length()));

                        //System.out.println(String.format("  -> K: \"%1$s\" / V: \"%2$s\"", line.substring(0, 5), line.substring(6, line.length()) ));

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
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }

}