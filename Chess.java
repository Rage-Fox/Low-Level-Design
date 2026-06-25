import java.util.Scanner;

enum Color {
  WHITE,
  BLACK,
}

class Square {

  private Piece piece;
  private Color color;

  public Square(Color color) {
    this.color = color;
  }

  public Piece getPiece() {
    return piece;
  }

  public Color getColor() {
    return this.color;
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
  }
}

abstract class Piece {

  protected Color color;

  public Piece(Color color) {
    this.color = color;
  }

  protected Color getColor() {
    return this.color;
  }

  public static boolean isWithinGrid(int endRow, int endCol) {
    if (endRow > 7 || endRow < 0 || endCol > 7 || endCol < 0) return false;
    return true;
  }

  public abstract boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  );

  protected abstract String getSymbol();
}

class Pawn extends Piece {

  public Pawn(Color color) {
    super(color);
  }

  public boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  ) {
    if (!isWithinGrid(endRow, endCol)) return false;

    int rowMovement = endRow - startRow;
    int colMovement = endCol - startCol;

    int direction = (this.getColor() == Color.WHITE) ? -1 : 1;

    if (colMovement != 0) return false;

    if (rowMovement == direction && board[endRow][endCol].getPiece() == null) {
      return true;
    }

    if (
      isFirstMove(startRow) &&
      rowMovement == (2 * direction) &&
      board[startRow + direction][startCol].getPiece() == null &&
      board[endRow][endCol].getPiece() == null
    ) {
      return true;
    }

    return false;
  }

  @Override
  public String getSymbol() {
    return (this.getColor() == Color.WHITE) ? "P" : "p";
  }

  private boolean isFirstMove(int startRow) {
    if (this.getColor() == Color.WHITE) {
      return startRow == 6;
    } else {
      return startRow == 1;
    }
  }
}

class Knight extends Piece {

  public Knight(Color color) {
    super(color);
  }

  @Override
  public boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  ) {
    if (!isWithinGrid(endRow, endCol)) return false;

    int rowMovement = Math.abs(endRow - startRow);
    int colMovement = Math.abs(endCol - startCol);

    // the only valid move for knight is to move in an L shape, 1 block along the x axis and 2 blocks along y, or vice versa. the knight can jump over pieces

    if (
      rowMovement == 2 &&
      colMovement == 1 ||
      rowMovement == 1 &&
      colMovement == 2
    ) {
      // there is a friendly piece
      if (
        board[endRow][endCol].getPiece() != null &&
        board[endRow][endCol].getPiece().getColor() == this.getColor()
      ) {
        return false;
      }

      // there is an enemy piece or the square is empty at the destination
      return true;
    }

    return false;
  }

  @Override
  public String getSymbol() {
    return (this.getColor() == Color.WHITE) ? "N" : "n";
  }
}

class MovementUtil {

  public static boolean isValidStraightMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Color color,
    Square[][] board
  ) {
    if (!Piece.isWithinGrid(endRow, endCol)) return false;

    int rowMovement = Math.abs(endRow - startRow);
    int colMovement = Math.abs(endCol - startCol);

    if (
      (rowMovement != 0 && colMovement != 0) ||
      (rowMovement == 0 && colMovement == 0)
    ) {
      return false;
    } else {
      // Determine the direction of movement
      int rowIncrement = (endRow > startRow) ? 1 : -1;
      int colIncrement = (endCol > startCol) ? 1 : -1;

      if (rowMovement == 0) { // Moving horizontally
        int y = startCol + colIncrement;
        while (y != endCol) {
          if (board[startRow][y].getPiece() != null) {
            return false; // There's a piece blocking the path
          }
          y += colIncrement;
        }
      } else { // Moving vertically
        int x = startRow + rowIncrement;
        while (x != endRow) {
          if (board[x][startCol].getPiece() != null) {
            return false; // There's a piece blocking the path
          }
          x += rowIncrement;
        }
      }

      // Check the destination square
      if (
        board[endRow][endCol].getPiece() != null &&
        board[endRow][endCol].getPiece().getColor() == color
      ) {
        return false;
      }

      return true;
    }
  }

  public static boolean isValidDiagonalMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Color color,
    Square[][] board
  ) {
    if (!Piece.isWithinGrid(endRow, endCol)) return false;

    int rowMovement = Math.abs(endRow - startRow);
    int colMovement = Math.abs(endCol - startCol);

    if (rowMovement == 0 || colMovement == 0) return false;

    if (rowMovement == colMovement) {
      int rowIncrement = (endRow > startRow) ? 1 : -1;
      int colIncrement = (endCol > startCol) ? 1 : -1;

      // our initial movement
      int x = startRow + rowIncrement;
      int y = startCol + colIncrement;

      // while we haven't reached our destination
      while (x != endRow && y != endCol) {
        // friendly piece is blocking the path.
        if (board[x][y].getPiece() != null) {
          if (board[x][y].getPiece().getColor() == color) {
            return false; // A friendly piece is blocking the path
          }
          return false; // An enemy piece blocks the path
        }

        x += rowIncrement;
        y += colIncrement;
      }

      if (
        board[endRow][endCol].getPiece() != null &&
        board[endRow][endCol].getPiece().getColor() == color
      ) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }
}

