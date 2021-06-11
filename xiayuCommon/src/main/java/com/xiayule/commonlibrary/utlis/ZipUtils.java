package com.xiayule.commonlibrary.utlis;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


/**
 * @Description: 文件压缩工具
 * @Author: 下雨了
 * @CreateDate: 2020-12-09 14:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-09 14:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ZipUtils {
    private static final int BUFFER_LEN = 8192;

    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    private ZipUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 压缩文件（夹）
     *
     * @param srcFiles 源文件
     * @param zipFile  目标文件
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFiles(final Collection<File> srcFiles, final File zipFile) throws IOException {
        return zipFiles(srcFiles, zipFile, null);
    }

    /**
     * 压缩文件（夹）
     *
     * @param srcFiles 源文件
     * @param zipFile  目标文件
     * @param comment  描述
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFiles(final Collection<File> srcFiles, final File zipFile, final String comment) throws IOException {
        if (srcFiles == null || zipFile == null) {
            return false;
        }
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File srcFile : srcFiles) {
                if (!zipFile(srcFile, "", zos, comment)) {
                    return false;
                }
            }
            return true;

        } finally {
            if (zos != null) {
                zos.finish();
                zos.close();
            }
        }
    }

    /**
     * 压缩文件（夹）
     *
     * @param srcFiles 源文件
     * @param zipFile  目标文件
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFiles(final Collection<String> srcFiles, final String zipFile) throws IOException {
        return zipFiles(srcFiles, zipFile, null);
    }

    /**
     * 压缩文件（夹）
     *
     * @param srcFiles 源文件
     * @param zipFile  目标文件
     * @param comment  描述
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFiles(final Collection<String> srcFiles, final String zipFile, final String comment) throws IOException {
        if (srcFiles == null || zipFile == null) {
            return false;
        }
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (String srcFile : srcFiles) {
                if (StringUtils.isSpace(srcFile)) {
                    return false;
                }
                if (!zipFile(new File(srcFile), "", zos, comment)) {
                    return false;
                }
            }
            return true;

        } finally {
            if (zos != null) {
                zos.finish();
                zos.close();
            }
        }
    }


    /**
     * 压缩文件
     *
     * @param srcFilePath 源文件
     * @param zipFilePath 目标文件
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFile(final String srcFilePath, final String zipFilePath) throws IOException {
        return zipFile(new File(srcFilePath), new File(zipFilePath), null);
    }

    /**
     * 压缩文件
     *
     * @param srcFilePath 源文件
     * @param zipFilePath 目标文件
     * @param comment     描述信息
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFile(final String srcFilePath, final String zipFilePath, final String comment) throws IOException {
        return zipFile(new File(srcFilePath), new File(zipFilePath), comment);
    }


    /**
     * 压缩文件
     *
     * @param srcFile 源文件
     * @param zipFile 目标文件
     * @return {@code true}: 成功 <br> {@code false}: 失败
     * @throws IOException IO异常
     */
    public static boolean zipFile(final File srcFile, final File zipFile) throws IOException {
        return zipFile(srcFile, zipFile, null);
    }

    /**
     * 压缩文件
     *
     * @param srcFile 源文件
     * @param zipFile 目标文件
     * @param comment 描述信息
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFile(final File srcFile, final File zipFile, final String comment) throws IOException {
        if (srcFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(srcFile, "", zos, comment);
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param srcFile  源文件
     * @param rootPath 目标文件
     * @param zos      压缩输入流
     * @param comment  描述信息
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    public static boolean zipFile(final File srcFile, String rootPath, final ZipOutputStream zos, final String comment) throws IOException {
        // 组装 目标文件路径
        rootPath = rootPath + (StringUtils.isSpace(rootPath) ? "" : File.separator) + srcFile.getName();
        // 测试源文件路径名是否为目录
        if (srcFile.isDirectory()) {
            File[] listFiles = srcFile.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + File.separator);
                entry.setComment(comment);
                // 进行压缩
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : listFiles) {
                    if (!zipFile(file, rootPath, zos, comment)) {
                        return false;
                    }
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(srcFile));
                ZipEntry entry = new ZipEntry(rootPath);
                entry.setComment(comment);
                // 进行压缩
                zos.putNextEntry(entry);
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        return true;
    }

    /**
     * 返回 计算数据流的CRC-32的值
     *
     * @param srcFile 源文件
     * @return CRC-32的值
     * @throws IOException IO异常
     */
    private static long getCrc(File srcFile) throws IOException {
        CRC32 crc32 = new CRC32();
        crc32.update(getFileBytes(srcFile));
        return crc32.getValue();
    }

    /**
     * 返回 文件的 byte[]
     *
     * @param file 要读取的文件 文件太大可能会出现 OOM
     * @return 返回文件的字节数组
     */
    private static byte[] getFileBytes(File file) throws IOException {
        byte[] buffer;
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFF_SIZE);
        byte[] b = new byte[BUFF_SIZE];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();

        return buffer;
    }


    //=====================================================================================================

    /**
     * 解压文件
     *
     * @param zipFile zip文件路径
     * @param destDir 解压到目标文件
     * @return 解缩后的文件
     * @throws IOException IO异常
     */
    public static List<File> unZipFile(final File zipFile, final File destDir) throws IOException {
        return unZipFileByKeyword(zipFile, destDir, null);
    }

    /**
     * 解压文件
     *
     * @param zipFilePath zip文件路径
     * @param destDirPath 解压到目标文件
     * @return 解缩后的文件
     * @throws IOException IO异常
     */
    public static List<File> unZipFile(final String zipFilePath, final String destDirPath) throws IOException {
        return unZipFileByKeyword(zipFilePath, destDirPath, null);
    }

    /**
     * 根据密码 解压文件
     *
     * @param zipFilePath zip文件路径
     * @param destDirPath 解压到目标文件
     * @param keyword     密码
     * @return 解缩后的文件
     * @throws IOException IO异常
     */
    public static List<File> unZipFileByKeyword(final String zipFilePath, final String destDirPath, final String keyword) throws IOException {
        return unZipFileByKeyword(new File(zipFilePath), new File(destDirPath), keyword);
    }

    /**
     * 根据密码 解压文件
     *
     * @param zipFile zip文件路径
     * @param destDir 解压到目标文件
     * @param keyword 密码
     * @return 解缩后的文件
     * @throws IOException IO异常
     */
    public static List<File> unZipFileByKeyword(final File zipFile, final File destDir, String keyword) throws IOException {
        if (zipFile == null || destDir == null) {
            return null;
        }
        if (keyword == null) {
            keyword = "";
        }
        List<File> files = new ArrayList<>();

        ZipFile zf = new ZipFile(zipFile);

        Enumeration<?> entries = zf.entries();
        try {
            if (!StringUtils.isSpace(keyword)) {
                while (entries.hasMoreElements()) {
                    ZipEntry entry = ((ZipEntry) entries.nextElement());
                    String entryName = entry.getName().replace("\\", "/");
                    if (entryName.contains("../")) {
                        KLog.e("ZipUtils", "entryName: " + entryName + " is dangerous!");
                        continue;
                    }
                    if (!unZipChildFile(destDir, files, zf, entry, entryName)) {
                        return files;
                    }
                }
            } else {
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String entryName = entry.getName().replace("\\", "/");
                    if (entryName.contains("../")) {
                        KLog.e("ZipUtils", "entryName: " + entryName + " is dangerous!");
                        continue;
                    }
                    if (entryName.contains(keyword)) {
                        if (!unZipChildFile(destDir, files, zf, entry, entryName)) {
                            return files;
                        }
                    }
                }
            }
        } finally {
            zf.close();
        }
        return files;
    }

    /**
     * 解压 子文件
     *
     * @param destDir 解压到目标文件路径
     * @param files   解缩后的文件
     * @param zf      ZipFile
     * @param entry   ZipEntry
     * @param name    文件名
     * @return {@code true}:成功 <br> {@code false}:失败
     * @throws IOException IO异常
     */
    private static boolean unZipChildFile(final File destDir, final List<File> files, final ZipFile zf, final ZipEntry entry, final String name) throws IOException {
        File file = new File(destDir, name);
        files.add(file);
        if (entry.isDirectory()) {
            return file.exists() ? file.isDirectory() : file.mkdirs();
        } else {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(zf.getInputStream(entry));
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        }
        return true;
    }

    /**
     * 返回文件的路径
     */
    public static List<String> getFilesPath(final String zipFilePath)
            throws IOException {
        return getFilesPath(new File(zipFilePath));
    }

    /**
     * 返回文件的路径
     */
    public static List<String> getFilesPath(final File zipFile)
            throws IOException {
        if (zipFile == null) return null;
        List<String> paths = new ArrayList<>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<?> entries = zip.entries();
        while (entries.hasMoreElements()) {
            String entryName = ((ZipEntry) entries.nextElement()).getName().replace("\\", "/");
            if (entryName.contains("../")) {
                KLog.e("ZipUtils", "entryName: " + entryName + " is dangerous!");
                paths.add(entryName);
            } else {
                paths.add(entryName);
            }
        }
        zip.close();
        return paths;
    }

    /**
     * 返回文件的注释
     */
    public static List<String> getComments(final String zipFilePath)
            throws IOException {
        return getComments(new File(zipFilePath));
    }

    /**
     * 返回文件的注释
     */
    public static List<String> getComments(final File zipFile)
            throws IOException {
        if (zipFile == null) return null;
        List<String> comments = new ArrayList<>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<?> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            comments.add(entry.getComment());
        }
        zip.close();
        return comments;
    }

    //========================================================================================

    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @return
     * @throws IOException IO异常
     */
    public static boolean unZipFile(final File zipFile, String folderPath) throws IOException {
        if (zipFile == null || folderPath == null) {
            return false;
        }
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);

        InputStream is = null;
        OutputStream os = null;
        try {
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                is = zf.getInputStream(entry);
                String str = folderPath + File.separator + entry.getName();
                str = new String(str.getBytes("8859_1"));
                File desFile = new File(str);
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                os = new FileOutputStream(desFile);
                byte buffer[] = new byte[BUFF_SIZE];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            zf.close();
        }
        return true;
    }

    /**
     * 解压文件名包含传入文字的文件
     *
     * @param zipFile      压缩文件
     * @param folderPath   目标文件夹
     * @param nameContains 传入的文件匹配名
     * @throws ZipException 压缩格式有误时抛出
     * @throws IOException  IO错误时抛出
     */
    public static ArrayList<File> unZipSelectedFile(File zipFile, String folderPath, String nameContains) throws ZipException,
            IOException {
        ArrayList<File> fileList = new ArrayList<File>();
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdir();
        }
        ZipFile zf = new ZipFile(zipFile);
        InputStream in = null;
        OutputStream out = null;
        try {
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                if (entry.getName().contains(nameContains)) {
                    in = zf.getInputStream(entry);
                    String str = folderPath + File.separator + entry.getName();
                    str = new String(str.getBytes("8859_1"));
                    // str.getBytes(AppConstans.UTF_8),"8859_1" 输出
                    // str.getBytes("8859_1"),AppConstans.UTF_8 输入
                    File desFile = new File(str);
                    if (!desFile.exists()) {
                        File fileParentDir = desFile.getParentFile();
                        if (!fileParentDir.exists()) {
                            fileParentDir.mkdirs();
                        }
                        desFile.createNewFile();
                    }
                    out = new FileOutputStream(desFile);
                    byte buffer[] = new byte[BUFF_SIZE];
                    int realLength;
                    while ((realLength = in.read(buffer)) > 0) {
                        out.write(buffer, 0, realLength);
                    }
                    fileList.add(desFile);
                }
            }
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
        return fileList;
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  当解压缩过程出错时抛出
     */
    public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
        ArrayList<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            entryNames.add(new String(getEntryName(entry).getBytes(), "8859_1"));
        }
        return entryNames;
    }

    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  IO操作有误时抛出
     */
    public static Enumeration<?> getEntriesEnumeration(File zipFile) throws ZipException, IOException {
        ZipFile zf = new ZipFile(zipFile);
        return zf.entries();
    }

    /**
     * 取得压缩文件对象的注释
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的注释
     * @throws UnsupportedEncodingException
     */
    public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getComment().getBytes(), "8859_1");
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     * @throws UnsupportedEncodingException
     */
    public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getName().getBytes(), "8859_1");
    }
}
