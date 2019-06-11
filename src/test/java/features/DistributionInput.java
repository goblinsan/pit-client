package features;

import client.TargetTrade;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
class DistributionInput {
    String commodity;
    Integer amount;
    Integer low;
    Integer high;

    TargetTrade getTargetTrade(){
        return new TargetTrade(commodity.toUpperCase(), amount);
    }

    boolean expectedDistribution(Integer actual){
        return actual >= low && actual <= high;
    }
}
