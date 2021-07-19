public class LZSSfile extends Comprimit {

    private String body;
    private byte[] pre_body;
    public byte[] getPre_Body()
    {
        return pre_body;
    }

    public void setPre_body(byte[] arg)
    {
        pre_body = arg;
    }
    public void LZSSfile()
    {
        this.setFormat("UNICODE");
    }

    @Override
    public String getFullNom() {
        return this.getNom() + ".LZS";
    }

    @Override
    public void setBody(String s)
    {
        body = s;
    }
    @Override
    public String getBody()
    {
        return body;
    }

    public String tradueix()
    {
        return body;
    }



}
