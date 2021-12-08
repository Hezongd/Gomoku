package Project;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Gomoku {
    private static double x0,y0,x2,y2;//x1 is mouseX,y1 is mouseY,x2 and y2 is processed x0&y0.
    private static double c;
    private static int b=0;//用于解决结束界面的问题的参数
    private static final double c1=1.0/17;//15*15
    private static final double c2=1.0/19;//17*17
    private static final double c3=1.0/21;//19*19
    private static final double a = 1.1;// is H/W
     private static int chessman = 2;//调整后的
    public static int choiceConstant;//用于解决undosaveload的参数
    
    public static Calendar Cal;//timer
    public static long midtime;//timer


   

     public static void main(String[] args){
        StdDraw.setCanvasSize(800,880);
        l1:
        for (;true;){
            clear();

            x2=0;
            y2=0;
            if (b!=0){
                bufferP();
                bufferN();
                win2(chessman);
                bufferP();
                bufferN();
                while (Choice()==0)
                    printChoose3();
                if (Choice()==1)
                    break;
                bufferP();
                bufferN();
            }

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


            if (Choice()==2){
                bufferP();
                bufferN();
                while (!StdDraw.isMousePressed()||Choice()==0){
                        printChoose2();

                }bufferP();
                bufferN();
                }
            int[][] state = new int[(int)(1/c)][(int)(1/c)];

            Font font = new Font("Default",Font.PLAIN,15);
            clear();
            StdDraw.setFont(font);

            printBoard();

            InitialState(state);

            do {//禁手方法和saveloadundo判断请写在这个循环里
                check(state);
                choiceConstant=0;
                MouseMonitoring();//如果有点按钮这里之后choiceConstant会发生变化
                if (x2>c&&x2<1-c&&y2>c/a&&y2<(1-c)/a&&x0!=x2||y0!=y2){
                    x0=x2;
                    y0=y2;
                }

                System.out.println(checkBottom());//save,load,undo的按钮还在调试
                Record(state,x2,y2, chessman);
                if (!Judge(state,chessman))
                    printChess(state);
                if (Judge(state,chessman)){
                    InitialState(state);
                    clear();
                    b++;
                    continue l1;
                }
            }while (true);//请不要质疑这里的true
        }
    }
    }public static void clear(){
        StdDraw.setPenColor(Color.black);
        StdDraw.filledSquare(0.5,0.5,0.5);
    }
   public static void welcome(){
        clear();
        while (!StdDraw.isMousePressed()){
            Font font3 = new Font("Default",Font.PLAIN,100);
            StdDraw.setFont(font3);
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.5,0.62,"CYBER");
            Font font = new Font("Default",Font.PLAIN,150);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.text(0.5,0.5,"Gomoku!");
            Font font2 = new Font("Default",Font.PLAIN,30);
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
    public static void printChoose2(){//offensive or defensive

        while (!StdDraw.isMousePressed()){
            Font font = new Font("Default",Font.PLAIN,50);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.magenta);
            StdDraw.rectangle(0.5,0.7,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.7,"offensive");
            StdDraw.setPenColor(Color.yellow);
            StdDraw.rectangle(0.5,0.3,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.3,"defensive");
        }
    }
   public static void printBoard(){
        clear();
        Font font = new Font("Default",Font.PLAIN,30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.green);
        StdDraw.text(0.1,0.92,"save");
        StdDraw.rectangle(0.1,0.92,0.05,0.05*0.618);
        StdDraw.text(0.2+0.05*0.618,0.92,"load");
        StdDraw.rectangle(0.2+0.05*0.618,0.92,0.05,0.05*0.618);
        StdDraw.text(0.3+2*0.05*0.618,0.92,"undo");
        StdDraw.rectangle(0.3+2*0.05*0.618,0.92,0.05,0.05*0.618);


        StdDraw.setPenColor(Color.CYAN);
        Font font2 = new Font("Default",Font.PLAIN,15);
        StdDraw.setFont(font2);


        String[] str ="aabcdefghijklmnopqrs".split("");

        for (int i=1;i<1/c;i++){
            StdDraw.line(c*i,c/a,c*i,(1-c)/a);
            StdDraw.line(c,(c*i)/a,1-c,(c*i)/a);
        }int j=0;
        do {
            StdDraw.text(c*j+1.5*c,c*0.5/a, String.valueOf(j+1));
            j++;
            StdDraw.text(c*0.5,(c*j+0.5*c)/a,str[j]);

        }while (j<1/c-2);
    }
    public static void MouseMonitoring(){
        if (StdDraw.isMousePressed()) {
                double x1 = StdDraw.mouseX();
                double y1 = StdDraw.mouseY();
                checkBottom(x1,y1);
                x2 = ArrayInterpreter(x1);
                y2 = ArrayInterpreter(y1*a);//这里有更新
                

        }
    }
    public static void checkBottom(double x,double y ){
        if (y<0.92+0.05*0.618&&y>0.92-0.05*0.618){
            if (x>0.05&&x<0.15)
                choiceConstant=1;
            if (x>0.2+0.05*0.618-0.05&&x<0.2+0.05*0.618+0.05)
                choiceConstant=2;
            if (x>0.3+2*0.05*0.618-0.05&&x<0.3+2*0.05*0.618+0.05)
                choiceConstant=3;

        }
    }
   public static void drawChess1(double x,double y){//print chess
        double xf=ArrayToCoord(x);
        double yf=ArrayToCoord(y);
        if (xf>=c&&xf<=1-c&&yf>=c&&yf<(1-c)){
            StdDraw.setPenColor(Color.magenta);
            StdDraw.ellipse(xf,yf/a,0.015,0.015/a);
        }
    }
    public static void drawChess2(double x,double y){//print chess
        double xf=ArrayToCoord(x);
        double yf=ArrayToCoord(y);
        if (xf>=c&&xf<=1-c&&yf>=c&&yf<(1-c)){
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.ellipse(xf,yf/a,0.015,0.015/a);

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
            for (int j = 0;j<State0.length;j++){
                if(i==0||i==State0.length-1||j==0||j==State0[i].length-1){
                    State0[i][j]=9;
                }else State0[i][j]=0;
            }
        }return State0;
    }

    public static int[][] Record(int[][] State,double x,double y,int Chessman){
        for (int i = 0; i < 1/c; i++) {
            for (int j = 0; j < 1/c ; j++) {
                if((int)x == i && (int)y == j && State[i][j]==0)
                    State[i][j] = Chessman;
            }
        }
        return State;
    }public static void check(int[][] list){//用于代替counter

        int n =0;
        for (int i =0;i< list.length;i++){
            for (int j =0;j< list[i].length;j++){
                if (list[i][j]==1||list[i][j]==2){
                    n++;
                }
            }
        }if (n%2==1)
            chessman=1;
        if (n%2==0)
            chessman=2;

    }

    public static void printChess(int[][] a){
        for(int i=0;i<a.length;i++){
            for (int j=0;j<a[i].length;j++){
                if (a[i][j]==2){
                    drawChess1(i,j);
                }
                if (a[i][j]==1)
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
    }public static void win2(int a){//调整后的结束界面
        MouseMonitoring();
        Gomoku.clear();
        Font font = new Font("Default", Font.PLAIN, 100);
        StdDraw.setFont(font);

        while (!StdDraw.isMousePressed()){if (a==1){


                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.text( 0.5, 0.3, "Wolley win!");
                StdDraw.text( 0.5, 0.5, "Wolley win!");
                StdDraw.text( 0.5, 0.7, "Wolley win!");
                StdDraw.setPenColor(Color.orange);
                StdDraw.text( 0.5, 0.3, "Wolley win!");
                StdDraw.text( 0.5, 0.5, "Wolley win!");
                StdDraw.text( 0.5, 0.7, "Wolley win!");
                StdDraw.setPenColor(StdDraw.CYAN);
                StdDraw.text( 0.5, 0.3, "Wolley win!");
                StdDraw.text( 0.5, 0.5, "Wolley win!");
                StdDraw.text( 0.5, 0.7, "Wolley win!");


            }
            if (a==2){

                StdDraw.setPenColor(Color.magenta);


                StdDraw.text(0.51, 0.3, "Atnegam win!");
                StdDraw.text(0.5, 0.5, "Atnegam win!");
                StdDraw.text(0.5, 0.7, "Atnegam win!");
                StdDraw.setPenColor(Color.PINK);
                StdDraw.text(0.51, 0.3, "Atnegam win!");
                StdDraw.text(0.5, 0.5, "Atnegam win!");
                StdDraw.text(0.5, 0.7, "Atnegam win!");
                StdDraw.setPenColor(StdDraw.CYAN);
                StdDraw.text(0.51, 0.3, "Atnegam win!");
                StdDraw.text(0.5, 0.5, "Atnegam win!");
                StdDraw.text(0.5, 0.7, "Atnegam win!");
            }
        }

    }public static void printChoose3(){//结束界面选项
        clear();
        while (!StdDraw.isMousePressed()){
            Font font = new Font("Default",Font.PLAIN,50);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.magenta);
            StdDraw.rectangle(0.5,0.7,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.7,"Leave");
            StdDraw.setPenColor(Color.yellow);
            StdDraw.rectangle(0.5,0.3,0.3,0.5*0.618/3);
            StdDraw.text(0.5,0.3,"Again");
        }
    }private static void time(){//还在调试
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                midtime--;
                long hh =midtime/60/60;
                long mm =midtime/60%60;
                long ss =midtime%60;
                System.out.println(hh+" "+mm+" "+ss);
            }
        },0,1000);
    }
    private static void printTime(){//还在调试
    }
    


}
