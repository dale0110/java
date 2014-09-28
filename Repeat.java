import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
 
/**
 * 将给定目录去除重复文件，或打印输出重复文件列表，根据文件的MD5，忽略文件的文件名是否相同。
 * <br>
 * 删除重复的文件，优先保留路径最短的那个文件。
 * <br>
 * 仅适用一般情况，对于需要重复存在的情况慎用。
 * <br>
 * <br>例子：</b>
 * <br>
 * 1）默认只打印输出重复文件列表，不删除重复文件，如：C:\\>java com.goofus.tool.FileRepeat D:\\test\\aa
 * <br>
 * 或：C:\\>java com.goofus.tool.FileRepeat D:\\test\\aa -m:list
 * <br>
 * 2）删除重复文件，如：C:\\>java com.goofus.tool.FileRepeat D:\\test\\aa -m:clear
 * <br>
 * @author goofus/Administrator
 * Jul 14, 2014
 */
public class FileRepeat {
     
    //文件实例作为KEY，文件MD5值作为VALUE
    private final static Map<File, String> FILE_MD5_MAP = new HashMap<File, String>();
    //重复文件寄存器；文件MD5值作为KEY，重复文件的列表作为VALUE
    private final static Map<String, List<File>> FILE_REPEATED_MAP = new HashMap<String, List<File>>();
    //模式；默认输出重复文件列表
    private static String HANDLE_MODE = "list";
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        long s = System.currentTimeMillis();
         
        if(null == args || args.length < 1) {
            System.out.println("输入正确的参数，如：C:\\>java FileRepeat D:\\test\\aa");
            System.exit(-1);
        }
         
        String filePath = args[0];
        File dir = new File(filePath);
        if(!dir.isDirectory() || !dir.exists()) {
            System.out.println("输入正确的目录");
            System.exit(-1);
        }
         
        String args1 = args.length > 1 ? args[1] : null;
        if(null != args1) {
            if(args1.startsWith("-m:")) {
                String mode = args1.replaceAll("-m:", "");
                if(null == mode || ("list".equalsIgnoreCase(mode) && !"clear".equalsIgnoreCase(mode))){
                    System.out.println("-m:可选项：list、clear");
                    System.exit(-1);
                }
                HANDLE_MODE = mode;
            }else {
                System.out.println("输入正确的参数，如：C:\\>java com.goofus.tool.FileRepeat D:\\test\\aa -m:clear");
                System.exit(-1);
            }
        }
         
        int tally = 0;
        computeFileMd5(dir);
        handleRepeat();
        tally = deleteForRepeat();
         
        //打印输出重复文件列表
        if("list".equalsIgnoreCase(HANDLE_MODE)){
            Set<Entry<String, List<File>>> rSet = FILE_REPEATED_MAP.entrySet();
            for(Iterator<Entry<String, List<File>>> it = rSet.iterator(); it.hasNext();){
                Entry<String, List<File>> entry = it.next();
                String key = entry.getKey();
                List<File> files = entry.getValue();
                if(null != files && files.size() > 0){
                    StringBuffer fPath = new StringBuffer();
                    for(int a=0,al=files.size(); a<al; a++){
                        File f = files.get(a);
                        if(a > 0) fPath.append(",");
                        fPath.append(f.getAbsolutePath());
                    }
                    System.out.println("MD5值为：" + key + " 的重复文件：" + fPath);
                }
            }
        }else if(tally < 1){
            System.out.println("没有重复的文件。");
        }
 
        System.out.println("删除了" + tally + "个重复文件，耗时：" + (System.currentTimeMillis() - s) + "毫秒。");
 
    }
     
    private static void computeFileMd5(File dir){
        if(null == dir) return ;
        File[] files = dir.listFiles();
        if(null != files && files.length > 0){
            for(File f : files){
                if(f.isDirectory()){
                    computeFileMd5(f);
                }else{
                    FILE_MD5_MAP.put(f, getMd5ForFile(f));
                }
            }
        }
    }
     
    private static void handleRepeat(){
        if(FILE_MD5_MAP.size() < 2) return ;
         
        Set<Entry<File, String>> set = FILE_MD5_MAP.entrySet();
        for(Iterator<Entry<File, String>> it = set.iterator(); it.hasNext();){
            Entry<File, String> entry = it.next();
            if(FILE_REPEATED_MAP.containsKey(entry.getValue())){
                //文件的MD5值已经作为KEY存在于重复寄存器中，则认为该文件存在重复，直接记入重复寄存器
                addToRepeatedMap(entry);
            }else{
                //否则，迭代比较是否存在重复文件
                for(Iterator<Entry<File, String>> it2 = set.iterator(); it2.hasNext();){
                    Entry<File, String> entry2 = it2.next();
                    if(!entry.getKey().equals(entry2.getKey()) && entry.getValue().equals(entry2.getValue())){
                        //存在重复的文件，先记入集合，等待进一步处理
                        if(FILE_REPEATED_MAP.containsKey(entry.getValue())){
                            addToRepeatedMap(entry);
                        }else{
                            List<File> value = new ArrayList<File>();
                            value.add(entry.getKey());
                            FILE_REPEATED_MAP.put(entry.getValue(), value);
                        }
                        break;
                    }
                }
            }
        }
    }
     
    private static int deleteForRepeat(){
        int c = 0;
             
        //去重，仅保留一个
        if(FILE_REPEATED_MAP.size() > 0 && "clear".equalsIgnoreCase(HANDLE_MODE)){
            Set<Entry<String, List<File>>> rSet = FILE_REPEATED_MAP.entrySet();
            for(Iterator<Entry<String, List<File>>> it = rSet.iterator(); it.hasNext();){
                Entry<String, List<File>> entry = it.next();
                String key = entry.getKey();
                List<File> files = entry.getValue();
                if(null != files && files.size() > 0){
                    System.out.println("【保留】保留MD5值为：" + key + " 的重复文件之一：" + files.get(0).getAbsolutePath());
                    for(int a=1,al=files.size(); a<al; a++){
                        File f = files.get(a);
                        String fPath = f.getAbsolutePath();
                        boolean success = f.delete();
                        if(success) {
                            c++;
                            System.out.println("【成功】删除MD5值为：" + key + " 的重复文件之一：" + fPath);
                        }else{
                            System.out.println("【失败】删除MD5值为：" + key + " 的重复文件之一：" + fPath);
                        }
                    }
                }
            }
        }
         
        return c;
    }
 
    private static void addToRepeatedMap(Entry<File, String> entry) {
        int depth = computeDepth(entry.getKey());
        if(depth < computeDepth(FILE_REPEATED_MAP.get(entry.getValue()).get(0))){
            //路径最短放在第1个，去重时将被保留
            FILE_REPEATED_MAP.get(entry.getValue()).add(0, entry.getKey());
        }else{
            FILE_REPEATED_MAP.get(entry.getValue()).add(entry.getKey());
        }
    }
     
    private static int computeDepth(File f){
        String path = convertPath(f.getAbsolutePath());
        String[] paths = path.split("/");
        if(null != paths) return paths.length;
        else return 999;
    }
     
    private static String convertPath(String path) {
        if(null != path)
            return path.replaceAll("\\\\", "/");
        else return "";
    }
     
    /**
     * 计算文件的MD5值
     * @param f 文件
     * @return MD5值
     */
    private static String getMd5ForFile(File f) {
        String rtn = "";
        if(null == f || f.isDirectory() || !f.exists()) return rtn;
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(f);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
             
            BigInteger bi = new BigInteger(1, md.digest());
            rtn = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtn;
    }
     
}
