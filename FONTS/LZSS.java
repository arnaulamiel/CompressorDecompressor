import javafx.util.Pair;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.font.FontRenderContext;
import java.io.*;
import java.util.*;


public class LZSS implements Algorithmstrategy{

    private static String code_pointer(Integer offset, Integer length)
    {
        int new_pointer = (int)offset;
        new_pointer = new_pointer & 0x00000FFF;
        new_pointer = new_pointer << 4;
        int l = (int)length;
        l = l & 0x0000000F;
        new_pointer = new_pointer | l;
        int a  = (new_pointer >>> 8);
        a = a & 0x000000FF;
        int b = new_pointer & 0x000000FF;
        String c = "";
        c += (char)a;
        c += (char)b;
        return c;
    }

    private static byte insert_flag(byte flag_byte , boolean is_raw)
    {
        byte a;
        int b = flag_byte<<1;
        if (!is_raw) b = b | 1;
        a = (byte)b;
        return a;
    }
    //funcio complementaria de find
    private   static int next_coincidence(HashMap<Integer,Character> SLW,Integer map_key,byte[] uncompressed_spl, Integer index, Integer num)
    {
        if( (index+1 < uncompressed_spl.length) && SLW.get(map_key + 1) != null && (map_key + 1 < index) && num < 15 && (SLW.get(map_key + 1) == uncompressed_spl[index + 1]))
        {
            return 1 + next_coincidence(SLW, map_key + 1, uncompressed_spl, index + 1, num + 1);
        }
        else return 0;
    }
    //busca i retorna la clau de la coincidencia mes llarga que hi ha al hashmap, sino retorna zero
    //cal modificar i que retorne un pair en lo index de la coincidencia i la longitud.
    private static Pair<Integer, Integer> find(HashMap<Integer,Character> SLW, byte value, Integer pos, byte[] uncompressed_spl)
    {
        int i = 0;
        Integer max_coincidence = -1;
        Integer index = -1;
        for (HashMap.Entry<Integer, Character> mapElement : SLW.entrySet())
        {
            if(mapElement.getValue() == (char)value)
            {
                i = 1 + next_coincidence(SLW, mapElement.getKey(), uncompressed_spl, pos, 1);
                if(i >= max_coincidence)
                {

                    index = mapElement.getKey();
                    max_coincidence = i;
                }
            }
        }

        return new Pair<Integer, Integer>(index,max_coincidence);
    }
    public static void add_coincidences(Pair<Integer,Integer> p, HashMap<Integer,Character> SLW, byte[] uncompressed_spl)
    {
        for (int i  = 0; i<p.getValue() && i+p.getKey() < uncompressed_spl.length; ++i)
        {

            if(SLW.size() >= 4096) SLW.remove(p.getKey() - 4095 + i);
            SLW.put(p.getKey() + i, (char)uncompressed_spl[p.getKey() + i]);
        }

    }

