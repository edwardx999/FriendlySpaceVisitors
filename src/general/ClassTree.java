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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: May 25, 2017
 * Author: Edward Xie 6
 * File: EntityManager.java
 * Purpose:
 *
 * @param <Super>
 */
public class ClassTree<Super> /*implements Iterable<Super>*/ {

    public final Class<Super> clazz;
    private List<Super> instances=Collections.synchronizedList(new ArrayList<>());
    private List<ClassTree<Super>> subclazzes=Collections.synchronizedList(new ArrayList<>());

    public ClassTree(Class<Super> type) {
        this.clazz=type;
    }

    public boolean addInstance(Super sup) {
        return instances.add(sup);
    }

    /**
     * Returns the instance of Super at a given index within the entire tree
     * Higher and left most has lower indices
     *
     * @param index
     * @return
     */
    public Super get(int index) {
        if(index<instances.size()) {
            return instances.get(index);
        }
        index-=shallowSize();
        for(ClassTree ct:subclazzes) {
            if(index<ct.size()) {
                return (Super)ct.get(index);
            }
            index-=ct.size();
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public Super remove(int index) {
        if(index<instances.size()) {
            return instances.get(index);
        }
        index-=shallowSize();
        for(ClassTree ct:subclazzes) {
            if(index<ct.size()) {
                return (Super)ct.remove(index);
            }
            index-=ct.size();
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public boolean remove(Super o) {
        return instances.remove(o);
    }

    /**
     * Returns the subtree that is of the given class
     *
     * @param <Sub>
     * @param subclazz
     * @return
     */
    public <Sub extends Super> ClassTree<Sub> getTreeOfType(Class<Sub> subclazz) {
        if(subclazz.equals(this.clazz)) {
            return (ClassTree<Sub>)this;
        }
        for(ClassTree ct:subclazzes) {
            if(ct.clazz.isAssignableFrom(subclazz)) {
                return ct.getTreeOfType(subclazz);
            }
        }
        return null;
    }

    /**
     * Adds a new subtree to this tree of Class type
     *
     * @param <Sub>
     * @param type
     * @return
     */
    public <Sub extends Super> boolean addType(Class<Sub> type) {
        return subclazzes.add((ClassTree<Super>)new ClassTree<>(type));
    }

    public int shallowSize() {
        return instances.size();
    }

    /**
     * Returns the total amount of instances in this tree
     *
     * @return
     */
    public int size() {
        int size=instances.size();
        for(ClassTree ct:subclazzes) {
            size+=ct.size();
        }
        return size;
    }

    public <Sub extends Super> ClassTree<Sub> removeType(Class<Sub> type) {
        for(int i=0;i<subclazzes.size();++i) {
            if(subclazzes.get(i).clazz.equals(type)) {
                return (ClassTree<Sub>)subclazzes.remove(i);
            }
        }
        return null;
    }

    //@Override
    /*public Iterator<Super> iterator() {
        return new Iterator<Super>() {

            int whichList=0;
            int index=0;

            @Override
            public boolean hasNext() {
                if(index<instances.get(classes.get(whichList)).size()) {
                    return true;
                }
                for(int i=whichList+1;i<classes.size();++i) {
                    if(!instances.get(classes.get(i)).isEmpty()) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Super next() {
                ArrayList<Super> which=instances.get(classes.get(whichList));
                if(index<which.size()) {
                    return which.get(index++);
                }
                index=0;
                ++whichList;
                for(;whichList<classes.size();++whichList) {
                    if(!(which=instances.get(classes.get(whichList))).isEmpty()) {
                        return which.get(index++);
                    }
                }
                return null;
            }

        };

    }*/
}
