package ra63_2014.pnrs1.rtrk.taskmanager;

/**
 * Created by godra on 06/06/17.
 */

public class CalculateStatsNative {
    public native int calculatePercentage(int total, int completed);
    static {
        System.loadLibrary("calculatestats");
    }
}
