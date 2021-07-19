public class TXT extends No_comprimit {
    String body;

    public TXT(){}
    public TXT(String name)
    {
        this.setNom(name);
    }
    @Override
    public void setBody(String s) {body = s;};

    @Override
    public String getBody() {return body;}

    @Override
    public String getFullNom() {
        return this.getNom()+".txt";
    }
}
