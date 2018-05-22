package client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameConnectionServiceTest {
    private GameConnectionService testObject = new GameConnectionService();

    @Test
    public void clientCanJoinGame() {
        assertEquals("ok", testObject.joinGame());
    }

}