import io.jbotsim.core.Link;
import io.jbotsim.core.Node;
import io.jbotsim.core.Color;

import java.util.*;
import java.lang.*;
public class ColorNode  extends Node  {
    // array of predefined jbotsim colors
    private static final Color all_colors[] = {Color.black, Color.white, Color.gray, Color.
            lightGray, Color.pink, Color.red, Color.darkGray, Color.orange, Color.yellow,
            Color.magenta, Color.cyan, Color.blue, Color.green};
    private static final int nb_colors = all_colors.length;
    // the node current color
    private int color = 0;
    private boolean byzantine = false;
    // Convert internal color to jBotSim color

    Color toColor(int i) {

        return all_colors[i % nb_colors];
    }

    // change node color and adjust graphics
    void setColor(int i) {
        color = i % nb_colors;
        setColor(toColor(i));
    }

    // returns true if the node has a color conflict with a neighbor
    boolean isConflict(List<Node> l) {
        for (Node node : l) {
            System.out.println("in isonflit " + node.getID());
            System.out.println(color + " " + ((ColorNode)node).color);
            if (color == ((ColorNode) node).color) {

                return true;
            }
        }
        return false;
    }


    // finds a new color for the node among available colors
// if the node has more neighbors than max nb of colors, the node may not change its color
    void findColor(List<Node> l) {
        int last = color;
        //color = 0;
        while (isConflict(l)) {
            color += 1 % nb_colors;
            if (color == last) {
                break;
            }
        }
        setColor(color);
    }

   @Override
    public void onStart() {
// JBotSim executes this method on each node upon initialization
        setColor(0);
    }
   public  int randomNeighbor(){
        return (int) Math.random()*(getNeighbors().size()+1);
    }

    @Override
    public void onSelection() {
        // JBotSim executes this method on a selected node (middle−click)
        byzantine = !byzantine;
        System.out.println(byzantine);
        if(byzantine && hasNeighbors()) {
            int nbVoisin = getLinks().size();
            color = ((ColorNode)getNeighbors().get(randomNeighbor())).color;
            setColor(color);
        }
        if(!byzantine){
            findColor(getNeighbors());
        }
    }


    @Override
    public void onMovement() {
        if(byzantine && hasNeighbors()){
            color = ((ColorNode)getNeighbors().get(randomNeighbor())).color;
            setColor(color);
        }
    }

    @Override
    public void onClock() {
        //System.out.println(getID());
// JBotSim executes this method on each node in each round
        if (!byzantine){
            if (isConflict(getNeighbors())) {
                color = 0;
                findColor(getNeighbors());
            }
        }else{
            if(hasNeighbors()) {
            color = ((ColorNode)getNeighbors().get(randomNeighbor())).color;
            setColor(color);}
        }
    }
}
