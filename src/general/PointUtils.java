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

import java.awt.geom.Point2D;

/**
 * Date: May 23, 2017
 * Author: Edward Xie 6
 * File: PointUtils.java
 * Purpose:
 */
public class PointUtils {
    
    /**
     * Adds two points
     * @param a
     * @param b
     * @return 
     */
    public static Point2D.Double add(Point2D a,Point2D b){
        return new Point2D.Double(a.getX()+b.getX(),a.getY()+b.getY());
    }
    
    /**
     * Multiplies a point and a scalar
     * @param a
     * @param scalar
     * @return 
     */
    public static Point2D.Double mult(Point2D a, double scalar){
        return new Point2D.Double(a.getX()*scalar,a.getY()*scalar);
    }
    
    /**
     * Adds two points
     * @param a
     * @param b
     * @return 
     */
    public static Point2D.Float add(Point2D.Float a,Point2D.Float b){
        return new Point2D.Float(a.x+b.x,a.y+b.y);
    }
    
    /**
     * Multiplies a point and a scalar
     * @param a
     * @param scalar
     * @return 
     */
    public static Point2D.Float mult(Point2D.Float a, float scalar){
        return new Point2D.Float(a.x*scalar,a.y*scalar);
    }
    
}
