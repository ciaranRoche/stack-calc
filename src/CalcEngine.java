import java.util.Stack;

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
    public Stack<Integer> memory;
    public Stack<Character>stack;

    public String ans;
    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready
     * for use.
     */
    public CalcEngine()
    {
        stack = new Stack<Character>();
        memory = new Stack<Integer>();
        output = "";
        ans = "";
    }

    public void calculate(String output){
        memory.clear();
        ans = "";
        for(int n=0; n<output.length(); n++){
            int ch = output.charAt(n);
            if('0'<=ch && ch<='9')
                memory.push(ch - '0');
            else{
                switch(ch){
                    case '+':
                        int ba = memory.pop();
                        int aa = memory.pop();
                        memory.push(aa+ba);
                        break;
                    case '-':
                        int bm = memory.pop();
                        int am = memory.pop();
                        memory.push(am-bm);
                        break;
                    case '*':
                        int bmu = memory.pop();
                        int amu = memory.pop();
                        memory.push(amu*bmu);
                        break;
                    case '/':
                        int bd = memory.pop();
                        int ad = memory.pop();
                        memory.push(ad/bd);
                        break;
                    case '^':
                        int bp = memory.pop();
                        int ap = memory.peek();
                        memory.push((int) Math.pow(ap, bp));
                        break;
                }
            }
        }
        ans = ans + memory.pop();
    }

    public String infixToPostfix(String s){
        for(int i = 0; i< s.length(); i++){
            char ch = s.charAt(i);
         //   if('0'<=ch && ch>='9')
            switch (ch){
                case '+':
                case '-':
                    operator(ch, 1);
                    break;
                case '*':
                case '/':
                    operator(ch, 2);
                    break;
                case '^':
                    operator(ch, 3);
                    break;
                case '(':
                    stack.push(ch);
                    break;
                case ')':
                    priority(ch);
                    break;
                default:
                    output = output + ch;
                    break;
            }
        }
        while(!stack.isEmpty()){
            output = output + stack.pop();
        }
        System.out.println(output);
        calculate(output);
        return output;

    }

    private void power(char ch) {
    }

    public void operator(char opThis, int prec1){
        while(!stack.isEmpty()){
            char topStack = stack.pop();
            if (topStack == '('){
                stack.push(topStack);
                break;
            }else{
                int prec2;
                if(topStack == '+' || topStack == '-')
                    prec2 = 1;
                else
                    prec2 = 2;
                if(topStack == '*' || topStack == '/')
                    prec2 = 1;
                else
                    prec2 = 2;
                if(prec2 < prec1){
                    stack.push(topStack);
                    break;
                }else output = output + topStack;
            }
        }
        stack.push(opThis);
    }

    public void priority(char ch){
        while(!stack.isEmpty()){
            char cha = stack.pop();
            if (cha == '(')
                break;
            else {output = output + cha;
                //stack.clear();
            }
        }
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
