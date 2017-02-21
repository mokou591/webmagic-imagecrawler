package com.example.imagespider.processor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.imagespider.content.Content;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class KonachanSearchPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(10).setSleepTime(5000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6");

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		// ���Դ���ͼƬ��ַ�ļ���
		Set<String> picUrlSet = new HashSet<String>();
		// ԭhtml��class���пո�������������ʽ
		List<String> list = page.getHtml().$("a.directlink", "href").all();
		for (String picUrl : list) {
			if (picUrl.trim().length() == 0) {
				continue;
			}
			picUrlSet.add(picUrl);
		}
		// �����ϱ��������Դ�����
		page.putField(Content.PICTURE_URL.toString(), picUrlSet);
	}

}
