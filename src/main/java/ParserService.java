

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParserService extends Thread {

    private String tagName;
    private String attrName;
    private String attrValue;
    private String[] args;

    public ParserService(String[] args){
        this.args = args;
    }
    public void setTagName(String tagName){
        this.tagName = tagName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    @Override
    public void run() {
        String pathToFile;
        if (args.length == 0){
            pathToFile = getFromConsole();
        } else {
            pathToFile = args[0];
        }
        File htmlFile = new File(pathToFile);
        System.out.println(getPerson(htmlFile,tagName));
    }

    private String getFromConsole() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please write the full path to file you want to parse!");
            return reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String getPerson(File htmlFile, String tagName) {
        try {
            Document html = Jsoup.parse(htmlFile, "UTF-8");
            return getPerson(html, tagName);
        } catch (IOException e) {
            return "File not found!";
        }
    }

    private String getPerson(Document html,String tagName) {
        Element body = html.body();
        Elements p = body.getElementsByTag(tagName);
        return getPerson(p);
    }

    private String getPerson(Elements p) {
        String currentAttrValue = p.attr(attrName);

        if (p.hasAttr(attrName) && currentAttrValue.equals(attrValue)) {
            String fullName = p.text();
            String[] fullNameList = fullName.trim().split("\\s+");
            try {
                return getPerson(fullNameList);
            } catch (PersonFullNameException ex) {
                return ex.getMessage();
            }
        }

        return "File does not have valid data";
    }

    private String getPerson(String[] names) {
        String template = "Great! We have found second name:";

        if (names[0].isEmpty()){
            throw new PersonFullNameException("Tag <p> is empty!");
        }

        switch (names.length){

            case (1) : return String.format("Great! We have found name: %s!", names[0]);

            case (2) : return String.format("%s %s, name: %s!", template, names[0], names[1]);

            case (3) : return String.format("%s %s, name: %s, third name: %s!", template, names[0], names[1], names[2]);

            default :  throw new PersonFullNameException("Oops! It seems, it`s something difficult :(");
        }

    }
}
