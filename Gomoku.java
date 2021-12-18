import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.*;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class Gomoku {

    private static double x0, y0, x2, y2;//x1 is mouseX,y1 is mouseY,x2 and y2 is processed x1&y1.
    private static double c;
    private static final double c1 = 1.0 / 17;//15*15
    private static final double c2 = 1.0 / 19;//17*17
    private static final double c3 = 1.0 / 21;//19*19
    private static final double a = 1.1;//is h/w
    private static int b = 0;//
    public static long midtime;
    public static int choiceConstant;
    public static long mm;
    public static long ss;
    public static boolean pan;//确定是否下一手
    public static boolean pan2;//确定是否超时
    public static boolean pan3;//确定是否胜利
    public static boolean pan4;//确定是否没有子
    public static int[][] state;
    public static int undoCommand = 0;
    public static int undocounter = 0;
    public static int times = 0;
    public static int HoC;
    public static int Null0;
    public static int Nullcommand;
    public static double coverX, coverY;

    private static int chessman = 0;

    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 880);
        l1:
        for (; true; ) {
            clear();
            x2 = 0;
            y2 = 0;
            if (b != 0 || pan2) {
                if (!pan2) {
                    bufferN();
                }
                if (pan2) {
                    if (pan4)
                        chessman = 1;
                    if (!pan4)
                        chessman = 2;
                    pan2 = false;
                }
                win2(chessman);
                bufferP();
                bufferN();
                while (Choice() == 0)
                    printChoose3();
                if (Choice() == 1)
                    break;
                bufferP();
                bufferN();
            }
            welcome();
            Font font2 = new Font("Default", Font.PLAIN, 50);
            StdDraw.setFont(font2);
            bufferP();
            bufferN();
            while (Choice() == 0) {
                load();
            }
            bufferP();
            bufferN();
            while (Size() == 0)
                chooseSize();
            if (Size() == 15)
                c = c1;
            if (Size() == 17)
                c = c2;
            if (Size() == 19)
                c = c3;
            bufferP();
            bufferN();
            while (Choice() == 0)
                printChoose();//computer2
            if (Choice() == 1)
                HoC = 1;

            if (Choice() == 2) {
                HoC = 2;
                bufferP();
                bufferN();
                while (!StdDraw.isMousePressed() || Choice() == 0) {
                    printChoose2();//先手1，后手2
                    if (Choice() == 1)
                        Nullcommand = 1;
                    else
                        Nullcommand = 2;
                }
                bufferP();
                bufferN();
            }


            Font font = new Font("Default", Font.PLAIN, 15);
            state = new int[(int) (1 / c)][(int) (1 / c)];
            int[][] copy1 = new int[(int) (1 / c)][(int) (1 / c)];
            int[][] copy2 = new int[(int) (1 / c)][(int) (1 / c)];
            int[][] copy3 = new int[(int) (1 / c)][(int) (1 / c)];
            int[][] copy4 = new int[(int) (1 / c)][(int) (1 / c)];
            int[][] copy5 = new int[(int) (1 / c)][(int) (1 / c)];
            int[][] copy6 = new int[(int) (1 / c)][(int) (1 / c)];

            InitialState(state, Nullcommand);
            InitialState(copy1, Nullcommand);
            InitialState(copy2, Nullcommand);
            InitialState(copy3, Nullcommand);
            InitialState(copy4, Nullcommand);
            InitialState(copy5, Nullcommand);
            InitialState(copy6, Nullcommand);
            StdDraw.setFont(font);
            clear();
            printBoard();
            timer();
            try {
                if (HoC == 1) {
                    do {
                        check(state);
                        choiceConstant = 0;
                        if (pan2) {
                            continue l1;
                        }
                        MouseMonitoring();
                        if (isMousePressed()) {
                            MouseMonitoring();

                            if (undocounter != 0) {
                                undoCommand = undoCommand + 1;
                            } else
                                undoCommand = 0;
                            if (undoCommand == 0) {
                                if (x0 != x2 || y0 != y2) {
                                    x0 = x2;
                                    y0 = y2;
                                    if (BalanceBrake(state, 2, x0, y0)) {
                                    } else {
                                        for (int i = 0; i < 1 / c; i++) {
                                            for (int j = 0; j < 1 / c; j++) {
                                                copy6[i][j] = copy5[i][j];
                                                copy5[i][j] = copy4[i][j];
                                                copy4[i][j] = copy3[i][j];
                                                copy3[i][j] = copy2[i][j];
                                                copy2[i][j] = copy1[i][j];
                                                copy1[i][j] = state[i][j];
                                            }
                                        }
                                        System.out.print("0");
                                        Record(state, x0, y0, chessman);
                                        pan = true;
                                    }
                                }
                            } else {
                                System.out.print(undoCommand);
                                Undo(state, copy2, copy4, copy6, undoCommand);
                                printList(state);
                                printBoard();
                            }

                            if (!Judge(state, chessman))
                                printChess(state);
                            if (Judge(state, chessman)) {
                                pan3 = true;
                                clear();
                                b++;
                                continue l1;
                            }
                        }
                    } while (true);
                } else {
                    do {
                        check(state);
                        choiceConstant = 0;
                        if (pan2) {
                            continue l1;
                        }
                        MouseMonitoring();
                        if (isMousePressed()) {
                            MouseMonitoring();

                            if (undocounter != 0) {
                                undoCommand = undoCommand + 1;
                            } else
                                undoCommand = 0;
                            if (undoCommand == 0) {
                                if (x0 != x2 || y0 != y2) {
                                    x0 = x2;
                                    y0 = y2;
                                    if (BalanceBrake(state, 2, x0, y0)) {

                                    } else {
                                        for (int i = 0; i < 1 / c; i++) {
                                            for (int j = 0; j < 1 / c; j++) {
                                                copy6[i][j] = copy4[i][j];
                                                copy4[i][j] = copy2[i][j];
                                                copy2[i][j] = state[i][j];
                                            }
                                        }
                                        Record(state, x2, y2, chessman);
                                        pan = true;
                                        if (Judge(state, chessman)) {
                                            printChess(state);
                                            if (Judge(state, chessman)) {
                                                pan3 = true;
                                                clear();
                                                b++;
                                                continue l1;
                                            }
                                        }
                                        chessman = chessman - 1;
                                        Compute(state, x2, y2, chessman);
                                    }
                                }
                            } else {
                                System.out.print(undoCommand);
                                Undo(state, copy2, copy4, copy6, undoCommand);
                                printList(state);
                                printBoard();
                            }
                            if (!Judge(state, chessman))
                                printChess(state);
                            if (Judge(state, chessman)) {
                                pan3 = true;
                                clear();
                                b++;
                                continue l1;
                            }
                        }

                    } while (true);
                }


            } catch (InterruptedException ex) {
            }
        }
    }

    public static void clear() {
//        StdDraw.setPenColor(Color.black);
//        StdDraw.filledSquare(0.5, 0.5, 0.5);
        StdDraw.picture(0.5,0.5,"background.png");
    }

    public static void welcome() {
        clear();
        while (!StdDraw.isMousePressed()) {
            StdDraw.picture(0.5, 0.5, "welcome1.png");
        }
    }

    public static void load() {
        clear();
        while (!StdDraw.isMousePressed()) {
//            StdDraw.setPenColor(Color.magenta);
//            StdDraw.rectangle(0.5, 0.7, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.7, "Load Game");
//            StdDraw.setPenColor(Color.yellow);
//            StdDraw.rectangle(0.5, 0.3, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.3, "New Game");
            StdDraw.picture(0.5,0.5,"preface1.png");
        }

    }

    public static void chooseSize() {
        while (!StdDraw.isMousePressed()) {

//            StdDraw.setPenColor(StdDraw.MAGENTA);
//            StdDraw.rectangle(0.5, 0.17, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.5 - 0.33, "15 X 15");
//            StdDraw.setPenColor(Color.cyan);
//            StdDraw.rectangle(0.5, 0.5, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.5, "17 X 17");
//            StdDraw.setPenColor(Color.yellow);
//            StdDraw.rectangle(0.5, 0.83, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.5 + 0.33, "19 X 19");
            StdDraw.picture(0.5,0.5,"preface2.png");
        }
    }

    public static int Size() {
        if (StdDraw.isMousePressed()) {
            double y = StdDraw.mouseY();
            double x = StdDraw.mouseX();
            if (x < 0.8 && x > 0.2 && y > 0.17 - 0.5 * 0.618 / 3 && y < 0.17 + 0.5 * 0.618 / 3)
                return 15;
            if (x < 0.8 && x > 0.2 && y > 0.5 - 0.5 * 0.618 / 3 && y < 0.5 + 0.5 * 0.618 / 3)
                return 17;
            if (x < 0.8 && x > 0.2 && y > 0.83 - 0.5 * 0.618 / 3 && y < 0.83 + 0.5 * 0.618 / 3)
                return 19;
        }
        return 0;
    }

    public static void printChoose() {

        while (!StdDraw.isMousePressed()) {
//            StdDraw.setPenColor(Color.magenta);
//            StdDraw.rectangle(0.5, 0.7, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.7, "Human");
//            StdDraw.setPenColor(Color.yellow);
//            StdDraw.rectangle(0.5, 0.3, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.3, "Computer");
            StdDraw.picture(0.5,0.5,"preface3.png");
        }
    }

    public static int Choice() {
        if (StdDraw.isMousePressed()) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            if (x < 0.8 && x > 0.2 && y < 0.7 + 0.5 * 0.618 / 3 && y > 0.7 - 0.5 * 0.618 / 3)
                return 1;//+
            if (x < 0.8 && x > 0.2 && y < 0.3 + 0.5 * 0.618 / 3 && y > 0.3 - 0.5 * 0.618 / 3)
                return 2;//-

        }
        return 0;
    }

    public static void printChoose2() {//offensive or defensive

        while (!StdDraw.isMousePressed()) {
            Font font = new Font("Default", Font.PLAIN, 50);
            StdDraw.setFont(font);
//            StdDraw.setPenColor(Color.magenta);
//            StdDraw.rectangle(0.5, 0.7, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.7, "offensive");
//            StdDraw.setPenColor(Color.yellow);
//            StdDraw.rectangle(0.5, 0.3, 0.3, 0.5 * 0.618 / 3);
//            StdDraw.text(0.5, 0.3, "defensive");
            StdDraw.picture(0.5,0.5,"preface4.png");
        }
    }

    public static void printBoard() {
        clear();
//        Font font = new Font("Default", Font.PLAIN, 30);
//        StdDraw.setFont(font);
//        StdDraw.setPenColor(Color.green);
//        StdDraw.text(0.1, 0.92, "save");
//        StdDraw.rectangle(0.1, 0.92, 0.05, 0.05 * 0.618);
//        StdDraw.text(0.3 + 2 * 0.05 * 0.618, 0.92, "undo");
//        StdDraw.rectangle(0.3 + 2 * 0.05 * 0.618, 0.92, 0.05, 0.05 * 0.618);
//        StdDraw.setPenColor(Color.CYAN);
//        Font font2 = new Font("Default", Font.PLAIN, 15);
//        StdDraw.setFont(font2);
//        String[] str = "aabcdefghijklmnopqrs".split("");
//        for (int i = 1; i < 1 / c; i++) {
//            StdDraw.line(c * i, c / a, c * i, (1 - c) / a);
//            StdDraw.line(c, (c * i) / a, 1 - c, (c * i) / a);
//        }
        if(1/c==17)
            StdDraw.picture(0.5,0.5,"15X15chart.png");
        if(1/c==19)
            StdDraw.picture(0.5,0.5,"17X17chart.png");
        if(1/c==21)
            StdDraw.picture(0.5,0.5,"19X19chart.png");
//        int j = 0;
//        do {
//            StdDraw.text(c * j + 1.5 * c, c * 0.5 / a, String.valueOf(j + 1));
//            j++;
//            StdDraw.text(c * 0.5, (c * j + 0.5 * c) / a, str[j]);
//        } while (j < 1 / c - 2);

    }

    public static void MouseMonitoring() throws InterruptedException {
        if (isMousePressed()) {
            double x1 = StdDraw.mouseX();
            double y1 = StdDraw.mouseY();
            checkBottom(x1, y1);
            System.out.print(x1 + " " + y1 + "\n");
            if (x1 > c && x1 < 1 - c && y1 > c && y1 < (1 - c) / a) {
                x2 = ArrayInterpreter(x1);
                y2 = ArrayInterpreter(y1 * a);
            }
        }
    }

    public static boolean isMousePressed() throws InterruptedException {
        if (!StdDraw.isMousePressed()) {
            return false;
        }
        Thread.sleep(60);
        return StdDraw.isMousePressed();
    }

    public static void checkBottom(double x, double y) {
        if (y < 0.92 + 0.05 * 0.618 && y > 0.92 - 0.05 * 0.618) {
            if (x > 0.05 && x < 0.15) {
                choiceConstant = 1;
            }
            if (x > 0.2 + 0.05 * 0.618 - 0.05 && x < 0.2 + 0.05 * 0.618 + 0.05) {
                choiceConstant = 2;
            }
            if (x > 0.3 + 2 * 0.05 * 0.618 - 0.05 && x < 0.3 + 2 * 0.05 * 0.618 + 0.05) {
                undocounter = 1;
            }
        } else
            undocounter = 0;

    }

    public static int[][] Undo(int[][] State, int[][] copy1, int[][] copy2, int[][] copy3, int a) {
        if (a == 1) {
            for (int i = 0; i < State.length; i++) {
                for (int j = 0; j < State.length; j++) {
                    State[i][j] = copy1[i][j];
                }
            }
        }
        if (a == 2) {
            for (int i = 0; i < State.length; i++) {
                for (int j = 0; j < State.length; j++) {
                    State[i][j] = copy2[i][j];
                }
            }
        }
        if (a > 2) {
            for (int i = 0; i < State.length; i++) {
                for (int j = 0; j < State.length; j++) {
                    State[i][j] = copy3[i][j];
                }
            }
        }
        return State;
    }

    public static void drawChess1(double x, double y) {//print chess
        double xf = ArrayToCoord(x);
        double yf = ArrayToCoord(y);
        if (xf >= c && xf <= 1 - c && yf >= c && yf < (1 - c)) {
            StdDraw.picture(xf, yf / a, "red3.png");
        }
    }

    public static void drawChess2(double x, double y) {//print chess
        double xf = ArrayToCoord(x);
        double yf = ArrayToCoord(y);
        if (xf >= c && xf <= 1 - c && yf >= c && yf < (1 - c)) {
            StdDraw.picture(xf, yf / a, "gemblue3.png");
        }
    }

    public static void drawChess3(double x, double y) {
        double xf = ArrayToCoord(x);
        double yf = ArrayToCoord(y);
        if (xf >= c && xf <= 1 - c && yf >= c && yf < (1 - c)) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.ellipse(xf, yf / a, 0.015, 0.015 / a);

        }
    }

    public static void bufferP() {
        while (!StdDraw.isMousePressed()) {
            clear();
        }
    }

    public static void bufferN() {
        while (StdDraw.isMousePressed()) {
            clear();
        }

    }

    public static int[][] InitialState(int[][] State0, int nullcommand) {//initialize
        for (int i = 0; i < State0.length; i++) {
            for (int j = 0; j < State0.length; j++) {
                if (i == 0 || i == State0.length - 1 || j == 0 || j == State0[i].length - 1) {
                    State0[i][j] = 9;
                } else State0[i][j] = 0;

            }
        }
        if (nullcommand == 2)
            State0[(State0.length - 1) / 2][(State0.length - 1) / 2] = 2;
        return State0;

    }

    public static int[][] Record(int[][] State, double x, double y, int Chessman) {
        for (int i = 0; i < 1 / c; i++) {
            for (int j = 0; j < 1 / c; j++) {
                if ((int) x == i && (int) y == j && State[i][j] == 0)
                    State[i][j] = Chessman;
            }
        }
        return State;
    }

    public static void check(int[][] list) {

        int n = 0;
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (list[i][j] == 1 || list[i][j] == 2) {
                    n++;
                }
            }
        }
        if (n % 2 == 1)
            chessman = 1;
        if (n % 2 == 0)
            chessman = 2;
        if (n % 2 == 0)
            pan4 = true;
        if (n % 2 == 1)
            pan4 = false;


    }

    public static boolean checkList(int[][] b, int[][] a) {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                if (b[i][j] != a[i][j])
                    return false;
            }
        }
        return true;
    }

    public static void printList(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public static void printChess(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] == 2) {
                    drawChess1(i, j);
                }
                if (a[i][j] == 1)
                    drawChess2(i, j);


            }
        }
    }

    public static double ArrayInterpreter(double a) {
        return a / c;
    }

    public static double ArrayToCoord(double a) {
        return (a + 0.5) * c;
    }

    public static boolean Judge(int[][] field, int Chessman) {

        boolean result1 = true, result2 = true, result3 = true, result4 = true, Result;
        l1:
        for (int i = 0; i < field.length - 5; i++) {
            for (int j = 0; j < field[i].length; j++) {
                result1 = field[i][j] == Chessman && field[i + 1][j] == Chessman && field[i + 2][j] == Chessman && field[i + 3][j] == Chessman && field[i + 4][j] == Chessman;
                if (result1)
                    break l1;
            }
        }
        l2:
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length - 5; j++) {
                result2 = field[i][j] == Chessman && field[i][j + 1] == Chessman && field[i][j + 2] == Chessman && field[i][j + 3] == Chessman && field[i][j + 4] == Chessman;
                if (result2)
                    break l2;
            }
        }
        l3:
        for (int i = 0; i < field.length - 5; i++) {
            for (int j = 0; j < field[i].length; j++) {
                result3 = field[i][j] == Chessman && field[i + 1][j + 1] == Chessman && field[i + 2][j + 2] == Chessman && field[i + 3][j + 3] == Chessman && field[i + 4][j + 4] == Chessman;
                if (result3)
                    break l3;
            }
        }
        l4:
        for (int i = field.length - 1; i > 4; i--) {
            for (int j = 0; j < field[i].length - 4; j++) {
                result4 = field[i][j] == Chessman && field[i - 1][j + 1] == Chessman && field[i - 2][j + 2] == Chessman && field[i - 3][j + 3] == Chessman && field[i - 4][j + 4] == Chessman;
                if (result4)
                    break l4;
            }
        }
        Result = result1 | result2 | result3 | result4;
        return Result;
    }

    public static void printChoose3() {
        clear();
        while (!StdDraw.isMousePressed()) {
            StdDraw.setPenColor(Color.magenta);
            StdDraw.rectangle(0.5, 0.7, 0.3, 0.5 * 0.618 / 3);
            StdDraw.text(0.5, 0.7, "Leave");
            StdDraw.setPenColor(Color.yellow);
            StdDraw.rectangle(0.5, 0.3, 0.3, 0.5 * 0.618 / 3);
            StdDraw.text(0.5, 0.3, "Again");
        }
    }

    private static void timer() {
        final Font font = new Font("Default", Font.PLAIN, 30);
        StdDraw.setFont(font);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (pan) {
                    midtime = 0;
                    pan = false;
                }
                midtime++;
                mm++;

                long m = mm / 60;
                long ts = mm % 60 / 10;
                long s = mm % 60 % 10;
                ss = 16 - midtime % 60;


                StdDraw.setPenColor(Color.black);
                StdDraw.filledRectangle(0.8, 0.93, 0.35, 0.05);
                StdDraw.setPenColor(Color.RED);
                if (ss != 0 && !pan3) {
                    StdDraw.text(0.7, 0.92, m + ":" + ts + s + "      " + "time last:" + ss);
                }


                if (ss == 0) {
                    timer.cancel();
                    pan2 = true;
                    midtime = 0;
                    mm = 0;
                }
                if (pan3) {
                    timer.cancel();
                    pan3 = false;
                    midtime = 0;
                    mm = 0;
                }


            }
        }, 0, 1000);


    }

    public static void win2(int a) {


        Font font2 = new Font("Default", Font.PLAIN, 100);
        StdDraw.setFont(font2);

        while (!StdDraw.isMousePressed()) {
            if (a == 1) {


                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.text(0.5, 0.3, "BLUE win!");

                StdDraw.setPenColor(Color.orange);

                StdDraw.text(0.5, 0.5, "BLUE win!");

                StdDraw.setPenColor(StdDraw.CYAN);

                StdDraw.text(0.5, 0.7, "BLUE win!");
                StdDraw.setPenColor(Color.black);


            }
            if (a == 2) {

                StdDraw.setPenColor(Color.magenta);
                StdDraw.text(0.51, 0.3, "RED win!");
                StdDraw.setPenColor(Color.PINK);
                StdDraw.text(0.5, 0.5, "RED win!");
                StdDraw.setPenColor(StdDraw.CYAN);
                StdDraw.text(0.5, 0.7, "RED win!");
                StdDraw.setPenColor(Color.black);

            }
        }

    }

    public static boolean BalanceBrake(int[][] State, int chessman, double x, double y) {
        boolean check = false;
        int check1 = 0, check2 = 0, check3 = 0, check4 = 0, check5 = 0, check6 = 0, check7 = 0, check8 = 0, check9 = 0, check10 = 0, check11 = 0, check12 = 0;
        int x0 = (int) (x);
        int y0 = (int) (y);

        if (x0 - 3 > 0 && x0 + 1 < State.length - 1)
            if (State[x0 - 1][y0] == chessman && State[x0 - 2][y0] == chessman && State[x0 - 3][y0] == 0 && State[x0 + 1][y0] == 0)
                check1 = 1;

        if (x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0 + 1][y0] == chessman && State[x0 + 2][y0] == chessman && State[x0 + 3][y0] == 0 && State[x0 - 1][y0] == 0)
                check2 = 1;
        }

        if (x0 + 2 < State.length - 2 && x0 + 2 > 0) {
            if (State[x0 - 1][y0] == chessman && State[x0 + 1][y0] == chessman && State[x0 - 2][y0] == 0 && State[x0 + 2][y0] == 0)
                check3 = 1;
        }
        if (x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0][y0 - 1] == chessman && State[x0][y0 - 2] == chessman && State[x0][y0 - 3] == 0 && State[x0][y0 + 1] == 0)
                check4 = 1;
        }
        if (y0 + 3 < State.length - 1 && y0 - 1 > 0) {
            if (State[x0][y0 + 1] == chessman && State[x0][y0 + 2] == chessman && State[x0][y0 + 3] == 0 && State[x0][y0 - 1] == 0)
                check5 = 1;
        }
        if (y0 + 2 < State.length - 2 && y0 + 2 > 0) {
            if (State[x0][y0 - 1] == chessman && State[x0][y0 + 1] == chessman && State[x0][y0 - 2] == 0 && State[x0][y0 + 2] == 0)
                check6 = 1;
        }
        if (y0 - 3 > 0 && y0 + 1 < State.length - 1 && x0 - 3 > 0 && x0 + 1 < State.length - 1) {
            if (State[x0 - 1][y0 - 1] == chessman && State[x0 - 2][y0 - 2] == chessman && State[x0 - 3][y0 - 3] == 0 && State[x0 + 1][y0 + 1] == 0)
                check7 = 1;
        }
        if (x0 + 3 < State.length - 1 && x0 - 1 > 0 && y0 + 3 < State.length - 1 && y0 - 1 > 0) {
            if (State[x0 + 1][y0 + 1] == chessman && State[x0 + 2][y0 + 2] == chessman && State[x0 + 3][y0 + 3] == 0 && State[x0 - 1][y0 - 1] == 0)
                check8 = 1;
        }
        if (y0 + 2 < State.length - 2 && y0 + 2 > 0 && x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0 - 1][y0 - 1] == chessman && State[x0 + 1][y0 + 1] == chessman && State[x0 - 2][y0 - 2] == 0 && State[x0 + 2][y0 + 2] == 0)
                check9 = 1;
        }
        if (y0 + 3 < State.length - 1 && y0 - 1 > 0 && x0 - 3 > 0 && x0 + 1 < State.length - 1) {
            if (State[x0 - 1][y0 + 1] == chessman && State[x0 - 2][y0 + 2] == chessman && State[x0 - 3][y0 + 3] == 0 && State[x0 + 1][y0 - 1] == 0)
                check10 = 1;
        }
        if (x0 + 3 < State.length - 1 && x0 - 1 > 0 && x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0 + 1][y0 - 1] == chessman && State[x0 + 2][y0 - 2] == chessman && State[x0 + 3][y0 - 3] == 0 && State[x0 - 1][y0 + 1] == 0)
                check11 = 1;
        }
        if (y0 + 2 < State.length - 2 && y0 + 2 > 0 && x0 + 2 < State.length - 2 && x0 + 2 > 0) {
            if (State[x0 - 1][y0 + 1] == chessman && State[x0 + 1][y0 - 1] == chessman && State[x0 - 2][y0 + 2] == 0 && State[x0 + 2][y0 - 2] == 0)
                check12 = 1;
        }
        if (check1 + check2 + check3 + check4 + check5 + check6 + check7 + check8 + check9 + check10 + check11 + check12 > 1)
            check = true;

        return check;
    }

    public static int[][] Compute(int[][] State, double x, double y, int chessman) {
        int x0 = (int) (x);
        int y0 = (int) (y);
        for (int i = 0; i < 100; i++) {
            int unknown1 = (int) (Math.random() * (3)) - 1;
            int unknown2 = (int) (Math.random() * (3)) - 1;
            if (State[x0 + unknown1][y0 + unknown2] == 0) {
                State[x0 + unknown1][y0 + unknown2] = chessman;
                break;
            }
        }
        return State;
    }
}