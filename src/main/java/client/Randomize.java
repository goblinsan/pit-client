package client;

import java.util.Map;

public interface Randomize {
    TargetTrade getRandomTargetTrade(Map<String, Integer> hand);
}
