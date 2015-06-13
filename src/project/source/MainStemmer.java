package project.source;

import java.io.*;

public class MainStemmer {
    protected static String SAVE_DIRECTORY = "stemmedFiles" + File.separator ;
    public String ExistingFileName;
    File file;

    // public int  LineNumber;

/* public static void main(String[] args) {
        String word = "প্রতিভা";
        System.out.println(word.substring(3, word.length()) + " " + "আড়".length());
        Stemmer st = new Stemmer();
        st.setWord("\",Existing:");
        System.out.println(st.getWord());
//        new MainStemmer(new File(DocumentProcessor.OUTPUT_FOLDER));
    }*/


    public MainStemmer(File file){
        this.file = file;
        getWordAndFileName();
    }

    public void getWordAndFileName(){
        Stemmer st = new Stemmer();

        File file = this.file;

        ExistingFileName = file.getName();

        try {
            String temp;

            boolean flag = false;

            StringBuilder sb = new StringBuilder();

            BufferedReader reader = new BufferedReader(new FileReader(file));

            // LineNumber = 0;
            try {
                while((temp = reader.readLine()) != null){

                    if(!flag){
                        sb.append(temp).append("\n");
                        flag = true;
                    }
                    else {
                        String [] words = temp.split(" ");

                        for(String str : words){
                            if(str.length() < 2)
                                continue;
                            st.setWord(str);
                            sb.append(st.getWord()).append("\n");
                        }
                    }
                }

                makeFile(sb.toString() , SAVE_DIRECTORY + ExistingFileName );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void makeFile(String value, String fileName) {


        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(fileName);
            outputStream.write(value.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}