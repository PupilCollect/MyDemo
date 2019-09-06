package com.collect.library.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public ZipUtils() {
    }

    /**
     * 以输入流的形式解压
     */
    public static void UnZipFolder(InputStream zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(zipFileString);
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(outPathString + File.separator + szName);
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    /**
     * DeCompress the ZIP to the path
     * 以文件形式解压
     *
     * @param zipFileString name of ZIP
     * @param outPathString path to be unZIP
     */
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(
                zipFileString));

        String substring = zipFileString.substring(zipFileString.lastIndexOf("/") + 1, zipFileString.lastIndexOf("."));
        File filePath = new File(outPathString, substring);
        if (!filePath.exists()) {
            filePath.mkdirs();
            outPathString = filePath.getAbsolutePath();
        }

        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                szName = szName.substring(szName.lastIndexOf("/") + 1);
                File file = new File(outPathString + File.separator + szName);
                // file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    /**
     * Compress file and folder  压缩文件和文件夹
     *
     * @param srcFileString file or folder to be Compress  要压缩的文件或文件夹
     * @param zipFileString the path name of result ZIP  压缩完成的Zip路径
     */
    public static void ZipFolder(String srcFileString, String zipFileString) throws Exception {
        // create ZIP
        FileOutputStream out = new FileOutputStream(zipFileString);
        ZipOutputStream outZip = new ZipOutputStream(out);
        // create the file
        File file = new File(srcFileString);
        // compress
        ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
        // finish and close
        outZip.finish();
        outZip.close();
    }

    /**
     * compress files
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
            // folder
            String fileList[] = file.list();
            // no child file and compress
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            // child files and recursion
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString, fileString + File.separator
                        + fileList[i], zipOutputSteam);
            }// end of for
        }
    }

    /**
     * return the InputStream of file in the ZIP
     *
     * @param zipFileString name of ZIP
     * @param fileString    name of file in the ZIP
     * @return InputStream
     */
    public static InputStream UpZip(String zipFileString, String fileString) throws Exception {
        ZipFile zipFile = new ZipFile(zipFileString);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
        return zipFile.getInputStream(zipEntry);
    }

    /**
     * return files list(file and folder) in the ZIP
     *
     * @param zipFileString  ZIP name
     * @param bContainFolder contain folder or not
     * @param bContainFile   contain file or not
     * @return
     */
    public static List<File> GetFileList(String zipFileString, boolean bContainFolder, boolean bContainFile) throws Exception {
        List<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(
                zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(szName);
                if (bContainFolder) {
                    fileList.add(folder);
                }

            } else {
                File file = new File(szName);
                if (bContainFile) {
                    fileList.add(file);
                }
            }
        }
        inZip.close();
        return fileList;
    }

}
