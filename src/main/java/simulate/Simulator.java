package simulate;


import client.GameConnectionService;
import client.SwitchTraderLogic;
import client.TraderLogic;
import client.TraderRunner;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private static String host = "localhost";
    private static String port = "8080";

    public static void main(String [] args) {

        GameConnectionService adminService = new GameConnectionService(host, port, "admin", "chase123");
        adminService.schedule("start");

        List<TraderRunner> traderRunners = new ArrayList<>();
        traderRunners.add(getTraderRunner("JAMES", "password", new SwitchTraderLogic("JAMES")));
        traderRunners.add(getTraderRunner("LUKE", "password", new SwitchTraderLogic("LUKE")));
        traderRunners.add(getTraderRunner("MASON", "password", new SwitchTraderLogic("MASON")));
//        traderRunners.add(getTraderRunner("DANI", "password", new SwitchTraderLogic("DANI")));
//        traderRunners.add(getTraderRunner("WILL", "password", new SimpleTraderLogic("WILL")));
//        traderRunners.add(getTraderRunner("OWEN", "password"));
//        traderRunners.add(getTraderRunner("DEBBIE", "password"));

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
            System.out.println("    wins: " + tr.getTraderLogic().getName() + " : " + tr.getTraderLogic().getWins());
        }


    }

    private static void sleepAndIgnoreInterruptExcp(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static TraderRunner getTraderRunner(String username, String pass, TraderLogic traderLogic) {
        GameConnectionService gameConnectionService = new GameConnectionService(host, port, username, pass);
        return new TraderRunner(traderLogic, gameConnectionService);
    }

}
