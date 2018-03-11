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

/**
 * Date: May 31, 2017
 * Author: Edward Xie 6
 * File: GameRun.java
 * Purpose:
 */
public class GameRun {

    //declaration of game info
    public static void main(String[] args) {
        GameData gd=new GameData();
        GameProcessor gp=new OnePlayerGame(gd);
        new Thread(gp).start();
        //new Thread(gd).start();
        GameGUI gg=new GameGUI(gd);
        PhysicsEngine pe=new PhysicsEngine(gd);
        gg.addKeyListener(gp);
        //new Thread(pe).start();
        //new Thread(gg).start();
        long time=System.currentTimeMillis();
        long curTime;
        while(true) {
            pe.physCalc();

            curTime=System.currentTimeMillis();
            if(curTime-time>gg.minFramePer) {//frame rate control
                time=curTime;
                gg.draw();
            }
        }
    }

}
