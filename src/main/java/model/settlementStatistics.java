package model;

public class settlementStatistics {
    private int count_case;
    private int sum_pay;

    public int getCount_case() {
        return count_case;
    }

    public void setCount_case(int count_case) {
        this.count_case = count_case;
    }

    public int getSum_pay() {
        return sum_pay;
    }

    public void setSum_pay(int sum_pay) {
        this.sum_pay = sum_pay;
    }

    @Override
    public String toString() {
        return "settlementStatistics{" +
                "count_case=" + count_case +
                ", sum_pay=" + sum_pay +
                '}';
    }
}
