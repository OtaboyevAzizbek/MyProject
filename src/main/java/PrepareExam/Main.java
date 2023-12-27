package PrepareExam;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        String text = "FileOutputStreamga ma'lumot yozildi";
        FileOutputStream fileOutputStream = new FileOutputStream("File/Myfile.txt",true);
        fileOutputStream.write(text.getBytes());
        fileOutputStream.close();
    }
}
