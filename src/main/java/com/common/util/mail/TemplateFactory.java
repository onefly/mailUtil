package com.common.util.mail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mail.test.MailUtilTest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 邮件内容模板生产工厂类
 * 
 * @date 2014年4月26日 下午3:06:04
 * @author 曹志飞
 * @Description:
 * @project mailUtil
 */
public class TemplateFactory {
	// 日志记录对象
	private static Logger log = LoggerFactory.getLogger(MailUtilTest.class);
	// 模板文件路径
	private static String templatePath = "/template";
	// 模板文件内容编码
	private static final String ENCODING = "utf-8";
	// 模板生成配置
	private static Configuration conf = new Configuration();
	// 邮件模板缓存池
	private static Map<String, Template> tempMap = new HashMap<String, Template>();
	static {
		// 设置模板文件路径
		conf.setClassForTemplateLoading(TemplateFactory.class, templatePath);
	}

	/**
	 * 通过模板文件名称获取指定模板
	 * 
	 * @Date:2014年4月26日 下午3:09:05
	 * @author 曹志飞
	 * @param name
	 *            模板文件名称
	 * @return Template 模板对象
	 * @throws IOException
	 * @Description:
	 * @return Template
	 */
	private static Template getTemplateByName(String name) throws IOException {
		if (tempMap.containsKey(name)) {
			log.debug("the template is already exist in the map,template name :"
					+ name);
			// 缓存中有该模板直接返回
			return tempMap.get(name);
		}
		// 缓存中没有该模板时，生成新模板并放入缓存中
		Template temp = conf.getTemplate(name);
		tempMap.put(name, temp);
		log.debug("the template is not found  in the map,template name :"
				+ name);
		return temp;
	}

	/**
	 * 根据指定模板将内容输出到控制台
	 * 
	 * @Date:2014年4月26日 下午3:14:18
	 * @author 曹志飞
	 * @param name
	 *            模板文件名称
	 * @param map
	 *            与模板内容转换对象
	 * @Description:
	 * @return void
	 */
	public static void outputToConsole(String name, Map<String, String> map) {
		try {
			// 通过Template可以将模板文件输出到相应的流
			Template temp = getTemplateByName(name);
			temp.setEncoding(ENCODING);
			temp.process(map, new PrintWriter(System.out));
		} catch (TemplateException e) {
			log.error(e.toString(), e);
		} catch (IOException e) {
			log.error(e.toString(), e);
		}
	}

	/**
	 * 根据指定模板将内容直接输出到文件
	 * 
	 * @Date:2014年4月26日 下午3:20:15
	 * @author 曹志飞
	 * @param name
	 *            模板文件名称
	 * @param map
	 *            与模板内容转换对象
	 * @param outFile
	 *            输出的文件绝对路径
	 * @Description:
	 * @return void
	 */
	public static void outputToFile(String name, Map<String, String> map,
			String outFile) {
		FileWriter out = null;
		try {
			out = new FileWriter(new File(outFile));
			Template temp = getTemplateByName(name);
			temp.setEncoding(ENCODING);
			temp.process(map, out);
		} catch (IOException e) {
			log.error(e.toString(), e);
		} catch (TemplateException e) {
			log.error(e.toString(), e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error(e.toString(), e);
			}
		}
	}

	/**
	 * 
	 * @Date:2014年4月26日 下午3:24:37
	 * @author 曹志飞
	 * @param name
	 *            模板文件的名称
	 * @param map
	 *            与模板内容转换对象
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 * @Description:
	 * @return String
	 */
	public static String generateHtmlFromFtl(String name,
			Map<String, String> map) throws IOException, TemplateException {
		Writer out = new StringWriter(2048);
		Template temp = getTemplateByName(name);
		temp.setEncoding(ENCODING);
		temp.process(map, out);
		return out.toString();
	}
}
