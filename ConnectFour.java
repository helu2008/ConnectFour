/**
 * Hengwei Lu cs8sbcq helu@ucsd.edu A99010013
 * May 12th
 * due date: May 14th
 * 
 * 
 * You should answer these questions BEFORE you start coding:
 * 
 *   1. To what type of object will you add an instance of a PlayListener object (as a MouseListener)?
 * actionlistener
 *   2. If your board has row r and column c , how many BoardCell objects do you need to create? 
 * rc
 *      To which component will you add these BoardCell objects?
 * Jpanal
 *   3. Why is the JLabel status an instance variable (as opposed to just a local variable 
 *      in the constructor)?
 * in all classes
 *   4. Where is the information about what contents are stored in each cell located?  
 * boardcell class
 *      What method must the BoardCell call in its paintComponent method to determine what 
 *      color to paint the "checker"?
 * 
 *   5. Which method will determine when the game is over (by calling methods on the 
 *      ConnectFourBoard object theBoard)?  Which method will detect illegal moves 
 *      (again by calling methods on the ConnectFourBoard object theBoard)?
 * winsFor/allowsMove
 *   6. Will you need to create a separate listener to handle clicks on the New Game button, 
 *      or will you use another instance of the PlayListener class?
 * another instance of the playlistener class
 *   7. How do you run the game?
 * click the new game button
 *
 **/


// import all the necessary built-in java stuff
import javax.swing.*;  // For swing classes (the "J" classes)
import java.awt.*;     // For awt classes (e.g., Dimension)
import java.awt.event.*; // For events (which you will implement)
import java.util.*;

/**
 * A class that implements a graphical version of connect 4.  Don't forget to 
 * complete your header comment with your name and the date and the PSA.
 */
public class ConnectFour extends JFrame {
  
  /** The underlying board that will hold the state of the game */
  private ConnectFourBoard theBoard;
  
  /** Whose turn it is.  We use 'X' and 'O', but we will translate 'X's and 
    * 'O's into colors to display them (I use blue and red in my example,
    * but you can use any two colors you like).  */
  private char turn;
  /** The status message at the top of the window */
  private JLabel status;
  public int[] columnHistory;
  public int count = 0;
  /** Create a new Connect 4 game that is 7x6.  */
  public ConnectFour()
  {
    this( 6, 7 );
    columnHistory= new int[42];
  }
  
