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

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: May 25, 2017
 * Author: Edward Xie 6
 * File: CollisionSpaceTime.java
 * Purpose:
 */
public interface CollisionSpaceTime {
    //typedefs would be nice
    /**
     * 
     */
    public Point2D.Float globalForce(PhysicsEntity ent);
    /**
     * Gets the Dimension size of the CollisionSpaceTime
     *
     * @return Dimension size of the CollisionSpaceTime
     */
    public Dimension getDim();

    /**
     * Removes all entities from the space time
     */
    public void clear();

    /**
     * Moves an entity through this CollisionSpaceTime, invoking any necessary collision actions
     *
     * @param ent - the entity to be moved
     */
    public void moveEntity(PhysicsEntity ent);

    /**
     * Removes a given entity from this CollisionSpaceTime
     * @param ent - the entity to be removed
     */
    public void removeEntity(PhysicsEntity ent);

    /**
     * Adds an entity to this CollisionSpaceTime
     * @param ent - the entity to be removed
     * @return an ArrayList<Entity> containing any entities that were removed while adding ent
     */
    public ArrayList<PhysicsEntity> addEntity(PhysicsEntity ent);

    /**
     * Tells whether a given point (x,y) is in this CollisionSpaceTime
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @return whether a given point (x,y) is in this CollisionSpaceTime
     */
    public boolean inRange(int x,int y);

}
