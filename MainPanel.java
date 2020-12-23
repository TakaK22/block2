import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements MouseMotionListener, Runnable
{
	// �p�l���T�C�Y
	public static final int WIDTH = 360;
	public static final int HEIGHT = 480;
	
	private Racket racket; //���P�b�g
	private Ball ball;
	
    // �u���b�N�̍s��
    private static final int NUM_BLOCK_ROW = 10;
    // �u���b�N�̗�
    private static final int NUM_BLOCK_COL = 7;
    // �u���b�N��
    private static final int NUM_BLOCK = NUM_BLOCK_ROW * NUM_BLOCK_COL;

    private Block[] block; // �u���b�N

	// �A�j���[�V�����p�X���b�h
	Thread thread;
	public MainPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseMotionListener(this);
		
		// ���P�b�g�𐶐�
		racket = new Racket();
		// �{�[���𐶐�
		ball = new Ball();
		
        block = new Block[NUM_BLOCK];

        // �u���b�N����ׂ�
        for (int i = 0; i < NUM_BLOCK_ROW; i++) {
            for (int j = 0; j < NUM_BLOCK_COL; j++) {
                int x = j * Block.WIDTH + Block.WIDTH;
                int y = i * Block.HEIGHT + Block.HEIGHT;
                block[i * NUM_BLOCK_COL + j] = new Block(x, y);
            }
        }

		thread = new Thread(this);
		thread.start();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//�w�i
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		racket.draw(g);
		ball.draw(g);
		for(int i=0; i < NUM_BLOCK; i++){
			if(!block[i].isDeleted())
				block[i].draw(g);
		}
	}
	
	//�}�E�X�𓮂��������Ăяo�����
	public void mouseMoved(MouseEvent e)
	{
		int x = e.getX(); //�}�E�X��X���W
		racket.move(x); //���P�b�g���ړ�
		repaint();
	}
	
	//�}�E�X���h���b�O�������Ăяo�����
	public void mouseDragged(MouseEvent e){
	}
	
	/**
	 * �Q�[�����[�v
	 *
	 */
	public void run(){
		System.out.println("run");
		while(true){
			// �{�[���̈ړ�
			ball.move();
			
			// �������P�b�g�ƏՓ˂�����{�[�����o�E���h
			if(racket.collideWith(ball)){
				ball.boundY();
			}
			
            // �u���b�N�ƃ{�[���̏Փˏ���
			blockChec();
			
			repaint();
			
			try{
				Thread.sleep(20);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void blockChec(){
            // �u���b�N�ƃ{�[���̏Փˏ���
            for (int i = 0; i < NUM_BLOCK; i++) {
                // ���łɏ����Ă���u���b�N�͖���
                if (block[i].isDeleted())
                    continue;
                // �u���b�N�̓��������ʒu���v�Z
                int collidePos = block[i].collideWith(ball);
                if (collidePos != Block.NO_COLLISION) { // �u���b�N�ɓ������Ă�����
                    block[i].delete();
                    // �{�[���̓��������ʒu����{�[���̔��˕������v�Z
                    switch (collidePos) {
                        case Block.DOWN :
                        case Block.UP :
                            ball.boundY();
                            break;
                        case Block.LEFT :
                        case Block.RIGHT :
                            ball.boundX();
                            break;
                        case Block.UP_LEFT :
                        case Block.UP_RIGHT :
                        case Block.DOWN_LEFT :
                        case Block.DOWN_RIGHT :
                            ball.boundXY();
                            break;
                    }
                    break; // 1��ɉ󂹂�u���b�N��1��
                }
            }
	}

}