class Rook extends Piece {

  public Rook(Color color) {
    super(color);
  }

  @Override
  public boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  ) {
    return MovementUtil.isValidStraightMove(
      startRow,
      startCol,
      endRow,
      endCol,
      this.getColor(),
      board
    );
  }

  @Override
  public String getSymbol() {
    return (this.getColor() == Color.WHITE) ? "R" : "r";
  }
}

class Bishop extends Piece {

  public Bishop(Color color) {
    super(color);
  }

  @Override
  public boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  ) {
    return MovementUtil.isValidDiagonalMove(
      startRow,
      startCol,
      endRow,
      endCol,
      this.getColor(),
      board
    );
  }

  @Override
  public String getSymbol() {
    return (this.getColor() == Color.WHITE) ? "B" : "b";
  }
}

class King extends Piece {

  public King(Color color) {
    super(color);
  }

  @Override
  public boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  ) {
    if (!isWithinGrid(endRow, endCol)) return false;

    int rowMovement = Math.abs(endRow - startRow);
    int colMovement = Math.abs(endCol - startCol);

    if (rowMovement > 1 || colMovement > 1) {
      return false;
    }

    if (
      board[endRow][endCol].getPiece() != null &&
      board[endRow][endCol].getPiece().getColor() == this.getColor()
    ) {
      return false;
    }

    return true;
  }

  @Override
  public String getSymbol() {
    return (this.getColor() == Color.WHITE) ? "K" : "k";
  }
}

class Queen extends Piece {

  public Queen(Color color) {
    super(color);
  }

  @Override
  public boolean isValidMove(
    int startRow,
    int startCol,
    int endRow,
    int endCol,
    Square[][] board
  ) {
    return (
      MovementUtil.isValidStraightMove(
        startRow,
        startCol,
        endRow,
        endCol,
        this.getColor(),
        board
      ) ||
      MovementUtil.isValidDiagonalMove(
        startRow,
        startCol,
        endRow,
        endCol,
        this.color,
        board
      )
    );
  }

  @Override
  public String getSymbol() {
    return (this.getColor() == Color.WHITE) ? "Q" : "q";
  }
}

class ChessBoard {

  private final Square[][] board = new Square[8][8];

  public ChessBoard() {
    initializeBoardAndPieces();
  }

