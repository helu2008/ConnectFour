import java.util.*;

/** Hengwei Lu A99010013 helu@ucsd.edu cs8sbcq
  * Apr.27th
  * due date: Apr 28th*/

public class ConnectFourBoard
{
  /** A matrix of a board to play the game on 
    * */
  private char[][] board;
  /** The length of the rows 
    * */
  private int numRows;
  /** The length of the columns 
    * */
  private int numColumns;
  
  
  
  /** This is to initialize the board 
    * */
  public ConnectFourBoard( int rows, int columns )
  {
    numRows = rows;
    numColumns = columns;
    board = new char[numRows][numColumns];
  }
  
  
  /** This is to initialize the board with 6 rows and 7 columns 
    * */
  public ConnectFourBoard()
  {
    this(6,7);
  }
  
  
  /** Returns the string of the board
    * @return a string of the board with the checker
    * */
  public String toString()
  {
    String toReturn=new String();
    toReturn += "\n";
    for(int i=0;i<this.numRows;i++){
      for(int j=0;j<this.numColumns;j++){
        if(board[i][j]=='X'){
          toReturn +="|X";
        }else if(board[i][j]=='O'){
          toReturn +="|O";
        }else if(board[i][j]=='\u0000'){
          toReturn +="| ";
        }
      }
      toReturn +="|\n";
    }
    
    for(int rNum=0;rNum<this.numColumns;rNum++){
      toReturn += "--";
    }
    toReturn +="-\n";
    for(int rNum=0;rNum<this.numColumns;rNum++){
      toReturn += " " +rNum%10;
    }
    toReturn += "\n";
    
    return toReturn;
  }
  
  /** This is to add a move in the board
    * @param columns the column the checker will move into
    * @param checker the player who make the move
    * */
  public void addMove(int columns, char checker)
  {
    for(int i=this.numRows-1;i>=0;i--){
      if(board[i][columns]=='\u0000'){
        board[i][columns]=checker;
        break;
      }
    }
  }
  
  
  /** This is to make every block grid
    * */
  public void clear()
  {
    for(int i=0;i<numRows;i++){
      for(int j=0;j<numColumns;j++){
        board[i][j]='\u0000';
      }
    }
  }
  public char getContents(int row, int column)
  {
  return board[row][column];
  }
  
  /** Takes in a string of columns and places
    * alternating checkers in those columns,
    * starting with 'X'
    * 
    * For example, call b.setBoard("012345")
    * to see 'X's and 'O's alternate on the
    * bottom row, or b.setBoard("000000") to
    * see them alternate in the left column.
    * 
    * @param moveString A string of integers. 
    *    Note that this method will only play 
    *    in the first 10 columns of a board.
    */   
  public void setBoard( String moveString )
  {
    char nextCh = 'X';   // start by playing 'X'
    clear();
    for ( int i = 0; i < moveString.length(); i++ )
    {
      int col = Character.getNumericValue( moveString.charAt( i ) );
      if ( 0 <= col && col < numColumns )
        addMove(col, nextCh);
      if ( nextCh == 'X' )
        nextCh = 'O';
      else
        nextCh = 'X';
    }
  }
  
  
  /** Returns true or false that the move can be done
    * @param column the column the checker will move into
    * @return boolean that whether the move can be done
    * */
  public boolean allowsMove( int column )
  {
    boolean boo=false;
    if(column>=numColumns||column<0){
      boo=false;
    }else{
      for(int i=0;i<this.numRows;i++){
        if(board[i][column]=='\u0000'){
          boo=true;
        }
      }
    }
    return boo;
  }
  
  /** Returns true or false that the board is full
    * @return boolean that whether the board is full
    * */
  public boolean isFull()
  {
    boolean boo = true;
    for(int i=0;i<numRows;i++){
      for(int j=0;j<numColumns-1;j++){
        if(board[i][j]=='\u0000'){
          boo=boo&&false;
        }
      }
    }
    return boo;
  }
  
  
  /** Returns true or false that the player win
    * @param checker the player
    * @return boolean that whether player win
    * */
  public boolean winsFor( char checker )
  {
    boolean boo=false;
    for(int i=0;i<numRows;i++){
      for(int j=0;j<numColumns-3;j++){
        if(board[i][j]==checker&&
           board[i][j+1]==checker&&
           board[i][j+2]==checker&&
           board[i][j+3]==checker){
          boo= true;
        }
      }
    }
    for(int i=0;i<numRows-3;i++){
      for(int j=0;j<numColumns;j++){
        if(board[i][j]==checker&&
           board[i+1][j]==checker&&
           board[i+2][j]==checker&&
           board[i+3][j]==checker){
          boo= true;
        }
      }
    }
    for(int i=0;i<numRows-3;i++){
      for(int j=0;j<numColumns-3;j++){
        if(board[i][j]==checker&&
           board[i+1][j+1]==checker&&
           board[i+2][j+2]==checker&&
           board[i+3][j+3]==checker){
          boo= true;
        }
      }
    }
    for(int i=3;i<numRows;i++){
      for(int j=0;j<numColumns-3;j++){
        if(board[i][j]==checker&&
           board[i-1][j+1]==checker&&
           board[i-2][j+2]==checker&&
           board[i-3][j+3]==checker){
          boo= true;
        }
      }
    }
    return boo;
  }
  
  /** This is to play the game
    * */
  public void hostGame()
  {
    this.clear();
    System.out.println("Welcome to Connect Four!");
    System.out.println(this);
    Scanner input = new Scanner(System.in);
    while(!this.winsFor('X')&&!this.winsFor('O')){
      int checkerX=input.nextInt();
      while(!this.allowsMove(checkerX)){
        System.out.println("Invalid Column!");
        checkerX=input.nextInt();
      }
      System.out.println("X's choice: "+ checkerX);
      this.addMove(checkerX,'X');
      System.out.println(this);
      
      if(this.winsFor('X')){
        System.out.println("X wins!");
        break;
      }
      if(this.isFull()){
        System.out.println("game over");
        break;
      }
      
      int checkerO=input.nextInt();
      while(!this.allowsMove(checkerO)){
        System.out.println("Invalid Column!");
        checkerO=input.nextInt();
      }
      
      System.out.println("O's choice: "+ checkerO);
      this.addMove(checkerO,'O');
      System.out.println(this);
      if(this.winsFor('O')){
        System.out.println("O wins!");
        break;
      }
      if(this.isFull()){
        System.out.println("game over");
        break;
      }
    }
    
  }
  
  /** main mehtod
    * */
  public static void main( String[] args ) 
  {
    ConnectFourBoard b=new ConnectFourBoard();
    b.hostGame();
  }
  
  
  
}