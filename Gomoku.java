package Project;

import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.util.Arrays;

public class Gomoku {
    private static double x0,y0,x2,y2;//x1 is mouseX,y1 is mouseY,x2 and y2 is processed x0&y0.
    private static double c;
    private static final double c1=1.0/17;//15*15
    private static final double c2=1.0/19;//17*17
    private static final double c3=1.0/21;//19*19

    private static int counter = 1;

    public static void main(String[] args){



        for (;true;){
            welcome();

            bufferP();
            bufferN();

            while (Size()==0)
                chooseSize();
            if (Size()==15)
                c=c1;
            if (Size()==17)
                c=c2;
            if (Size()==19)
                c=c3;

            bufferP();
            bufferN();

            while (Choice()==0)
                printChoose();

            bufferN();
            Font font = new Font("Default",Font.PLAIN,15);
            StdDraw.setFont(font);
            printBoard();
            int[][] state = new int[(int)(1/c)+2][(int)(1/c)+2];
            InitialState(state);

            do {
                MouseMonitoring();
                if (x0!=x2||y0!=y2&&x2>c&&x2<1-c&&y2>c&&y2<1-c){
                    counter++;
                    x0=x2;
                    y0=y2;
                }
                int chessman = counter % 2 + 1;
                Record(state,x2,y2, chessman);
                printChess(state);
            }while (Judge(state,chessman));


        }
    }public static void clear(){
        StdDraw.setPenColor(Color.black);
        StdDraw.filledSquare(0.5,0.5,0.5);
    }
    public static void welcome(){
        clear();
        while (!StdDraw.isMousePressed()){
            Font font = new Font("Default",Font.PLAIN,100);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.text(0.5,0.5,"Gomoku!");
            Font font2 = new Font("Default",Font.PLAIN,15);
            StdDraw.setFont(font2);
            StdDraw.setPenColor(Color.cyan);
            StdDraw.text(0.5,0.1,"touch to start");}
    }
    public static void chooseSize(){
        clear();
        while (!StdDraw.isMousePressed()){
            Font font = new Font("Default",Font.PLAIN,50);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.rectangle(0.5,0.17,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.5-0.33,"15 X 15");
            StdDraw.setPenColor(Color.cyan);
            StdDraw.rectangle(0.5,0.5,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.5,"17 X 17");
            StdDraw.setPenColor(Color.yellow);
            StdDraw.rectangle(0.5,0.83,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.5+0.33,"19 X 19");
        }
    }
    public static int Size(){
        if(StdDraw.isMousePressed()){
            double y = StdDraw.mouseY();
            double x = StdDraw.mouseX();
            if(x<0.8&&x>0.2&&y>0.17-0.5*0.618/3&&y<0.17+0.5*0.618/3)
                return 15;
            if(x<0.8&&x>0.2&&y>0.5-0.5*0.618/3&&y<0.5+0.5*0.618/3)
                return 17;
            if(x<0.8&&x>0.2&&y>0.83-0.5*0.618/3&&y<0.83+0.5*0.618/3)
                return 19;
        }return 0;
    }
    public static void printChoose(){
        while (!StdDraw.isMousePressed()){
            Font font = new Font("Default",Font.PLAIN,50);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.magenta);
            StdDraw.rectangle(0.5,0.7,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.7,"Human");
            StdDraw.setPenColor(Color.yellow);
            StdDraw.rectangle(0.5,0.3,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.3,"Computer");
        }
    }
    public static int Choice(){
        if (StdDraw.isMousePressed()){
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            if (x<0.8&&x>0.2&&y<0.7+0.5*0.618/3&&y>0.7-0.5*0.618/3)
                return 1;//Human
            if (x<0.8&&x>0.2&&y<0.3+0.5*0.618/3&&y>0.3-0.5*0.618/3)
                return -1;

        }
        return 0;
    }
    public static void printBoard(){
        clear();
        StdDraw.setPenColor(Color.cyan);
        String[] str ="aabcdefghijklmnopqrs".split("");

        for (int i=1;i<1/c;i++){
            StdDraw.line(c*i,c,c*i,1-c);
            StdDraw.line(c,c*i,1-c,c*i);
        }int j=0;
        do {
            StdDraw.text(c*j+1.5*c,c*0.5, String.valueOf(j+1));
            j++;
            StdDraw.text(c*0.5,c*j+0.5*c,str[j]);

        }while (j<1/c-2);
    }
    public static void MouseMonitoring(){
        if (StdDraw.isMousePressed()) {
                double x1 = StdDraw.mouseX();
                double y1 = StdDraw.mouseY();
                System.out.print(x1+" "+y1+"|\t");
                x2 = ArrayInterpreter(x1);
                y2 = ArrayInterpreter(y1);
                System.out.print(x2+" "+y2+"\t\n");

        }
    }
    public static void drawChess1(double x,double y){//print chess
        double xf=ArrayToCoord(x);
        double yf=ArrayToCoord(y);
        if (xf>=c&&xf<=1-c&&yf>=c&&yf<1-c){
            StdDraw.setPenColor(Color.magenta);
            StdDraw.circle(xf,yf,0.015);
        }
    }
    public static void drawChess2(double x,double y){//print chess
        double xf=ArrayToCoord(x);
        double yf=ArrayToCoord(y);
        if (xf>=c&&xf<=1-c&&yf>=c&&yf<1-c){
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.circle(xf,yf,0.015);

        }
    }
    public static void bufferP(){
        while (!StdDraw.isMousePressed()){
            clear();
        }
    }
    public static void bufferN(){
        while (StdDraw.isMousePressed()){
            clear();
        }

    }
    public static int[][] InitialState(int[][] State0){//initialize
        for (int i = 0; i < State0.length; i++) {
            Arrays.fill(State0[i], 0);
        }
        Arrays.fill(State0[0],9);
        Arrays.fill(State0[State0.length-1],9);


        return State0;
    }

    public static int[][] Record(int[][] State,double x,double y,int Chessman){
        for (int i = 0; i < 1/c; i++) {
            for (int j = 0; j < 1/c ; j++) {
                if((int)x == i && (int)y == j && State[i][j]==0)
                    State[i][j] = Chessman;
            }
        }
        return State;
    }

    public static void printChess(int[][] a){
        for(int i=0;i<a.length;i++){
            for (int j=0;j<a[i].length;j++){
                if (a[i][j]==1){
                    drawChess1(i,j);
                }
                if (a[i][j]==2)
                    drawChess2(i,j);
            }
        }
    }

    public static double ArrayInterpreter(double a){
        return a/c;
    }

    public static double ArrayToCoord(double a){
        return (a+0.5)*c;
    }
    public static void timer(){
        
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
        for (int i = field.length - 1; i > 5; i--) {
            for (int j = 0; j < field[i].length - 4; j++) {
                result4 = field[i][j] == Chessman && field[i - 1][j + 1] == Chessman && field[i - 2][j + 2] == Chessman && field[i - 3][j + 3] == Chessman && field[i - 4][j + 4] == Chessman;
                if (result4)
                    break l4;
            }
        }
        Result = result1 | result2 | result3 | result4;
        return Result;
    }


}
