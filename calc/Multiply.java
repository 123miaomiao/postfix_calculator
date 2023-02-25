package test.calc;


import java.math.BigDecimal;
import java.util.HashMap;

public class Multiply extends Operator{
public Multiply(){

}
public Multiply(HashMap priority, HashMap opt) {
    priority.put("*",10);
    opt.put("*",this.getClass().toString());
}

@Override
public double operator(double a, double b, String operator) {
    return new BigDecimal(String.valueOf(a)).multiply(new BigDecimal(String.valueOf(b))).doubleValue();
}
}


