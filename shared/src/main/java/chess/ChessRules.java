package chess;

import java.util.HashSet;

import static chess.ChessPiece.PieceType.*;

public record ChessRules(ChessBoard board) {

    public HashSet<ChessMove> getSlidingMoves(ChessPosition myPosition, int[][] directions){
        HashSet<ChessMove> moves = new HashSet<>();

        for (int[] dir : directions){
            int dRow = dir[0];
            int dCol = dir[1];

            ChessPosition current = myPosition;
            while (true){
                current = new ChessPosition(current.getRow()+dRow, current.getColumn()+dCol);
                if (!board.inBounds(current)){
                    break;
                } else if (board.isEmpty(current)){
                    moves.add(new ChessMove(myPosition,current,null));
                    if (board.getPiece(myPosition).getPieceType()== ChessPiece.PieceType.KING ||
                            board.getPiece(myPosition).getPieceType()== ChessPiece.PieceType.KNIGHT){
                        break;
                    }
                } else if (board.getPiece(current).getTeamColor()!=board.getPiece(myPosition).getTeamColor()){
                    moves.add(new ChessMove(myPosition,current,null));
                    break;
                } else {
                    break;
                }
            }
        }
    return moves;
    }

    public void getPawnPromotion(HashSet<ChessMove> moves, ChessPosition from, ChessPosition to, ChessGame.TeamColor color){
        ChessPiece pawn = board.getPiece(from);
        if (to.getRow()==1&& color == ChessGame.TeamColor.BLACK || to.getRow()==8 && color== ChessGame.TeamColor.WHITE){
            for (ChessPiece.PieceType promotion : new ChessPiece.PieceType[] {QUEEN,ROOK,BISHOP,KNIGHT}){
                moves.add(new ChessMove(from,to,promotion));
            }
        } else{
            moves.add(new ChessMove(from,to,null));
        }
    }

    public HashSet<ChessMove> getPawnMoves(ChessPosition myPosition){
        HashSet<ChessMove> moves = new HashSet<>();
        ChessPosition current = myPosition;
        ChessPiece pawn = board.getPiece(current);

        int direction = (board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE)? 1: -1;

        ChessPosition oneForward = new ChessPosition(current.getRow()+direction, current.getColumn());
        if (board.inBounds(oneForward)){
            if(board.isEmpty(oneForward)){
                getPawnPromotion(moves,current,oneForward,pawn.getTeamColor());
                if(pawn.getTeamColor()== ChessGame.TeamColor.WHITE && current.getRow() == 2 || pawn.getTeamColor()== ChessGame.TeamColor.BLACK && current.getRow() == 7){
                    ChessPosition twoForward = new ChessPosition(current.getRow()+direction*2, current.getColumn());
                    if (board.inBounds(twoForward) && board.isEmpty(twoForward)){
                        getPawnPromotion(moves,current,twoForward,pawn.getTeamColor());
                }
             }
            }
        }
        int[][] diagonal = new int[][]{{current.getRow()+direction, current.getColumn()+1}, {current.getRow()+direction, current.getColumn()-1}};
        for (int[]dia : diagonal){
            int diaRow= dia[0];
            int diaCol= dia[1];
            ChessPosition diaForward = new ChessPosition(diaRow, diaCol);
            if (board.inBounds(diaForward) && !board.isEmpty(diaForward) && board.getPiece(diaForward).getTeamColor()!=pawn.getTeamColor()){
                getPawnPromotion(moves,current,diaForward,pawn.getTeamColor());
            }
        }
        return moves;
    }
}
