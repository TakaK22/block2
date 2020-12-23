import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class MainPanel2 extends JPanel implements MouseMotionListener, Runnable
{
	// �p�l���T�C�Y
	public static final int WIDTH = 360;
	public static final int HEIGHT = 480;
	// �{�[���̍ő吔
	public static final int BALL_COUNT = 3;
    // �u���b�N�̍s��
    private static final int NUM_BLOCK_ROW = 10;
    // �u���b�N�̗�
    private static final int NUM_BLOCK_COL = 7;
    // �u���b�N��
    private static final int NUM_BLOCK = NUM_BLOCK_ROW * NUM_BLOCK_COL;

	private Racket racket; // ���P�b�g
	private Ball[] ball;   // �{�[��
    private Block[] block; // �u���b�N

	// �A�j���[�V�����p�X���b�h
	Thread thread;
	
	public MainPanel2()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseMotionListener(this);
		
		// ���P�b�g�𐶐�
		racket = new Racket();
		// �{�[���𐶐�
		ball = new Ball[BALL_COUNT];
		for(int i=0; i<BALL_COUNT; i++){
			ball[i] = new Ball();
		}
		
		// �u���b�N�𐶐�
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
		for(int i = 0; i < BALL_COUNT; i++){
			if(ball[i] != null)
			ball[i].draw(g);
		}
		for(int i = 0; i < NUM_BLOCK; i++){
			if(!block[i].isDeleted()) block[i].draw(g);
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
			for(int i=0; i < BALL_COUNT; i++){
				// �{�[���̈ړ�
				ball[i].move();
				
	            // ���P�b�g�ƃ{�[���̏Փˏ���
	            int collidePos = racket.collideWith(ball[i]);
	            if (collidePos != Racket.NO_COLLISION) {  // ���P�b�g�ɓ������Ă�����
	                // ���P�b�g�ɓ���������{�[���̊ђʑ���������
	            	ball[i].setPierce(false);
	                // �{�[���̓��������ʒu�ɉ����ă{�[���̑��x��ς���
	                switch (collidePos) {
	                    case Racket.LEFT:
	                        // ���P�b�g�̍����ɓ��������Ƃ��͍��ɔ��˂���悤�ɂ�����
	                        // �����{�[�����E�ɐi��ł����甽�]���č���
	                        // ���ɐi��ł����炻�̂܂�
	                        if (ball[i].getVX() > 0) ball[i].boundX();
	                        ball[i].boundY();
	                        break;
	                    case Racket.RIGHT:
	                        // ���P�b�g�̉E���ɓ��������Ƃ��͉E�ɔ��˂���悤�ɂ�����
	                        // �����{�[�������ɐi��ł����甽�]���ĉE��
	                        // �E�ɐi��ł����炻�̂܂�
	                        if (ball[i].getVX() < 0) ball[i].boundX();
	                        ball[i].boundY();
	                        break;
		                case Racket.CENTER:
	                		// ���P�b�g�̒��S�ɓ���������ђʑ���������
	                		ball[i].setPierce(true);
	                		break;
	                }
	            }
				
	            // �u���b�N�ƃ{�[���̏Փˏ���
				blockChec(ball[i]);
			}// end of for
			
			repaint();
			
			try{
				Thread.sleep(20);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void blockChec(Ball ball){
            // �u���b�N�ƃ{�[���̏Փˏ���
            for (int i = 0; i < NUM_BLOCK; i++) {
                // ���łɏ����Ă���u���b�N�͖���
                if (block[i].isDeleted())
                    continue;
                // �u���b�N�̓��������ʒu���v�Z
                int collidePos = block[i].collideWith(ball);
                if (collidePos != Block.NO_COLLISION) { // �u���b�N�ɓ������Ă�����
                    block[i].delete();
                	if(ball.getPierce()){
                		break;
                	}
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
