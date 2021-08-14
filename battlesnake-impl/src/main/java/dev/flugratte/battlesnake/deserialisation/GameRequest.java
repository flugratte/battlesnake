package dev.flugratte.battlesnake.deserialisation;

public class GameRequest {

    private Game game;
    private int turn;
    private Board board;
    private Battlesnake you;

    public GameRequest() {

    }

    public GameRequest(Game game, int turn, Board board, Battlesnake you) {
        this.game = game;
        this.turn = turn;
        this.board = board;
        this.you = you;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Battlesnake getYou() {
        return you;
    }

    public void setYou(Battlesnake you) {
        this.you = you;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + ((game == null) ? 0 : game.hashCode());
        result = prime * result + turn;
        result = prime * result + ((you == null) ? 0 : you.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameRequest other = (GameRequest) obj;
        if (board == null) {
            if (other.board != null)
                return false;
        } else if (!board.equals(other.board))
            return false;
        if (game == null) {
            if (other.game != null)
                return false;
        } else if (!game.equals(other.game))
            return false;
        if (turn != other.turn)
            return false;
        if (you == null) {
            if (other.you != null)
                return false;
        } else if (!you.equals(other.you))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MoveRequest [board=");
        builder.append(board);
        builder.append(", game=");
        builder.append(game);
        builder.append(", turn=");
        builder.append(turn);
        builder.append(", you=");
        builder.append(you);
        builder.append("]");
        return builder.toString();
    }

}
