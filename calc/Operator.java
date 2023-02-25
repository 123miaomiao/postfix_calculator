package test.calc;


import java.util.HashMap;
import java.util.Map;

public class Operator {
public Map<String, Integer> priority;
public Map<String, Integer> opt;

public int level;

public Operator(){

}


public Operator(HashMap priority,HashMap opt){
    priority.put("#", 0);
    priority.put("(", 1);
    priority.put(")", 1);

}


public double operator(double a,double b,String operator) {
    return 0;
}
}


