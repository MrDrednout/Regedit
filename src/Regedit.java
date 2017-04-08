/**
 * Created by korot on 08.04.2017.
 */

import java.io.*;

public class Regedit {

    private static String readWinReg (String zn, String param) throws Exception{
        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        String result = "";
        String r = "";

        try {
            proc = rt.exec("cmd");

        } catch (Exception exc){
            System.out.println("Консоль не запущена");
        }

        BufferedReader bfIn = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
        BufferedWriter bfOut = new BufferedWriter (new OutputStreamWriter (proc.getOutputStream(), "UTF-8"));

        int ir=0;
        while ( (ir = bfIn.read()) != 62 )
        {
            //System.out.print((char) ir);
        }

        //System.out.print((char) ir);
        bfOut.write("reg query \"" + zn + "\" /v " + param);
        bfOut.newLine();
        bfOut.flush();

        while ( (ir = bfIn.read()) != 62 )
        {
            result = result + ((char) ir);
        }

        for (String s : result.split(" ")) {
            if (s.length() > 1000 ) return s;
        }

        return r;

    }

    private static void addWinReg (String zn, String zn2, String zn3) {

        String s = "cmd /C " + "reg add " + zn + " /v " + zn2 + " /t REG_SZ /d " + "\"" + zn3 + "\"";
        try {  Runtime.getRuntime().exec(s); }
        catch(Exception ex){ex.printStackTrace();}
    }

    private static void delWinReg (String zn, String zn2) {
        String s = "cmd /C " + "reg delete " + zn + " /v " + zn2 + " /f\r\n";
        try {  Runtime.getRuntime().exec(s); }
        catch(Exception ex){ex.printStackTrace();}
    }

    public static String trans(String str){

        String result= "";
        //System.out.println(str.length());

        str = str.substring(0, 1600);

        for (String s : str.split("(?<=\\G.{2})")) {
            //System.out.println(s);
            //System.out.println(Integer.parseInt(s, 16));
            //System.out.println((char)Integer.parseInt(s, 16));
            //System.out.println(s);
            result = result + ((char) Integer.parseInt(s, 16));
            //System.out.println(((char) Integer.parseInt(s, 16)));
            //System.out.println(s);
            //System.out.println(result);

        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        String rees = "HKEY_CURRENT_USER\\Control Panel\\Desktop";
        String param = "TranscodedImageCache";

        String val = readWinReg (rees, param);

        //System.out.println(val);

        String path;
        path = trans(val);
        //System.out.println(path);
        char inkr = (char) Integer.parseInt("00", 16);
        String p = path.replace(String.valueOf(inkr), "");

        String C = (String.valueOf((char) Integer.parseInt("43", 16)+String.valueOf((char) Integer.parseInt("3A", 16))));

        System.out.println(p.indexOf(C));

        System.out.println(p.substring(18));

    }
}