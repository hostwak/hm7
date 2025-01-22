package hw16;

import java.util.List;

interface Source {
    List<Integer> getData(int i);
    void saveData(int i, List<Integer> data);
}