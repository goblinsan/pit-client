package client;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class GameConnectionServiceTest {
    private GameConnectionService testObject = new GameConnectionService("localhost", "8080", "LUKE", "password");

    @Test
    public void startGameConnectPlayerOpenMarket() {
        GameConnectionService adminService = new GameConnectionService("localhost", "8080", "admin", "chase123");
        assertTrue(adminService.schedule("start"));
        assertTrue(testObject.connect());
        assertTrue(adminService.schedule("open"));
    }

    @Test
    public void clientCanJoinGame() {
        assertTrue(testObject.connect());
    }

    @Test
    public void playerCanGetHand() {
        Map<String, Integer> cards = testObject.getHand();
        assertEquals(9, cards.values().stream().mapToInt(Integer::intValue).sum());
    }
}