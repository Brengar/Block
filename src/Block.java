import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Block {
    private static Path path = Paths.get("file.txt");
    private static MessageDigest md5;
    static {
        try {
            md5 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private String index;
    private String time;
    private String name;
    private String grades;
    private String hash;

    public Block(String name, String grades) {
        List<String> file=null;
        try {
            file=Files.readAllLines(path,Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        index=Integer.toString(Integer.parseInt(file.get(file.size()-6).substring(6))+1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.time = dateFormat.format(date);
        this.name = name.toLowerCase();
        if (grades!="2"&&grades!="3"&&grades!="4"&&grades!="5") try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.grades = grades;
        this.hash = file.get(file.size()-1);
        this.write();
    }

    public static void createFirstBlock(){
        String tmp=Integer.toString(0)+"0"+""+""+"";
        List<String> lines = Arrays.asList("Index "+0, "Time "+"", "Student "+"", "Grades "+"","",
                Arrays.toString(md5.digest(tmp.getBytes())));
        try {
            Files.write(path, lines,Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(){
        String tmp=index+time+name+grades+hash;
        List<String> file=null;
        try {
            file=Files.readAllLines(path,Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.add("Index "+index);
        file.add("Time "+time);
        file.add("Student "+name);
        file.add("Grades "+grades);
        file.add(hash);
        file.add(Arrays.toString(md5.digest(tmp.getBytes())));
        try {
            Files.write(path,file,Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
