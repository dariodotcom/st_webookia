package it.webookia.backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern requestPattern = Pattern
        .compile("/([a-z|A-Z]+)/([a-z|A-Z]*).*");

    public static void main(String[] args) {
        String url = "/home/;jsessionid=1bqz1yi92hk45#_=_";
        Matcher m = requestPattern.matcher(url);
        
        if(m.matches()){
            System.out.println(m.group(2));
        }
    }
}
