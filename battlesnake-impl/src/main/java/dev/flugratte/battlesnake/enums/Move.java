package dev.flugratte.battlesnake.enums;

public enum Move {

  RIGHT("right", 1, 0),
  LEFT("left", -1, 0), 
  DOWN("down", 0, -1), 
  UP("up", 0, 1), 
  NONE("none", 0, 0);

  public String moveName;
  public int xChange;
  public int yChange;

  private Move(String moveName, int xChange, int yChange) {
    this.moveName = moveName;
    this.xChange = xChange;
    this.yChange = yChange;
  }

  public String toString() {
    return this.moveName;
  }

  public Move opposite() {
    switch (this) {
    case DOWN:
      return UP;
    case LEFT:
      return RIGHT;
    case RIGHT:
      return LEFT;
    case UP:
      return DOWN;
    default:
    case NONE:
      return NONE;
    }
  }
}