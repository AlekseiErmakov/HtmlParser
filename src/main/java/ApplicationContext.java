public class ApplicationContext extends Thread{

    public ApplicationContext(String[] args){
        ParserService service = new ParserService(args);
        service.setTagName("p");
        service.setAttrName("class");
        service.setAttrValue("full_name");
        service.start();
    }
}
