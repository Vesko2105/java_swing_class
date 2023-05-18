import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) {
        MainWindow w = new MainWindow();
        StringJoiner sj = new StringJoiner(", ");
        sj.add("sss").add("llll");
        System.out.println(sj.length());
    }
}
