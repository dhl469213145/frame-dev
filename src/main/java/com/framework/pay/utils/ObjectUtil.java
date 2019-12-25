/**
 * Copyright (c) 2018-2028, DreamLu 卢春梦 (qq596392912@gmail.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.pay.utils;

import org.springframework.lang.Nullable;

import java.util.Random;

/**
 * 对象工具类
 *
 * @author L.cm
 */
public class ObjectUtil extends org.springframework.util.ObjectUtils {

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtil.isEmpty(obj);
	}

	/**
	 * 电话号码转码
	 * */
	public static   String  replaceMobile(String mobile){
		String str = "****";
		StringBuilder sb = new StringBuilder(mobile);
		return sb.replace(3, 7, str).toString();
	}

	/**
	 * 随机渠道code
	 * */
	public static  String  randomCode(){
		String []str2=new String[5];
		String str3="";
		int str=0;
		char str1=0;
		for (int i=0;i<str2.length;i++){
			Random random =new Random();
			str=random .nextInt(58)+65;
			str1=(char)str;
			if (str>=65&&str<=90||str>=97&&str<=122){
				str3=str1 + str3;
			}
		}
		return str3;
	}
}
