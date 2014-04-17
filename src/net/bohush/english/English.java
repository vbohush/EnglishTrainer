package net.bohush.english;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class English extends JApplet {
	private static final long serialVersionUID = 1L;
	private JTextField jtfInputString = new JTextField("");
	private int currentLesson = 0;
	private Lesson[] lessons = new Lesson[Data.getNumberOfLessons()];
	private JTextArea ftaText;
	private JTextField jtfTip= new JTextField("");
	private JLabel jlblInformation;
	private JButton jbtnCheck = new JButton("Check");
	private JCheckBox jcbShowTips = new JCheckBox("Tips", true);
	private JButton jbtnReset = new JButton("Restart");
	private JComboBox<Lesson> jcbLesson;
	
	public English() {
		JPanel jpBorder = new JPanel(new BorderLayout(5, 5));
		jpBorder.setBorder(new TitledBorder("English Trainer"));
		JPanel jpMain = new JPanel(new BorderLayout(5, 5));
		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		for (int i = 0; i < lessons.length; i++) {
			lessons[i] = new Lesson(i);
		}			
		
		JPanel jpLessonSelect = new JPanel(new BorderLayout(5, 5));
		jcbLesson = new JComboBox<Lesson>(lessons);
		jcbLesson.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentLesson = jcbLesson.getSelectedIndex();
				restart();
			}
		});
		jcbLesson.setSelectedItem(lessons[currentLesson]);
		jcbLesson.setFont(new Font("Dialog", Font.BOLD, 16));
		jpLessonSelect.add(jcbLesson, BorderLayout.CENTER);
		JButton jbtnPrev = new JButton("Prev");
		JButton jbtnNext = new JButton("Next");
		jpLessonSelect.add(jbtnPrev, BorderLayout.WEST);
		jpLessonSelect.add(jbtnNext, BorderLayout.EAST);
		jpMain.add(jpLessonSelect, BorderLayout.NORTH);
		
		jbtnPrev.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentLesson > 0) {
					currentLesson--;
				} else {
					currentLesson = lessons.length - 1;
				}
				jcbLesson.setSelectedIndex(currentLesson);
				restart();
			}
		});
		
		jbtnNext.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentLesson < lessons.length - 1) {
					currentLesson++;
				} else {
					currentLesson = 0;
				}
				jcbLesson.setSelectedIndex(currentLesson);
				restart();
			}
		});
		
		ftaText = new JTextArea(lessons[currentLesson].getGuessedText(), 25, 60);
		ftaText.setFont(new Font("Dialog", Font.PLAIN, 16));
		ftaText.setEditable(false);
		ftaText.setLineWrap(true);
		ftaText.setWrapStyleWord(true);
		jpMain.add(ftaText, BorderLayout.CENTER);
		
		JPanel jpControl = new JPanel(new BorderLayout(5, 5));
		JPanel jpAnswer = new JPanel(new GridLayout(2, 1, 5, 5));
		JPanel jpConfig = new JPanel(new GridLayout(2, 2, 5, 5));

		Font inputFont = new Font("Monospaced", Font.PLAIN, 14);		
		jtfInputString.setFont(inputFont);
		jtfTip.setFont(inputFont);
		jtfTip.setEditable(false);
		jtfTip.setText(lessons[currentLesson].getTip());
		jpAnswer.add(jtfTip);
		jpAnswer.add(jtfInputString);
		jpControl.add(jpAnswer, BorderLayout.CENTER);
		
		jlblInformation = new JLabel(" " + lessons[currentLesson].getNumberOfGuessedLines() + " / " + lessons[currentLesson].getNumberOfLines() + " ");
		jlblInformation.setHorizontalAlignment(SwingConstants.CENTER);
		jpConfig.add(jlblInformation);
		jcbShowTips.setHorizontalAlignment(SwingConstants.CENTER);
		jpConfig.add(jcbShowTips);
		jpConfig.add(jbtnCheck);
		jpConfig.add(jbtnReset);	
		jpControl.add(jpConfig, BorderLayout.EAST);
		
		jpMain.add(jpControl, BorderLayout.SOUTH);
		jtfInputString.requestFocusInWindow();
		
		jpBorder.add(jpMain, BorderLayout.CENTER);
		add(jpBorder);

		
		
		ActionListener actionListener = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lessons[currentLesson].isValid(jtfInputString.getText()) || jtfInputString.getText().equals("")) {
					if(jtfInputString.getText().equals("")) {
						lessons[currentLesson].skip();
					}
					ftaText.setText(lessons[currentLesson].getGuessedText());
					jtfInputString.setText("");
					if(lessons[currentLesson].isFinish()) {
						jtfInputString.setEditable(false);
						jbtnCheck.setEnabled(false);
						jcbShowTips.setEnabled(false);
					}
					jtfInputString.requestFocus();
					if(jcbShowTips.isSelected()) {
						jtfTip.setText(lessons[currentLesson].getTip());
					} else {
						jtfTip.setText("");
					}
					jlblInformation.setText(" " + lessons[currentLesson].getNumberOfGuessedLines() + " / " + lessons[currentLesson].getNumberOfLines() + " ");
				}
			}
		};		
		jbtnCheck.addActionListener(actionListener);
		jtfInputString.addActionListener(actionListener);
		
		jcbShowTips.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jcbShowTips.isSelected()) {
					jtfTip.setText(lessons[currentLesson].getTip());
				} else {
					jtfTip.setText("");
				}
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
		jlblInformation.setText(" " + lessons[currentLesson].getNumberOfGuessedLines() + " / " + lessons[currentLesson].getNumberOfLines() + " ");
		ftaText.setText(lessons[currentLesson].getGuessedText());
		jtfInputString.setText("");
		jtfInputString.setEditable(true);
		jbtnCheck.setEnabled(true);
		jcbShowTips.setEnabled(true);
		if(jcbShowTips.isSelected()) {
			jtfTip.setText(lessons[currentLesson].getTip());
		} else {
			jtfTip.setText("");
		}
		jtfInputString.requestFocus();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("English Trainer");
		JApplet applet = new English();
		frame.add(applet);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}