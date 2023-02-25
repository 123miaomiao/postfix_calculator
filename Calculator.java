package test;

import test.calc.Operator;
import test.calc.Add;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public class Calculator {

private String expression; //�洢���ʽ
private Map<String, Integer> priority = new HashMap<>();
private Map<String,String> opt = new HashMap<>(); // �洢�����
private String charOpt;    // �洢���������

public Calculator(String expression) {
    init();
    this.expression = expression;
}
//��ʼ��
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


    //�����ڸ���ǰ�油0
    for (int i = 0; i < expression.length(); i++) {
        if (expression.charAt(i) == '-' && ((i > 0 && !Character.isDigit(expression.charAt(i - 1))) || i == 0)) {
            StringBuilder exp = new StringBuilder(expression);
            exp.insert(i, "0");
            expression = exp.toString();System.out.println(expression);
        }
    }
    //�ж������Ƿ�ƥ�䣬'('��1��')'��1
    int backets = 0;
    //��������ɨ��
    for (int i = 0; i < expression.length(); i++) {
        if(expression.charAt(i) == '('){
            ++backets;     //'('��1
        }else if (expression.charAt(i) == ')'){
            --backets;          //')'��1
            if (backets < 0) //�м����'('����')'�����
                return false;
        }else if(expression.charAt(i) == '-'){
            if (i == expression.length() - 1) return false; //��������ܳ�����β��
            if (expression.charAt(i + 1) == '/' || expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '+' || expression.charAt(i + 1) == '-' || expression.charAt(i + 1) == ')')
                return false; //������������������Һ��治��Ϊ')'
        }else if ((String.join("",priority.keySet()).replace("#","").indexOf(expression.indexOf(expression.charAt(i))))!=-1){
            if (i == 0) return false; // '/'��'*'��'+'���ܳ�����λ
            if (i > 0 && (expression.charAt(i - 1) == '/' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-' || expression.charAt(i - 1) == '('))
                return false; //�������������������ǰ�治��Ϊ'('
        }
        
    }
    if (backets != 0) //������Ų�ƥ��
        return false;
    return true;
}


//��׺���ʽת��Ϊ��׺���ʽ
public Stack<String> Conversion() {
    Stack<String> s1 = new Stack<>();//���ַ����д����ջ
    Stack<String> s2 = new Stack<>();//�洢��׺���ʽ��ջ
    s1.push("#"); //��������ȼ���#���ŷ���S1ջ��Ϊ�˷���ͳһ��������
    for (int i = 0; i < expression.length(); i++) { //ѭ���������ʽ
        switch (expression.charAt(i)) {
            case '(': s1.push("("); break; //��ȡ�������ţ�ֱ��ѹ��S1ջ
            case ')': //��ȡ�����ַ���")"���򽫾���S1ջջ�������"("֮���������������ջ����������S2ջ����ʱ����"("��
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
                //�ò�����ֱ������S2ջ
                s2.push(num.toString());
                break;
            default:
                /*
                 * ��ȡ�����ַ�����������򽫸��������S1ջջ��Ԫ�رȽϣ�
                 * �������������ȼ�(���������������)����S1ջջ����������ȼ����򽫸��������S1ջ��
                 * ���򣬽�S1ջ��ջ�����������������S2ջ�У�ֱ��S1ջջ�����������(����������)����������ȼ���
                 * ��󽫸����������S1ջ��
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
    //��S1ջ�����������(������"#")�������ջ����������S2ջ��
    while (!s1.peek().equals("#"))
        s2.push(s1.pop());
    
    Stack<String> stack = new Stack<>();//��s2����������
    while (!s2.isEmpty()) {
        stack.push(s2.pop());
    }
    while(!s2.isEmpty()) {
    	System.out.println(s2.pop());
    }

    return stack; //����S2������ջ
}

//��׺����ֵ
public String Calculated() {
    if (!this.Check()) //�Ϸ��Լ���
        return "Error!"; //���Ϸ�����"Error!"
    Stack<String> stack = Conversion(); //�õ���׺���ʽ
    Stack<Double> tmp = new Stack<>(); //����һ��ջ���ڴ�Ų���������ÿ�δ�ջ������ȡ����
    while (!stack.isEmpty()) {
        String s = stack.pop();
        if (Character.isDigit(s.charAt(0))) //����ǲ�����
            tmp.push(Double.parseDouble(s)); //ֱ�ӽ���tmpջ
        else {//������������ȡ�����������м���
            double b = tmp.pop();
            double a = tmp.pop();
            switch (s) {
                case "/":
                    if (b == 0.0) //�������Ϊ0������
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
    System.out.print("������ʽ��");
    Scanner scanner = new Scanner(System.in);
    String express = scanner.nextLine();
    Calculator calculator = new Calculator(express);
    System.out.println(calculator.Calculated());
}
}


