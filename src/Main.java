import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Scanner scanner;
    public static Random rnd;

    static final char EMPTY = '-';
    static final char IS_BATTLESHIP = '#';
    static final int HORIZONTAL = 0;
    static final int VERTICAL = 1;
    static final char GOOD_GUESS = 'V';
    static final char BAD_GUESS = 'X';
    static final char HIT_BATTLESHIP = 'X';


    /**
     * Gets board size from the user. Get rows (r) and columns (c) numbers,
     * in the following format as a string: "rXc".
     * 
     * @return an array of 2 integers in the following order: {rows, columns}.
     */
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

    /**
     * Creates a board, 2D array, of chars '-'.
     *
     * @param rows the number of rows in the board.
     * @param columns the number of columns in the board.
     * @return an empty game or guessing board filled with '-' char.
     */
    public static char[][] createBoard(int rows, int columns) {
        char[][] board = new char[rows][columns];
        for ( int i=0 ; i<rows ; i++) {
            for (int j=0 ; j<columns ; j++ ) {
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    /**
     * Gets the description of battleships amounts and sizes from the user.
     * Gets input in the following format as a string "n1Xs1 n2Xs2 .. nkXsk",
     * representing nk battleships of size sk.
     *
     * @return an array of 2 sized int arrays. each one represents {amount of battleships, size}.
     */
    public static int[][] getBattleships() {
        String input = scanner.nextLine();
        String[] tempBattleships = input.split(" ");
        int[][] battleships = new int[tempBattleships.length][2]; // leagal?? its an attribute
        for (int i=0 ; i < tempBattleships.length ; i++) {
            String[] temp = (tempBattleships[i]).split("X");
            battleships[i][0] = Integer.parseInt(temp[0]);
            battleships[i][1] = Integer.parseInt(temp[1]);
        }
        return battleships;
    }

    /**
     * Counts the amount of battleships each player has at the beginning of the game
     * as determined by the user.
     * Sums the amount of battleships from each size (referred as types).
     *
     * @param battleships an array of 2 sized int arrays. each one represents {amount of battleships, size}.
     * @return the amount of battleships each player has at the beginning of the game.
     */
    public static int countBattleships(int[][] battleships) {
        int amount = 0;
        for (int[] type : battleships) {
            amount += type[0];
        }
        return amount;
    }

    /**
     * Gets the position of each battleship, tile and orientation, from the user.
     * Gets position as a string "row,column,orientation".
     *
     * @param size the size of the battleship which the user needs to place.
     * @return the position of the battleships as a 3 sized int array as following: {row, column, orientation}.
     */
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

    /**
     * Gets the position of each battleship, tile and orientation, from the computer.
     * Generates 3 random integers for row, column and orientation.
     * Row and column are in board range, orientation can be 0/1.
     *
     * @param rows the number of rows in the game board.
     * @param columns the number of columns in the game board.
     * @return the position of the battleships as a 3 sized int array as following: {row, column, orientation}.
     */
    public static int[] positionByComputer(int rows , int columns) {
        int[] position = new int[3];
        position[0] = rnd.nextInt(rows-1);
        position[1] = rnd.nextInt(columns-1);
        position[2] = rnd.nextInt(2);
        return position;
    }

    /**
     * Checks if the given battleship orientation is valid (0 or 1).
     *
     * @param orientation the orientation of the battleship.
     * @return true if the orientation is 0 (horizontal) or 1 (vertical).
     */
    public static boolean isLegalOrientation(int orientation) {
        return orientation == HORIZONTAL || orientation == VERTICAL;
    }

    /**
     * Checks if the tile given by the player, is in the game board.
     * The tile only represents the place where the battleship starts.
     *
     * @param row the number of the tile row.
     * @param column the number of the tile column.
     * @param rowsNum the number of rows in the game board.
     * @param columnsNum the number of columns in the game board.
     * @return true if the tile is in the boards borders.
     */
    public static boolean isLegalTile(int row, int column, int rowsNum, int columnsNum) {
        if (row < 0 || row >= rowsNum)
            return false;
        if (column < 0 || column >= columnsNum )
            return false;
        return true;
    }

    /**
     * Checks if the whole battleships is in the board borders.
     *
     * @param position the position of the battleship as a 3 sized int array as following:
     *                 {row, column, orientation}.
     * @param size the size of the battleship.
     * @param rowsNum the number of rows in the game board.
     * @param columnsNum the number of rows in the game board.
     * @return true if the whole battleships is in the board borders.
     */
    public static boolean isInBoard(int[] position,int size, int rowsNum, int columnsNum) {
        if (position[2] == HORIZONTAL) {
            if (position[1]+size-1 >= columnsNum)
                return false;
        }
        else if (position[2] == VERTICAL) {
            if (position[0]+size-1 >= rowsNum)
                return false;
        }
        return true;
    }

    /** Checks if the battleships is placed over another battleship.
     *
     * @param position the position of the battleship as a 3 sized int array as following:
     *                 {row, column, orientation}.
     * @param size the size of the battleship.
     * @param board the 2D array of chars representing the current battleships placing.
     * @return true if the battleships is placed over another battleship.
     */
    public static boolean isOverlapping(int[] position,int size, char[][] board) {
        if (position[2] == HORIZONTAL) {
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

    /**
     * Checks if the battleships is placed near another battleship (adjoining).
     * It is assumed that the battleships place on board has been checked and is empty.
     *
     * @param position the position of the battleship as a 3 sized int array as following:
     *                 {row, column, orientation}.
     * @param size the size of the battleship.
     * @param board the 2D array of chars representing the current battleships placing.
     * @return true if the battleships is placed nearby another battleship.
     */
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

    /**
     * Checks if the position given by the player is valid by the game rules.
     * If the player is the user and not the computer there are additional print commands.
     *
     * @param position the position of the battleship as a 3 sized int array as following:
     *                 {row, column, orientation}.
     * @param size the size of the battleship.
     * @param board the 2D array of chars representing the current battleships placing.
     * @param player the name of the player as a string, "user" or "computer".
     * @return true if the position given by the player is valid.
     */
    public static boolean isValidPosition(int[] position, int size, char[][] board, String player ) {
        int rows = board.length;
        int columns = (board[0]).length;

        if (player.equals("user")) {
            if(!isLegalOrientation(position[2])) {
                System.out.println("Illegal orientation, try again!");
                return false;
            }
            if (!isLegalTile(position[0], position[1], rows, columns)) {
                System.out.println("Illegal tile, try again!");
                return false;
            }
        }
        if (!isInBoard(position, size, rows, columns)) {
            if (player.equals("user"))
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
            return false;
        }
        if (isOverlapping(position, size, board)) {
            if (player.equals("user"))
                System.out.println("Battleship overlaps another battleship, try again");
            return false;
        }
        if (isAdjacent(position, size, board)) {
            if (player.equals("user"))
                System.out.println("Adjacent battleship detected, try again");
            return false;
        }
        else return true;
    }

    /**
     * Adds a battleships to the game board by its given position and size.
     *
     * @param position the position of the battleship as a 3 sized int array as following:
     *                 {row, column, orientation}.
     * @param size size the size of the battleship.
     * @param board the 2D array of chars representing the current battleships placing.
     */
    public static void addBattleship(int[] position, int size, char[][] board) {
        for (int i=0 ; i<size ; i++) {
            if (position[2] == HORIZONTAL)
                board[position[0]][position[1]+i] = IS_BATTLESHIP;
            else if (position[2] == VERTICAL)
                board[position[0]+i][position[1]] = IS_BATTLESHIP;
        }
    }

    /**
     * Places all battleships by a player at the beginning of the game in a valid way.
     * Takes battleships position by the player until a valid position is given.
     * If the player is the user and not the computer there are additional print commands.
     *
     * @param battleships an array of 2 sized int arrays. each one represents {amount of battleships, size}.
     * @param board the 2D array of chars representing the current battleships placing.
     * @param player the name of the player as a string, "user" or "computer".
     */
    public static void placeBattleships(
            int[][] battleships,
            char[][] board,
            String player) {

        int rows = board.length;
        int columns = (board[0]).length;

        for (int[] type : battleships) {
            for (int i =0 ; i < type[0] ; i++) {
                int[] position = new int[0];
                if (player.equals("user")) {
                    System.out.println("Your current game board:");
                    printBoard(rows, columns, board);
                }
                do {
                    if (player.equals("user")) {
                        position = positionByUser(type[1]);
                    }
                    else if (player.equals("computer")) {
                        position = positionByComputer(rows, columns);
                    }
                } while (!isValidPosition(position, type[1], board, player));
                addBattleship(position, type[1], board);
            }
        }
    }

    public static void printBoard(int row, int col, char[][] borad){
        int temp_row = (row-1)*10;
        int maxDigit = 0;
        while (temp_row / 10 != 0) {
            System.out.print(' ');
            temp_row /= 10;
            maxDigit++;
        }
        temp_row = row-1;
        for (int i = 0 ; i < col; i++) {
            System.out.print(" " + i);
        }
        System.out.println(" ");

        for (int i = 0; i < row ; i++){
            int digit = 1, tempI = i;
            while (tempI / 10 != 0) {
                tempI /= 10;
                digit++;
            }
            while (maxDigit > digit) {
                digit++;
                System.out.print(' ');
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
            int new_y = y;
            while (new_y > 0 && borad[x][new_y] != EMPTY)  {
                if (borad[x][new_y-1] == IS_BATTLESHIP) {
                    return 0;
                }
                new_y--;
            }
            new_y = y;
            while (new_y < col-1 && borad[x][new_y] != EMPTY) {
                if (borad[x][new_y-1+1] == IS_BATTLESHIP) {
                    return 0;
                }
                new_y++;
            }
        }
        if ((y > 0 && borad[x][y-1] == EMPTY) && (y < col-1 && borad[x][y+1] == EMPTY)){
            System.out.println("if 2");
            int new_x = x;
            while (new_x > 0 && borad[new_x][y] != EMPTY) {
                if (borad[new_x-1][y] == IS_BATTLESHIP) {
                    return 0;
                }
                new_x--;
            }
            new_x = x;
            while (new_x < row-1 && borad[new_x][y] != EMPTY) {
                if (borad[new_x+1][y] == IS_BATTLESHIP) {
                    return 0;
                }
                new_x++;
            }
        }
        return 1;
    }
    public static int userTurn(char[][] userGuessingBorad, char[][] ComputerGameBorad, int computerBattelships,
                               int row , int col) {
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
                computerBattelships--;
                System.out.println("The computer's battleship has been drowned,"+computerBattelships
                        +" more battleships to go!");
            }
        }
        return computerBattelships;
    }
    public static int ComputerTurn(char[][] ComputerGuessingBorad, char[][] userGameBorad, int userBattelships,
                                   int row , int col) {
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
                userBattelships--;
                System.out.println("The computer's battleship has been drowned," +userBattelships
                        + " more battleships to go!");
            }
        }
        System.out.println("Your current game board:");
        printBoard(row,col,userGameBorad);
        return userBattelships;
    }


    /**
     * A single round of battleship game of the user against the computer.
     */
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

        int userBattelships = countBattleships(battleships);
        int computerBattelships = countBattleships(battleships);


        while (userBattelships > 0 && computerBattelships > 0) {
            computerBattelships -= userTurn(userGuessingBorad, computerGameBorad,computerBattelships,rows,columns);
            if (computerBattelships <= 0) break;
            userBattelships -= ComputerTurn(computerGuessingBorad, userGameBorad , userBattelships,rows,columns);
        }
        if (userBattelships == 0)
            System.out.println("You lost ):");
        else
            System.out.println("You won the game!");
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



