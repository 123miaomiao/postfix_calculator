package test;

import test.calc.Operator;
import test.calc.Add;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public class Calculator {

private String expression; //存储表达式
private Map<String, Integer> priority = new HashMap<>();
private Map<String,String> opt = new HashMap<>(); // 存储运算符
private String charOpt;    // 存储其他运算符

public Calculator(String expression) {
    init();
    this.expression = expression;
}
//初始化
public void init() {
     System.out.println(new File(".").getAbsolutePath());
     String[] list = new File(".\\src\\test\\calc").list();
   
     int index=0;
     Iterator<String> iterator = Arrays.stream(list).iterator();
     while (iterator.hasNext()){
         try {
             Class<?> aClass = Class.forName("test.calc." + iterator.next().replace(".java", ""));
             Constructor<?> constructor = aClass.getConstructor(HashMap.class,HashMap.class);
             Operator operator = (Operator) constructor.newInstance(priority, opt);
         } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
             e.printStackTrace();
         }
     }
    Iterator<String> iterator1 = priority.keySet().iterator();
    Iterator<String> iterator2 = opt.keySet().iterator();
}


public boolean Check() {
    String str = "[0-9"+String.join("",priority.keySet()).replace("#","")+"]+";
    if (!expression.matches(str) ||  !Character.isDigit(expression.charAt(expression.length()-1))){
        return false;
    }


    //首先在负号前面补0
    for (int i = 0; i < expression.length(); i++) {
        if (expression.charAt(i) == '-' && ((i > 0 && !Character.isDigit(expression.charAt(i - 1))) || i == 0)) {
            StringBuilder exp = new StringBuilder(expression);
            exp.insert(i, "0");
            expression = exp.toString();System.out.println(expression);
        }
    }
    //判断括号是否匹配，'('加1，')'减1
    int backets = 0;
    //从左向右扫描
    for (int i = 0; i < expression.length(); i++) {
        if(expression.charAt(i) == '('){
            ++backets;     //'('加1
        }else if (expression.charAt(i) == ')'){
            --backets;          //')'减1
            if (backets < 0) //中间出现'('少于')'的情况
                return false;
        }else if(expression.charAt(i) == '-'){
            if (i == expression.length() - 1) return false; //运算符不能出现在尾部
            if (expression.charAt(i + 1) == '/' || expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '+' || expression.charAt(i + 1) == '-' || expression.charAt(i + 1) == ')')
                return false; //运算符不能连续出现且后面不能为')'
        }else if ((String.join("",priority.keySet()).replace("#","").indexOf(expression.indexOf(expression.charAt(i))))!=-1){
            if (i == 0) return false; // '/'、'*'、'+'不能出现首位
            if (i > 0 && (expression.charAt(i - 1) == '/' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-' || expression.charAt(i - 1) == '('))
                return false; //运算符不能连续出现且前面不能为'('
        }
        
    }
    if (backets != 0) //如果括号不匹配
        return false;
    return true;
}


//中缀表达式转换为后缀表达式
public Stack<String> Conversion() {
    Stack<String> s1 = new Stack<>();//对字符进行处理的栈
    Stack<String> s2 = new Stack<>();//存储后缀表达式的栈
    s1.push("#"); //将最低优先级的#符号放入S1栈，为了方便统一后续操作
    for (int i = 0; i < expression.length(); i++) { //循环遍历表达式
        switch (expression.charAt(i)) {
            case '(': s1.push("("); break; //读取到左括号，直接压入S1栈
            case ')': //若取出的字符是")"，则将距离S1栈栈顶最近的"("之间的运算符，逐个出栈，依次送入S2栈，此时抛弃"("。
                while (!s1.peek().equals("(")) {
                    s2.push(s1.pop());
                }
                s1.pop(); break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                
                StringBuilder num = new StringBuilder();
                while (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.') {
                    num.append(expression.charAt(i));
                    if (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.'))
                        ++i;
                    else break;
                }
                //该操作数直接送入S2栈
                s2.push(num.toString());
                break;
            default:
                /*
                 * 若取出的字符是运算符，则将该运算符与S1栈栈顶元素比较，
                 * 如果该运算符优先级(不包括括号运算符)大于S1栈栈顶运算符优先级，则将该运算符进S1栈，
                 * 否则，将S1栈的栈顶运算符弹出，送入S2栈中，直至S1栈栈顶运算符低于(不包括等于)该运算符优先级，
                 * 最后将该运算符送入S1栈。
                 * */
                if (priority.get(expression.charAt(i) + "") > priority.get(s1.peek())) {
                    s1.push(expression.charAt(i) + "");
                }
                else {
                    while (!(priority.get(expression.charAt(i) + "") > priority.get(s1.peek()))) {
                        s2.push(s1.pop());
                    }
                    s1.push(expression.charAt(i) + "");
                }
                break;
        }
    }
    //将S1栈内所有运算符(不包括"#")，逐个出栈，依次送入S2栈。
    while (!s1.peek().equals("#"))
        s2.push(s1.pop());
    
    Stack<String> stack = new Stack<>();//对s2进行逆序处理
    while (!s2.isEmpty()) {
        stack.push(s2.pop());
    }
    while(!s2.isEmpty()) {
    	System.out.println(s2.pop());
    }

    return stack; //返回S2的逆序栈
}

//后缀的求值
public String Calculated() {
    if (!this.Check()) //合法性检验
        return "Error!"; //不合法返回"Error!"
    Stack<String> stack = Conversion(); //得到后缀表达式
    Stack<Double> tmp = new Stack<>(); //声明一个栈用于存放操作的数，每次从栈顶往下取两个
    while (!stack.isEmpty()) {
        String s = stack.pop();
        if (Character.isDigit(s.charAt(0))) //如果是操作数
            tmp.push(Double.parseDouble(s)); //直接进入tmp栈
        else {//如果是运算符，取出两个数进行计算
            double b = tmp.pop();
            double a = tmp.pop();
            switch (s) {
                case "/":
                    if (b == 0.0) //如果除数为0，报错
                        return "Error!";
                    tmp.push(Div(a, b)); break;
                case "*": tmp.push(Mul(a, b)); break;
                case "-": tmp.push(Sub(a, b)); break;
                case "+":
                    Operator operator = new Add();
                    tmp.push(operator.operator(a,b,"+")); break;
                default:
                    try {
                        System.out.println(opt.get(s).toString().replace("class ", ""));
                        Class<?> aClass = Class.forName(opt.get(s).replace("class ", ""));
                        Operator o = (Operator) aClass.newInstance();

                        tmp.push( o.operator(a,b,s));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

            }
        }
    }
    return tmp.pop().toString();
}
public double Div(double a, double b) {
    BigDecimal a1 = new BigDecimal(a);
    BigDecimal b1 = new BigDecimal(b);
    return a1.divide(b1, 5, BigDecimal.ROUND_HALF_UP).doubleValue();
}
public double Mul(double a, double b) {
    return a * b;
}
public double Sub(double a, double b) {
    return a - b;
}
public double Add(double a, double b) {
    return a + b;
}
public static void main(String[] args) {
    System.out.print("输入算式：");
    Scanner scanner = new Scanner(System.in);
    String express = scanner.nextLine();
    Calculator calculator = new Calculator(express);
    System.out.println(calculator.Calculated());
}
}


