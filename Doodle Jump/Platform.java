import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Platform {
    int width = 120;
    int height = 20;
    int xvel, yvel, id;
    double gravity;
    GamePanel game;
    Rectangle rect;

    public Platform(GamePanel gamePanel, int xpos, int ypos){
        this.game = gamePanel;
        this.gravity = 0;

        this.rect = new Rectangle();
        this.rect.x = xpos;
        this.rect.y = ypos;
        this.rect.width = width;
        this.rect.height = height;
        this.id = 1 + (int)(Math.random() * 10);
    }

    public void update(){
        this.yvel -= this.gravity;
        this.rect.y += this.yvel;
        int Min = 0;
        int Max = this.game.screenWidth - 100;
        int Randint = Min + (int)(Math.random() * ((Max - Min) + 1));
        //If platform goes out of screen, move it to top of screen again
        if(this.rect.y > this.game.screenHeight){
            this.rect.y -= this.game.screenHeight;
            this.rect.x = Randint; 
            this.id = 1 + (int)(Math.random() * 20);
            this.game.player.points++;
        }
        //Stop platform movement
        if(this.yvel < 0){
            this.gravity = 0;
            this.yvel = 0;
        }
        //moving platforms
        if(this.id == 9 || this.id == 19){
            if(this.xvel == 0){
                this.xvel = 3;
            }
            this.rect.x += xvel;
            if(this.rect.x + this.rect.width > this.game.screenWidth){
                this.rect.x  = this.game.screenWidth - this.rect.width;
                this.xvel = -this.xvel;
            } else if(this.rect.x < 0){
                this.rect.x = 0;
                this.xvel = -this.xvel;
            }
        }

    }

    public void draw(Graphics2D g2){
        switch(this.id){
            case 1:
                g2.setColor(Color.red);
                break;
            case 11:
                g2.setColor(Color.red);
                break;
            case 9:
                g2.setColor(Color.green);
                break;
            case 19:
                g2.setColor(Color.green);
                break;
            case 10:
                g2.setColor(Color.blue);
                break;
            case 20:
                g2.setColor(Color.black);
                break;
            default:
                g2.setColor(Color.orange);
        }
        g2.fillRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
    }
}
