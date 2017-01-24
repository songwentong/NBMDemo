package nbm.mobimagic.com.nbmkit;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class RosterReadTool {

    private final static String RECOMMAND_LIST_FILE_NAME = "nr_list.dat";
    private final static String DEF_RECOMMAND_LIST_FILE_NAME = "def_nr_list.dat";

    private final static String DES_KEY = "nsl_remote_list";

    public static void writeFileData(Context c, String fileName, String message) {
        try {
            FileOutputStream fout = c.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] MD5(byte[] input) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        if(md != null) {
            md.update(input);
            return md.digest();
        } else {
            return null;
        }
    }
    public static String DES_decrypt(String encrypted, String key) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(MD5(key.getBytes()));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, secretKey, sr);
            byte[] decryptedData = cipher.doFinal(ByteConvertor.hexStringToBytes(encrypted));
            return new String(decryptedData);
        } catch (Exception var8) {
            return "";
        }
    }
    public static List<RosterBeanRemote> getInfoFromFile(Context c) {
        List<RosterBeanRemote> beanList = new ArrayList<RosterBeanRemote>();
        InputStream is = null;
        BufferedReader in = null;
        try {
            is = c.openFileInput(RECOMMAND_LIST_FILE_NAME);
            if (is != null) {
                if (is != null) {
                    in = new BufferedReader(new InputStreamReader(is));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    String str = "";
                    while ((line = in.readLine()) != null) {
                        buffer.append(line);
                    }
                    str = buffer.toString();
                    str = DES_decrypt(buffer.toString(), DES_KEY);
                    if (str == null || str.trim().length() == 0) {
                        return beanList;
                    }
                    JSONArray jArray = new JSONArray(str);
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObj = jArray.getJSONObject(i);
                            RosterBeanRemote obj = new RosterBeanRemote();
                            obj.setPkgName(jObj.getString("pkgName"));
                            beanList.add(obj);
                        }
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return beanList;
    }

    public static Set<String> ReadDefRosterFile(Context c) {
        Set<String> list = new HashSet<String>();
        InputStream instream = null;
        try {
            instream = c.getAssets().open(DEF_RECOMMAND_LIST_FILE_NAME);
            if (instream != null) {
                BufferedReader buffreader = new BufferedReader(new InputStreamReader(instream));
                String line;
                while ((line = buffreader.readLine()) != null) {
                    list.add(line);
                }
            }
        } catch (java.io.FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}