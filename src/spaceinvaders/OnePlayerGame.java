/*
Copyright(C) 2017 Edward Xie

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package spaceinvaders;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Date: May 31, 2017
 * Author: Edward Xie 6
 * File: OnePlayerGame.java
 * Purpose:
 */
public class OnePlayerGame extends GameProcessor {

    private Ship myShip;//player ship
    AtomicBoolean[] keys;//left, right, space

    public OnePlayerGame(GameData gd) {
        super(gd);
    }
    
    /**
     * Maps keys to game actions
     */
    @Override
    public void processKeys() {
        try {
            if(keys[1].get()) {
                myShip.accelerateHoriz(1);
            } else if(keys[0].get()) {
                myShip.accelerateHoriz(-1);
            }
            if(keys[2].get()) {
                fire(myShip);
            }
        } catch(NullPointerException e) {
        }
    }

    /**
     * Sets up game for one player
     */
    @Override
    public void setupGame() {
        for(int i=0;i<keys.length;++i) {
            keys[i]=new AtomicBoolean(false);
        }
        myShip=new Ship(new Point2D.Float(gd.GAME_WIDTH/2-3,gd.GAME_HEIGHT-10));
        ships.add(myShip);
        int limit=gd.level<22?gd.level:22;
        for(int i=0;i<limit;++i) {
            aliens.add(new Alien(new Point2D.Float(i*11,10)));
        }
        gd.winner=null;
        gd.gameOn=true;
    }
    
    /**
     * Resets keys
     */
    @Override
    public void setupKeys() {
        keys=new AtomicBoolean[3];
        for(int i=0;i<keys.length;++i) {
            keys[i]=new AtomicBoolean(false);
        }
    }
    
    /**
     * Key actions
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                keys[0].set(true);
                keys[1].set(false);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                keys[1].set(true);
                keys[0].set(false);
                break;
            case KeyEvent.VK_SPACE:
                keys[2].set(true);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_COMMA:
                if(gd.level>1) {
                    --gd.level;
                    fullReset();
                }
                break;
            case KeyEvent.VK_PERIOD:
                ++gd.level;
                fullReset();
                break;
            case KeyEvent.VK_R: {
                if("You".equals(gd.winner))
                    ++gd.level;
                fullReset();
            }
        }
    }
    
    /**
     * Unpresses actions
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                keys[0].set(false);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                keys[1].set(false);
                break;
            case KeyEvent.VK_SPACE:
                keys[2].set(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Determines who is winner
     */
    @Override
    public void winner() {
        //gd.gameOn=false;
        if(gd.gameOn) {
            if(aliens.isEmpty()&&gd.gameOn) {
                gd.winner="You";
            } else if(!myShip.alive&&gd.gameOn) {
                gd.winner="Aliens";
            }
        }
    }
}
