package nbm.mobimagic.com.nbmkit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sunruijian
 *         本地白名单bean, 直接决定本地通知的屏蔽与显示, 在某些情况下会受RosterBeanRemote影响.比如在本地白名单不存在或某个app首次安装时.
 *         读写方式采用本地序列化保存.
 */
public class RosterBeanLocal implements Serializable {

    private static final long serialVersionUID = -7662393902330427158L;

    private Set<String> list;

    public Set<String> getList() {
        if (list == null) {
            list = new HashSet<String>();
        }
        return list;
    }

    public void setList(Set<String> list) {
        this.list = list;
    }
}