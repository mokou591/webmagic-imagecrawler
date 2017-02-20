package com.example.imagespider;

import us.codecraft.webmagic.Spider;

import com.example.imagespider.pipeline.ImagePipeline;
import com.example.imagespider.processor.WallhavenSearchPageProcessor;

public class WallhavenSpider {
	public static void main(String[] args) {
		String savePath = "e:/wallhaven/";
		Spider spider = Spider.create(new WallhavenSearchPageProcessor());
		spider.thread(3);
		spider.addPipeline(new ImagePipeline(savePath));
		// �����˼�������ҳ��
		for (int i = 1; i <= 5; ++i) {
			spider.addUrl("https://alpha.wallhaven.cc/search?q=touhou&categories=010&page=" + i);
		}
		// ����
		spider.start();
	}
}