  private void initializeBoardAndPieces() {
    // Initialize all squares
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        // alternating squares
        Color squareColor = (i + j) % 2 == 0 ? Color.BLACK : Color.WHITE;
        board[i][j] = new Square(squareColor);
      }
    }
    initializeBlackPieces();
    initializeWhitePieces();
  }

  private void initializeBlackPieces() {
    for (int i = 0; i < 8; i++) {
      board[1][i].setPiece(new Pawn(Color.BLACK));
    }
    board[0][0].setPiece(new Rook(Color.BLACK));
    board[0][7].setPiece(new Rook(Color.BLACK));
    board[0][1].setPiece(new Knight(Color.BLACK));
    board[0][6].setPiece(new Knight(Color.BLACK));
    board[0][2].setPiece(new Bishop(Color.BLACK));
    board[0][5].setPiece(new Bishop(Color.BLACK));
    board[0][3].setPiece(new Queen(Color.BLACK));
    board[0][4].setPiece(new King(Color.BLACK));
  }

  private void initializeWhitePieces() {
    for (int i = 0; i < 8; i++) {
      board[6][i].setPiece(new Pawn(Color.WHITE));
    }
    board[7][0].setPiece(new Rook(Color.WHITE));
    board[7][7].setPiece(new Rook(Color.WHITE));
    board[7][1].setPiece(new Knight(Color.WHITE));
    board[7][6].setPiece(new Knight(Color.WHITE));
    board[7][2].setPiece(new Bishop(Color.WHITE));
    board[7][5].setPiece(new Bishop(Color.WHITE));
    board[7][3].setPiece(new Queen(Color.WHITE));
    board[7][4].setPiece(new King(Color.WHITE));
  }

  public boolean movePiece(Player currentPlayer) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      int startRow = promptInput(scanner, "Enter starting row: ");
      int startCol = promptInput(scanner, "Enter starting column: ");
      int endRow = promptInput(scanner, "Enter destination row: ");
      int endCol = promptInput(scanner, "Enter destination column: ");

      if (!Piece.isWithinGrid(endRow, endCol)) {
        return false;
      }

      Piece pieceToMove = this.board[startRow][startCol].getPiece();

      if (pieceToMove == null) {
        System.out.println(
          "There's no piece at the specified starting position."
        );
        continue;
      }

      if (pieceToMove.getColor() != currentPlayer.getColor()) {
        System.out.println("It's not your turn to move this piece.");
        continue;
      }

      if (
        pieceToMove.isValidMove(startRow, startCol, endRow, endCol, this.board)
      ) {
        Piece destinationPiece = this.board[endRow][endCol].getPiece();
        if (
          destinationPiece != null &&
          destinationPiece.getColor() != pieceToMove.getColor()
        ) {
          this.board[endRow][endCol].setPiece(null);
        }

        this.board[endRow][endCol].setPiece(pieceToMove);
        this.board[startRow][startCol].setPiece(null); // Clear the original square
        System.out.println(
          pieceToMove.getSymbol() + " moved to " + endRow + ", " + endCol
        );
        return true;
      } else {
        System.out.println(
          "Invalid move for the " +
          pieceToMove.getSymbol() +
          ". Please try again."
        );
      }
    }
  }

  private int promptInput(Scanner scanner, String message) {
    System.out.print(message);
    return scanner.nextInt();
  }

  public void displayBoard() {
    System.out.println("  0 1 2 3 4 5 6 7");
    System.out.println("  ---------------");
    for (int i = 0; i < 8; i++) {
      System.out.print(i + "|");
      for (int j = 0; j < 8; j++) {
        if (board[i][j].getPiece() != null) {
          System.out.print(board[i][j].getPiece().getSymbol() + " ");
        } else {
          System.out.print(". ");
        }
      }
      System.out.println();
    }
  }
}

class Player {

  private Color color;

  public Player(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }
}

class ChessGame {

  private ChessBoard board;
  private Player whitePlayer;
  private Player blackPlayer;

  public ChessGame() {
    this.board = new ChessBoard();
    this.whitePlayer = new Player(Color.WHITE);
    this.blackPlayer = new Player(Color.BLACK);
  }

  public void startGame() {
    System.out.println(
      "Welcome to Chess, UPPERCASE denotes white, LOWERCASE denotes black"
    );
    board.displayBoard();

    Player currentPlayer = whitePlayer;

    while (true) {
      System.out.println(
        "Current turn: " +
        (currentPlayer.getColor() == Color.WHITE ? "White" : "Black")
      );

      boolean moveSuccessful = board.movePiece(currentPlayer);
      if (moveSuccessful) {
        board.displayBoard();
        currentPlayer =
          (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
      } else {
        System.out.println("Invalid move, try again.");
      }
    }
  }
}

class Main {

  public static void main(String[] args) {
    ChessGame game = new ChessGame();
    game.startGame();
  }
}
