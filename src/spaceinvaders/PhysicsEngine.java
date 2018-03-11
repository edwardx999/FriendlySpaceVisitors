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
import java.util.List;

/**
 * Date: May 31, 2017
 * Author: Edward Xie 6
 * File: PhysicsEngine.java
 * Purpose:
 */
public class PhysicsEngine implements Runnable {

    public final GameData gd;
    public volatile boolean running=true;

    private long time=System.currentTimeMillis();
    private long minFramePer=1000/100;//in milliseconds

    public PhysicsEngine(GameData g) {
        gd=g;
    }

    /**
     * Does the physics movements repeatedly
     */
    @Override
    public void run() {
        long newTime;
        run:
        while(true) {
            while(gd.gameOn) {
                newTime=System.currentTimeMillis();
                if(newTime-time>minFramePer) {
                    time=newTime;
                    physCalc();
                }
            }
        }
    }

    /**
     * Does the physics movements
     */
    public void physCalc() {
        for(int q=0;q<gd.entities.size();++q) {
            try {
                List<PhysicsEntity> list=gd.entities.get(q);
                for(int i=list.size()-1;i>=0;--i) {
                    try {
                        gd.space.moveEntity(list.get(i));
                    } catch(IndexOutOfBoundsException err) {
                        i=list.size()-1;
                    }
                    if(!running) {
                        return;
                    }
                }
            } catch(IndexOutOfBoundsException err) {
            }
        }
    }
}
