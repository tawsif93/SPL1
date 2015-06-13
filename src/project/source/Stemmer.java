package project.source;


public class Stemmer {
    private String Word;

    public Stemmer(){
    }

    public Stemmer(String word){
        this.Word = word;
        stem();
    }
    
    public boolean checkConsonant(int index){
        switch(Word.charAt(index)){
            case 'a' : case 'e' : case 'i': case 'o' : case 'u': return false;
            case 'y' : {
                return index == 0 || !checkConsonant(index - 1);
            }
            default: return true;
        }
    }
    
    public int m(String temp){
        int n = 0;
        int a = 0;
        while(true){
            if(a >= temp.length())
                return n;
            if(checkConsonant(a++)) break;
            
        }
        while(true){
            while(true){
                if(a >= temp.length())
                    return n;
                if(checkConsonant(a++))
                    break;
            }
            n++;
            while(true){
                if(a >= temp.length())
                    return n;
                if(!checkConsonant(a++)) break;
            }
        }
    }
    
    public boolean vowelExist(){
        for (int counter = 0; counter < Word.length(); counter++) {
            if(!checkConsonant(counter))
                return true;
        }
        return false;
    }
    
    public boolean doubleConstant(int l){
        if(l < 1) return false;
        if(Word.charAt(l) != Word.charAt(l-1)) return false;
        return checkConsonant(l);
    }
    
    public boolean cvc(int num){
        if(num < 2 || !checkConsonant(num) || checkConsonant(num-1) || !checkConsonant(num-2))
            return false;
        char ch = Word.charAt(num);
        return !(ch == 'w' || ch == 'x' || ch == 'y');


    }


    public void step1(){
        if(Word.charAt(Word.length() - 1) == 's'){
            if(Word.endsWith("sses")){
                Word = Word.substring(0, (Word.length() - 2));
            }
            else if(Word.endsWith("ies")){
                Word = Word.substring(0, (Word.length() - 3)) + "i"; //Word.replace("ies", "i");
            }
            else if(Word.charAt(Word.length() - 2) != 's'){
                Word = Word.substring(0, (Word.length() - 1));
            }
        }
        if(Word.endsWith("eed")){
            String temp = Word.substring(0, (Word.length() - 1));
            if(m(temp) > 0){
                Word = temp;
            }
        }
        else if(Word.endsWith("ing") && vowelExist()){
            Word = Word.substring(0, (Word.length() - 3));
            if(Word.endsWith("at"))
                Word += "e";
            else if(Word.endsWith("bl"))
                Word += "e";
            else if(Word.endsWith("iz"))
                Word += "e";


            else if(doubleConstant(Word.length() - 1)){
                char ch = Word.charAt(Word.length() - 1);
                if(ch == 'l' || ch == 's' || ch == 'z'){}
                else Word = Word.substring(0, (Word.length() - 1));
            }
            else if(m(Word) == 1 && cvc(Word.length() - 1))
                Word += "e";
        }
        else if(Word.endsWith("ed") && vowelExist()){
                Word = Word.substring(0, (Word.length() - 2));
            if(Word.endsWith("at"))
                Word += "e";
            else if(Word.endsWith("bl"))
                Word += "e";
            else if(Word.endsWith("iz"))
                Word += "e";


            else if(doubleConstant(Word.length() - 1)){
                char ch = Word.charAt(Word.length() - 1);
                if(ch == 'l' || ch == 's' || ch == 'z'){}
                else Word = Word.substring(0, (Word.length() - 1));
            }
            else if(m(Word) == 1 && cvc(Word.length() - 1))
                Word += "e";
        }
    }

    public void step2(){
        if(Word.length() == 0)
            return;
        if(Word.charAt(Word.length()-1) == 'y' && vowelExist()){
            Word = Word.substring(0, Word.length()-1) + 'i';
        }
    }

    public void step3(){
        String temp = "";
        if(Word.endsWith("ational")){
            temp = Word.substring(0, (Word.length()-7));
            if(m(temp) > 0){
                Word = temp + "ate";
                return;
            }
        }
        
        if(Word.endsWith("tional")){
            temp = Word.substring(0, (Word.length()-6));
            if(m(temp) > 0){
                Word = temp + "tion";
                return;
            }
        }
        
        if(Word.endsWith("enci")){
            temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 0){
                Word = temp + "ence";
                return;
            }
        }
        
