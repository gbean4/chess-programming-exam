package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessRules Rule = new ChessRules(board);
        if (board.getPiece(myPosition).getPieceType()== PieceType.BISHOP){
            return Rule.getSlidingMoves(myPosition, new int[][]{{1,1}, {-1,1}, {1,-1}, {-1,-1}});
        } else if (board.getPiece(myPosition).getPieceType()== PieceType.ROOK){
            return Rule.getSlidingMoves(myPosition, new int[][]{{1,0}, {-1,0}, {0,-1}, {0,1}});
        } else if (board.getPiece(myPosition).getPieceType()== PieceType.QUEEN ||
                board.getPiece(myPosition).getPieceType()== PieceType.KING){
            return Rule.getSlidingMoves(myPosition, new int[][]{{1,1}, {-1,1}, {1,-1}, {-1,-1}, {1,0}, {-1,0}, {0,-1}, {0,1}});
        } else if (board.getPiece(myPosition).getPieceType()== PieceType.KNIGHT){
            return Rule.getSlidingMoves(myPosition, new int[][]{{1,2}, {-1,2}, {1,-2}, {-1,-2}, {2,1}, {-2,1}, {2,-1}, {-2,-1}});
        } else{
            return Rule.getPawnMoves(myPosition);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
