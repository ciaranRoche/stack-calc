import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the calculator. No calculation is being
 * done here. This class is responsible just for putting up the display on
 * screen. It then refers to the "CalcEngine" to do all the real work.
 *
 * @author Michael Kolling
 * @version 31 July 2000
 * @editedBy Ciaran Roche
 * @date 23/01/2017
 */
public class UserInterface
        implements ActionListener
{
    private CalcEngine calc;
    private boolean showingAuthor;
    private String infix;

    private JFrame frame;
    private JTextField display, displayAns;
    private JLabel status;

    /**
     * Create a user interface for a given calcEngine.
     */
    public UserInterface(CalcEngine engine)
    {
        calc = engine;
        infix = "";
        showingAuthor = true;
        makeFrame();
        frame.setVisible(true);
    }

    /**
     * Make this interface visible again. (Has no effect if it is already
     * visible.)
     */
    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    /**
     * Make the frame for the user interface.
     */
    private void makeFrame()
    {
        frame = new JFrame(calc.getTitle());

        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));

        contentPane.setBackground(Color.darkGray);

//        JPanel textPanel = new JPanel(new GridLayout(3,1));
//        textPanel.add(display);
//        textPanel.add(displayAns);
//        contentPane.add(textPanel, BorderLayout.NORTH);

        display = new JTextField();
        contentPane.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        addButton(buttonPanel, "C");
        addButton(buttonPanel, "(");
        addButton(buttonPanel, ")");
        addButton(buttonPanel, "/");
        addButton(buttonPanel, "7");
        addButton(buttonPanel, "8");
        addButton(buttonPanel, "9");
        addButton(buttonPanel, "*");
        addButton(buttonPanel, "4");
        addButton(buttonPanel, "5");
        addButton(buttonPanel, "6");
        addButton(buttonPanel, "+");
        addButton(buttonPanel, "1");
        addButton(buttonPanel, "2");
        addButton(buttonPanel, "3");
        addButton(buttonPanel, "-");
        addButton(buttonPanel, "0");
        addButton(buttonPanel, ".");
        addButton(buttonPanel, "^");
        addButton(buttonPanel, "=");
        contentPane.add(buttonPanel, BorderLayout.CENTER);

//		status = new JLabel(calc.getAuthor());
//		contentPane.add(status,BorderLayout.PAGE_END);
        displayAns = new JTextField();
        contentPane.add(displayAns, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    /**
     * Add a button to the button panel.
     */
    private void addButton(Container panel, String buttonText)
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(this);
        panel.add(button);
    }

    /**
     * An interface action has been performed. Find out what it was and
     * handle it.
     */
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();

        if(command.equals("0") ||
                command.equals("1") ||
                command.equals("2") ||
                command.equals("3") ||
                command.equals("4") ||
                command.equals("5") ||
                command.equals("6") ||
                command.equals("7") ||
                command.equals("8") ||
                command.equals("9") ||
                command.equals("^") ||
                command.equals("(") ||
                command.equals(")") ||
                command.equals(".") ||
                command.equals("+") ||
                command.equals("-") ||
                command.equals("*") ||
                command.equals("/"))
        {
            infix = infix.concat(command);
        }
        else if(command.equals("=")){
            calc.infixToPostfix(infix);
            ansDisplay();
        }
        else if(command.equals("C"))
            infix = "";
        calc.clear();
        redisplay();
    }

    /**
     * Update the interface display to show the current value of the
     * calculator.
     */
    private void redisplay()
    {
        display.setText(infix);
    }

    private void ansDisplay(){
        displayAns.setText(calc.ans);
    }

    /**
     * Toggle the info display in the calculator's status area between the
     * author and version information.
     */
    private void showInfo()
    {
        if(showingAuthor)
            status.setText(calc.getVersion());
        else
            status.setText(calc.getAuthor());

        showingAuthor = !showingAuthor;
    }
}