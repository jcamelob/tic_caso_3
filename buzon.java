import java.util.LinkedList;
import java.util.Queue;

public class buzon {

    static Queue<mensaje> queue;
    int len_max = 9999;
    int queueLength;
    
    public buzon(int max){
        len_max = max;
        queue = new LinkedList<>();
        queueLength = 0;

    }

    public buzon(){
        queue = new LinkedList<>();
        queueLength = 0;

    }

    public boolean add_msm(mensaje msm){
        if (queueLength < len_max) {
            queue.add(msm);
            queueLength += 1;
            return true;
        } else {
            return false;
        }

    }

    public static mensaje get_msm(){
        return queue.poll();
    }


}
