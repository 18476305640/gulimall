package demo.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目1：模式的使用
 * 完成编写testMethod方法代码,实现如下效果：
 * 当paramStr为A时，调用A类的aPrintln方法
 * 当paramStr为B时，调用B类的aPrintln方法
 * 当paramStr为C时，调用C类的aPrintln方法
 *
 * 不允许出现if，switch 语句 ，出现即为不合格.
 * 开卷考试，允许使用互联网查询相关帮助。
 * 本题考点：理解模式的使用。使用合理的模式能极大提高代码复用性和可维护性，同时能让程序员更好的梳理业务逻辑
 * @author 同略面试题
 *
 */
public class Test1 {


	private  static void testMethod(String a) {
		//在这里编写代码
		Map<String,printlnString> map = new HashMap<>();
		map.put("A",new A());
		map.put("B",new B());
		map.put("C",new C());
		printlnString p = map.get(a.toUpperCase());
		p.aPrintln(a);

	}


	public static void main(String[] param) {
		String paramStr = "A";
		testMethod(paramStr);
	}

}



class A implements printlnString{
	public void aPrintln(String a) {
		System.out.println("A类方法调用了");
		System.out.println(a);
	}
}

class B implements printlnString{
	public void aPrintln(String a) {
		System.out.println("B类方法调用了");
		System.out.println(a);
	}
}


class C implements printlnString{
	public void aPrintln(String a) {
		System.out.println("C类方法调用了");
		System.out.println(a);
	}
}
interface printlnString{
	public void aPrintln(String a);
} 
