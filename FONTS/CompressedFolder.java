import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CompressedFolder extends Comprimit {

    public ArrayList<String> Cont_Name = new ArrayList<>();
    private ArrayList<String> Cont_Body = new ArrayList<>();
    private ArrayList<String> Cont_type = new ArrayList<>();
    private ArrayList<String> Cont_encoding = new ArrayList<>();

    public static void main(String[] args)
    {
        CompressedFolder t = new CompressedFolder();
        t.setNom("Test");
        t.Cont_type.add("LZW");
        t.Cont_Name.add("Test");
        t.Cont_Body.add("test");
        t.Cont_encoding.add("UNICODE");
        t.Cont_type.add("LZW");
        t.Cont_Name.add("Test2");
        t.Cont_Body.add("test");
        t.Cont_encoding.add("UNICODE");

        t.setFormat("ISO-8859-1");
        CtrlPersistencia.WriteComprimit(t,"C:\\Users\\Efren\\IdeaProjects\\subgrup8-2");
        CompressedFolder f = CtrlPersistencia.ReadCompressedFolder("C:\\Users\\Efren\\IdeaProjects\\subgrup8-2\\Test.cpt");
        System.out.println(f.getBody());
    }
    public void CompressedFolder()
    {
        this.setFormat("UTF-16");
    }

    @Override
    public String getFullNom() {
        return super.getNom() + ".cpt";
    }

    @Override
    public String getBody() {
        String body = "";
        JSONObject carpeta = new JSONObject();
        JSONArray content = new JSONArray();
        carpeta.put("nom", this.getNom());
        for (int i = 0; i<Cont_Name.size(); ++i)
        {
            JSONObject elem = new JSONObject();
            elem.put("nom", Cont_Name.get(i));
            elem.put("body", Cont_Body.get(i));
            elem.put("type", Cont_type.get(i));
            elem.put("encoding", Cont_encoding.get(i));
            content.add(elem);
        }
        carpeta.put("contingut", content);
        body = carpeta.toString();
        return body;
    }

    @Override
    public void setBody(String b) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(b);
            JSONObject cpt = (JSONObject) obj;
            Object cont = cpt.get("contingut");
            JSONArray content = (JSONArray) cont;
            content.forEach(compr -> AddJSONContent(compr));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFormat(){
        return "UTF-16";
    }

    private void AddJSONContent(Object compr)
    {
        JSONObject j = (JSONObject) compr;
        Cont_Name.add(j.get("nom").toString());
        Cont_type.add(j.get("type").toString());
        try {
            Cont_Body.add(new String(j.get("body").toString().getBytes("UTF-16"),j.get("encoding").toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cont_encoding.add(j.get("encoding").toString());
    }

    public void AddElement(Comprimit c)
    {
        Cont_Name.add(c.getFullNom());
        /*try {*/
            //Cont_Body.add(new String(c.getBody().getBytes(c.getFormat()),"UTF-8"));
            Cont_Body.add(c.getBody());
        /*} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        if(c instanceof LZ78file) Cont_type.add("LZ78");
        if(c instanceof LZSSfile) Cont_type.add("LZSS");
        if(c instanceof LZWfile) Cont_type.add("LZW");
        if(c instanceof JPGfile) Cont_type.add("JPG");
        if(c instanceof CompressedFolder) Cont_type.add("CPT");
        Cont_encoding.add(c.getFormat());
    }

    public Comprimit GetComprimit(int index)
    {
        if(index >= Cont_Name.size()) return  null;
        String type = Cont_type.get(index);
        Comprimit comp = null;
        switch (type)
        {
            case "LZSS":
                comp = new LZSSfile();
                break;
            case "JPG":
                comp = new JPGfile(Cont_Name.get(index));
                break;
            case "LZ78":
                comp = new LZ78file();
                break;

            case "LZW":
                comp = new LZWfile();
                break;
            case "CPT":
                comp = new CompressedFolder();
                break;


        }
        String s = Cont_Name.get(index);
        comp.setNom(s.substring(0,s.length()-4));
        comp.setBody(Cont_Body.get(index));
        comp.setFormat(Cont_Body.get(index));
        return comp;

    }



}
