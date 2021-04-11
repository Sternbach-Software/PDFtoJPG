import java.io.File;

public class MyVersionOfListFiles {
    File[] myListFiles(File file) {
        File[] x;
        x = file.listFiles();
        if (x == null) new MyNullException();
        return x;
    }
}

class MyNullException extends NullPointerException {
    MyNullException() {
        super("There are no files in directory.");
    }
}
