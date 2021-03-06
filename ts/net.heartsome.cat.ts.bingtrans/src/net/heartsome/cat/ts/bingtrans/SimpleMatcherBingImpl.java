/**
 * SimpleMatcherBingImpl.java
 *
 * Version information :
 *
 * Date:2012-5-13
 *
 * Copyright notice :
 * 本文件及其附带的相关文件包含机密信息，仅限瀚特盛科技有限公司指定的，与本项目有关的内部人员和客户联络人员查阅和使用。 
 * 如果您不是本保密声明中指定的收件者，请立即销毁本文件，禁止对本文件或根据本文件中的内容采取任何其他行动， 
 * 包括但不限于：泄露本文件中的信息、以任何方式制作本文件全部或部分内容之副本、将本文件或其相关副本提供给任何其他人。
 */
package net.heartsome.cat.ts.bingtrans;

import net.heartsome.cat.ts.bingtrans.bean.PrefrenceParameters;
import net.heartsome.cat.ts.tm.bean.TransUnitInfo2TranslationBean;
import net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher;

import com.memetix.mst.translate.Translate;

/**
 * http://code.google.com/p/microsoft-translator-java-api/
 * @author jason
 * @version
 * @since JDK1.6
 */
public class SimpleMatcherBingImpl implements ISimpleMatcher {

	private PrefrenceParameters parameters = PrefrenceParameters.getInstance();
	private String type = "Bing";
	private String toolId = "Bing Translation";
	private String origin = "Bing Translation";

	/**
	 * 
	 */
	public SimpleMatcherBingImpl() {
	}

	/**
	 * (non-Javadoc)
	 * @see net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher#getMatcherOrigin()
	 */
	public String getMatcherType() {
		return type;
	}

	/**
	 * (non-Javadoc)
	 * @see net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher#getMathcerToolId()
	 */
	public String getMathcerToolId() {
		return toolId;
	}

	/**
	 * (non-Javadoc)
	 * @see net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher#matchChecker()
	 */
	public boolean matchChecker() {
		if (!parameters.getState()) {
			return false;
		}
		if (parameters.isNeverAccess()) {
			return false;
		}
		return true;
	}

	// private boolean validator() {
	// try {
	// String result = Translate.execute("test", BingTransUtils.processLanguage("en-US"),
	// BingTransUtils.processLanguage("zh-CN"));
	// if (result.equals("测试")) {
	// return true;
	// }
	// } catch (Exception e) {
	// return false;
	// }
	//
	// return false;
	// }

	/**
	 * (non-Javadoc)
	 * @see net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher#executeMatch(net.heartsome.cat.ts.tm.bean.TransUnitInfo2TranslationBean)
	 */
	public String executeMatch(TransUnitInfo2TranslationBean tuInfo) {
		try {
			String srcText = tuInfo.getSrcPureText();
			String srcLang = tuInfo.getSrcLanguage();
			String tgtLang = tuInfo.getTgtLangugage();
			String bingId = parameters.getId();
			String bingKey = parameters.getKey();
			Translate.setClientId(bingId);
			Translate.setClientSecret(bingKey);
			String result = Translate.execute(srcText, BingTransUtils.processLanguage(srcLang),
					BingTransUtils.processLanguage(tgtLang));
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	/**
	 * (non-Javadoc)
	 * @see net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher#isSuportPreTrans()
	 */
	public boolean isSuportPreTrans() {
		if(parameters != null){
			return parameters.isSuportPreTrans() && parameters.getState();
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * @see net.heartsome.cat.ts.tm.simpleMatch.ISimpleMatcher#isOverwriteMatch()
	 */
	public boolean isOverwriteMatch() {
		return parameters.isAlwaysAccess();
	}

	public String getMathcerOrigin() {
		return origin;
	}

	public static void main(String arg[]) {
		Translate.setClientId("Bing_Client_ID");
		Translate.setClientSecret("tEIlN/kkAFGFaNve4Q3q85sB6QARrlf2pMyBkuIgTjs=");
		String result;
		try {
			result = Translate.execute(" Obey all safety messages that follow this symbol to avoid possible injury or death.", BingTransUtils.processLanguage("en-US"), BingTransUtils.processLanguage("zh-CN"));
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