    public LZSSfile comprimir(No_comprimit n)
    {
        LZSSfile res = new LZSSfile();
        String uncompressed = n.getBody();
        res.setNom(n.getNom());
        String result = "";
        HashMap<Integer,Character> SLW = new HashMap<>() ;
        //ISO-8859-1
        byte[] uncompressed_spl = new byte[0];
        try {
            uncompressed_spl = uncompressed.getBytes(n.getFormat());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //variables necessaries per a la compresió
        String temporal ="";
        byte flag_byte = 0;
        int act_char = 0;
        int final_char = uncompressed.length();
        int byte_counter = 0;
        while (act_char < final_char)
        {

            byte actual = uncompressed_spl[act_char];

            if(byte_counter <= 7)
            {
                ++byte_counter;
                Pair<Integer, Integer> coincidence = find(SLW,actual,act_char, uncompressed_spl);
                if(coincidence.getValue() <= 2)
                {

                    if(SLW.size() < 4096)
                    {
                        SLW.put(act_char, (char)actual);
                        flag_byte = insert_flag(flag_byte,true);
                    }
                    else
                    {
                        SLW.remove((act_char-4095));

                        SLW.put(act_char, (char)actual);
                        flag_byte = insert_flag(flag_byte,true);
                    }
                    temporal += (char)actual;
                    act_char++;
                }
                else if(coincidence.getValue() > 2)
                {
                    Pair <Integer, Integer> temporal_pair = new Pair<>(act_char,coincidence.getValue());
                    add_coincidences(temporal_pair, SLW, uncompressed_spl);
                    String new_pointer = code_pointer(act_char - coincidence.getKey(), coincidence.getValue());
                    flag_byte = insert_flag(flag_byte, false);
                    temporal += new_pointer;
                    act_char += coincidence.getValue(); }
            }
            else
            {
                int temp = (char)flag_byte;
                temp = temp & 0x000000FF;
                char t = (char) temp;
                result += t;
                flag_byte = 0;
                byte_counter = 0;
                result += temporal;
                temporal = "";
            }
        }
        if(temporal != "")
        {
            int fl = flag_byte << (8 - byte_counter);
            result += (char)fl;
            result += temporal;
        }
        res.setBody(result);
        return res;
    }

    private static Pair<Integer, Integer> decode_pointer(byte a, byte b)
    {
        int total = a;
        total = total & 0x000000FF;
        total = total << 8;
        int b1 = (int)b;
        b1 = b1 & 0x000000FF;
        total = total | b1;
        total = total & 0x0000FFFF;
        int offset = total >>>4;
        int length = total & 0x0000000F;
        int at = a & 0x000000FF;
        int bt = b & 0x000000FF;
        Pair<Integer,Integer> p = new Pair<>(offset,length);
        return  p;

    }
    private static boolean decode_flag(byte flag_byte )
    {
        //return true if pointer, false if raw
        int res = flag_byte >>> 7;
        return res!=0;
    }
    private static String decode(Pair<Integer,Integer> pointer, HashMap<Integer,Character> SLW, Integer actual)
    {
        String result = "";
        Integer offset = pointer.getKey();
        for (int i = 0; i<pointer.getValue(); ++i)
        {
            char a = SLW.get((actual - offset) + i);
            if(SLW.size() >= 4096)
            {
                SLW.remove((actual - 4095) + i);
            }
            SLW.put(actual + i , a);
            result += (char)a;
        }
        return result;


    }
    public No_comprimit descomprimir(Comprimit c)
    {
        No_comprimit n = new TXT();
        String compressed = c.getBody();
        n.setNom(c.getNom());
        String result = "";
        HashMap<Integer,Character> SLW = new HashMap<>() ;
        byte[] compressed_spl = new byte[0];
        try {
            //ISO-8859-1
            compressed_spl = compressed.getBytes(c.getFormat());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    //variables necessaries per a la decompresio
        String temporal ="";
        byte flag_byte = compressed_spl[0];
        int act_char = 1;
        int final_char = compressed.length();
        int byte_counter = 0;
        Integer act_noflag_char = 0;
        while (act_char < final_char)
        {
            byte actual = compressed_spl[act_char];
            if(byte_counter < 8)
            {

                if(decode_flag(flag_byte))
                {
                    //is a pointer
                    Pair<Integer,Integer> m = decode_pointer(actual, compressed_spl[act_char + 1]);
                    String coincidence = decode(m, SLW, act_noflag_char);
                    act_noflag_char += m.getValue();
                    result += coincidence;
                    act_char += 2;
                    ++byte_counter;
                }
                else
                {
                        //is raw, ADD TO THE SLW
                    if(SLW.size() >= 4096)
                    {
                        SLW.remove(act_noflag_char - 4095);
                    }

                    SLW.put(act_noflag_char, (char)(((char)actual)&0x00FF));
                    result += (char)(((char)actual)&0x00FF);
                    ++act_char;
                    ++act_noflag_char;
                    ++byte_counter;
                }
                flag_byte = (byte)(flag_byte << 1);
            }
            else
            {
                byte_counter = 0;
                flag_byte = actual;
                ++act_char;

            }

        }
        n.setBody(result);
        return  n;

    }



}
