import java.util.Stack;
import java.util.StringTokenizer;

import static java.lang.Character.*;

/**
 * The main part of the calculator doing the calculations.
 *
 * @author  M. Kolling
 * @version 0.1 (incomplete)
 * @editedBy Ciaran Roche
 * @date 23/01/2017
 */
public class CalcEngine
{
    public String output;
    public Stack<Double> memory;
    public Stack<Character>stack;
    public String ans;
    double temp;

    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready
     * for use.
     */
    public CalcEngine()
    {
        stack = new Stack<>();
        memory = new Stack<>();
        output = "";
        ans = "";
    }


    public void calculate(String output){
        memory.clear();
        ans = "";
        double a;
        double b;
        String[] tokens = output.split(" ");
        for(int i=0; i< tokens.length; i++){
            String currentElement = tokens[i];
            if( (!currentElement.equals("+")) && (!currentElement.equals("-"))
                    && (!currentElement.equals("*")) && (!currentElement.equals("/"))
                    && (!currentElement.equals("^"))) {
                memory.push(Double.parseDouble(currentElement));
            }else if (memory.size() >= 2){
                if (currentElement.equals("+")){
                    b = memory.pop();
                    a = memory.pop();
                    memory.push(a + b);
                }
                if (currentElement.equals("-")){
                    b = memory.pop();
                    a = memory.pop();
                    memory.push(a - b);
                }
                if (currentElement.equals("*")){
                    b = memory.pop();
                    a = memory.pop();
                    memory.push(a * b);
                }
                if (currentElement.equals("/")){
                    b = memory.pop();
                    a = memory.pop();
                    memory.push(a / b);
                }
                if (currentElement.equals("^")){
                    b = memory.pop();
                    a = memory.pop();
                    memory.push(Math.pow(a, b));
                }
            }
        }ans = ans + memory.pop();
    }


    public String infixToPostfix(String s){
        for(int i = 0; i< s.length(); i++){
            char in = s.charAt(i);
            if((!isDigit(in)) && (in!='.') && (in!='(') && (in!=')')){
                char filler = ' ';
                output = output + filler;
            }
            switch (in){
                case '+':
                case '-':
                    operator(in, 1);
                    break;
                case '*':
                case '/':
                    operator(in, 2);
                    break;
                case '^':
                    while(!stack.isEmpty()){
                        int prec1 = 3;
                        char topStack = stack.pop();
                        if (topStack == '('){
                            stack.push(topStack);
                            break;
                        }else{
                            int prec2;
                            if(topStack == '+' || topStack == '-') {
                                prec2 = 1;
                            }else {
                                prec2 = 2;
                            }
                             if(topStack == '*' || topStack == '/') {
                                prec2 = 1;
                            }else{
                                prec2 = 2;
                            }
                            if(prec2 < prec1){
                                stack.push(topStack);
                                break;
                            }else output = output + topStack + ' ';
                        }
                    }
                    stack.push(in);
                    break;
                case '(':
                    stack.push(in);
                    break;
                case ')':
                    while(!stack.isEmpty()){
                        char per = stack.pop();
                        if(per=='('){
                            break;
                        }else{
                            output = output + " " + per;
                        }
                    }
                    break;
                default:
                    output = output + in;
                    break;
            }
        }
        while(!stack.isEmpty()){
            output = output + ' ' + stack.pop();
        }
        System.out.println(output);
        calculate(output);
        return output;

    }


    public void operator(char operatorThis, int prec1){
        while(!stack.isEmpty()){
            char topStack = stack.pop();
            if (topStack == '('){
                stack.push(topStack);
                break;
            }else{
                int prec2;
                if(topStack == '+' || topStack == '-') {
                    prec2 = 1;
                }else {
                    prec2 = 2;
                }
                if(prec2 < prec1){
                    stack.push(topStack);
                    break;
                }else output = output + topStack + ' ';
            }
        }
        stack.push(operatorThis);
    }

    public void clear(){
        output = "";
    }

    public String getOutput(){
        return output;
    }


    /**
     * Return the title of this calculation engine.
     */
    public String getTitle()
    {
        return("Not So Scientific Calculator");
    }

    /**
     * Return the author of this engine. This string is displayed as it is,
     * so it should say something like "Written by H. Simpson".
     */
    public String getAuthor()
    {
        return("Ciaran Roche");
    }

    /**
     * Return the version number of this engine. This string is displayed as
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.0");
    }

}

