package com.mail.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.common.util.mail.MailUtil;
import freemarker.template.TemplateException;

/**
 * 邮件工具测试类
 * 
 * @date 2014年4月26日 下午3:37:11
 * @author 曹志飞
 * @Description:
 * @project mailUtil
 */
public class MailUtilTest {
	private static Logger log = Logger.getLogger(MailUtilTest.class);

	@Test
	public void testMailTemplate() {
		String templateName = "template_1.ftl";
		Map<String, String> map = new HashMap<String, String>();
		map.put("content", "test");
		try {
			MailUtil.sendMailByTemplate("529352479@qq.com", "test", map,
					templateName);
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (TemplateException e) {
			log.error(e.toString(), e);
		} catch (MessagingException e) {
			log.error(e.toString(), e);
		}
	}

	@Test
	public void testMail() {
		try {
			MailUtil.sendMail("529352479@qq.com", "test", "普通邮件");
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (MessagingException e) {
			log.error(e.toString(), e);
		}
	}

	@Test
	public void testMailAndFile() {
		try {
			String filePath = "d:/data.zip";
			MailUtil.sendMailAndFile("529352479@qq.com", "test", "普通邮件",
					filePath);
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (MessagingException e) {
			log.error(e.toString(), e);
		}
	}
}
