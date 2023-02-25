package test.calc;


import java.math.BigDecimal;
import java.util.HashMap;

public class Divide extends Operator{

public Divide(){

}
public Divide(HashMap priority, HashMap opt) {
    priority.put("/", 10);
    opt.put("/",this.getClass().toString());
}


@Override
public double operator(double a, double b, String operator) {
    return new BigDecimal(String.valueOf(a)).divide(new BigDecimal(String.valueOf(b))).doubleValue();
}
}



