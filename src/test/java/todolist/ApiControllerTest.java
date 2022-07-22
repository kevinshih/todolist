package todolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;

//import org.junit.jupiter.api.Test;

class ApiControllerTest {

	//@Test
	void test() throws Exception {
//		ApiController a = new ApiController();
//		List<PrintListAndItem> list = a.getMyAllListAndItemByUserid(0);
//		System.out.println(list.get(0).getListName());
//		System.out.println(list.get(0).getDueDate());
//		System.out.println(list.get(0).getItemName());
//		System.out.println(list.get(0).getDescription());
//		System.out.println(list.get(0).getDeadline());
//		System.out.println(list.get(0).getSortid());
//		String temp = "mp3, mp4";
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		DateFormat df = DateFormat.getDateInstance();
//		Date date = df.parse("2022-03-31 00:00:00");
//		Date date2 = df.parse("2022-03-30 00:00:00");
//		PrintListAndItem ans = new PrintListAndItem("projectA",date,"upload file",temp,date2,0);
//		assertEquals(ans, list.get(0));
	}
	public static void main(String[] args) throws Exception {
		short num=1;
		num=num+1;
		short num2=1;num2+=1;
		System.out.println( aa() & bb());
        System.out.println( aa() && bb());
    }
	
	public static boolean aa() {
		System.out.println( "456");
		return true;
	}
	public static boolean bb() {
		System.out.println( "123");
		return false;
	}
	private static long time = System.currentTimeMillis();
	private static int createTokenRate=3;
	private static int size = 10;
	private static int tokens = 0;
	private static boolean grant() {
		long now = System.currentTimeMillis();
		long tag = now-time;
		System.out.println("tag:"+tag);
		int in = (int)((tag)/50*createTokenRate);
		System.out.println("in:"+in);
		tokens = Math.min(size, tokens+in);
		time = now;
		if(tokens >0) {
			--tokens;
			return true;
		}else {
			return false;
		}
	}
	
	private int width = 140;
	private int height = 32;
	private int length = 6;
	public static final String VERIFICATION_CODE_NAME = "oneka.okmarket.verification.code";
	private char[] generateCheckCode() {
		// 定義驗證碼的字符集
		String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		char[] rands = new char[this.length];
		for (int i = 0; i < this.length; i++) {
			int rand = (int) (Math.random() * chars.length());
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}
}
