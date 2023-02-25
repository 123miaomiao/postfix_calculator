package test.calc;


import java.math.BigDecimal;
import java.util.HashMap;

public class mod extends Operator{
public mod() {

}
public mod(HashMap priority, HashMap opt) {
    priority.put("%", 10);
    opt.put("%",this.getClass().toString());
}

@Override
public double operator(double a, double b, String operator) {
    return new BigDecimal(String.valueOf(a)).add(new BigDecimal("100")).doubleValue();
}
}



