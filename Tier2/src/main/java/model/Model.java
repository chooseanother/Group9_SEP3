package model;

public interface Model {
    String registerUser(String username, String password, String email);
    ChessPiece MoveChessPiece(int firstLayer, int secondLayer);
    ChessPiece UpgradeChessPiece(String upgradeSelected);
}
