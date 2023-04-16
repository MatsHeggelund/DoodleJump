import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
    int width = 80;
    int height = 10;
    int gravity, xvel, yvel, dy, points, jetpackTimer;
    Boolean springJumpFromBelowHalf, hasJetpack;
    GamePanel game;
    Rectangle rect;

    public Player(GamePanel gamePanel, int x, int y){
        this.game = gamePanel;
        this.points = 0;
        this.springJumpFromBelowHalf = false;
        this.hasJetpack = false;
        this.jetpackTimer = 0;

        //Hitbox variables
        this.rect = new Rectangle();
        this.rect.x = x;
        this.rect.y = y;
        this.rect.width = width;
        this.rect.height = height;

        //Physics variables
        this.gravity = 1;
        this.yvel = 0;
        this.xvel = 10;
    }

    public void update(){
        //move left
        if(this.game.keyH.leftPressed){
            this.rect.x -= this.xvel;
        }
        //move right
        if(this.game.keyH.rightPressed){
            this.rect.x += this.xvel;
        }

        //Apply gravity and move player
        this.yvel += this.gravity;
        this.rect.y += this.yvel;

        if(!this.hasJetpack){
            //Check if player intersects any platform
            for(int i = 0; i < this.game.platforms.length; i++){
                //if the player is falling
                if(this.rect.intersects(this.game.platforms[i].rect) && this.yvel > 0){
                    this.springJumpFromBelowHalf = false;
                    //if player is jumping on spring
                    if(this.game.platforms[i].id == 10){
                        this.jump(-40, false, i);
                    //red platforms
                    } else if(this.game.platforms[i].id == 1 || this.game.platforms[i].id == 11){
                        this.jump(-20, true, i);
                    //jetpack (black platform)
                    } else if(this.game.platforms[i].id == 20){
                        this.hasJetpack = true;
                    //normal platforms
                    } else{
                        this.jump(-20, false, i);
                    }
                }
            }
        }

        //if the player has a jetpack
        if(this.hasJetpack){
            //if the player has had the jetpack for less than 3 seconds
            if(this.jetpackTimer < game.FPS*3){
                //move all platforms
                for(int i = 0; i < this.game.platforms.length; i++){
                    this.game.platforms[i].yvel = 40;
                    this.game.platforms[i].gravity = 1;
                }
                //set constant speed (-1 to counter +1 gravity)
                this.dy = -1;
                this.jetpackTimer++; //increment jetpacktimer
                this.yvel = this.dy;
            } else{
                //if the player has had the jetpack for more than 3 seconds, remove the jetpack
                this.hasJetpack = false;
                this.jetpackTimer = 0;
                this.dy = -20;
                this.yvel = this.dy;
            }
        }

        //Quality of Gameplay stuff
        //if the player jumps on a spring, and is below the middle of the screen, move platforms
        if(this.springJumpFromBelowHalf && this.rect.y < 600){
            //move all platforms
            for(int i = 0; i < this.game.platforms.length; i++){
                this.game.platforms[i].yvel = 40;
                this.game.platforms[i].gravity = 1;
            }
            this.springJumpFromBelowHalf = false;
        }
    }

    public void jump(int id, Boolean breakPlatform, int platformIndex){
        //if the player is above the middle of the screen, move platforms
        if(this.rect.y < 400-10*id){
            this.dy = id/2;
            //move all platforms
            for(int i = 0; i < this.game.platforms.length; i++){
                this.game.platforms[i].yvel = -id;
                this.game.platforms[i].gravity = 1;
            }
        } else{
            //if the player is below the middle of the screen, but jumping on a spring (blue platform), move the platforms
            if(this.game.platforms[platformIndex].id == 10){
                this.springJumpFromBelowHalf = true;
                this.dy = -30;
            } else{
                //if the player is below the middle of the screen (ish), jump normally
                this.dy = id;
            }
        }
        //jump
        this.yvel = this.dy;
        if(breakPlatform){
            this.game.platforms[platformIndex].rect.x += this.game.screenWidth;
        }
    }

    //draw player
    public void draw(Graphics2D g2){
        g2.setColor(java.awt.Color.lightGray);
        g2.fillRect(this.rect.x, this.rect.y-80, this.rect.width, 80);

        g2.setColor(Color.black);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Points: " + Integer.toString(this.points), 20, 30);
    }
}
