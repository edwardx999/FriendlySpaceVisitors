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
package general;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import static general.PointUtils.*;

/**
 * Date: May 23, 2017
 * Author: Edward Xie 6
 * File: Entity.java
 * Purpose:
 */
public abstract class PhysicsEntity {

    public final byte[][] collisionModel;//WE NEED CONST ARRAYS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public volatile float mass=1;
    public volatile Point2D.Float vel=new Point2D.Float(0,0);
    public volatile Point2D.Float accelThisTick=new Point2D.Float(0,0);
    public volatile Point2D.Float pos=new Point2D.Float(0,0);
    public volatile boolean alive=true;
    long time=System.nanoTime();

    /**
     * Applies a Point2D force vector between target and doer
     *
     * @param target
     * @param force
     * @param doer
     */
    public static void applyForce(PhysicsEntity target,Point2D.Float force,PhysicsEntity doer) {
        target.addAccel(mult(force,1/target.mass));
        doer.addAccel(mult(force,-1/target.mass));
    }

    /**
     * Adds acceleration to the target
     *
     * @param accel
     */
    public void addAccel(Point2D.Float accel) {
        accelThisTick=add(accelThisTick,accel);
    }

    /**
     * Builds model with a certain collision model
     *
     * @param sprite
     */
    public PhysicsEntity(byte[][] sprite) {
        this.collisionModel=sprite;
    }

    /**
     * Moves the entity based on its current velocity
     *
     * @return the time passed in seconds
     */
    public double nextIteration() {
        double timeDif=(-time+(time=System.nanoTime()))/1e9;
        //System.out.println(this.getClass()+" "+alive);
        vel=add(vel,mult(accelThisTick,(float)timeDif/2));
        pos=add(pos,mult(vel,(float)timeDif));//Euler's method
        vel=add(vel,mult(accelThisTick,(float)timeDif/2));
        //System.out.println(timeDif);
        return timeDif;
    }

    /**
     * Actions to be done upon collision with entities
     * @param who 
     */
    public abstract void collisionAction(ArrayList<PhysicsEntity> who);

    public void collisionAction(PhysicsEntity who) {
        ArrayList<PhysicsEntity> w=new ArrayList<>(1);
        w.add(who);
        collisionAction(w);
    }
    
    public abstract void draw(Graphics2D g,int scale);

    /**
     * Kills Entity
     */
    public void kill() {
        alive=false;
    }
}
