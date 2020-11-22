//
// task2
// Горчаков А. В.
//
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// класс, определяющий структуру хранения точек
class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
}

public class task2 {

    private final static double EPS = 0.000001; // точность сравнения близких точек
    private final static int N = 4; // количество вершин

    // уточнение близких точек
    private static boolean preciseCalcAbs(double a, double b) {
        boolean res = (Math.abs(a - b)) <= EPS;
        return res;
    }

    private static boolean preciseCalc(double a, double b) {
        boolean res = (a - b) <= EPS;
        return res;
    }

    private static boolean inLine(double x1, double y1, double x2, double y2, double x, double y) {
        boolean res = preciseCalcAbs((x - x1) * (y2 - y1) - (y - y1) * (x2 - x1), 0) &&
                ((preciseCalc(x, x1) && preciseCalc(x2, x)) ||
                        (preciseCalc(x, x2) && preciseCalc(x1, x)));
        return res;
    }

    private static void readFile(String filePath, List<Point> l) {
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
                    // распарсим строки по пробелу на x и y
                    String[] rawData = line.split(" ");
                    double x = Double.parseDouble(rawData[0]);
                    double y = Double.parseDouble(rawData[1]);

                    // добавляем точку в список
                    l.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    System.err.println("\nВо введенных значениях встретились НЕ числа! :\n" + e + "\n");
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("IOException\n" + e + "\n");
        }
    }

    private static boolean inPolygon(double[] xp, double[] yp, double x, double y) {

            int j = N - 1;
            double maxX = xp[0];
            boolean isInside = false;

            for (int i = 0; i < N; i++) {
                if (xp[i] > maxX)
                    maxX = xp[i];
            }

            for (int i = 0; i < N; i++) {
                if ((((yp[i] <= y) && (y < yp[j])) || ((yp[j] <= y) && (y < yp[i]))) &&
                        (x < (xp[j] - xp[i]) * (y - yp[i]) / (yp[j] - yp[i]) + xp[i]))
                    isInside = true;

                j = i;
            }

            if ((isInside) && (x >= maxX)) isInside = false;

            //System.out.println("inPolygon -> " + isInside);

            return isInside;
    }

    private static boolean inLine2(double x1, double y1, double x2, double y2, double x3, double y3) {
        if ((x1 - x2) * (y1 - y3) == (x1 - x3) * (y1 - y2)) return true;
        return false;
    }

    public static void main(String[] args) {

//        String filePolygon = "C:\\Java_projects\\PerformanceLab\\task2\\src\\file1";
//        String filePoints = "C:\\Java_projects\\PerformanceLab\\task2\\src\\file2";

        String filePolygon = args[0];
        String filePoints = args[1];

        List<Point> listPolygon = new ArrayList<>();
        List<Point> listPoints = new ArrayList<>();

        readFile(filePolygon, listPolygon);
        //System.out.println(listPolygon);
        readFile(filePoints, listPoints);
        //System.out.println(listPoints);


        double x1, y1, x2, y2, x3, y3, x4, y4, x, y;

        // полигон
        // будем хранить его в массиве x и y
        x1 = listPolygon.get(0).x;
        y1 = listPolygon.get(0).y;
        x2 = listPolygon.get(1).x;
        y2 = listPolygon.get(1).y;
        x3 = listPolygon.get(2).x;
        y3 = listPolygon.get(2).y;
        x4 = listPolygon.get(3).x;
        y4 = listPolygon.get(3).y;
        double[] xPolygon = {x1, x2, x3, x4};
        double[] yPolygon = {y1, y2, y3, y4};


        // обработка точек из списка
        for (Point p : listPoints) {

            boolean ISINPOLYGON = false; // флаг принадлежности полигону
            boolean ISINLINE = false; // флаг принадлежности линии

            x = p.x;
            y = p.y;

            // Проверим принадлежность вершинам - если "да", то дальше не проверяем
            if (
                            ((x == x1) && (y == y1)) ||
                            ((x == x2) && (y == y2)) ||
                            ((x == x3) && (y == y3)) ||
                            ((x == x4) && (y == y4)))
            {
                System.out.println("0"); // "точка на одной из вершин"
                continue;
            }

            // Если точка не на вершине - проверим принадлежность полигону и линиям
            // 1. принадлежит полигону - устанавливаем флаг в true
            if (inPolygon(xPolygon, yPolygon, x, y)) ISINPOLYGON = true;

            // 2. принадлежит линии - устанавливаем флаг в true
            if (
                            (inLine(x1, y1, x2, y2, x, y)) ||
                            (inLine(x2, y2, x3, y3, x, y)) ||
                            (inLine(x3, y3, x4, y4, x, y)) ||
                            (inLine(x4, y4, x1, y1, x, y))) {
                ISINLINE = true;
                //System.out.println("inLine -> true");
            }
            //else System.out.println("inLine -> false");

            //System.out.println("Endind ISINPOLYGON = " + ISINPOLYGON);
            //System.out.println("Endind ISINLINE = " + ISINLINE);

            // делаем вывод по комбинации флагов
            if (ISINPOLYGON && ISINLINE) System.out.println("1"); // "точка на одной из сторон"
            if (ISINPOLYGON && !ISINLINE) System.out.println("2"); // "принадлежит многоугольнику"
            if (!ISINPOLYGON && ISINLINE) System.out.println("1"); // "Точка на одной из сторон"
            if (!ISINPOLYGON && !ISINLINE) System.out.println("3"); // "Не принадлежит многоугольнику"

            //System.out.println();
        }
    }
}