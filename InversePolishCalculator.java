    package test;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class InversePolishCalculator extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel backgroundPanel;
    private JLabel nifixExpressionLabel;
    private JTextField nifixExpressionTextField;
    private JButton calculateButton;
    private JButton clearButton;
    private JLabel postfixExpressionLabel;
    private JTextField postfixExpressionTextField;
    private JLabel calculateResultLabel;
    private JTextField calculateResultTextField;
    private static Font textFont = new Font("微软雅黑", Font.PLAIN, 18);
    private static Font buttonFont = new Font("微软雅黑", Font.PLAIN, 16);

    public InversePolishCalculator() {
        initialization();
    }

    /**
     * 窗口初始化的方法
     */
    public void initialization() {
        this.setTitle("表达式求值器");//设置窗口标题
        this.setSize(660, 320);//设置窗口大小
        this.setLocationRelativeTo(null);//设置窗口居中
        this.setResizable(false);//设置窗口大小不可改变

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addViewElement();//窗口添加组件
        this.setVisible(true);
    }

    public String con(Stack<String> stack) {
    	String str = "";
    	while(!stack.isEmpty()) {
    		str += stack.pop();
    	}
		return str;
    	
    }
    
    /**
     * 窗口添加组件的方法
     */
    public void addViewElement() {
        backgroundPanel = new JPanel(null);
        nifixExpressionLabel = new JLabel("请输入中缀表达式：");
        nifixExpressionLabel.setSize(300, 20);
        nifixExpressionLabel.setLocation(40, 50);
        nifixExpressionLabel.setFont(textFont);
        backgroundPanel.add(nifixExpressionLabel);

        nifixExpressionTextField = new JTextField();
        nifixExpressionTextField.setSize(300, 30);
        nifixExpressionTextField.setLocation(40, 75);
        nifixExpressionTextField.setFont(textFont);
        backgroundPanel.add(nifixExpressionTextField);

        calculateButton = new JButton("计算");
        calculateButton.setSize(70, 30);
        calculateButton.setLocation(350, 75);
        calculateButton.setFont(buttonFont);
        calculateButton.setBorder(BorderFactory.createRaisedBevelBorder());
        calculateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	Calculator calc = new Calculator(nifixExpressionTextField.getText());
                postfixExpressionTextField.setText(con(calc.Conversion()));
                calculateResultTextField.setText("" + calc.Calculated());
            }
        });
        backgroundPanel.add(calculateButton);

        clearButton = new JButton("清空");
        clearButton.setSize(70, 30);
        clearButton.setLocation(430, 75);
        clearButton.setFont(buttonFont);
        clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nifixExpressionTextField.setText("");
                postfixExpressionTextField.setText("");
                calculateResultTextField.setText("");
            }
        });
        backgroundPanel.add(clearButton);

        postfixExpressionLabel = new JLabel("中缀表达式转换后的后缀表达式：");
        postfixExpressionLabel.setSize(300, 20);
        postfixExpressionLabel.setLocation(40, 115);
        postfixExpressionLabel.setFont(textFont);
        backgroundPanel.add(postfixExpressionLabel);

        postfixExpressionTextField = new JTextField();
        postfixExpressionTextField.setSize(300, 30);
        postfixExpressionTextField.setLocation(40, 140);
        postfixExpressionTextField.setFont(textFont);
        backgroundPanel.add(postfixExpressionTextField);

        calculateResultLabel = new JLabel("计算结果：");
        calculateResultLabel.setSize(300, 20);
        calculateResultLabel.setLocation(40, 180);
        calculateResultLabel.setFont(textFont);
        backgroundPanel.add(calculateResultLabel);

        calculateResultTextField = new JTextField();
        calculateResultTextField.setSize(300, 30);
        calculateResultTextField.setLocation(40, 205);
        calculateResultTextField.setFont(textFont);
        backgroundPanel.add(calculateResultTextField);

        this.setContentPane(backgroundPanel);
    }
    public static int calculateResult(int operand1, int operand2, char operator) {
        int result = 0;
        switch (operator) {
            case '+':
                result = operand2 + operand1;
                break;
            case '-':
                result = operand2 - operand1;
                break;
            case '*':
                result = operand2 * operand1;
                break;
            case '/':
                result = operand2 / operand1;
                break;
            default:
                throw new RuntimeException("未定义运算符：" + operator);
        }
        return result;
    }

    public static void main(String[] args) {
        new InversePolishCalculator();
    }

}

class Stack<E> {

    private int maxSize;//栈的最大容量
    private Object[] elements;
    private int top;//指向栈顶元素

    public Stack() {
        this(30);
    }

    public Stack(int maxSize) {
        this.maxSize = maxSize;
        elements = new Object[this.maxSize];
        top = -1;
    }
    public boolean isEmpty() {
        return top == -1;
    }
    public boolean isFull() {
        return top == maxSize - 1;
    }
    public void push(E element) {
        if (isFull()) {
            System.out.println("栈满，元素无法入栈！");
            return ;
        }
        elements[++top] = element;
    }

    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法取栈顶元素！");
        }
        return (E)elements[top];
    }
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，栈顶元素无法出栈！");
        }
        return (E)elements[top--];
    }
}

    

