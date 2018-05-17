package com.yy.until;

/** 
 * @Title: FileHelper.java 
 * @Package com.tes.textsd 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Alex.Z 
 * @date 2013-2-26 下午5:45:40 
 * @version V1.0 
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class FileUntil {
	private Context context;
	/** SD卡是否存在 **/
	private boolean hasSD = false;
	/** SD卡的路径 **/
	private String SDPATH;
	/** 当前程序包的路径 **/
	private String FILESPATH;

	public FileUntil(Context context) {
		this.context = context;
		hasSD = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		SDPATH = Environment.getExternalStorageDirectory().getPath();
		FILESPATH = this.context.getFilesDir().getPath();
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + "//" + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 在SD卡上创建文件夹
	 * 
	 * @throws IOException
	 */
	public File createSDFolder(String folderName) {
		File file = new File(SDPATH, folderName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 递归删除文件夹（包含文件夹下所有文件和文件夹）
	 * 
	 * @param fileName
	 */
	public static void DeleteFile(File file) {
		if (file.exists() == false) {
			// 文件夹不存在
			return;
		} else {
			if (file.isFile()) {
				file.delete();
				return;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return;
				}
				for (File f : childFile) {
					DeleteFile(f);
				}
				file.delete();
			}
		}
	}

	/**
	 * 删除SD卡上的文件
	 * 
	 * @param fileName
	 */
	public boolean deleteSDFile(String fileName) {
		File file = new File(SDPATH + "//" + fileName);
		if (file == null || !file.exists() || file.isDirectory())
			return false;
		return file.delete();
	}

	/**
	 * 写入内容到SD卡中的txt文本中 str为内容
	 */
	public void writeSDFile(String str, String fileName) {
		try {
			FileWriter fw = new FileWriter(SDPATH + "//" + fileName);
			File f = new File(SDPATH + "//" + fileName);
			fw.write(str);
			FileOutputStream os = new FileOutputStream(f);
			DataOutputStream out = new DataOutputStream(os);
			out.writeShort(2);
			out.writeUTF("");
			System.out.println(out);
			fw.flush();
			fw.close();
			System.out.println(fw);
		} catch (Exception e) {
		}
	}

	/**
	 * 读取SD卡中文本文件
	 * 
	 * @param fileName
	 * @return
	 */
	public String readSDFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(SDPATH + "//" + fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			int c;
			while ((c = fis.read()) != -1) {
				sb.append((char) c);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String getFILESPATH() {
		return FILESPATH;
	}

	public String getSDPATH() {
		return SDPATH;
	}

	public boolean hasSD() {
		return hasSD;
	}
}
