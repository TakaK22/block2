import java.awt.*;

public class Racket
{
	// ラケットのサイズ
	public static final int WIDTH = 80;
	public static final int HEIGHT = 5;
	public static final int CENTER_SIZE = 10;
	// ボールの当たり位置
    public static final int NO_COLLISION = 0;  // 未衝突
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
	public static final int CENTER = 3;

	//ラケットの中心位置
	private int centerPos;
	
	public Racket()
	{
		//ラケットの位置を画面の真ん中で初期化
		centerPos = MainPanel.WIDTH / 2;
	}
	
	/**
	 * ラケットの描画
	 *
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(centerPos - WIDTH / 2, MainPanel.HEIGHT - HEIGHT, WIDTH, HEIGHT);
	}
	
	
	/**
	 * ラケットの移動
	 *
	 * @param pos 移動先座標
	 */
	public void move(int pos){
		centerPos = pos;
		
		// ラケットが画面端から飛び出さないようにする
		if(centerPos < WIDTH / 2) { // 左端
			centerPos = WIDTH / 2;
		}
		else if(centerPos > MainPanel.WIDTH - WIDTH / 2){ // 右端
			centerPos = MainPanel.WIDTH - WIDTH / 2;
		}
	}
	
	/**
	 * ボールに当たったらtrueを返す
	 *
	 * @param ball ボール
	 * @return ボールに当たったらtrue
	 */
	public int collideWith(Ball ball){
        // ラケットの矩形
        Rectangle racketRectLeft = new Rectangle(
                centerPos - WIDTH / 2, MainPanel.HEIGHT - HEIGHT,
        		(WIDTH - CENTER_SIZE) / 2, HEIGHT);
        Rectangle racketRectRight = new Rectangle(
                centerPos + CENTER_SIZE / 2, MainPanel.HEIGHT - HEIGHT,
                WIDTH / 2, HEIGHT);
        Rectangle racketRectCenter = new Rectangle(
                centerPos - CENTER_SIZE / 2, MainPanel.HEIGHT - HEIGHT,
                centerPos + CENTER_SIZE / 2, HEIGHT);

        // ボールの矩形
        Rectangle ballRect = new Rectangle(
                ball.getX(), ball.getY(),
                ball.getSize(), ball.getSize());

        // ラケットとボールの矩形領域が重なったら当たっている
        if (racketRectLeft.intersects(ballRect)) {
            return LEFT;
        }
		else if (racketRectRight.intersects(ballRect)) {
            return RIGHT;
        }
		else if(racketRectCenter.intersects(ballRect)){
			return CENTER;
		}

        return NO_COLLISION;

	}
}
