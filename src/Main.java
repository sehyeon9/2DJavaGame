import GameModel.Game;

public class Main {

    //TODO: Need to look into Thread Pools for performance improvement
    public static void main(String[] args) {
        Game game = new Game(6);
        game.start();
    }
    
}
