package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ServerFrame extends JFrame {
	private JPanel contentPanel;
	private JButton startButton;
	private JTextArea textArea;
	
	private Server server;
	
	private int port;
	
	public ServerFrame(Server server, int port) {
		this.server = server;
		this.port = port;
	}
	
	// Server Setter
	public ServerFrame setServer(Server server) {
		this.server = server;
		return this;
	}
	
	// Initializer
	public ServerFrame init() {
		this.initGraphic();
		return this;
	}
	
	// Init Graphic
	private void initGraphic() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 400);
		
		/* ContentPanel 세팅 - 기본 프레임 */
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setContentPane(contentPanel);

		
		/* 콘솔 창 세팅 */
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setRows(5);
		textArea.setEditable(false); // 텍스트 입력 불가
		
		/* 스크롤 바 세팅 */
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 264, 254);
		scroll.setViewportView(textArea);
		contentPanel.add(scroll);

		/* Port번호 보여주는 라벨 */
		JLabel lblNewLabel = new JLabel("Port Number :" + this.port);
		lblNewLabel.setBounds(12, 24, 150, 37);
		contentPanel.add(lblNewLabel);

		/* 실행 버튼 */
		startButton = new JButton("서버 실행");
		System.out.println(this.server.toString());
		startButton.addActionListener(
				new ServerStartAction().setServer(this.server)
			); 
		// textField.addActionListener(action); //Port는 고정값이므로 필요X
		startButton.setBounds(0, 325, 264, 37);
		contentPanel.add(startButton);
		
	}
	
	// 내부클래스로 액션 이벤트 처리 클래스
	class ServerStartAction implements ActionListener {
		private Server server;
		
		public ServerStartAction setServer(Server server) {
			this.server = server;
			return this;
		}
		
		// 버튼 클릭하면 서버 시작
		public void actionPerformed(ActionEvent e) {
			this.server.start();
		}
	}
}
