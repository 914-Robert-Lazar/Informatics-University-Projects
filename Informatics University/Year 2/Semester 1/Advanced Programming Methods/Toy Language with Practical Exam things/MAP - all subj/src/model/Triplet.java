package model;

import java.util.List;

public class Triplet {
    public Integer first;
    public List<Integer> second;
    public Integer third;

    public Triplet(Integer first, List<Integer> second, Integer third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
