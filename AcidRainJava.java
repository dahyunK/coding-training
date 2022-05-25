import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.util.Random;

public class AcidRainJava implements ActionListener, KeyListener{
	ImageIcon icon;
	JFrame frame;
	JPanel board;
	JTextField field;
	JButton enter;
	JLabel info;
	JLabel[] wordlabel;
	int level =1 ,correct = 0, life = 3, speed;
	String[] word = {"String", "Scanner", "nextLine", "next", "nextInt", "nextDouble" , "if-else", "Switch", "while"
			, "do-while", "for", "break", "continue", "for-each", "Arrays.sort", "this", "super.print", "SetLocation"
			, "SetBounds", "SetSize", "SetIconImage", "SetTitle", "SetResizalbe", "Jpanel", "JLabel", "JTextField"
			, "JFormattedTextField", "JpasswordField", "JcomboBox", "JSpinner", "JButton", "JCheckBox", "JRadionButton"
			, "FlowLayout", "GridBagLayout", "GridLayout", "BorderLayout", "BoxLayout", "CardLayout", "SetLayout"};
	Random random;
	int i = 0;
	
	public AcidRainJava() {
		wordlabel = new JLabel[50];
		
		for(int i = 0; i < 50; i++) {
			wordlabel[i] = new JLabel("");
		}
		random = new Random();
		
		frame = new JFrame("AcidRainJava");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		URL imageURL = AcidRainJava.class.getClassLoader().getResource("background2.png");
		ImageIcon icon = new ImageIcon(imageURL);
		System.out.println(imageURL);
		
		board = new JPanel() {
			public void paintComponent(Graphics g) {
				
				g.drawImage(icon.getImage(),0,0, null);
				
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		board.setBounds(0, 0, 800, 630);
		board.setPreferredSize(new Dimension(800,700));
		board.setForeground(Color.GREEN);
		board.setLayout(null);
		
		field = new JTextField();
		field.setBounds(0, 670, 710, 30);
		Font f = field.getFont();
		field.setFont(new Font(f.getName(), Font.BOLD,20));
		
		enter = new JButton("입력");
		enter.setBounds(710, 670, 90, 30);

		info = new JLabel("레벨: " + level +"                                            점수: " + correct + "점                                               목숨 : " + life +"개");
		info.setBounds(0, 630, 800, 40);
		info.setOpaque(true);
		info.setBackground(Color.GREEN);
		Font font = info.getFont();
		info.setFont(new Font(font.getName(),Font.BOLD,20));
		
		frame.add(field);
		frame.add(enter);
		frame.add(info); 
		frame.getContentPane().add(board);
		frame.pack(); 
		frame.setVisible(true);
		
		field.addKeyListener(this);
		enter.addActionListener(this);
	}
	
	public void setinfo(int level, int correct, int life) {
		info.setText("레벨: " + level +"                                            점수: " + correct + "점                                               목숨 : " + life +"개");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(enter)) {
			if(!field.getText().toString().equals("")) {
				String answer = field.getText().toString();
				for(int idx = 0; idx < wordlabel.length; idx++) {
					if(answer.equals(wordlabel[idx].getText().toString() )) {
						if(wordlabel[idx].isVisible()) {
							correct += 1;
							setinfo(level,correct,life);
							if(correct > 10) levelup();
							wordlabel[idx].setVisible(false);
							break;
						}
						else {
							field.setText("");
						}
					}
				}
				field.setText("");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void levelup() {
		level += 1;
		correct = 0;
		life = 3;
		setinfo(level,correct,life);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!field.getText().toString().equals("")) {
				String answer = field.getText().toString();
				for(int idx = 0; idx < wordlabel.length; idx++) {
					if(answer.equals(wordlabel[idx].getText().toString())) { 
						if(wordlabel[idx].isVisible()) {
							correct += 1;
							setinfo(level,correct,life);
							if(correct > 10) levelup();
							wordlabel[idx].setVisible(false);
							break;
						}
					}
					else {
						field.setText("");
					}
					
				}
				field.setText("");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	class Rain_Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(life > 0) {
				try {
					if(i < wordlabel.length - 1) {
						wordlabel[i] = new JLabel(word[random.nextInt(word.length)]);
						wordlabel[i].setBounds(0,0,180 ,20);
						wordlabel[i].setFont(new Font(wordlabel[i].getName(), Font.BOLD,20));
						board.add(wordlabel[i]);
						wordlabel[i].setLocation(random.nextInt(765 - wordlabel[i].getText().length()), 1);
					}
						Thread rm = new Thread(new Rain_Move());
						rm.start();
						Thread.sleep(2000 - level * 300);
					
					if(i < wordlabel.length - 1) i++;
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				if(level == 5) {
					JOptionPane.showConfirmDialog(null, "게임 클리어!", "GAME CLEAR", JOptionPane.DEFAULT_OPTION);
					field.setEditable(false);
					try {
						new Thread(new Rain_Thread()).interrupt();
						new Thread(new Rain_Move()).wait();
						System.exit(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.exit(0);
					}
					
					System.exit(0);
				}
			}
		}
	} 
	
	class Rain_Move implements Runnable {
		@Override
		public void run() {
			int j;
			// TODO Auto-generated method stub
			for(j = 0; j <= i; j++) {
				if(wordlabel[j].isVisible()) {
					int px = wordlabel[j].getX();
					int py = wordlabel[j].getY();
					
					wordlabel[j].setLocation(px, py + 20); 
				}
				if(wordlabel[j].isVisible() && wordlabel[j].getY() > 600) { 
					wordlabel[j].setVisible(false);
					life--;
				}
				switch(life) {
				//목숨이 없다면
				case 0: 
					try {
						setinfo(level,correct,life);
						field.setText("GAME OVER!!");
						JOptionPane.showConfirmDialog(null, "GAME OVER!!","GAME OVER",JOptionPane.DEFAULT_OPTION);
						field.setEditable(false);
						new Thread(new Rain_Thread()).interrupt();
						new Thread(new Rain_Move()).interrupt();
						Thread.sleep(2000);
						System.exit(0);
						break;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				default:
					setinfo(level,correct,life);
					break;
				}
			}			
		}		
	}
	
	class PlayWav implements Runnable {
		@Override
		public void run() {
			File Wav = new File("sample2.wav");
			PlaySound(Wav);
			PlaySound(Wav);
			PlaySound(Wav);
			PlaySound(Wav);
		}
			static void PlaySound(File Sound) {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(Sound));
					clip.start();
					
					Thread.sleep(clip.getMicrosecondLength()/1000);
					
				}catch(Exception e) {
					
				}
		 }
	}
}

 