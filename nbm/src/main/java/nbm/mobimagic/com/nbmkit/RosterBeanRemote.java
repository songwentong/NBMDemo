package nbm.mobimagic.com.nbmkit;

import java.io.Serializable;

/**
 * @author sunruijian
 *         远端的黑白名单bean, 由运营配置通过v5下发到本地, 会影响本地名单的一些策略.
 *         读写采用json数据格式.
 */
public class RosterBeanRemote implements Serializable {

    private static final long serialVersionUID = -7807135304529679356L;

    /**
     * 包名
     */
    private String pkgName;

    //    private long date;
    //    private int ver;

    /**
     * 百分比
     * 用于文案:xx% users allow its notification.
     */
    //    private int percent;
    /**
     * 运营建议是否显示该通知,但最终结果取决于客户端的本地逻辑.
     * 0 - 建议隐藏
     * 1 - 建议展示
     */
    //    private int suggestShow;

    public String getPkgName() {
        return pkgName;
    }

    //    public int getPercent() {
    //        return percent;
    //    }

    //    public int isSuggestShow() {
    //        return suggestShow;
    //    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    //    public void setPercent(int percent) {
    //        this.percent = percent;
    //    }

    //    public void setSuggestShow(int suggestShow) {
    //        this.suggestShow = suggestShow;
    //    }
}