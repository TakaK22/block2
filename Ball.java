import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import javax.swing.*;

public class Ball
{
	// �d��
	public static final double GRAVITY = 0.1;
	// �T�C�Y
	private static final int SIZE = 11;
	// �ʒu(�{�[�����͂ޒZ�`�̍����)
	private int x, y;
	// ���x
	private double vx, vy;
	
	// �{�[���C���[�W
	private Image ballImage;

	// �ђʑ���
	private boolean pierce;
	// ����������
	private Random rand;
	
	public Ball(){
		loadImage();
		rand = new Random(System.currentTimeMillis());
		
		// �ʒu��������
		x = rand.nextInt(MainPanel.WIDTH - SIZE);
		y = 0;
		
		// ���x��������
		vx = 5;
		vy = 0;
		
		pierce = false;
	}
	
	/**
	 * �{�[����`��
	 *
	 * @param g
	 */
	public void draw(Graphics g){
//		g.setColor(Color.YELLOW);
//		g.fillOval(x, y, SIZE, SIZE);
		int ix, iy;
		if(pierce){
			ix = SIZE;
		}
		else{
			ix = 0;
		}
		iy = 0;
		g.drawImage(ballImage, x, y, x + SIZE, y + SIZE,
							ix, iy, ix + SIZE, SIZE, null);
	}
	
	/**
	 * �C���[�W�����[�h����
	 */
	private void loadImage(){
		// �C���[�W��ǂݍ���
		ImageIcon icon = new ImageIcon(getClass().getResource("image/ball1.png"));
		ballImage = icon.getImage();
		
		// ���ƍ������Z�b�g����
//		SIZE = icon.getIconWidth();
	}
	
	/**
	 * �{�[���̈ړ�
	 *
	 */
	public void move(){
		vy += GRAVITY;
		
		x += vx;
		y += vy;
		
		// ���E�̕ǂɂԂ������ꍇ�Ƀo�E���h
		if(x < 0 || x > MainPanel.WIDTH - SIZE){
			vx = -vx;
		}
		// �V��ɂԂ������ꍇ�Ƀo�E���h
		if(y < 0){
			vy = -vy;
		}
		if(y > MainPanel.HEIGHT - SIZE){
			vy = -vy;
		}
	}
	
	/**
	 * X�����̃o�E���h
	 */
	public void boundX(){
		vx = -vx;
	}
	/**
	 * Y�����̃o�E���h
	 */
	public void boundY(){
		vy = -vy;
	}
	/**
	 * �΂߂Ƀo�E���h
	 */
	public void boundXY(){
		vx = -vx;
		vy = -vy;
	}
	
	
	public int getSize(){
		return SIZE;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public double getVX(){
		return vx;
	}
	public double getVY(){
		return vy;
	}
	public void setPierce(boolean bl){
		pierce = bl;
	}
	public boolean getPierce(){
		return pierce;
	}
}
