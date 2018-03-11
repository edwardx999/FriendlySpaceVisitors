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
 * File: SuicidalEntity.java
 * Purpose:
 */
public abstract class SuicidalEntity extends PhysicsEntity {
    //Entity that kills itself
    public List whereAmI;

    public SuicidalEntity(byte[][] sprite) {
        super(sprite);
    }

    @Override
    public void kill() {
        super.kill();
        if(whereAmI!=null)
            whereAmI.remove(this);
    }
}
