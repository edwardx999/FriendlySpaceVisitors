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

/**
 * Date: May 23, 2017
 * Author: Edward Xie 6
 * File: Ship.java
 * Purpose:
 */
public class Ship2 extends Shooter {

    long timeFired=System.currentTimeMillis();//last time fired
    public double maxSpeed=100;//max speed
    int accelerating;//which direction (left, right) ship is accelerating
    public int firePeriod=300;
    private long powerupTime;//when powerup started
    public long powerupLength=10000;//length of powerup
    //function pointers would be convenient for powerups

    public Ship2(Point2D.Float where) {
        super(new byte[][]{
            {1,1,1,1,1,1,1},
            {0,1,1,1,1,1,0},
            {0,0,1,1,1,0,0},
            {0,0,0,1,0,0,0}
        });
        pos=where;
    }

    /**
     * Activates powerup
     */
    public void powerup() {
        powerupTime=System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics2D g,int scale) {
    }

    /**
     * Moves the ship
     *
     * @return
     */
    @Override
    public double nextIteration() {
        double timeDif=super.nextIteration();
        //noDrift(timeDif);
        easyDrift(timeDif);
        //realAccel(timeDif);
        return timeDif;
    }

    /**
     * Ship with drift
     *
     * @param timeDif
     */
    private void easyDrift(double timeDif) {
        if(accelerating!=0) {
            double dif=accelerating*maxSpeed-vel.x;
            //if(right) System.out.println(dif);
            vel.setLocation(vel.x+2*dif*timeDif,0);
            accelerating=0;
        } //else {
        vel.setLocation(vel.x/Math.pow(3,timeDif),0);

        //}
    }

    /**
     * Ship with no drift
     *
     * @param timeDif
     */
    private void noDrift(double timeDif) {
        if(accelerating!=0) {
            vel.setLocation(accelerating*maxSpeed,0);
            accelerating=0;
        } else {
            vel.setLocation(0,0);
        }
    }

    /**
     * Ship with realistic acceleration
     *
     * @param timeDif
     */
    private void realAccel(double timeDif) {
        if(accelerating!=0) {
            vel.setLocation(vel.x+accelerating*50*timeDif,0);
            accelerating=0;
        }
    }

    /**
     * Killed unless hit by powerup
     *
     * @param who
     */
    @Override
    public void collisionAction(ArrayList<PhysicsEntity> who) {
        if(!(who.get(0) instanceof Powerup)) {
            alive=false;
        }
    }

    /**
     * Returns whether ship is ready to fire
     *
     * @return
     */
    @Override
    public boolean fireReady() {
        long timeNow=System.currentTimeMillis();
        if(timeNow-timeFired>firePeriod/((System.currentTimeMillis()-powerupTime<powerupLength)?2:1)) {
            timeFired=timeNow;
            return alive;
        }
        return false;
    }

    /**
     * Activates horizontal acceleration
     *
     * @param direction
     */
    public void accelerateHoriz(int direction) {
        accelerating=(int)Math.signum(direction);
    }

    /**
     * Returns a bullet to be fired
     *
     * @return
     */
    @Override
    public Bullet fire() {
        return new Bullet(PointUtils.add(pos,new Point2D.Float(3,4)),
                new Point2D.Float(this.vel.x,40));
    }
}
