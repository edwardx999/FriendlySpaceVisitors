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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Date: May 31, 2017
 * Author: Edward Xie 6
 * File: GameGUI.java
 * Purpose:
 */
public class GameGUI extends JFrame implements Runnable {

    public final GameData gd;
    public ArrayList<PhysicsEntity>[] runThrough;//physics entities to draw
    private GamePanel panel;
    public static final int PIX_SIZE=8;
    private long time=System.currentTimeMillis();

    public long minFramePer=1000/70;//in milliseconds

    @Override
    public void run() {
        long newTime;
        while(true) {
            if(gd.gameOn) {
                newTime=System.currentTimeMillis();
                if(newTime-time>minFramePer) {
                    //System.out.println("FPS: "+1000.0/(newTime-time));
                    time=newTime;
                    draw();
                }
                if(gd.winner!=null) {
                    JOptionPane.showMessageDialog(this,gd.winner+" won!\n R to restart\n, or . to change difficulty");
                    gd.winner=null;
                    gd.gameOn=false;
                }
            }
        }
    }

    public void draw() {
        panel.repaint();
    }

    //sets up the window
    public void windowSetup() {
        panel=new GamePanel();
        panel.setPreferredSize(new Dimension(gd.GAME_WIDTH*PIX_SIZE,gd.GAME_HEIGHT*PIX_SIZE));
        add(panel);
        panel.setBackground(Color.BLACK);
        pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GameGUI(GameData gameData) {
        this.gd=gameData;
        windowSetup();
    }

    /*void setupTimer() {
        drawer.cancel();
        drawer=new Timer();
        drawer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                panel.repaint();
            }
        },
                0,minFramePer);
        drawer=new Thread(this);
        drawer.start();
    }*/
    public void setMaxFPS(double fps) {
        minFramePer=(long)(1000/Math.abs(fps));
    }

    class GamePanel extends JPanel {

        double drawTime=0;
        
        /**
         * Draws entities and win messages
         * @param g 
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setFont(new Font("Consolas",0,12));
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Difficulty: "+gd.level,0,15);
            g.drawString(drawTime+"",130,15);
            long timeToDraw=System.nanoTime();
            for(int i=0;i<gd.entities.size();++i) {
                List<PhysicsEntity> list=gd.entities.get(i);
                for(int e=list.size()-1;e>=0;--e) {
                    try {
                        paintEntity(g,list.get(e),false);
                    } catch(IndexOutOfBoundsException aioobe) {
                        e=list.size()-1;
                    }
                }
            }
            timeToDraw=-timeToDraw+System.nanoTime();
            timeToDraw/=1e6;
            if(timeToDraw>.05) {
                drawTime=timeToDraw;
            }
            if(gd.winner!=null) {
                final int fontsize=36;
                g.setFont(new Font("Consolas",0,fontsize));
                g.setColor(Color.LIGHT_GRAY);
                String disp=gd.winner+" won! R to restart";
                int halfsize=disp.length()*fontsize/4;
                g.drawString(disp,getWidth()/2-halfsize,getHeight()/2);
            }


            /*for(List<Entity> ents:gd.entities)
                for(Entity ent:ents)for(List<PhysicsEntity> ents:gd.entities)
                for(PhysicsEntity ent:ents)
                    paintEntity(g,ent,false);*/
        }
        
        /**
         * Paints an individual entity
         * @param g
         * @param ent
         * @param unpaint 
         */
        void paintEntity(Graphics g,PhysicsEntity ent,boolean unpaint) {
            if(!ent.alive) {
                return;
            }
            int x=(int)(ent.pos.x*PIX_SIZE);
            int y=(int)(ent.pos.y*PIX_SIZE);
            //if(ent instanceof Bullet) System.out.println(x+","+y);
            if(unpaint) {
                g.setColor(Color.BLACK);
                for(int dy=0;dy<ent.collisionModel.length;++dy) {
                    for(int dx=0;dx<ent.collisionModel[0].length;++dx) {
                        if(ent.collisionModel[dy][dx]==1) {
                            g.fillRect(x+dx*PIX_SIZE,y+dy*PIX_SIZE,PIX_SIZE,PIX_SIZE);
                        }
                    }
                }
            } else {
                for(int dy=0;dy<ent.collisionModel.length;++dy) {
                    for(int dx=0;dx<ent.collisionModel[0].length;++dx) {
                        if(ent.collisionModel[dy][dx]==0) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(x+dx*PIX_SIZE,y+dy*PIX_SIZE,PIX_SIZE,PIX_SIZE);
                        }
                    }
                }
            }
        }
    }
}
