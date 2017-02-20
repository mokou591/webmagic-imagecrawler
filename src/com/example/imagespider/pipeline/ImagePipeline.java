package com.example.imagespider.pipeline;

import java.io.File;
import java.util.Set;

import com.example.imagespider.content.Content;
import com.example.imagespider.processor.ImageProcessor;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * ���ͼƬ��ַ���ϣ������������Զ��̵߳�����ͼƬ
 *
 */
public class ImagePipeline implements Pipeline {

	private String savePath = "";

	public ImagePipeline(String savePath) {
		this.savePath = savePath;
		File rootPath = new File(savePath);
		rootPath.mkdirs();
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		// ��ȡ��Ҫ���ص�����
		Set<String> picUrlSet = (Set<String>) resultItems.get(Content.PICTURE_URL.toString());
		// ��������������ͼƬ
		Spider spider = Spider.create(new ImageProcessor(savePath));
		// ͬʱ����ͼƬ���߳���
		spider.thread(10);
		// ��Ӵ�ͼƬ��ַ
		for (String picUrl : picUrlSet) {
			spider.addUrl(picUrl);
		}
		spider.start();
	}
}
