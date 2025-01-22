package hw16;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker();
        var res = worker.fibonachi(11);
        var res2 = worker.fibonachi(9);
        System.out.println(res);
        System.out.println(res2);
    }
}