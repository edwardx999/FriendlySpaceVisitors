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

import general.PhysicsEntity;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Date: May 31, 2017
 * Author: Edward Xie 6
 * File: GameProcessor.java
 * Purpose:
 */
public abstract class GameProcessor implements Runnable,KeyListener {

    public final GameData gd;

    //entities to process
    public volatile List<PhysicsEntity> aliens;
    public volatile List<PhysicsEntity> bullets;
    public volatile List<PhysicsEntity> ships;

    public volatile boolean gameRunning=true;

    public abstract void winner();

    public abstract void setupGame();

    public abstract void setupKeys();

    public abstract void processKeys();

    /**
     * Sets up game
     */
    protected void generalSetup() {
        setupKeys();
        gd.entities.clear();
        gd.entities.add(aliens=Collections.synchronizedList(new ArrayList<>(40)));
        gd.entities.add(bullets=Collections.synchronizedList(new ArrayList<>(500)));
        gd.entities.add(ships=Collections.synchronizedList(new ArrayList<>(1)));
        Alien.difficulty=gd.level;
    }

    public GameProcessor(GameData gd) {
        this.gd=gd;
        generalSetup();
        setupGame();
    }

    /**
     * Makes an entity fire
     * @param who 
     */
    public void fire(Shooter who) {
        if(who.fireReady()) {
            bullets.add(who.fire());
        }
    }

    /**
     * Resets the game
     */
    public void fullReset() {
        gd.reset();
        generalSetup();
        setupGame();
    }

    /**
     * Plays the game
     */
    @Override
    public void run() {
        while(gameRunning) {
            while(gd.gameOn) {
                for(int e=aliens.size()-1;e>=0;--e) {
                    try {
                        if(!aliens.get(e).alive) {
                            aliens.remove(e);
                        } else {
                            fire((Shooter)aliens.get(e));
                        }
                    } catch(IndexOutOfBoundsException aioobe) {
                        e=aliens.size()-1;
                    }
                }
                for(int e=bullets.size()-1;e>=0;--e) {
                    try {
                        if(!bullets.get(e).alive) {
                            bullets.remove(e);
                        }
                    } catch(IndexOutOfBoundsException aioobe) {
                        e=bullets.size()-1;
                    }
                }
                for(int e=ships.size()-1;e>=0;--e) {
                    try {
                        if(!ships.get(e).alive) {
                            ships.remove(e);
                        }
                    } catch(IndexOutOfBoundsException aioobe) {
                        e=ships.size()-1;
                    }
                }
                processKeys();
                winner();
            }
        }
    }
}
