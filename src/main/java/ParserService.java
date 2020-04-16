

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParserService extends Thread {

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please write the full path to file you want to parse!");
            String pathToFile = reader.readLine();
            File htmlFile = new File(pathToFile);
            System.out.println(getPerson(htmlFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPerson(File htmlFile) {
        if (htmlFile.getName().endsWith(".html")) {
            try {
                Document html = Jsoup.parse(htmlFile, "UTF-8");
                return getPerson(html);
            } catch (IOException e) {
                return "File not found!";
            }
        } else {
            return "File is not html. I can parse only html!";
        }
    }

    private String getPerson(Document html) {
        Element body = html.body();
        Elements p = body.getElementsByTag("p");
        return getPerson(p);
    }

    private String getPerson(Elements p) {
        String attrName = "class";
        String attrValue = "full_name";
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
        return "There is no such tag or tag don`t have attribute class or class value is not full_name";
    }


    private String getPerson(String[] names) {
        String template = "Great! We have found second name:";
        if (names.length > 3) {
            throw new PersonFullNameException("Oops! It seems, it`s something difficult :(");
        }
        if (names[0].isEmpty()) {
            throw new PersonFullNameException("Tag <p> is empty!");
        }
        if (names.length == 1) {
            return String.format("Great! We have found name: %s!", names[0]);
        } else if (names.length == 2) {
            return String.format("%s %s, name: %s!", template, names[0], names[1]);
        } else {
            return String.format("%s %s, name: %s, third name: %s!", template, names[0], names[1], names[2]);
        }
    }
}
