package ocelot.rt;

import ocelot.JVMStorage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The SimpleLinkedJVMHeap takes in object IDs (longs) such as are stored in JVMValue and
 returns the actual object corresponding to the ID.
 * 
 * @author ben
 */
public class SimpleLinkedJVMHeap implements JVMStorage {

    private static final AtomicLong objCounter = new AtomicLong(1L);
    private List<JVMObj> heap = new LinkedList<>();

    public long allocateObj(OCKlass klass) {
        JVMObj o = JVMObj.of(klass, objCounter.getAndIncrement());
        heap.add(o);
        return o.getId();
    }

    public JVMObj findObject(long id) {
        for (JVMObj o : heap) {
            if (o.getId() == id)
                return o;
        }
        return null;
    }

    // FIXME Synchronization
    public void runGC() {
        // Clear the mark bit
        for (JVMObj o : heap) {
            o.getMark().setLive(false);
        }

        // Scan from roots
        // FIXME
        // Sweep
        for (JVMObj o : heap) {
            if (!o.getMark().isLive()) {
                // Remove dead object
            }
        }

    }

}
