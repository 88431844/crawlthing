// Copyright 2018 Mobvoi Inc. All Rights Reserved.

package fun.iotgo.crawlthing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtil {

  public static String getStringNumber(String str){
    String regEx="[^0-9]";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    String sum = m.replaceAll("").trim();
    if (Integer.parseInt(sum) < 10){
      sum = "0" + sum;
    }
    return sum;
  }
}