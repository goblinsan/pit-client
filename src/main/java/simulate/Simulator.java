package simulate;


import client.GameConnectionService;
import client.TraderLogic;
import client.TraderRunner;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public static void main(String [] args) {

        GameConnectionService adminService = new GameConnectionService("localhost", "8080", "admin", "chase123");
        adminService.schedule("start");

        List<TraderRunner> traderRunners = new ArrayList<>();
        traderRunners.add(getTraderRunner("JAMES", "password"));
        traderRunners.add(getTraderRunner("LUKE", "password"));
        traderRunners.add(getTraderRunner("MASON", "password"));
        traderRunners.add(getTraderRunner("DANI", "password"));
        traderRunners.add(getTraderRunner("WILL", "password"));
        traderRunners.add(getTraderRunner("OWEN", "password"));
        traderRunners.add(getTraderRunner("DEBBIE", "password"));

        List<Thread> threads = new ArrayList<>();
        for (TraderRunner tr : traderRunners) {
            Thread t = new Thread(tr);
            threads.add(t);
            t.start();
        }
        sleepAndIgnoreInterruptExcp(500L);
        adminService.schedule("open");

        sleepAndIgnoreInterruptExcp(300000L);

        adminService.schedule("close");
        sleepAndIgnoreInterruptExcp(2000L);

        System.out.println("Results:");
        for (TraderRunner tr : traderRunners) {
            System.out.println("    wins: " + tr.getTraderLogic().getWins());
        }


    }

    private static void sleepAndIgnoreInterruptExcp(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static TraderRunner getTraderRunner(String username, String pass) {
        TraderLogic traderLogic = new TraderLogic(username);
        GameConnectionService gameConnectionService = new GameConnectionService("localhost", "8080", username, pass);
        return new TraderRunner(traderLogic, gameConnectionService);
    }

}
