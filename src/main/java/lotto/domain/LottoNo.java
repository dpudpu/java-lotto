package lotto.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LottoNo implements Comparable<LottoNo> {
    private final static Map<Integer, LottoNo> LOTTO_NOS = new HashMap<>();
    static final int MAX_NUMBER = 45;
    static final int MIN_NUMBER = 1;

    static {
        for (int i = MIN_NUMBER; i <= MAX_NUMBER; i++) {
            LOTTO_NOS.put(i, new LottoNo(i));
        }
    }

    private final int number;

    private LottoNo(final int number) {
        validate(number);
        this.number = number;
    }

    public static LottoNo from(final int number) {
        return LOTTO_NOS.getOrDefault(number, new LottoNo(number));
    }

    private static void validate(final int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException("로또 번호는 " + MIN_NUMBER + " ~ " + MAX_NUMBER + " 만 입력 가능합니다.");
        }
    }

    public int value() {
        return number;
    }

    @Override
    public int compareTo(final LottoNo o) {
        return this.number - o.number;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LottoNo lottoNo = (LottoNo) o;
        return number == lottoNo.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
