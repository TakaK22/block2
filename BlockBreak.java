import java.awt.*;
import javax.swing.*;

public class BlockBreak extends JFrame
{
	public BlockBreak(){
		// タイトルを設定
		setTitle("BlockBreak");
		
		// メインパネルを作成してフレームに追加
		MainPanel2 panel = new MainPanel2();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		
		// パネルサイズに合わせてフレームサイズを自動調整
		pack();
	}
	
	public static void main(String args[])
	{
		BlockBreak frame = new BlockBreak();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
