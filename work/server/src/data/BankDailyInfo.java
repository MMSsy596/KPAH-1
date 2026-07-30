package data;

public class BankDailyInfo {

    public String dayLog;
    public long xuOut;
    public int luongFromLock;
    public long xuIn;
    public int luongIn;
    public int luongLockIn;

    public BankDailyInfo(final String dayLog, final long xuOut, final int luongFromLock) {
        this(dayLog, xuOut, luongFromLock, 0L, 0, 0);
    }

    public BankDailyInfo(final String dayLog, final long xuOut, final int luongFromLock, final long xuIn, final int luongIn, final int luongLockIn) {
        this.dayLog = dayLog;
        this.xuOut = xuOut;
        this.luongFromLock = luongFromLock;
        this.xuIn = xuIn;
        this.luongIn = luongIn;
        this.luongLockIn = luongLockIn;
    }
}
