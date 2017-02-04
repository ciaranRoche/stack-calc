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
    public String ans, postfix;

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
        postfix = "";
    }

    /**
     * Calculate method which takes in a string "prefix" which has white space delims
     * add, to which the string gets split into tokens, these tokens then are evaluated
     * if not operator are pushed to a stack, if operator, then stack is popped twice
     * calculation is carried out and answer is pushed back on the stack.
     * when no tokens are left the answer is returned from a final stack pop
     */

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

    /**
     * Takes in a string to inserts white spaces around operators to make splitting the
     * string easier,
     * if not an operator item is added to the string, when operator white space is added
     * priority is taken on operator, depending on outcome it is either pushed to a stack
     * or sent to the output string
     * @param s
     * @return
     */

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
                    operatorPriority(in, 1);
                    break;
                case '*':
                case '/':
                    operatorPriority(in, 2);
                    break;
                case '^':
                    while(!stack.isEmpty()){
                        int prec1 = 3;
                        char topStack = stack.pop();
                        if (topStack == '('){
                            stack.push(topStack);
                            break;
                        }else{
                            int priority2;
                            if(topStack == '+' || topStack == '-') {
                                priority2 = 1;
                            }else {
                                priority2 = 2;
                            }
                             if(topStack == '*' || topStack == '/') {
                                 priority2 = 1;
                            }else{
                                 priority2 = 2;
                            }
                            if(priority2 < prec1){
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
        postfix = output;
        System.out.println(postfix);
        calculate(output);
        return output;

    }


    /**
     * Messy method which calculates operator priority, takes in operator with
     * a given priority rating, if stack is empty operator is pushed straight
     * to the stack, other wise stack is popped, this is checked, and depending
     * on what it is, a priority is given and priorities are checked, depending
     * on result one operator gets pushed to stack other gets put on to the string
     * @param operatorThis
     * @param priority1
     */
    public void operatorPriority(char operatorThis, int priority1){
        while(!stack.isEmpty()){
            char topStack = stack.pop();
            if (topStack == '('){
                stack.push(topStack);
                break;
            }else{
                int priority2;
                if(topStack == '+' || topStack == '-') {
                    priority2 = 1;
                }else {
                    priority2 = 2;
                }
                if(priority2 < priority1){
                    stack.push(topStack);
                    break;
                }else output = output + topStack + ' ';
            }
        }
        stack.push(operatorThis);
    }

    public void clear(){
        output = "";
        postfix = "";
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

