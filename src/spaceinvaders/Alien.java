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

import general.PointUtils;
import general.PhysicsEntity;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Date: May 23, 2017
 * Author: Edward Xie 6
 * File: Alien.java
 * Purpose:
 */
public class Alien extends Shooter {
    
    public float standardVel=20;
    long timeFired=System.currentTimeMillis();//time last fired
    public static final Random rand=new Random();//random
    public static int avgFirePeriod=30000;//average time between firing on difficulty 1
    public static int difficulty=1;//difficulty
    public int firePeriod=(int)(avgFirePeriod+rand.nextGaussian()*avgFirePeriod/2)/difficulty;//time between firing

    public Alien(Point2D.Float where) {
        super(new byte[][]{
            {0,1,1,1,1,1,1,1,0},
            {1,1,1,1,1,1,1,1,1},
            {1,1,1,0,1,0,1,1,1},
            {1,1,1,1,0,1,1,1,1},
            {1,1,0,1,1,1,0,1,1},
            {1,1,1,0,0,0,1,1,1},
            {0,1,1,1,1,1,1,1,0},
            {0,0,1,1,1,1,1,0,0},
            {0,0,0,1,1,1,0,0,0}
        });
        pos=where;
        vel.setLocation(standardVel,1f);
    }

    @Override
    public void draw(Graphics2D g,int scale) {
    }
    
    /**
     * Fires either a bullet or a powerup
     * @return 
     */
    @Override
    public PhysicsEntity fire() {
        if(Math.random()>0.99) {
            return new Powerup((Point2D.Float)PointUtils.add(pos,new Point2D.Float(4,11.5f)),
                    new Point2D.Float(vel.x+((float)Math.random()-0.5f)*20f,10*(float)Math.log(difficulty+1f)));
        }
        return new Bullet((Point2D.Float)PointUtils.add(pos,new Point2D.Float(4,9.5f)),
                new Point2D.Float(vel.x+((float)Math.random()-0.5f)*20,10*(float)Math.log(difficulty+1f)));
    }

    /**
     * Bumps off of other aliens and walls, dies to anything else
     * @param who 
     */
    @Override
    public void collisionAction(ArrayList<PhysicsEntity> who) {
        PhysicsEntity ent=who.get(0);
        if(ent instanceof Wall||ent instanceof Alien) {
            ent.vel.setLocation(-ent.vel.x,ent.vel.y);
            vel.setLocation(-vel.x,vel.y);
        } else {
            alive=false;
        }
    }

    /**
     * Returns whether alien is ready to fire
     * @return 
     */
    @Override
    public boolean fireReady() {
        long timeNow=System.currentTimeMillis();
        if(timeNow-timeFired>firePeriod) {
            timeFired=timeNow;
            firePeriod=(int)(avgFirePeriod+rand.nextGaussian()*avgFirePeriod/2)/difficulty;//random fire rate
            return alive;
        }
        return false;
    }

}
