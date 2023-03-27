import java.util.ArrayList;
import java.net.*;
import java.io.*;


//Create a Stack class that will be part of the algorithm

class Stack {

    private String[] elements;
    private int top;

    public Stack(int capacity){
        this.elements = new String[capacity];
        this.top = -1;
    }

    public void push(String element){
        if (this.top == this.elements.length - 1) {
            throw new IllegalStateException("Pilha cheia");
        }
        this.top++;
        this.elements[this.top] = element;
    }

    public boolean isEmpty() {
        return this.top == -1;
    }

    public String pop(){
        if (this.isEmpty()) {
            throw new IllegalStateException("Empty stack");
        }
        
        String element = this.elements[this.top];
        this.top--;
        return element;
    }

    public int size(){
        return this.top + 1;
    }

    public String peek(){
        if(this.isEmpty()){
            throw new IllegalStateException("Empty stack");
        }

        return this.elements[this.top];
    }
}


public class HtmlAnalyzer {

    //This method will process the string with the html code
    // in an array of strings that contains each element
    public static ArrayList<String> htmlCodeTextProcess(String htmlCodeText){

        String[] wordsArray = htmlCodeText.split(" ");
        ArrayList<String> validStringsArray = new ArrayList<>(); 
        for(int i = 0; i<wordsArray.length; i++){
            if (!(wordsArray[i].length() == 0 || wordsArray[i] == "\n")){
                validStringsArray.add(wordsArray[i]);
            }
        }
        
        ArrayList<String> resultArray = new ArrayList<>(); 
        String stringAccumulator = "";
        String word;
        for (int i = 0; i < validStringsArray.size();i++){
            word = validStringsArray.get(i);
            if(word.charAt(0) == '<' && word.charAt(word.length()-1) == '>'
                && word.length() > 2){

                if (stringAccumulator.length() != 0){
                    stringAccumulator=stringAccumulator.substring(1);
                    resultArray.add(stringAccumulator);
                    stringAccumulator = "";
                    resultArray.add(word);
                    continue;
                }
                resultArray.add(word);
            }
            else{
                if (stringAccumulator == ""){
                    stringAccumulator = " ".concat(word);
                }
                else{
                    stringAccumulator=stringAccumulator.concat(" ");
                    stringAccumulator = stringAccumulator.concat(word);
                    
                }
            }
        }
        return resultArray;
    }



    //This method checks if the string is a open tag
    public static boolean isOpenedTag(String text){
        return text.charAt(0) == '<' && text.charAt(text.length()-1) == '>' && text.charAt(1) != '/';
    }   



    //This method checks if the string is a close tag
    public static boolean isClosedTag(String text){
        return text.charAt(0) == '<' && text.charAt(text.length()-1) == '>' && text.charAt(1) == '/';
    }



    //This method change a close to opened tag
    public static String changeFromClosedToOpenTag(String tag){
        String new_tag = "";
        for (int i = 0; i<tag.length(); i++){
            if(i == 1){
                continue;
            }
            new_tag = new_tag + tag.charAt(i);
        }
        return new_tag;
    }


    //This method receives two arrays:
    // -> one with the depths of the HTML texts
    // ->one with the HTML texts in the same index of their texts
    // return the first most deeper text of these two arrays 
    public static String getFirstMostDeeperText(ArrayList<Integer> depthsNumbers, ArrayList<String> texts){
        int deeperNumber = 0;
        int index = 0;
        for (int i = 0; i<texts.size(); i++){
            if (depthsNumbers.get(i) > deeperNumber){
                deeperNumber  = depthsNumbers.get(i);
                index = i;
            }
        }
        return texts.get(index);
    }



    //This method get an html code through a URL
    public static String getHtmlCodeBySocket(String urlString){
        try{
            URL url = new URL(urlString);
            URLConnection conexao = (URLConnection) url.openConnection();
            conexao.setRequestProperty("Request-Method", "GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String htmlCodeText = "";
            String line = "";
            while (null != ((line = br.readLine()))) {
                htmlCodeText = htmlCodeText+ line + " ";
                
            }
            br.close();
            return htmlCodeText;

        }catch (Exception e) {
            System.out.println("URL connection error");
            System.exit(0);
        }
        return "";
    }


    //This method is the algorithm that gets the deeper text of HTML code 
    public static String getDeeperText(ArrayList<String> htmlCodeArray){
        
        Stack stk = new Stack(htmlCodeArray.size());
        ArrayList<String> texts = new ArrayList<>();
        ArrayList<Integer> depthsNumbers = new ArrayList<>();
        String tempString = "";
        int depthcounter = 0;

        for (int i = 0; i < htmlCodeArray.size(); i++){
            if(isOpenedTag(htmlCodeArray.get(i))){
                stk.push(htmlCodeArray.get(i));
                depthcounter++;
                continue;
            }
            else if (isClosedTag(htmlCodeArray.get(i))){
                tempString = changeFromClosedToOpenTag(htmlCodeArray.get(i));

                while (stk.peek().equals(tempString) == false){
                    if(isOpenedTag(stk.peek()) == true){
                        return "malformed HTML";
                    }
                    stk.pop();
                    depthcounter--;
                    if (stk.isEmpty() ==  true){
                        break;
                    }
                }
                if (stk.isEmpty() == false){
                    stk.pop();
                    depthcounter--;}
            }
            else{
                stk.push(htmlCodeArray.get(i));
                depthcounter++;
                texts.add(htmlCodeArray.get(i));
                depthsNumbers.add(depthcounter);
            }
        }

        if (stk.isEmpty() != true){
            return "malformed HTML";
        }

        String result = getFirstMostDeeperText(depthsNumbers, texts);
        return result;
    }

    
    //The main of the code
    public static void main(String[] args) throws Exception {

        String url = args[0];

        String htmlCodeText = getHtmlCodeBySocket(url);
        ArrayList<String> text_processed = htmlCodeTextProcess(htmlCodeText);
        String result = getDeeperText(text_processed);
        System.out.println(result);
    }
}
