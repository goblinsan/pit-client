package simulate;


import client.GameConnectionService;
import client.TraderLogic;
import client.TraderRunner;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public static void main(String [] args) {

        String host = "localhost";
        String port = "8080";
        GameConnectionService adminService = new GameConnectionService(host, port, "admin", "chase123");
        adminService.schedule("start");

        List<TraderRunner> traderRunners = new ArrayList<>();
        traderRunners.add(getTraderRunner("JAMES", "password", host, port));
        traderRunners.add(getTraderRunner("LUKE", "password", host, port));
        traderRunners.add(getTraderRunner("MASON", "password", host, port));
        traderRunners.add(getTraderRunner("DANI", "password", host, port));
        traderRunners.add(getTraderRunner("WILL", "password", host, port));
        traderRunners.add(getTraderRunner("OWEN", "password", host, port));
        traderRunners.add(getTraderRunner("DEBBIE", "password", host, port));

        List<Thread> threads = new ArrayList<>();
        for (TraderRunner tr : traderRunners) {
            Thread t = new Thread(tr);
            threads.add(t);
            t.start();
        }
        sleepAndIgnoreInterruptExcp(500L);
        adminService.schedule("open");

        sleepAndIgnoreInterruptExcp(30000L);

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

    private static TraderRunner getTraderRunner(String username, String pass, String host, String port) {
        TraderLogic traderLogic = new TraderLogic(username);
        GameConnectionService gameConnectionService = new GameConnectionService(host, port, username, pass);
        return new TraderRunner(traderLogic, gameConnectionService);
    }

}
