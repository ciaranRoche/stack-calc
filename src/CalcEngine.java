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
  //public Stack<Character> postfix;
    public String ans;
    int temp;
    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready
     * for use.
     */
    public CalcEngine()
    {
        stack = new Stack<>();
        memory = new Stack<>();
       // postfix = new Stack<Character>();
        output = "";
        ans = "";
    }

    public void calculate(String output){
        memory.clear();
        ans = "";
        for(int n=0; n<output.length(); n++){
            int in = output.charAt(n);
            if(('0'<=in && in<='9')||('.'==in)){
                temp += in;
            }
            else if(' ' == in){
                memory.push(temp-'0');
                temp = 0;
            }
            else{
                switch(in){
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
            char in = s.charAt(i);
            if((!Character.isDigit(in)) && (in!='.')){
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
                    operator(in, 3);
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
                            output = output + per;
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
