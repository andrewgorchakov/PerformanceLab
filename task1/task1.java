//
// task1
// Горчаков А. В.
//
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class task1 {

    private static float mediana(List<Integer> l) {
        int countNumbers = l.size();
        float med;
        if (countNumbers % 2 == 0) {
            int a = countNumbers / 2;
            float x1 = l.get(a - 1);
            float x2 = l.get(a);
            med = (x1 + x2) / 2;
        }
        else {
            float x1 = l.get(countNumbers/2);
            med = x1;
        }
        return med;
    }

    private static float percentile90(List<Integer> l) {
        int n = l.size();
        int p = 90;
        float p90;

        float r = (float) (0.9 * (n - 1) + 1); // ранг

        if (r % 1 == 0){
            // если r - целое, то  p90 = значению элемента с этим рангом
            //System.out.println("r целое");
            p90 = l.get((int) (r - 1));
        }
        else {
            // если r -дробное, то вычисляем ri и rf
            //System.out.println("r дробное");
            int ri = (int) r;
            //System.out.println("ri = " + ri);
            float rf = (r - ri);
            //System.out.println("rf = " + rf);
            p90 = l.get(ri - 1) + rf * (l.get(ri) - l.get(ri - 1));
        }
        return p90;
    }

    public static void main(String[] args) {

        //String filePath = "test1";
        //String filePath = "C:\\Java_projects\\PerformanceLab\\task1\\src\\test1";

        // определим ОС,чтобы ставить правильный префикс в пути к папке
        String OS = System.getProperty("os.name").toLowerCase();
        String prefix;
        //System.out.println(OS);
        if (OS.indexOf("win") >= 0) prefix = "\\";
        else if (OS.indexOf("mac") >= 0) prefix = "/";
        else if ((OS.indexOf("nix") >= 0) || (OS.indexOf("nux") >= 0)) prefix = "/";
        else prefix = "/";
        //String folderPath = args[0];
        //String filePath1 = folderPath + prefix + "test1";

        // будем брать полный путь к файлу (включая название) из командной строки
        String filePath = args[0];

        List <Integer> list = new ArrayList<>();

        File file = new File(filePath);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("\nФайл не найден!\n" + e);
        }
        BufferedReader br = new BufferedReader(fr);

        String line;

        try {
            while ((line = br.readLine()) != null) {
                try {
                    list.add(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    System.err.println("\nВо введенных значениях встретились НЕ числа! :\n" + e + "\n");
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("IOException\n" + e + "\n");
        }

        //System.out.println(list);
        // Сортируем (нужно для вычисления медианы и перцентиля)
        Collections.sort(list);
        //System.out.println(list);

        // Секция вывода результатов
        float sum = 0;
        float average;
        int countNumbers = list.size();
        float max = (float) Collections.max(list);
        float min = (float) Collections.min(list);

        for (int i = 0; i < countNumbers; i++) {
            sum += list.get(i);
        }
        average = sum / countNumbers;

        System.out.printf("%.2f\n", percentile90(list));
        System.out.printf("%.2f\n", mediana(list));
        System.out.printf("%.2f\n", max);
        System.out.printf("%.2f\n", min);
        System.out.printf("%.2f\n", average);

//        System.out.printf("Percentile = %.2f%n", percentile90(list));
//        System.out.printf("Медиана = %.2f%n", mediana(list));
//        System.out.printf("max = %.2f%n", max);
//        System.out.printf("min = %.2f%n", min);
//        System.out.printf("Average = %.2f%n", average);
//        System.out.println("Count = " + countNumbers);
//        System.out.println("Sum = " +sum);

    }
}