import java.util.Scanner;

public class HumanHumanBattle {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int length;
        length = input.nextInt() + 2;
        double x, y;
        int counter = 0;
        int chessman;
        int undoCommand = 0;
        boolean command = true;
        int[][] state = new int[length][length];
        int[][] copy1 = new int[length][length];
        int[][] copy2 = new int[length][length];
        int[][] copy3 = new int[length][length];
        int[][] copy4 = new int[length][length];
        int[][] copy5 = new int[length][length];
        int[][] copy6 = new int[length][length];
        InitialState(state);
        do {

            chessman = counter % 2 + 1;
            int undocounter = input.nextInt();
            if (undocounter != 0)
                undoCommand = undoCommand + 1;
            else
                undoCommand = 0;
//            if(command)
//                undoCommand = 0;
//            else
//                undoCommand = undoCommand+1;
            if (undoCommand == 0) {
                x = input.nextDouble() / length;
                y = input.nextDouble() / length;
                if (BalanceBrake(state, x, y)) {
                    Print(state);
                } else {
                    for (int i = 0; i < length; i++) {
                        for (int j = 0; j < length; j++) {
                            copy6[i][j] = copy5[i][j];
                            copy5[i][j] = copy4[i][j];
                            copy4[i][j] = copy3[i][j];
                            copy3[i][j] = copy2[i][j];
                            copy2[i][j] = copy1[i][j];
                            copy1[i][j] = state[i][j];
                        }
                    }
                    Record(state, x, y, chessman);
                }
                counter++;
            } else
                Undo(state, copy2, copy4, copy6, undoCommand);
        } while (Judge(state, chessman) == false);
        if (chessman == 1)
            System.out.println("Black win");
        else
            System.out.println("White win");
    }

    public static int[][] InitialState(int[][] State0) {
        for (int i = 0; i < State0.length; i++) {
            for (int j = 0; j < State0.length; j++) {
                if (i == 0 || i == State0.length - 1 || j == 0 || j == State0.length - 1)
                    State0[i][j] = 9;
                else
                    State0[i][j] = 0;
            }
        }
        return State0;
    }

    public static int[][] Record(int[][] State, double x, double y, int Chessman) {
        for (int i = 0; i < State.length; i++) {
            for (int j = 0; j < State.length; j++) {
                if (x * State.length == i && y * State.length == j && State[i][j] == 0)
                    State[i][j] = Chessman;
                System.out.print(State[i][j] + "  ");
            }
            System.out.print("\n");
        }
        return State;
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

    public static int[][] Undo(int[][] State, int[][] copy1, int[][] copy2, int[][] copy3, int times) {
        if (times == 1) {
            for (int i = 0; i < State.length; i++) {
                for (int j = 0; j < State.length; j++) {
                    State[i][j] = copy1[i][j];
                    System.out.print(State[i][j] + "  ");
                }
                System.out.print("\n");
            }
        }
        if (times == 2) {
            for (int i = 0; i < State.length; i++) {
                for (int j = 0; j < State.length; j++) {
                    State[i][j] = copy2[i][j];
                    System.out.print(State[i][j] + "  ");
                }
                System.out.print("\n");
            }
        }
        if (times > 2) {
            for (int i = 0; i < State.length; i++) {
                for (int j = 0; j < State.length; j++) {
                    State[i][j] = copy3[i][j];
                    System.out.print(State[i][j] + "  ");
                }
                System.out.print("\n");
            }
        }
        return State;
    }

    public static boolean BalanceBrake(int[][] State, double x, double y) {
        boolean check = false;
        int check1 = 0, check2 = 0, check3 = 0, check4 = 0, check5 = 0, check6 = 0, check7 = 0, check8 = 0, check9 = 0, check10 = 0, check11 = 0, check12 = 0;
        int x0 = (int) (x * State.length);
        int y0 = (int) (y * State.length);

        if (x0 - 3 > 0 && x0 + 1 < State.length - 1)
            if (State[x0 - 1][y0] == 1 && State[x0 - 2][y0] == 1 && State[x0 - 3][y0] == 0 && State[x0 + 1][y0] == 0)
                check1 = 1;

        if (x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0 + 1][y0] == 1 && State[x0 + 2][y0] == 1 && State[x0 + 3][y0] == 0 && State[x0 - 1][y0] == 0)
                check2 = 1;
        }

        if (x0 + 2 < State.length - 2 && x0 + 2 > 0) {
            if (State[x0 - 1][y0] == 1 && State[x0 + 1][y0] == 1 && State[x0 - 2][y0] == 0 && State[x0 + 2][y0] == 0)
                check3 = 1;
        }
        if (x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0][y0 - 1] == 1 && State[x0][y0 - 2] == 1 && State[x0][y0 - 3] == 0 && State[x0][y0 + 1] == 0)
                check4 = 1;
        }
        if (y0 + 3 < State.length - 1 && y0 - 1 > 0) {
            if (State[x0][y0 + 1] == 1 && State[x0][y0 + 2] == 1 && State[x0][y0 + 3] == 0 && State[x0][y0 - 1] == 0)
                check5 = 1;
        }
        if (y0 + 2 < State.length - 2 && y0 + 2 > 0) {
            if (State[x0][y0 - 1] == 1 && State[x0][y0 + 1] == 1 && State[x0][y0 - 2] == 0 && State[x0][y0 + 2] == 0)
                check6 = 1;
        }
        if (y0 - 3 > 0 && y0 + 1 < State.length - 1 && x0 - 3 > 0 && x0 + 1 < State.length - 1) {
            if (State[x0 - 1][y0 - 1] == 1 && State[x0 - 2][y0 - 2] == 1 && State[x0 - 3][y0 - 3] == 0 && State[x0 + 1][y0 + 1] == 0)
                check7 = 1;
        }
        if (x0 + 3 < State.length - 1 && x0 - 1 > 0 && y0 + 3 < State.length - 1 && y0 - 1 > 0) {
            if (State[x0 + 1][y0 + 1] == 1 && State[x0 + 2][y0 + 2] == 1 && State[x0 + 3][y0 + 3] == 0 && State[x0 - 1][y0 - 1] == 0)
                check8 = 1;
        }
        if (y0 + 2 < State.length - 2 && y0 + 2 > 0 && x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0 - 1][y0 - 1] == 1 && State[x0 + 1][y0 + 1] == 1 && State[x0 - 2][y0 - 2] == 0 && State[x0 + 2][y0 + 2] == 0)
                check9 = 1;
        }
        if (y0 + 3 < State.length - 1 && y0 - 1 > 0 && x0 - 3 > 0 && x0 + 1 < State.length - 1) {
            if (State[x0 - 1][y0 + 1] == 1 && State[x0 - 2][y0 + 2] == 1 && State[x0 - 3][y0 + 3] == 0 && State[x0 + 1][y0 - 1] == 0)
                check10 = 1;
        }
        if (x0 + 3 < State.length - 1 && x0 - 1 > 0 && x0 + 3 < State.length - 1 && x0 - 1 > 0) {
            if (State[x0 + 1][y0 - 1] == 1 && State[x0 + 2][y0 - 2] == 1 && State[x0 + 3][y0 - 3] == 0 && State[x0 - 1][y0 + 1] == 0)
                check11 = 1;
        }
        if (y0 + 2 < State.length - 2 && y0 + 2 > 0 && x0 + 2 < State.length - 2 && x0 + 2 > 0) {
            if (State[x0 - 1][y0 + 1] == 1 && State[x0 + 1][y0 - 1] == 1 && State[x0 - 2][y0 + 2] == 0 && State[x0 + 2][y0 - 2] == 0)
                check12 = 1;
        }
        if (check1 + check2 + check3 + check4 + check5 + check6 + check7 + check8 + check9 + check10 + check11 + check12 > 1)
            check = true;

        return check;
    }

    public static int[][] Print(int[][] State) {
        for (int i = 0; i < State.length; i++) {
            for (int j = 0; j < State.length; j++) {
                System.out.print("  " + State[i][j]);
            }
            System.out.print("\n");
        }

        return State;
    }
}
