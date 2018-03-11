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
 * Created by edwar on 6/11/2017.
 */
public class Collision {
	public final PhysicsEntity who;
	public final Point2D.Float where;
	public Collision(PhysicsEntity who, Point2D.Float where){
		this.who=who;
		this.where=where;
	}
}
