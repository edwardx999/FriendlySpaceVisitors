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
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: May 29, 2017
 * Author: Edward Xie 6
 * File: Powerup.java
 * Purpose:
 */
public class Powerup extends PhysicsEntity {

    public Powerup(Point2D.Float where,Point2D.Float vel) {
        super(new byte[][]{
            {1,1,1},
            {1,0,1},
            {1,1,1}
        });
        pos=where;
        this.vel=vel;
    }

    /**
     * Gives improvements to ships
     * @param who 
     */
    @Override
    public void collisionAction(ArrayList<PhysicsEntity> who) {
        for(PhysicsEntity w:who) {
            if(w instanceof Ship) {
                alive=false;
                ((Ship)w).powerup();
            }
        }
    }

    @Override
    public void draw(Graphics2D g,int scale) {
    }

}
