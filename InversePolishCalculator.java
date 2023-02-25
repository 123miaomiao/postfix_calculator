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
    private static Font textFont = new Font("΢���ź�", Font.PLAIN, 18);
    private static Font buttonFont = new Font("΢���ź�", Font.PLAIN, 16);

    public InversePolishCalculator() {
        initialization();
    }

    /**
     * ���ڳ�ʼ���ķ���
     */
    public void initialization() {
        this.setTitle("���ʽ��ֵ��");//���ô��ڱ���
        this.setSize(660, 320);//���ô��ڴ�С
        this.setLocationRelativeTo(null);//���ô��ھ���
        this.setResizable(false);//���ô��ڴ�С���ɸı�

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addViewElement();//����������
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
     * �����������ķ���
     */
    public void addViewElement() {
        backgroundPanel = new JPanel(null);
        nifixExpressionLabel = new JLabel("��������׺���ʽ��");
        nifixExpressionLabel.setSize(300, 20);
        nifixExpressionLabel.setLocation(40, 50);
        nifixExpressionLabel.setFont(textFont);
        backgroundPanel.add(nifixExpressionLabel);

        nifixExpressionTextField = new JTextField();
        nifixExpressionTextField.setSize(300, 30);
        nifixExpressionTextField.setLocation(40, 75);
        nifixExpressionTextField.setFont(textFont);
        backgroundPanel.add(nifixExpressionTextField);

        calculateButton = new JButton("����");
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

        clearButton = new JButton("���");
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

        postfixExpressionLabel = new JLabel("��׺���ʽת����ĺ�׺���ʽ��");
        postfixExpressionLabel.setSize(300, 20);
        postfixExpressionLabel.setLocation(40, 115);
        postfixExpressionLabel.setFont(textFont);
        backgroundPanel.add(postfixExpressionLabel);

        postfixExpressionTextField = new JTextField();
        postfixExpressionTextField.setSize(300, 30);
        postfixExpressionTextField.setLocation(40, 140);
        postfixExpressionTextField.setFont(textFont);
        backgroundPanel.add(postfixExpressionTextField);

        calculateResultLabel = new JLabel("��������");
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
                throw new RuntimeException("δ�����������" + operator);
        }
        return result;
    }

    public static void main(String[] args) {
        new InversePolishCalculator();
    }

}

class Stack<E> {

    private int maxSize;//ջ���������
    private Object[] elements;
    private int top;//ָ��ջ��Ԫ��

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
            System.out.println("ջ����Ԫ���޷���ջ��");
            return ;
        }
        elements[++top] = element;
    }

    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("ջ�գ��޷�ȡջ��Ԫ�أ�");
        }
        return (E)elements[top];
    }
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("ջ�գ�ջ��Ԫ���޷���ջ��");
        }
        return (E)elements[top--];
    }
}

    