        if(Word.endsWith("anci")){
            temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 0){
                Word = temp + "ance";
                return;
            }
        }
        
        if(Word.endsWith("izer")){
            temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 0){
                Word = temp + "ize";
                return;
            }
        }
        
        if(Word.endsWith("bli")){
            temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 0){
                Word = temp + "ble";
                return;
            }
        }
        
        if(Word.endsWith("alli")){
            temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 0){
                Word = temp + "al";
                return;
            }
        }
        
        if(Word.endsWith("entli")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 0){
                Word = temp + "ent";
                return;
            }
        }
        
        if(Word.endsWith("eli")){
            temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 0){
                Word = temp + "e";
                return;
            }
        }
        
        if(Word.endsWith("ousli")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 0){
                Word = temp + "ous";
                return;
            }
        }
        
        if(Word.endsWith("ization")){
            temp = Word.substring(0, (Word.length()-7));
            if(m(temp) > 0){
                Word = temp + "ize";
                return;
            }
        }
        
        if(Word.endsWith("ation")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 0){
                Word = temp + "ate";
                return;
            }
        }
        
        if(Word.endsWith("ator")){
            temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 0){
                Word = temp + "ate";
                return;
            }
        }
        
        if(Word.endsWith("alism")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 0){
                Word = temp + "al";
                return;
            }
        }
        
        if(Word.endsWith("iveness")){
            temp = Word.substring(0, (Word.length()-7));
			if (m(temp) > 0){
                Word = temp + "ive";
                return;
            }
        }
        
        if(Word.endsWith("fulness")){
            temp = Word.substring(0, (Word.length()-7));
            if(m(temp) > 0){
                Word = temp + "ful";
                return;
            }
        }
        
        if(Word.endsWith("ousness")){
            temp = Word.substring(0, (Word.length()-7));
            if(m(temp) > 0){
                Word = temp + "ous";
                return;
            }
        }
        
        if(Word.endsWith("aliti")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 0){
                Word = temp + "al";
                return;
            }
        }
        
        if(Word.endsWith("iviti")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 0){
                Word = temp + "ive";
                return;
            }
        }
        
        if(Word.endsWith("biliti")){
            temp = Word.substring(0, (Word.length()-6));
            if(m(temp) > 0){
                Word = temp + "ble";
                return;
            }
        }
        
        if(Word.endsWith("logi")){
            temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 0)
                Word = temp + "log";
        }
    }

    public void step4(){
        String temp = "";
        if(Word.endsWith("icate")){
            temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 1){
                Word = temp + "ic";
                return;
            }
        }
        
        if(Word.endsWith("ative")){
        	temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        
        if(Word.endsWith("alize")){
        	temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 1){
                Word = temp + "al";
                return;
            }
        }
        
        if(Word.endsWith("iciti")){
        	temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 1){
                Word = temp + "ic";
                return;
            }
        }
        
        if(Word.endsWith("ical")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp + "ic";
                return;
            }
        }
        
        if(Word.endsWith("ful")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        
        if(Word.endsWith("ness")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp + "ic";
            }
        }
    }

    public void step5(){
    	String temp = "";
    	
        if(Word.endsWith("al")){
        	temp = Word.substring(0, (Word.length()-2));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ance")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ence")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("er")){
        	temp = Word.substring(0, (Word.length()-2));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ic")){
        	temp = Word.substring(0, (Word.length()-2));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("able")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ible")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ant")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ement")){
        	temp = Word.substring(0, (Word.length()-5));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ment")){
        	temp = Word.substring(0, (Word.length()-4));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ent")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ion")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ou")){
        	temp = Word.substring(0, (Word.length()-2));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ism")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ate")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("iti")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ous")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ive")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
                return;
            }
        }
        if(Word.endsWith("ize")){
        	temp = Word.substring(0, (Word.length()-3));
            if(m(temp) > 1){
                Word = temp;
            }
        }
    }

    public void step6(){
        if(Word.length() == 0)
            return;
        if(Word.charAt(Word.length() - 1) == 'e'){
            int a = m(Word);
            if(a >= 1  && !cvc(Word.length() - 2))
                Word = Word.substring(0, (Word.length()-1));
            if(Word.charAt(Word.length() - 1) == 'l' && doubleConstant(Word.length() - 1) && m(Word) > 1)
                Word = Word.substring(0, (Word.length()-1));
        }
    }

    public void banglaSonsgskritoUposorgo(){
        if(Word.startsWith("প্ররা")){
            Word = Word.substring(5, Word.length());
            return;
        }

        if(Word.startsWith("প্রতি")){
            Word = Word.substring(5, Word.length());
            return;
        }

        if(Word.startsWith("প্র")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}

    	if(Word.startsWith("পরি")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}

    	if(Word.startsWith("অপ")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অপি")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অতি")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অধি")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অনু")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("দু")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("সম")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("নি")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("নির")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("উপ")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("বি")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অভি")){
    		Word = Word.substring(3, Word.length());
    	}
    }
    
    public void banglaUposorgo(){
    	if(Word.startsWith("অঘা")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অজ")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("অনা")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("আড়")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("আন")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("আব")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("ইতি")){
    		Word = Word.substring(3, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("উন")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("কদ")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("পাতি")){
    		Word = Word.substring(4, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("ভর")){
    		Word = Word.substring(2, Word.length());
    		return;
    	}
    	
    	if(Word.startsWith("রাম")){
    		Word = Word.substring(3, Word.length());
    	}
    }

    public void stem(){
            if(Word.length() > 1){
            	if((Word.charAt(0) <= 'z' && Word.charAt(0) >= 'a') || (Word.charAt(0) <= 'Z' && Word.charAt(0) >= 'A')){
            		step1();
                    step2();
                    step3();
                    step4();
                    step5();
                    step6(); 
            	}
            	else{
            		banglaSonsgskritoUposorgo();
            		banglaUposorgo();
            	}
            }
    }


    public String getWord() {
        return Word;
    }

    public void setWord(String Word) {
        this.Word = Word.toLowerCase();
        stem();
    }
}