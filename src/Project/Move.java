package Project;

// This class represents gameBoard single move of gameBoard player.
public final class Move
{
    private Unit unitMoved;
    private int xMove;
    private int yMove;

    public Move(Unit unitMoved, int xMove, int yMove) {
        this.unitMoved = unitMoved;
        this.xMove = xMove;
        this.yMove = yMove;
    }

    public int getxMove() { return xMove; }
    public int getyMove() { return yMove; }
    public Unit getUnitMoved() { return unitMoved; }
}
