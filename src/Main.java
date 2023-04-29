import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final char EMPTY = '-';
    static final char GOOD_GUESS = 'V';
    static final char BAD_GUESS = 'X';
    static final char HIT_BATTLESHIP = 'X';
    static final char IS_BATTLESHIP = '#';
    public static Scanner scanner;
    public static Random rnd;

    public static void printBoard(int row, int col, char[][] borad){
        int temp_row = row-1;
        while (temp_row / 10 != 0) {
            System.out.print(' ');
            temp_row /= 10;
        }
        temp_row = row-1;
        for (int i = 0 ; i < col; i++) {
            System.out.print(" " + i);
        }
        System.out.println(" ");
        for (int i = 0; i < row ; i++){
            while (temp_row/10 != 0) {
                System.out.print(" ");
                temp_row /= 10;
            }
            System.out.print(i);
            for (int j = 0; j < col ; j++){
                System.out.print(" "+borad[i][j]);
            }
            System.out.println(" ");
        }
    }
    public static int outBoard(int row, int col, int x, int y) {
        if (x < 0 || y < 0 || x > row-1 || y > col -1) {
            return 0;
        }
        return 1;
    }
    public static int newPoint(int x, int y, char[][] userGuessingBorad) {
        if (userGuessingBorad[x][y] == EMPTY) {
            return 1;
        }
        return 0;
    }
    public static int isHit(int x, int y, char[][] ComputerGameBorad){
        if (ComputerGameBorad[x][y] == IS_BATTLESHIP) {
            return 1;
        }
        return 0;
    }
    public static void updateBoards (int x, int y, char[][] borad, char updateSign){
        borad[x][y] = updateSign;
    }
    public static int isDrowned(int row , int col, int x, int y, char[][] borad){
        if ((x > 0 && borad[x-1][y] == EMPTY) && (x < row-1 && borad[x+1][y] == EMPTY)){
            while (borad[x][y] != EMPTY) {
                int new_x = x;
                while (new_x > 0) {
                    if (borad[new_x - 1][y] == IS_BATTLESHIP) {
                        return 0;
                    }
                    new_x--;
                }
                new_x = x;
                while (new_x < row-1) {
                    if (borad[x + 1][y] == IS_BATTLESHIP) {
                        return 0;
                    }
                    new_x++;
                }
            }
        }
        if ((y > 0 && borad[x][y-1] == EMPTY) && (y < col-1 && borad[x][y+1] == EMPTY)){
            while (borad[x][y] != EMPTY) {
                int new_y = y;
                while (new_y > 0) {
                    if (borad[x][new_y-1] == IS_BATTLESHIP) {
                        return 0;
                    }
                    new_y--;
                }
                new_y = y;
                while (new_y < col-1) {
                    if (borad[x][new_y+1] == IS_BATTLESHIP) {
                        return 0;
                    }
                    new_y++;
                }
            }
        }
        return 1;
    }
    public static int userTurn(char[][] userGuessingBorad, char[][] ComputerGameBorad, int r, int row , int col) {
        System.out.println("Your current guessing board:");
        printBoard(row,col,userGuessingBorad);
        boolean goodPoint = true;
        int x = 0, y = 0;
        while (goodPoint) {
            System.out.println("Enter a tile to attack");
            Scanner scanner = new Scanner(System.in);
            String getPointString = scanner.nextLine();
            String[] getPointArr = getPointString.split(",",2);
            x = Integer.parseInt(getPointArr[0]);
            y = Integer.parseInt(getPointArr[1]);

            if (outBoard(row, col , x, y) != 0){
                System.out.println("Illegal tile, try again!");
                continue;
            }
            if (newPoint(x,y,userGuessingBorad) == 0){
                System.out.println("Tile already attacked, try again!");
                continue;
            }
            goodPoint = false;
        }

        if (isHit(x,y,ComputerGameBorad) == 0) {
            System.out.println("That is a miss!");
            updateBoards(x,y, userGuessingBorad, BAD_GUESS);
        }
        else {
            System.out.println("That is a hit!");
            updateBoards(x,y, userGuessingBorad, GOOD_GUESS);
            updateBoards(x,y, ComputerGameBorad, HIT_BATTLESHIP);
            if (isDrowned(row, col ,x ,y , ComputerGameBorad) == 1){
                r--;
                System.out.println("The computer's battleship has been drowned,"+r+" more battleships to go!");
            }
        }
        return r;
    }
    public static int ComputerTurn(char[][] ComputerGuessingBorad, char[][] userGameBorad, int r, int row , int col) {
        boolean goodPoint = true;
        int x = 0, y = 0;
        while (goodPoint) {
            Random rn1 = new Random();
            x = rn1.nextInt((row-1) - 0);
            Random rn2 = new Random();
            y = rn2.nextInt((col-1) - 0);

            if (outBoard(row, col , x, y) == 1){
                continue;
            }
            goodPoint = false;
        }
        System.out.println("The computer attacked ("+x+","+y+")!");
        if (isHit(x,y,userGameBorad) == 0) {
            System.out.println("That is a miss!");
            updateBoards(x,y, ComputerGuessingBorad, BAD_GUESS);
        }
        else {
            System.out.println("That is a hit!");
            updateBoards(x,y, ComputerGuessingBorad, GOOD_GUESS);
            updateBoards(x,y, userGameBorad, HIT_BATTLESHIP);
            if (isDrowned(row, col ,x ,y , userGameBorad) == 1){
                r--;
                System.out.println("The computer's battleship has been drowned," +r+ " more battleships to go!");
            }
        }
        System.out.println("Your current game board:");
        printBoard(row,col,userGameBorad);
        return r;
    }

    public static void battleshipGame() {
        // TODO: Add your code here (and add more methods).
        int boardSize = [ , ]
        getBoardSize();
        char userGameBorad[][] =
        char userGuessingBorad[][] =
        char ComputerGameBorad[][] =
        char ComputerGuessingBorad[][] =
                initializeBoards();
        placeBattleships();

        int userBattelships = r;
        int computerBattelships = r;

        while (userBattelships > 0 && computerBattelships > 0) {
            computerBattelships -= userTurn(userGuessingBorad, ComputerGameBorad, r, row, col);
            userBattelships -= ComputerTurn(ComputerGuessingBorad, userGameBorad, r, row, col);
        }
        if (userBattelships)
            System.out.println("You lost ):\n");
        else
            System.out.println("You won the game!\n");
    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}



