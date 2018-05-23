package client;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameConnectionServiceTest {
    private GameConnectionService testObject = new GameConnectionService();

    @Test
    public void clientCanJoinGame() {
        assertTrue(testObject.joinGame());
    }

}