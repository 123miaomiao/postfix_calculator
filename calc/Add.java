package test.calc;


import java.math.BigDecimal;
import java.util.HashMap;

public class Add extends Operator{

public Add(){

}


public Add(HashMap priority, HashMap opt) {
    priority.put("+", 5);
    opt.put("+",this.getClass().toString());
}

@Override
public double operator(double a,double b,String operator) {
    //System.out.println(a+b);
    return new BigDecimal(String.valueOf(a)).add(new BigDecimal(String.valueOf(b))).doubleValue();
    //return a+b;
}
}



