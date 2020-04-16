import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ParserServiceTest {

    private ParserService service = new ParserService();

    private String NAMETEMPLATE = "src/main/resources/%s.html";

    @Test
    public void getPerson() {
        File emptyTag = new File(String.format(NAMETEMPLATE,"empty_tag"));
        File name = new File(String.format(NAMETEMPLATE,"name"));
        File surname_name = new File(String.format(NAMETEMPLATE,"surname_name"));
        File surname_name_thirdname = new File(String.format(NAMETEMPLATE,"surname_name_thirdname"));
        File without_attr = new File(String.format(NAMETEMPLATE,"without_attr"));
        File wrong_value = new File(String.format(NAMETEMPLATE,"wrong_value"));
        File wrong_tag = new File(String.format(NAMETEMPLATE,"wrong_tag"));


        String person1 = service.getPerson(emptyTag);
        String person2 = service.getPerson(name);
        String person3 = service.getPerson(surname_name);
        String person4 = service.getPerson(surname_name_thirdname);
        String person5 = service.getPerson(without_attr);
        String person6 = service.getPerson(wrong_value);
        String person7 = service.getPerson(wrong_tag);


        assertEquals("Tag <p> is empty!",person1);
        assertEquals("Great! We have found name: Анатолий!",person2);
        assertEquals("Great! We have found second name: Васильев, name: Анатолий!",person3);
        assertEquals("Great! We have found second name: Васильев, name: Анатолий, third name: Петрович!",person4);
        assertEquals("There is no such tag or tag don`t have attribute class or class value is not full_name",person5);
        assertEquals("There is no such tag or tag don`t have attribute class or class value is not full_name",person6);
        assertEquals("File not found!",person7);


    }
}