package test.calc;


import java.math.BigDecimal;
import java.util.HashMap;

public class Subtract extends Operator{
public Subtract(){

}
public Subtract(HashMap priority, HashMap opt) {
    priority.put("-",5);
    opt.put("-",this.getClass().toString());
}

@Override
public double operator(double a, double b, String operator) {
    return new BigDecimal(String.valueOf(a)).add(new BigDecimal(String.valueOf(b))).doubleValue();
}
}



