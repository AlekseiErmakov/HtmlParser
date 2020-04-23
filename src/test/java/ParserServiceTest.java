import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ParserServiceTest {

    private ParserService service = new ParserService(new String[1]);

    private String NAMETEMPLATE = "src/main/resources/%s.html";
    private String tagName = "p";
    private String attrName = "class";
    private String attrValue = "full_name";

    @Before
    public void setUp(){
        service.setAttrName(attrName);
        service.setAttrValue(attrValue);
        service.setTagName(tagName);
    }
    @Test
    public void getPerson() {
        File emptyTag = new File(String.format(NAMETEMPLATE,"empty_tag"));
        File name = new File(String.format(NAMETEMPLATE,"name"));
        File surname_name = new File(String.format(NAMETEMPLATE,"surname_name"));
        File surname_name_thirdname = new File(String.format(NAMETEMPLATE,"surname_name_thirdname"));
        File without_attr = new File(String.format(NAMETEMPLATE,"without_attr"));
        File wrong_value = new File(String.format(NAMETEMPLATE,"wrong_value"));
        File wrong_tag = new File(String.format(NAMETEMPLATE,"wrong_tag"));


        String person1 = service.getPerson(emptyTag,tagName);
        String person2 = service.getPerson(name,tagName);
        String person3 = service.getPerson(surname_name,tagName);
        String person4 = service.getPerson(surname_name_thirdname,tagName);
        String person5 = service.getPerson(without_attr,tagName);
        String person6 = service.getPerson(wrong_value,tagName);
        String person7 = service.getPerson(wrong_tag,tagName);


        assertEquals("Tag <p> is empty!",person1);
        assertEquals("Great! We have found name: Анатолий!",person2);
        assertEquals("Great! We have found second name: Васильев, name: Анатолий!",person3);
        assertEquals("Great! We have found second name: Васильев, name: Анатолий, third name: Петрович!",person4);
        assertEquals("File does not have valid data",person5);
        assertEquals("File does not have valid data",person6);
        assertEquals("File not found!",person7);


    }
}