  /** Create a new ConnectFour game with the specified row and column */
  public ConnectFour( int row, int column )
  {
    // X starts
    this.turn = 'X';
    
    // Make a new underlying board.
    this.theBoard = new ConnectFourBoard(row, column);
    columnHistory= new int[row*column];
    // The reset button.  It doesn't do anything YET.
    // You'll need to define and add the appropriate listener to it.
    JButton jbtReset = new JButton( "New Game" );
    jbtReset.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        theBoard.clear();
        turn = 'X';
        status.setText( "Welcome to Connect 4!" );
        count = 0;
        repaint();
      }
    }                        
    );
    //The retract button
    JButton jbtRetract = new JButton("Retract a Move");
    
    jbtRetract.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        int[] a = Arrays.copyOfRange(columnHistory,0,Math.max(count-1,0));
        String b = "";
        for(int i=0;i<a.length;i++)
        {
          b+=Integer.toString(a[i]-1);
        }
        theBoard.setBoard(b);
        if(count>0){
          count-=1;}
        if(count%2 ==0)
        {turn = 'X';
          status.setText("RED's turn" );}
        else
        {turn = 'O';
          status.setText("BLUE's turn" );}
        
        repaint();
      }
    }                        
    );
    // This message will always display the current status
    // of the game (e.g., whose turn it is, whether the game is 
    // over, who won, etc).  Feel free to change the initial message.
    this.status = new JLabel( "Welcome to Connect 4!" );
    
    // This is the panel that will hold the BoardCells. 
    // You will need to populate it.  I suggest writing a helper
    // method to create the BoardCell objects and add them to
    // the JPanel.  You will want to use a GridLayout on the displayBoard
    // to lay out the BoardCell objects.
    JPanel displayBoard = new JPanel();
    displayBoard.setLayout(new GridLayout(row,column,3,3));
    for(int i=1;i<=row;i++){
      for(int j=1;j<=column;j++){
        displayBoard.add(new BoardCell(i,j));
      }
    }
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(1,2,100,100));
    p.add(jbtReset);
    p.add(jbtRetract);
    
    // Use a BorderLayout to lay out the game board
    setLayout( new BorderLayout() );
    add( status, BorderLayout.NORTH );
    add( displayBoard, BorderLayout.CENTER );
    add( p, BorderLayout.SOUTH );
    // Size and show the board
    pack();
    setVisible( true );
    
  }
  
  
  // This is the method that is called when a BoardCell is clicked on.
  // Refer to the PSA instructions for more details.
  private void makeMove( int col )
  {
    if(!(theBoard.isFull()||(theBoard.winsFor('X')||theBoard.winsFor('O')))){
      if(theBoard.allowsMove(col-1)){
        theBoard.addMove(col-1,turn);
        columnHistory[count] = col;
        count+=1;
        if(this.turn=='X'&&!theBoard.winsFor('X')){
          this.turn='O';
          status.setText( "BLUE's turn" );
        }else if (theBoard.winsFor('X')){
          status.setText( "RED wins" );
        }else if(this.turn=='O'&&!theBoard.winsFor('O')){
          this.turn='X';
          status.setText( "RED's turn" );
        }else {status.setText( "BLUE wins" );}
      }}
    else if(theBoard.winsFor('X')){
      status.setText( "RED wins" );}
    
    else if(theBoard.winsFor('O')){
      status.setText( "BLUE wins" );
      
    }
    else{status.setText("Draw");}
    repaint();
    
  }
  
  
  /** An inner class that represents one graphical cell in the connect 4 board.
    * Each cell keeps track of what row and column it is in.
    * These are the objects that will listen for mouse clicks.
    * Because they are an inner class, they have access to all of
    * the methods in the ConnectFour outer class.  
    * 
    * Notice that a BoardCell object IS A JPanel, so it can be added directly
    * to a ConnectFour object (which IS A JFrame).  You can also add listeners
    * to JPanels (and BoardCells, since they are JPanels), which will be useful
    * to detect where the user wants to play.  */
  class BoardCell extends JPanel
  {
    /** The row in which this BoardCell appears in the board. *
      * */
    private int row;
    
    /** The column in which this BoardCell appears in the board. 
      * */
    private int column;
    
    /** Create a new BoardCell object at row r and column c. 
      * The constructor is the right place to add the PlayListener too. */
    BoardCell( int r, int c )
    {
      // You will implement this method.
      this.row=r;
      this.column=c;
      this.addMouseListener(new PlayListener());
    }
    
    /** Return the preferred size for this BoardCell */
    public Dimension getPreferredSize()
    {
      // Just a suggestion. Feel free to change it if you want.  
      return new Dimension( 50, 50 );
    }
    
    // Paint the BoardCell.  Note that this method should paint empty cells 
    // in one color, cells filled with and 'X' with another color, and cells 
    // filled with an 'O' in a third color.  
    //
    // IMPORTANT: You will need to refer to the 
    // theBoard object in the ConnectFour class to get the contents of the 
    // underlying cell that this visual cell represents.  If this does not make
    // sense to you, seek help now.
    //
    // My suggestion here is to paint the whole background of the cell with a 
    // solid rectangle, and then paint a circle (oval) on top to represent the space
    // or the checker.  You'll want to make your circle slightly smaller than
    // your rectangle.  Of course, feel free to get creative, add shadow, etc.
    protected void paintComponent( Graphics g )
    { 
      super.paintComponent( g );
      // Your code will go here.
      for(int i=0;i<row;i++){
        for(int j=0;j<column;j++){
          int width = this.getWidth();
          int height = this.getHeight();
          g.setColor(new Color(225,225,225));
          g.fillRect(0,0,width,height);
          if(theBoard.getContents(i,j)==' '){
            g.setColor(new Color(225,225,225));
            g.fillOval(0,0,width,height);
          }else if(theBoard.getContents(i,j)=='X'){
            g.setColor(new Color(225,0,0));
            g.fillOval(0,0,width,height);
          }else if(theBoard.getContents(i,j)=='O'){
            g.setColor(new Color(0,0,225));
            g.fillOval(0,0,width,height);
          }
        }
      }
    }
    
    /** This is the listener that will handle clicks on the board cell.
      * You will not need to change this code at all, but you should understand 
      * what is going on.
      * */
    class PlayListener implements MouseListener
    {
      /** Inform the ConnectFour object that the corresponding column has been
        * clicked */
      public void mouseClicked( MouseEvent e ) 
      {
        // We just need to tell the board to play a checker in the appropriate
        // column.  column refers to the instance variable in the BoardCell
        // object.  This method calls the makeMove method in the ConnectFour class. 
        makeMove( column );
        //System.out.println(row +" "+ column);
      }
      
      public void mousePressed( MouseEvent e ) {}
      public void mouseReleased( MouseEvent e ) {}
      public void mouseEntered( MouseEvent e ) {}
      public void mouseExited( MouseEvent e ) {}
    }
  }
  
}
