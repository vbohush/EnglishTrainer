package net.bohush.english;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileNotFoundException;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class English extends JApplet {

	private static final long serialVersionUID = 1L;

	public English() throws FileNotFoundException {
		Lesson lesson = new Lesson(1, false, false);
		setLayout(new BorderLayout(5, 5));
		JLabel jlblName = new JLabel(lesson.getLessonNumber() + ". " + lesson.getName());
		jlblName.setFont(new Font("Monospaced", Font.BOLD, 18));
		jlblName.setHorizontalAlignment(SwingConstants.CENTER);
		add(jlblName, BorderLayout.NORTH);
		
		JTextArea ftaText = new JTextArea(lesson.toString(), lesson.getNumberOfLines(), 120);
		ftaText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		ftaText.setEditable(false);
		ftaText.setLineWrap(true);
		ftaText.setWrapStyleWord(true);
		add(ftaText, BorderLayout.CENTER);
		
		
		JPanel jpAnswer = new JPanel(new BorderLayout(5, 5));
		JTextField jtfInputString = new JTextField("");
		jtfInputString.setFont(new Font("Monospaced", Font.PLAIN, 16));
		jpAnswer.add(jtfInputString, BorderLayout.CENTER);
		JButton jbtnOk = new JButton(" Ok ");
		jpAnswer.add(jbtnOk, BorderLayout.EAST);
		jtfInputString.requestFocusInWindow();
		add(jpAnswer, BorderLayout.SOUTH);
	}

	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame("English Trainer");
		JApplet applet = new English();
		frame.add(applet);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}