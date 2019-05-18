package simulate;


import client.GameConnectionService;
import client.TraderLogic;
import client.TraderRunner;

public class SimpleSimulator {

    public static void main(String[] args) {
        String host = "localhost";
        String port = "8080";
        GameConnectionService adminService = new GameConnectionService(host, port, "admin", "chase123");
        adminService.schedule("start");

        TraderRunner traderRunner = getTraderRunner(host, port);
        traderRunner.run();

        System.out.println("Results:");
        System.out.println("    wins: " + traderRunner.getTraderLogic().getWins());
    }

    private static TraderRunner getTraderRunner(String host, String port) {
        TraderLogic traderLogic = new TraderLogic("JAMES");
        GameConnectionService gameConnectionService = new GameConnectionService(host, port, "JAMES", "password");
        return new TraderRunner(traderLogic, gameConnectionService);
    }

}
