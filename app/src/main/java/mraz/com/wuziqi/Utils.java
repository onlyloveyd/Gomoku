package mraz.com.wuziqi;

import android.graphics.Point;

import java.util.List;

/**
 * Created by Mraz on 2016/7/21.
 */
public class Utils {

    public static int MAX_IN_LINE = 5;
    public static int[] mIconList = {
            R.drawable.e1, R.drawable.e2, R.drawable.e3, R.drawable.e4, R.drawable.e5,
            R.drawable.e6, R.drawable.e7, R.drawable.e8, R.drawable.e9, R.drawable.e10,
            R.drawable.e11, R.drawable.e12, R.drawable.e13, R.drawable.e14, R.drawable.e15,
            R.drawable.e16, R.drawable.e17, R.drawable.e18, R.drawable.e19, R.drawable.e20,
            R.drawable.e21, R.drawable.e22, R.drawable.e23, R.drawable.e24, R.drawable.e25,
            R.drawable.e26, R.drawable.e27, R.drawable.e28, R.drawable.e29, R.drawable.e30,
            R.drawable.e31};

    public static boolean checkFiveInLine(List<Point> points) {

        System.out.println("checkFiveInLine points = " + points);
        for (Point p : points) {
            int x = p.x;
            int y = p.y;

            boolean win = checkHorizontal(x, y, points);
            if (win) return true;
            win = checkVertical(x, y, points);
            if (win) return true;
            win = checkLeftDiagonal(x, y, points);
            if (win) return true;
            win = checkRightDiagonal(x, y, points);
            if (win) return true;
        }
        return false;
    }

    private static boolean checkHorizontal(int x, int y, List<Point> points) {
        System.out.println("checkHorizontal x = " + x + " y = " + y);
        int count = 1;
        //left direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        //right direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        return false;

    }


    private static boolean checkVertical(int x, int y, List<Point> points) {
        System.out.println("checkVertical x = " + x + " y = " + y);
        int count = 1;
        //left direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        //right direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        return false;

    }

    private static boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        System.out.println("checkLeftDiagonal x = " + x + " y = " + y);
        int count = 1;
        //left direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        //right direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        return false;

    }


    private static boolean checkRightDiagonal(int x, int y, List<Point> points) {
        System.out.println("checkRightDiagonal x = " + x + " y = " + y);
        int count = 1;
        //left direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        //right direction
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) return true;
        return false;

    }

}
