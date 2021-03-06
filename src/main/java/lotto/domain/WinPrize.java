package lotto.domain;

import java.util.HashMap;
import java.util.Map;

public class WinPrize {
    private static final int INIT_COUNT = 0;
    private static final int PERCENT = 100;
    private static final int COUNT = 1;

    private Map<Rank, Integer> ranks;

    public WinPrize() {
        ranks = new HashMap<>();
        for (Rank rank : Rank.values()) {
            ranks.put(rank, INIT_COUNT);
        }
    }

    public void addWinCount(final Rank rank) {
        ranks.put(rank, ranks.get(rank) + COUNT);
    }

    public Integer getWinCount(Rank rank) {
        return ranks.get(rank);
    }

    public double getRateOfProfit() {
        return getTotalPrize() / getPurchasedAmount() * PERCENT;
    }

    private Long getPurchasedAmount() {
        long count = 0;
        for (Rank rank : ranks.keySet()) {
            count += ranks.get(rank);
        }
        return count * Money.PRICE_PER_LOTTO;
    }

    public double getTotalPrize() {
        double totalPrize = 0;
        for (Rank rank : ranks.keySet()) {
            totalPrize += rank.getPrize() * ranks.get(rank);
        }
        return totalPrize;
    }

    public void put(final Rank rank, final int count) {
        ranks.put(rank, count);
    }
}
