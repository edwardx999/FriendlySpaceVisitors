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
 * Date: Jun 5, 2017
 * Author: Edward Xie 6
 * File: TwoPlayerGame.java
 * Purpose:
 */
public class TwoPlayerGame extends GameProcessor {

    AtomicBoolean[] keys;//a, d, left, right
    Ship ship1;
    Ship2 ship2;

    public TwoPlayerGame(GameData gd) {
        super(gd);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                keys[2].set(true);
                keys[3].set(false);
                break;
            case KeyEvent.VK_A:
                keys[0].set(true);
                keys[1].set(false);
                break;
            case KeyEvent.VK_RIGHT:
                keys[3].set(true);
                keys[2].set(false);
                break;
            case KeyEvent.VK_D:
                keys[1].set(true);
                keys[0].set(false);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_R: {
                fullReset();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                keys[2].set(false);
                break;
            case KeyEvent.VK_A:
                keys[0].set(false);
                break;
            case KeyEvent.VK_RIGHT:
                keys[3].set(false);
                break;
            case KeyEvent.VK_D:
                keys[1].set(false);
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void processKeys() {
        try {
            if(keys[1].get()) {
                ship1.accelerateHoriz(1);
            } else if(keys[0].get()) {
                ship1.accelerateHoriz(-1);
            }
            if(keys[2].get()) {
                ship2.accelerateHoriz(-1);
            } else if(keys[3].get()) {
                ship2.accelerateHoriz(1);
            }
        } catch(NullPointerException err) {
        }
        fire(ship1);
        fire(ship2);
        ship1.powerup();
        ship2.powerup();
    }

    @Override
    public void setupGame() {
        for(int i=0;i<keys.length;++i) {
            keys[i]=new AtomicBoolean(false);
        }
        ship1=new Ship(new Point2D.Float(gd.GAME_WIDTH/2-3,gd.GAME_HEIGHT-10));
        ships.add(ship1);
        ship2=new Ship2(new Point2D.Float(gd.GAME_WIDTH/2-3,10));
        ships.add(ship2);
        gd.winner=null;
        gd.gameOn=true;
    }

    @Override
    public void setupKeys() {
        keys=new AtomicBoolean[4];
        for(int i=0;i<keys.length;++i) {
            keys[i]=new AtomicBoolean(false);
        }
    }

    @Override
    public void winner() {
        if(!ship1.alive&&!ship2.alive)
            gd.winner="No one";
        else if(!ship1.alive)
            gd.winner="Player 2";
        else if(!ship2.alive)
            gd.winner="Player 1";
    }

}
