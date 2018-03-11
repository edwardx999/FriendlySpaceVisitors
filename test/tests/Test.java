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
package tests;

import general.*;
import java.awt.geom.Point2D;
import spaceinvaders.*;

/**
 * Date: May 30, 2017
 * Author: Edward Xie 6
 * File: Test.java
 * Purpose:
 */
public class Test {

    public static void main(String[] args) {
        ClassTree<PhysicsEntity> em=new ClassTree<>(PhysicsEntity.class);

        em.addType(Shooter.class);
        ClassTree<Shooter> shooters=em.getTreeOfType(Shooter.class);
        shooters.addType(Alien.class);
        shooters.addType(Ship.class);

        ClassTree<Alien> aliens=shooters.getTreeOfType(Alien.class);
        ClassTree<Ship> ships=shooters.getTreeOfType(Ship.class);

        em.addType(Bullet.class);
        System.out.println(em.getTreeOfType(Bullet.class));
        for(int i=0;i<100;++i) {
            em.getTreeOfType(Bullet.class).addInstance(new Bullet(new Point2D.Float(0,0),new Point2D.Float(0,0)));
            aliens.addInstance(new Alien(new Point2D.Float(0,0)));
        }
        ships.addInstance(new Ship(new Point2D.Float(0,0)));
        System.out.println(em.size());
    }

}
