package net.bohush.english;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class English extends JApplet {
	private static final long serialVersionUID = 1L;
	private JTextField jtfInputString = new JTextField("");
	private Lesson lesson;
	private JTextArea ftaText;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;

	public English() throws FileNotFoundException {
		lesson = new Lesson(1, false, false);
		setLayout(new BorderLayout());
		JLabel jlblName = new JLabel(lesson.getLessonNumber() + ". " + lesson.getName());
		jlblName.setFont(new Font("Monospaced", Font.BOLD, 20));
		jlblName.setHorizontalAlignment(SwingConstants.CENTER);
		add(jlblName, BorderLayout.NORTH);
		
		ftaText = new JTextArea(lesson.getGuessedText(), lesson.getNumberOfLines(), 120);
		ftaText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		ftaText.setEditable(false);
		ftaText.setLineWrap(true);
		ftaText.setWrapStyleWord(true);
		add(ftaText, BorderLayout.CENTER);
		
		
		JPanel jpAnswer = new JPanel(new BorderLayout());
		jtfInputString.setFont(new Font("Monospaced", Font.BOLD, 18));
		jpAnswer.add(jtfInputString, BorderLayout.CENTER);
		JButton jbtnOk = new JButton(" Ok ");
		jpAnswer.add(jbtnOk, BorderLayout.EAST);
		
		JPanel jpStatistic = new JPanel(new GridLayout(1, 4, 5, 5));
		jLabel1 = new JLabel("Total Lines: " + lesson.getNumberOfLines());
		jLabel2 = new JLabel("Guessed Lines: " + lesson.getNumberOfGuessedLines());
		jLabel3 = new JLabel("Errors: " + lesson.getNumberOfErrors());
		jLabel4 = new JLabel("Used Tips: " + lesson.getNumberOfUsedTips());		
		jLabel1.setFont(new Font("Monospaced", Font.BOLD, 14));
		jLabel2.setFont(new Font("Monospaced", Font.BOLD, 14));
		jLabel3.setFont(new Font("Monospaced", Font.BOLD, 14));
		jLabel4.setFont(new Font("Monospaced", Font.BOLD, 14));
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		jpStatistic.add(jLabel1);
		jpStatistic.add(jLabel2);
		jpStatistic.add(jLabel3);
		jpStatistic.add(jLabel4);
		jpAnswer.add(jpStatistic, BorderLayout.NORTH);
		
		add(jpAnswer, BorderLayout.SOUTH);
		jtfInputString.requestFocusInWindow();
		
		ActionListener actionListener = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lesson.isValid(jtfInputString.getText())) {
					ftaText.setText(lesson.getGuessedText());
					jtfInputString.setText("");
					jtfInputString.requestFocus();
					if(lesson.isFinish()) {
						jtfInputString.setEditable(false);
					}
				} else {
					jtfInputString.requestFocus();
				}
				jLabel1.setText("Total lines: " + lesson.getNumberOfLines());
				jLabel2.setText("Guessed lines: " + lesson.getNumberOfGuessedLines());
				jLabel3.setText("Errors: " + lesson.getNumberOfErrors());
				jLabel4.setText("Used Tips: " + lesson.getNumberOfUsedTips());
			}
		};
		
		jbtnOk.addActionListener(actionListener);
		jtfInputString.addActionListener(actionListener);
		
		jLabel4.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				JOptionPane.showMessageDialog(null, lesson.getTip(), "Tip", JOptionPane.INFORMATION_MESSAGE);
				jLabel4.setText("Used Tips: " + lesson.getNumberOfUsedTips());
				jtfInputString.requestFocus();
			}
		});
	}

	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame("English Trainer");
		JApplet applet = new English();
		frame.add(applet);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}