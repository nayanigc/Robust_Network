import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
public class Byzantine {
    public static void main(String[] args){
        Topology tp = new Topology();
        tp.setDefaultNodeModel(ColorNode.class);
        new JViewer(tp);
        tp.start();
    }
}