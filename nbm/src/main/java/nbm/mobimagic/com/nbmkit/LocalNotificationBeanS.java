package nbm.mobimagic.com.nbmkit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalNotificationBeanS implements Serializable {

    private static final long serialVersionUID = 8715688010231079860L;

    private Map<String, List<SimpleNotification>> map = new HashMap<String, List<SimpleNotification>>();
    private Map<String, Integer[]> positionOfPkg = new HashMap<String, Integer[]>();
    private List<String> pkgList = new ArrayList<String>();

    public synchronized void putNotification(String pkgName, SimpleNotification n) {
        if (map.get(pkgName) == null) {
            map.put(pkgName, new ArrayList<SimpleNotification>());
            map.get(pkgName).add(0, n);
        } else {
            if (map.get(pkgName).size() < 5) {
                map.get(pkgName).add(0, n);
            } else {
                map.get(pkgName).remove(0);
                map.get(pkgName).add(0, n);
            }
        }
        if (pkgList.contains(pkgName)) {
            pkgList.remove(pkgName);
            pkgList.add(0, pkgName);
        } else {
            pkgList.add(0, pkgName);
        }
    }

    public void removeNotification(String pkgName, SimpleNotification n) {
        if (map.get(pkgName) != null) {
            map.get(pkgName).remove(n);
            if (map.get(pkgName).size() == 0) {
                pkgList.remove(pkgName);
            }
        }

    }

    public int size() {
        return map == null ? 0 : map.size();
    }

    public int getItemCount() {
        int count = 0;
        //        if (map != null && getPkgs() != null) {
        //            for (int i = 0; i < map.size(); i++) {
        //                if (!map.isEmpty() && !getPkgs().isEmpty() && map.get(getPkgs().get(i)) != null) {
        //                    count += map.get(getPkgs().get(i)).size();
        //                }
        //            }
        //        }
        for (Map.Entry<String, List<SimpleNotification>> entry : map.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }

    public List<String> getPkgs() {
        return pkgList;
    }

    public List<SimpleNotification> getNotificationListByPkg(String pkgName) {
        return map.get(pkgName);
    }

    public List<SimpleNotification> toNotificationList() {

        if (map == null || getPkgs() == null) {
            return null;
        }

        int index = 0;
        List<SimpleNotification> list = new ArrayList<SimpleNotification>();

        //        for (Map.Entry<String, List<SimpleNotification>> entry : map.entrySet()) {
        //            for(SimpleNotification bean : entry.getValue()){
        //                list.add(bean);
        //            }
        //        }

        for (int i = 0; i < getPkgs().size(); i++) {
            if (map.containsKey(getPkgs().get(i))) {
                List<SimpleNotification> listOfApp = map.get(getPkgs().get(i));
                if (listOfApp == null) {
                    continue;
                }
                for (int j = 0; j < listOfApp.size(); j++) {
                    if (j == 0) {
                        listOfApp.get(j).setFrist(true);
                        if (positionOfPkg == null) {
                            positionOfPkg = new HashMap<String, Integer[]>();
                        }
                        positionOfPkg.put(getPkgs().get(i), new Integer[] { index, listOfApp.size() });
                    } else {
                        listOfApp.get(j).setFrist(false);
                    }
                    index++;
                    list.add(listOfApp.get(j));
                }
            }
        }
        return list;
    }

    public Map<String, List<SimpleNotification>> getMap() {
        return map;
    }

    public Map<String, Integer[]> getPositionOfPkg() {
        return positionOfPkg;
    }

    public String toString() {
        return map == null ? "NULL" : map.keySet().toString();
    }
}