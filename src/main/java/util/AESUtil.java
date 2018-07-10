package util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by admin on 2018/6/20.
 */
public class AESUtil {

    private static  final String ENCODE_RULES = "chentianxiu";
    public static String aesEncode(String content){
        try {
            //构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keyGenrator = KeyGenerator.getInstance("AES");
            //生成128位的随机源，根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(ENCODE_RULES.getBytes());

            keyGenrator.init(128,random);
            //产生原始对称密钥
            SecretKey originKey = keyGenrator.generateKey();

            //获得原始对称密钥对应的字节数组
            byte[] raw = originKey.getEncoded();
            //根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw,"AES");
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //初始化密码,第一个参数加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的key
            cipher.init(Cipher.ENCRYPT_MODE,key);

            //获取加密内容的字节数组(这里要设置成utf-8)，不然内容中有中英文混合的就会造成乱码
            byte[] byteEncode = content.getBytes("utf-8");
            //根据密码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String aesEncode = new String(new BASE64Encoder().encode(byteAES));

            return aesEncode;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String aesDecode(String content){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(ENCODE_RULES.getBytes());

            keyGenerator.init(128,random);

            SecretKey originalKey = keyGenerator.generateKey();

            byte[] raw = originalKey.getEncoded();

            SecretKey key = new SecretKeySpec(raw,"AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE,key);

            //将加密并编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);

            byte[] byteCon = cipher.doFinal(byteContent);

            String decStr = new String(byteCon,"utf-8");

            return decStr;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
             String code = args[0];
             if(code == null || code == ""){
                 System.out.println("请输入您的auth码（登录foxmail的凭证）");
                 return;
             }
//            String enCode = aesEncode("jdbc:mysql://120.92.33.218:60000/v3_stat_inter?useUnicode=true&characterEncoding=utf8");
//            System.out.println("加密后的字符串："+enCode);
            String encode = aesDecode(code);
            System.out.println("加密后的字符串:"+encode);
    }
}
