import java.awt.*;
import javax.swing.*;

public class BlockBreak extends JFrame
{
	public BlockBreak(){
		// �^�C�g����ݒ�
		setTitle("BlockBreak");
		
		// ���C���p�l�����쐬���ăt���[���ɒǉ�
		MainPanel2 panel = new MainPanel2();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		
		// �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y����������
		pack();
	}
	
	public static void main(String args[])
	{
		BlockBreak frame = new BlockBreak();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
