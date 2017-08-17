package com.jcs.md5sign;

import java.security.MessageDigest;

public class MD5
{
  private static final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };

  public static String hexdigest(String paramString)
  {
	  try
    {
      String str = hexdigest(paramString.getBytes());
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String hexdigest(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();

      char[] arrayOfChar = new char[32];
      int i = 0;
      int j = 0;
      for(i=0;i<16;i++){
      
      int k = arrayOfByte[i];
      int l = j + 1;
      arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
      j = l + 1;
      arrayOfChar[l] = hexDigits[(k & 0xF)];
      }
      if (i >= 16)
          return new String(arrayOfChar);
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
    return null;
  }
}