package nbm.mobimagic.com.nbmkit;

import java.io.Serializable;

public class SimpleNotification implements Serializable {

    private static final long serialVersionUID = 2660433831349944580L;

    private String pkgName;
    private String title;
    private String des;
    private String ticker;
    private boolean isFrist;
    private int id;

    public SimpleNotification(String pkgName, CharSequence title, CharSequence des, CharSequence ticker, int id) {
        this.pkgName = pkgName;
        this.title = title == null ? " " : title.toString();
        this.des = des == null ? " " : des.toString();
        this.ticker = ticker == null ? " " : ticker.toString();
        this.id = id;
    }

    public String getPkgName() {
        return pkgName;
    }

    public String getTitle() {
        return title;
    }

    public String getDes() {
        return des;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean isFrist() {
        return isFrist;
    }

    public void setFrist(boolean isFrist) {
        this.isFrist = isFrist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}