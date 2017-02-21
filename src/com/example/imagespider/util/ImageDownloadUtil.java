package com.example.imagespider.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ImageDownloadUtil {

	/**
	 * ����ָ����ַ��ͼƬ��ָ��Ŀ¼�����Զ������ļ���
	 * 
	 * @param url
	 *            ͼƬ��ַ
	 * @param savePath
	 *            ͼƬ����Ŀ¼
	 */
	public void downLoadImage(String url, String savePath) {
		String filePath = savePath;
		String fileName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		try {
			//תΪutf-8����
			fileName = URLDecoder.decode(fileName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String picType = url.substring(url.lastIndexOf(".") + 1);
		downLoadImage(url, filePath, fileName, picType);
	}

	private void downLoadImage(String url, String filePath, String fileName, String picType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		if (url == null || url.equals(""))
			return;
		// ����Ŀ���ļ�Ŀ¼
		File parentDir = new File(filePath);
		System.out.println("Save path:" + filePath);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		System.out.println("pic url:" + url);
		String fullPath = filePath + File.separator + fileName + "." + picType;
		File file = new File(fullPath);
		if (file.exists() && file.length() > 1000) {
			// ���ͼƬ�Ѿ�������������
			return;
		}
		// ��Դ��ַ����ͼƬ
		InputStream in = null;
		try {
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			in = entity.getContent();
			// �������ص�ַ
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
			}
			fout.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}