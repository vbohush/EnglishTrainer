package net.bohush.english;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
	private int numberOfLessons = 3;
	private int currentLesson = 0;
	private Lesson[] lessons = new Lesson[numberOfLessons];
	private JTextArea ftaText;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JButton jbtnOk = new JButton("Ok");
	private JButton jbtnTip = new JButton("Get Tip");
	private JButton jbtnReset = new JButton("Restart");
	private JComboBox<Lesson> jcbLesson;
	private JCheckBox jcbCaseSensitive = new JCheckBox("Case Sensitive", false);
	private JCheckBox jcbStrongCheck = new JCheckBox("Strong Check", false);
	
	public English() throws FileNotFoundException {
		for (int i = 0; i < lessons.length; i++) {
			lessons[i] = new Lesson(i + 1, false, false);	
		}			
		setLayout(new BorderLayout());
		
		jcbLesson = new JComboBox<>(lessons);
		jcbLesson.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentLesson = jcbLesson.getSelectedIndex();
				restart();
				lessons[currentLesson].setCaseSensitive(jcbCaseSensitive.isSelected());
				lessons[currentLesson].setStrongCheck(jcbStrongCheck.isSelected());
			}
		});
		jcbLesson.setSelectedItem(lessons[currentLesson]);
		jcbLesson.setFont(new Font("Dialog", Font.PLAIN, 20));
		JPanel jpLessonSelector = new JPanel(new GridLayout(1, 2));
		jpLessonSelector.add(jcbLesson);
		jcbCaseSensitive.setHorizontalAlignment(SwingConstants.CENTER);
		jcbStrongCheck.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel jpCheckBox = new JPanel(new GridLayout(1, 2));
		jpCheckBox.add(jcbCaseSensitive);
		jpCheckBox.add(jcbStrongCheck);
		jpLessonSelector.add(jpCheckBox);		
		add(jpLessonSelector, BorderLayout.NORTH);
		
		jcbCaseSensitive.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				lessons[currentLesson].setCaseSensitive(jcbCaseSensitive.isSelected());
			}
		});
		
		jcbStrongCheck.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				lessons[currentLesson].setStrongCheck(jcbStrongCheck.isSelected());
			}
		});
		
		ftaText = new JTextArea(lessons[currentLesson].getGuessedText(), lessons[currentLesson].getNumberOfLines(), 60);
		ftaText.setFont(new Font("Dialog", Font.PLAIN, 16));
		ftaText.setEditable(false);
		ftaText.setLineWrap(true);
		ftaText.setWrapStyleWord(true);
		add(ftaText, BorderLayout.CENTER);
		
		JPanel jpAnswer = new JPanel(new BorderLayout(2, 2));
		jtfInputString.setFont(new Font("Dialog", Font.PLAIN, 16));
		jpAnswer.add(jtfInputString, BorderLayout.CENTER);
		JPanel jpControl = new JPanel(new GridLayout(1, 3, 2, 2));
		Font buttonFont = new Font("Dialog", Font.BOLD, 12); 
		jbtnOk.setFont(buttonFont);
		jpControl.add(jbtnOk);		
		jbtnTip.setFont(buttonFont);
		jpControl.add(jbtnTip);		
		jbtnReset.setFont(buttonFont);
		jpControl.add(jbtnReset);
		jpAnswer.add(jpControl, BorderLayout.EAST);
		
		JPanel jpStatistic = new JPanel(new GridLayout(1, 4, 5, 5));
		jLabel1 = new JLabel("Total Lines: " + lessons[currentLesson].getNumberOfLines());
		jLabel2 = new JLabel("Guessed Lines: " + lessons[currentLesson].getNumberOfGuessedLines());
		jLabel3 = new JLabel("Errors: " + lessons[currentLesson].getNumberOfErrors());
		jLabel4 = new JLabel("Used Tips: " + lessons[currentLesson].getNumberOfUsedTips());	
		Font labelFont = new Font("Dialog", Font.PLAIN, 14); 
		jLabel1.setFont(labelFont);
		jLabel2.setFont(labelFont);
		jLabel3.setFont(labelFont);
		jLabel4.setFont(labelFont);
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
				if(lessons[currentLesson].isValid(jtfInputString.getText())) {
					ftaText.setText(lessons[currentLesson].getGuessedText());
					jtfInputString.setText("");
					if(lessons[currentLesson].isFinish()) {
						jtfInputString.setEditable(false);
						jbtnOk.setEnabled(false);
						jbtnTip.setEnabled(false);
					}
				}
				jtfInputString.requestFocus();
				jLabel1.setText("Total lines: " + lessons[currentLesson].getNumberOfLines());
				jLabel2.setText("Guessed lines: " + lessons[currentLesson].getNumberOfGuessedLines());
				jLabel3.setText("Errors: " + lessons[currentLesson].getNumberOfErrors());
				jLabel4.setText("Used Tips: " + lessons[currentLesson].getNumberOfUsedTips());
			}
		};
		
		jbtnOk.addActionListener(actionListener);
		jtfInputString.addActionListener(actionListener);
		
		jbtnTip.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, lessons[currentLesson].getTip(), "Tip", JOptionPane.INFORMATION_MESSAGE);
				jLabel4.setText("Used Tips: " + lessons[currentLesson].getNumberOfUsedTips());
				jtfInputString.requestFocus();
			}
		});
		
		jbtnReset.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
	}
	
	private void restart() {
		lessons[currentLesson].reset();
		jLabel1.setText("Total lines: " + lessons[currentLesson].getNumberOfLines());
		jLabel2.setText("Guessed lines: " + lessons[currentLesson].getNumberOfGuessedLines());
		jLabel3.setText("Errors: " + lessons[currentLesson].getNumberOfErrors());
		jLabel4.setText("Used Tips: " + lessons[currentLesson].getNumberOfUsedTips());
		ftaText.setText(lessons[currentLesson].getGuessedText());
		jtfInputString.setText("");
		jtfInputString.setEditable(true);
		jbtnOk.setEnabled(true);
		jbtnTip.setEnabled(true);
		jtfInputString.requestFocus();
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