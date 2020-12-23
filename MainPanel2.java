import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class MainPanel2 extends JPanel implements MouseMotionListener, Runnable
{
	// パネルサイズ
	public static final int WIDTH = 360;
	public static final int HEIGHT = 480;
	// ボールの最大数
	public static final int BALL_COUNT = 3;
    // ブロックの行数
    private static final int NUM_BLOCK_ROW = 10;
    // ブロックの列数
    private static final int NUM_BLOCK_COL = 7;
    // ブロック数
    private static final int NUM_BLOCK = NUM_BLOCK_ROW * NUM_BLOCK_COL;

	private Racket racket; // ラケット
	private Ball[] ball;   // ボール
    private Block[] block; // ブロック

	// アニメーション用スレッド
	Thread thread;
	
	public MainPanel2()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseMotionListener(this);
		
		// ラケットを生成
		racket = new Racket();
		// ボールを生成
		ball = new Ball[BALL_COUNT];
		for(int i=0; i<BALL_COUNT; i++){
			ball[i] = new Ball();
		}
		
		// ブロックを生成
        block = new Block[NUM_BLOCK];
        // ブロックを並べる
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
		//背景
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
	
	//マウスを動かした時呼び出される
	public void mouseMoved(MouseEvent e)
	{
		int x = e.getX(); //マウスのX座標
		racket.move(x); //ラケットを移動
		repaint();
	}
	
	//マウスをドラッグした時呼び出される
	public void mouseDragged(MouseEvent e){
	}
	
	/**
	 * ゲームループ
	 *
	 */
	public void run(){
		System.out.println("run");
		while(true){
			for(int i=0; i < BALL_COUNT; i++){
				// ボールの移動
				ball[i].move();
				
	            // ラケットとボールの衝突処理
	            int collidePos = racket.collideWith(ball[i]);
	            if (collidePos != Racket.NO_COLLISION) {  // ラケットに当たっていたら
	                // ラケットに当たったらボールの貫通属性を消す
	            	ball[i].setPierce(false);
	                // ボールの当たった位置に応じてボールの速度を変える
	                switch (collidePos) {
	                    case Racket.LEFT:
	                        // ラケットの左側に当たったときは左に反射するようにしたい
	                        // もしボールが右に進んでいたら反転して左へ
	                        // 左に進んでいたらそのまま
	                        if (ball[i].getVX() > 0) ball[i].boundX();
	                        ball[i].boundY();
	                        break;
	                    case Racket.RIGHT:
	                        // ラケットの右側に当たったときは右に反射するようにしたい
	                        // もしボールが左に進んでいたら反転して右へ
	                        // 右に進んでいたらそのまま
	                        if (ball[i].getVX() < 0) ball[i].boundX();
	                        ball[i].boundY();
	                        break;
		                case Racket.CENTER:
	                		// ラケットの中心に当たったら貫通属性をつける
	                		ball[i].setPierce(true);
	                		break;
	                }
	            }
				
	            // ブロックとボールの衝突処理
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
            // ブロックとボールの衝突処理
            for (int i = 0; i < NUM_BLOCK; i++) {
                // すでに消えているブロックは無視
                if (block[i].isDeleted())
                    continue;
                // ブロックの当たった位置を計算
                int collidePos = block[i].collideWith(ball);
                if (collidePos != Block.NO_COLLISION) { // ブロックに当たっていたら
                    block[i].delete();
                	if(ball.getPierce()){
                		break;
                	}
                    // ボールの当たった位置からボールの反射方向を計算
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
                    break; // 1回に壊せるブロックは1つ
                }
            }
	}

}
