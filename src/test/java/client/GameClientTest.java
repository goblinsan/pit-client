package client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameClientTest {

    private GameConnectionService gameConnectionService;
    private GameClient testObject;

    @Before
    public void setUp() {
        gameConnectionService = Mockito.mock(GameConnectionService.class);
        testObject = new GameClient("Will", gameConnectionService);
    }

    @Test
    public void clientHasConnectService() {
        assertNotNull(testObject.getGameConnectionService());
    }

    @Test
    public void clientCanJoinGame() {
        assertEquals("ok", testObject.joinGame());
    }



}