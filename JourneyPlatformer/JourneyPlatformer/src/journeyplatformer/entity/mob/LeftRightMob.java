/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.entity.mob;

import java.util.Random;
import journeyplatformer.JourneyPlatformer;
import journeyplatformer.collision.CollisionBox;
import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;
import journeyplatformer.level.Level;

/**
 * A mob that only moves left and right and can fall
 *
 * @author RAPHAEL
 */
public class LeftRightMob extends Mob {

    protected int anim = 0;
    private double accel = .2, maxspeed = 2;
    protected int leftHBOffset, topHBOffset;
    private Level level;
    public static int leftRightMobAmount = 0;

    public LeftRightMob(int x, int y, double dx, Level level, int leftHBOffset, int rightHBOffset, int topHBOffset, int bottomHBOffset) {
        this.x = x;
        this.y = y;
        this.sprite = Sprite.player_right;
        this.leftHBOffset = leftHBOffset;
        this.topHBOffset = topHBOffset;
        cb = new CollisionBox(x - leftHBOffset, y - topHBOffset, x + rightHBOffset, y + bottomHBOffset);
        dir = 1;
        this.dx = dx;
        dy = .1;
        this.level = level;
        mobs.add(this);
        leftRightMobAmount++;
    }

    @Override
    public void update() {

        cb.updateBox((int) x, (int) y);

        //animation value
        if (anim < 7500) anim++;
        else anim = 0;

        // dy setting
        if (dy == 0) {
            if (Math.random() > .9) {
                dy = -4.01;
                //snowballs!
                if (distanceFrom(JourneyPlatformer.player) < 1000 && Math.random() > .9) {
                    Projectile.projectiles.add(new Projectile(Sprite.snowball, Math.random() * 6 - 3, Math.random() * 6 - 3, (int) x, (int) y));
                }
            }
        }

        dy += .1;
        if (dy > 8) dy = 8;

        if (dx != 0 || dy != 0) {
            move(dx, dy);
            moving = true;
        } else {
            moving = false;
        }
        
        if (distanceFrom(JourneyPlatformer.player) < 32) {
            remove = true;
            leftRightMobAmount--;
        }
//        if (cb.collidesWith(JourneyPlatformer.player)) {
//            remove = true;
//            leftRightMobAmount--;
//        }

    }

    /**
     * moves entity's x and y values
     *
     * @param dx is the direction to move in x
     * @param dy is the direction to move in y
     */
    @Override
    public void move(double dx, double dy) {
        if (dy > 0) dir = 2;
        if (dy < 0) dir = 0;
        if (dx > 0) dir = 1;
        if (dx < 0) dir = 3;

        if (dx > 0) { // right
            if (!collision(dx, 1)) {
                x += dx;
            } else {
                this.dx = -dx;
            }
        } else if (dx < 0) { // left
            if (!collision(dx, 3)) {
                x += dx;
            } else {
                this.dx = -dx;
            }
        }
        if (dy > 0) { // down
            if (!collision(dy, 2)) {
                y += dy;
            } else {
                this.dy = 0;
            }
        } else if (dy < 0) { // up
            if (!collision(dy, 0)) {
                y += dy;
            } else {
                this.dy = 0;
            }
        }

    }

    @Override
    public void render(Screen screen) {
        if (dir == 0) {
            sprite = Sprite.player_forward;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_forward_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_forward_2;
                }
            }
        }
        if (dir == 1) {
            sprite = Sprite.player_left;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_left_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_left_2;
                }
            }
        }
        if (dir == 2) {
            sprite = Sprite.player_back;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_back_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_back_2;
                }
            }
        }
        if (dir == 3) {
            sprite = Sprite.player_right;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_right_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_right_2;
                }
            }
        }
        int halfSize = 16;
        screen.renderPlayer((int) (x - halfSize), (int) (y - halfSize), sprite);
    }

    /**
     * checks for collision with walls
     */
    @Override
    public boolean collision(double change, int dir) {
        if (dir == 1 || dir == 3) {
            // check outer boundaries
            int leftx = (int) (x + change - sprite.SIZE / 2 + 9);
            int rightx = (int) (x + change + sprite.SIZE / 2 - 9);
            if (leftx < 0 || rightx > level.levelwidth << 4) {
                return true;
            }

            //check level
            int levelx;
            if (dir == 1) {
                levelx = rightx / 16;
            } else {
                levelx = leftx / 16;
            }

            int topy = (int) (y - sprite.SIZE / 2 + 2);
            int bottomy = (int) (y + sprite.SIZE / 2 + 16);
            for (int levely = (topy + 1) / 16; levely < (bottomy - 1) / 16; levely++) {
                //if(levelx >= 0 && levely >= 0 && levelx < level.levelwidth && levely < level.levelheight) level.tiles[levelx + levely * level.levelwidth] = 5;
                if (level.getTile(levelx, levely).isSolid()) {
                    return true;
                }
            }
        }

        if (dir == 0 || dir == 2) {
            // check outer boundaries
            int topy = (int) (y + change - sprite.SIZE / 2 + 2);
            int bottomy = (int) (y + change + sprite.SIZE / 2);
            if (bottomy > level.levelheight << 4) {
                dy = 0;
                return true;
            }

            // check level
            int levely;
            if (dir == 0) {
                levely = (topy + 1) / 16;
            } else {
                levely = (bottomy - 1) / 16;
            }

            int leftx = (int) (x - sprite.SIZE / 2 + 9);
            int rightx = (int) (x + sprite.SIZE / 2 + 7);

            for (int levelx = leftx / 16; levelx < rightx / 16; levelx++) {

                //if(levelx >= 0 && levely >= 0 && levelx < level.levelwidth && levely < level.levelheight) level.tiles[levelx + levely * level.levelwidth] = 5;
                if (level.getTile(levelx, levely).isSolid()) {
                    //if(dir == 0) dy = -.09 ;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * checks for collision with player
     */
    protected boolean playerCollision(double change, int dir) {

        return false;
    }
}
