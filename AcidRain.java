import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AcidRain implements ActionListener, KeyListener{
	ImageIcon icon;
	JFrame frame;
	JPanel board;
	JTextField field;
	JButton enter;
	JLabel info;
	JLabel[] wordlabel;
	int level =1 ,correct = 0, life = 3, speed;
	String[] word = {"printf","scanf","fprintf","fscanf","sprintf","sscanf",
			"fgetc","fgets","fputc","fputs","getc","getchar","gets","putc","putchar"
			,"puts","ungetc","fread","fwrite", "fclose", "fflush", "fopen", "freopen", "setbuf", "setvbuf"
			, "fgetpos", "fseek", "fsetpos", "ftell", "rewind", "clearerr", "feof", "ferror", "perror", "remove"
			, "rename", "tempfile", "tempnam", "memchr", "strchr", "strcspn", "strpbrk", "strrchr", "strspn", "strstr", "strtok"
			, "memcpy", "memmove", "strcpy", "strncpy", "memcmp", "memcmp", "strcmp", "strcoll", "strncmp", "strxfrm", "strcat", "strncat"
			, "memset", "strerror", "strlen", "atof", "atoi", "atol", "strtod", "strtol", "strtoul", "rand", "srand", "malloc", "free"
			, "calloc", "realloc", "abort", "atexit", "exit", "getenv", "system", "bsearch", "qsort", "abs", "div", "labs", "ldiv", "mblen"
			, "mbtowc", "wctomb", "mbstowcs", "wcstombs", "clock", "difftime", "mktime", "time", "asctime", "ctime", "gmtime", "localtime", "strftime"};
	Random random;
	int i = 0;
	
	public AcidRain() {
		wordlabel = new JLabel[50];
		
		for(int i = 0; i < 50; i++) {
			wordlabel[i] = new JLabel("");
		}
		random = new Random();
		
		frame = new JFrame("AcidRain");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		icon = new ImageIcon("background.png");
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
		info.setBackground(Color.yellow);
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
			File Wav = new File("sample.wav");
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

 