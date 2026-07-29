package data;

public class ServerFinanceFundInfo {

    public long fundXu;
    public long fundLuong;
    public long fundLuongLock;
    public long bankFeeXu;
    public long bankFeeLuong;
    public long bankFeeLuongLock;
    public long clawbackXu;
    public long clawbackLuong;
    public long clawbackLuongLock;
    public long bankFeeBootstrapLogId;
    public long lastClawbackLogId;
    public boolean historyBootstrapped;
    public String updatedAt;

    public ServerFinanceFundInfo() {
        this.updatedAt = "2010-01-01 00:00:00";
    }

    public void refreshTotals() {
        this.fundXu = this.bankFeeXu + this.clawbackXu;
        this.fundLuong = this.bankFeeLuong + this.clawbackLuong;
        this.fundLuongLock = this.bankFeeLuongLock + this.clawbackLuongLock;
    }
}
