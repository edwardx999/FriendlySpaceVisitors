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

import general.CollisionSpaceTime;
import general.PhysicsEntity;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: May 23, 2017
 * Author: Edward Xie 6
 * File: CollisionSpace.java
 * Purpose:
 */
public class ArraySpaceTime implements CollisionSpaceTime {

    public PhysicsEntity[][] space;//please don't edit WE NEED CONST ARRAYS
    public final int precision;//how many collision test units per model unit
    private volatile boolean readable=true;

    public ArraySpaceTime(int width,int height,int scale) {
        if(scale<1) throw new IllegalArgumentException();
        this.precision=scale;
        space=new PhysicsEntity[scale*height][scale*width];
    }

    @Override
    public void clear() {
        readable=false;
        space=new PhysicsEntity[space.length][space[0].length];
        readable=true;
    }

    @Override
    public Dimension getDim() {
        return new Dimension(space[0].length/precision,space.length/precision);
    }

    @Override
    public Point2D.Float globalForce(PhysicsEntity ent) {
        return new Point2D.Float(0,0);
    }

    /**
     * Draws the entity and does any required action for collided entity
     *
     * @param ent
     */
    @Override
    public void moveEntity(PhysicsEntity ent) {
        if(!readable) return;
        if(!ent.alive) return;
        ArrayList<PhysicsEntity> collided;
        removeEntity(ent);
        ent.nextIteration();
        collided=addEntity(ent);
        for(PhysicsEntity col:collided) {
            col.collisionAction(ent);
            if(!col.alive) removeEntity(col);
        }
        if(!collided.isEmpty())
            ent.collisionAction(collided);
        if(!ent.alive)
            removeEntity(ent);
    }

    /**
     * Removes an entity from the SpaceTime
     * @param ent
     */
    @Override
    public void removeEntity(PhysicsEntity ent) {
        int x=(int)Math.round(ent.pos.x*precision);
        int y=(int)Math.round(ent.pos.y*precision);
        for(int dy=0;dy<ent.collisionModel.length*precision;++dy)
            for(int dx=0;dx<ent.collisionModel[dy/precision].length*precision;++dx)
                if(inRange(x+dx,y+dy)) {
                    space[y+dy][x+dx]=null;
                }
    }

    /**
     * Adds an entity to the collision space and returns any collisions
     *
     * @param ent
     * @return
     */
    @Override
    public ArrayList<PhysicsEntity> addEntity(PhysicsEntity ent) {
        boolean allOut=true;
        ArrayList<PhysicsEntity> collided=new ArrayList<>(1);
        int x=(int)Math.round(ent.pos.x*precision);
        int y=(int)Math.round(ent.pos.y*precision);
        for(int dy=0;dy<ent.collisionModel.length*precision;++dy)
            for(int dx=0;dx<ent.collisionModel[dy/precision].length*precision;++dx)
                if(inRange(x+dx,y+dy)) {
                    allOut=false;
                    if(ent.collisionModel[dy/precision][dx/precision]==1) {
                        if(space[y+dy][x+dx]!=null)
                            collided.add(space[(y+dy)][(x+dx)]);
                        space[y+dy][x+dx]=ent;
                    }
                }
        if(allOut) {
            ent.collisionAction(new Wall());
        }
        return collided;
    }

    /**
     * Returns whether point x,y is in range
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean inRange(int x,int y) {
        return x>=0&&y>=0&&y<space.length&&x<space[y].length;
    }
}
