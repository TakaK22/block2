import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import javax.swing.*;

public class Ball {
    // 重力
    public static final double GRAVITY = 0.1;
    // サイズ
    private static final int SIZE = 11;
    // 位置(ボールを囲む短形の左上隅)
    private int x, y;
    // 速度
    private double vx, vy;

    // ボールイメージ
    private Image ballImage;

    // 貫通属性
    private boolean pierce;
    // 乱数発生器
    private Random rand;

    public Ball() {
        loadImage();
        rand = new Random(System.currentTimeMillis());

        // 位置を初期化
        x = rand.nextInt(MainPanel.WIDTH - SIZE);
        y = 0;

        // 速度を初期化
        vx = 5;
        vy = 0;

        pierce = false;
    }

    /**
     * ボールを描画
     *
     * @param g
     */
    public void draw(Graphics g) {
        // g.setColor(Color.YELLOW);
        // g.fillOval(x, y, SIZE, SIZE);
        int ix, iy;
        if (pierce) {
            ix = SIZE;
        } else {
            ix = 0;
        }
        iy = 0;
        g.drawImage(ballImage, x, y, x + SIZE, y + SIZE, ix, iy, ix + SIZE, SIZE, null);
    }

    /**
     * イメージをロードする
     */
    private void loadImage() {
        // イメージを読み込む
        ImageIcon icon = new ImageIcon(getClass().getResource("image/ball1.png"));
        ballImage = icon.getImage();

        // 幅と高さをセットする
        // SIZE = icon.getIconWidth();
    }

    /**
     * ボールの移動
     *
     */
    public void move() {
        vy += GRAVITY;

        x += vx;
        y += vy;

        // 左右の壁にぶつかった場合にバウンド
        if (x < 0 || x > MainPanel.WIDTH - SIZE) {
            vx = -vx;
        }
        // 天井にぶつかった場合にバウンド
        if (y < 0) {
            vy = -vy;
        }
        if (y > MainPanel.HEIGHT - SIZE) {
            vy = -vy;
        }
    }

    /**
     * X方向のバウンド
     */
    public void boundX() {
        vx = -vx;
    }

    /**
     * Y方向のバウンド
     */
    public void boundY() {
        vy = -vy;
    }

    /**
     * 斜めにバウンド
     */
    public void boundXY() {
        vx = -vx;
        vy = -vy;
    }

    public int getSize() {
        return SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getVX() {
        return vx;
    }

    public double getVY() {
        return vy;
    }

    public void setPierce(boolean bl) {
        pierce = bl;
    }

    public boolean getPierce() {
        return pierce;
    }
}
