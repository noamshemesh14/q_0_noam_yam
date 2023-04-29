import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;
    static final char EMPTY = '-';
    static final int HORIZONTAL = 0;
    static final int VERTICAL = 1;

    public static int[] getBoardSize() {
        System.out.println("Enter the board size");
        String input = scanner.next();
        String[] tempBoardSize = input.split("X");
        int[] boardSize = new int[2];
        for (int i =0 ; i<2 ; i++) {
            boardSize[i] = Integer.parseInt(tempBoardSize[i]);
        }
        return boardSize;
    }

    public static char[][] createBoard(int rows, int columns) {
        char board[][] = new char[rows][columns];
        for ( int i=0 ; i<rows ; i++) {
            for (int j=0 ; j<columns ; j++ ) {
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    public static int[][] getBattleships() {
        String input = scanner.nextLine();
        String tempBattleships[] = input.split(" ");
        int battleships[][] = new int[tempBattleships.length][2]; // leagal?? its an attribute
        for (int i=0 ; i < tempBattleships.length ; i++) {
            String temp[] = (tempBattleships[i]).split("X");
            battleships[i][0] = Integer.parseInt(temp[0]);
            battleships[i][1] = Integer.parseInt(temp[1]);
        }
        return battleships;
    }

    public static int[] positionByUser(int size) {
        System.out.println("Enter location and orientation for battleship of size" + size);
        String input = scanner.nextLine();
        String[] positionAsStr = input.split(",");
        int[] position = new int[3];
        for (int i=0 ; i<3 ; i++) {
            position[i] = Integer.parseInt(positionAsStr[i]);
        }
        return position;
    }

    public static int[] positionByComputer(int rows , int columns) {
        int position[] = new int[3];
        position[0] = rnd.nextInt(rows-1);
        position[1] = rnd.nextInt(columns-1);
        position[2] = rnd.nextInt(2);
        return position;
    }

    public static boolean isLegalOrientation(int orientation) {
        if (orientation == HORIZONTAL || orientation == VERTICAL)
            return true;
        else return false;
    }

    public static boolean isLegalTile(int row, int column, int rowsNum, int columnsNum) {
        if (row < 0 || row >= rowsNum)
            return false;
        if (column < 0 || column >= columnsNum )
            return false;
        else return true;
    }

    public static boolean isInBoard(int[] position,int size, int rowsNum, int columnsNum) {
        if (position[2] == HORIZONTAL) { // position[2]: orientation
            if (position[1]+size-1 >= columnsNum) // position[1]: column
                return false;
        }
        else if (position[2] == VERTICAL) {
            if (position[0]+size-1 >= rowsNum) // position[0]: row
                return false;
        }
        return true;
    }

    public static boolean isOverlapping(int[] position,int size, char[][] board) {
        if (position[2] == HORIZONTAL) { // position[2]: orientation
            for (int i=0; i<size; i++) {
                if (board[position[0]][position[1] + i] != EMPTY)
                    return true;
            }
        }
        else if (position[2] == VERTICAL) {
            for (int i=0; i<size; i++) {
                if (board[position[0]+i][position[1]] != EMPTY)
                    return true;
            }
        }
        return false;
    }

    public static boolean isAdjacent(int[] position,int size, char[][] board) {
        int rows = board.length;
        int columns = (board[0]).length;

        if (position[2] == HORIZONTAL) {
            for (int i=-1 ; i<2 ; i++) {
                for (int j=-1 ; j<=size ; j++) {
                    if (isLegalTile(position[0]+i, position[1]+j, rows, columns) &&
                            (board[position[0]+i][position[1]+j] != EMPTY))
                        return true;
                }
            }
        }
        else if (position[2] == VERTICAL) {
            for (int i=-1 ; i<=size ; i++) {
                for (int j=-1 ; j<2 ; j++) {
                    if (isLegalTile(position[0]+i, position[1]+j, rows, columns) &&
                            (board[position[0]+i][position[1]+j] != EMPTY))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidPosition(int[] position, int size, char[][] board, String player ) {
        int rows = board.length;
        int columns = (board[0]).length; // i can send rows & cols as vars to the func

        if (player == "user") {
            if(!isLegalOrientation(position[2])) {
                System.out.println("Illegal orientation, try again!");
                return false;
            }
            if (!isLegalTile(position[0], position[1], rows, columns) {
                System.out.println("Illegal tile, try again!");
                return false;
            }
        }
        if (!isInBoard(position, size, rows, columns)) {
            if (player == "user")
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
            return false;
        }
        if (isOverlapping(position, size, board)) {
            if (player == "user")
                System.out.println("Battleship overlaps another battleship, try again");
            return false;
        }
        if (isAdjacent(position, size, board)) {
            if (player == "user")
                System.out.println("Adjacent battleship detected, try again");
            return false;
        }
        else return true;
    }

    public static void placeBattleships(
            int[][] battleships,
            char[][] board,
            int rows, int columns,
            String player) {

        for (int[] type : battleships) {
            for (int i =0 ; i < type[0] ; i++) {
                int position[];
                if (player == "user") {
                    System.out.println("Your current game board:");
                    printBoard(board);
                }
                do {
                    if (player == "user") {
                        position = positionByUser(type[1]);
                    }
                    else if (player == "computer") {
                        position = positionByComputer(rows, columns);
                    }
                } while (!isValidPosition(position, type[1], board, rows, columns, player));
                addBattleship(board, position);
            }
        }
    }


    userTurn() {
        printBoard()
        while (){
            getPoint()
            if (!inBoard()) continue
            if (!newPoint()) continue
        }
        if (!ishit()) {
            updateBoards()
            print
        }
        else {
            updateBoards()
            isdrowned()

        }

    }


    public static void battleshipGame() {

        int boardSize[] = getBoardSize();
        int rows = boardSize[0];
        int columns = boardSize[1];

        char userGameBorad[][] = createBoard(rows, columns);
        char userGuessingBorad[][] = createBoard(rows, columns);
        char computerGameBorad[][] = createBoard(rows, columns);
        char computerGuessingBorad[][] = createBoard(rows, columns);

        int battleships[][] = getBattleships();
        placeBattleships(battleships, userGameBorad, "user");
        placeBattleships(battleships, userGameBorad, "computer");

        userBattelships = countBatlleshpis()
        computerBattelships = countBatlleshpis()

        while (userBattelships > 0 && computerBattelships > 0) {
            computerBattelships -= userTurn()
            if (computerBattelships <=0) break;
            userBattelships -= ComputerTurn()
        }

        winnerEnnouncement()
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



