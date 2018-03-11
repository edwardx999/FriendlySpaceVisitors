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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: May 23, 2017
 * Author: Edward Xie 6
 * File: GameData.java
 * Purpose: Used to store game data
 */
public class GameData implements Runnable {

    //Where game data is stored
    public final int GAME_WIDTH=250, GAME_HEIGHT=120;

    public volatile List<List<PhysicsEntity>> entities;
    public volatile ArraySpaceTime space;

    public volatile int level=30;

    public volatile String winner=null;
    public volatile boolean gameOn=true;

    public void reset() {
        winner=null;
        gameOn=false;
        entities=Collections.synchronizedList(new ArrayList<>());
        space.clear();
    }

    public GameData() {
        space=new ArraySpaceTime(GAME_WIDTH,GAME_HEIGHT,5);
        reset();
    }
    public void removeDead(){
        for(int j=0;j<entities.size();++j) {
            List<PhysicsEntity> list=entities.get(j);
            for(int i=list.size()-1;i>=0;--i) {
                if(!list.get(i).alive) {
                    list.remove(i);
                }
            }
        }
    }
    @Override
    public void run() {//cleans out dead entities
        while(true) {
            removeDead();
        }
    }

}
