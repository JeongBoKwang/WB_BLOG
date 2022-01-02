package com.example.spring.demo.util;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class Ut {
	public static boolean empty(Object obj) {
		if(obj == null) {
			return true;
		}
		
		if(obj instanceof Integer) {
			return ((int)obj) == 0;
		}
		
		if(obj instanceof Long) {
			return ((long)obj) == 0;
		}
		
		if(obj instanceof String == false) {
			return true;
		}
		
		String str = (String)obj;
		
		return str.trim().length() == 0;
	}

	public static String f(String format, Object...args) {
		return String.format(format, args);
	}

	public static String jsHistoryBack(String msg) {
		StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
	}

	public static String jsReplace(String msg, String uri) {
		StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.replace('" + uri + "');");
        sb.append("</script>");

        return sb.toString();
	}
	
	public static String getUriEncoded(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }
	
	//현재로부터 ()시간뒤에 값을 보내라
	public static String getDateStrLater(long seconds) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format.format(System.currentTimeMillis() + seconds * 1000);

		return dateStr;
	}
	
	//변수값 랜덤코드 보내기
	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}
	
	//해시함수
	 public static String sha256(String base) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(base.getBytes("UTF-8"));
	            StringBuffer hexString = new StringBuffer();

	            for (int i = 0; i < hash.length; i++) {
	                String hex = Integer.toHexString(0xff & hash[i]);
	                if (hex.length() == 1)
	                    hexString.append('0');
	                hexString.append(hex);
	            }

	            return hexString.toString();

	        } catch (Exception ex) {
	            return "";
	        }
	    }

	public static Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> param = new HashMap<>();
		
		Enumeration<String> parameterNames = request.getParameterNames();
		
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			
			param.put(paramName, paramValue);
		}
		
		return param;
	}
	
	//로그인후 Uri에서 null 문장 리턴 버그 수정
	public static String getStrAttr(Map map, String attrName, String defaultValue) {
		if ( map.containsKey(attrName) ) {
			return (String)map.get(attrName);
		}
		
		return defaultValue;
	}

	public static Map<String, Object> mapOf(Object...args) {
		if (args.length % 2 != 0) {
            throw new IllegalArgumentException("인자를 짝수개 입력해주세요.");
        }

        int size = args.length / 2;

        Map<String, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < size; i++) {
            int keyIndex = i * 2;
            int valueIndex = keyIndex + 1;

            String key;
            Object value;

            try {
                key = (String) args[keyIndex];
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("키는 String으로 입력해야 합니다. " + e.getMessage());
            }

            value = args[valueIndex];

            map.put(key, value);
        }

        return map;
	}

	public static int getAsInt(Object object, int defaultValue) {
		if (object instanceof BigInteger) {
            return ((BigInteger) object).intValue();
        } else if (object instanceof Double) {
            return (int) Math.floor((double) object);
        } else if (object instanceof Float) {
            return (int) Math.floor((float) object);
        } else if (object instanceof Long) {
            return (int) object;
        } else if (object instanceof Integer) {
            return (int) object;
        } else if (object instanceof String) {
            return Integer.parseInt((String) object);
        }

        return defaultValue;
	}

	public static String getFileExtTypeCodeFromFileName(String fileName) {
		String ext = getFileExtFromFileName(fileName).toLowerCase();

        switch (ext) {
            case "jpeg":
            case "jpg":
            case "gif":
            case "png":
                return "img";
            case "mp4":
            case "avi":
            case "mov":
                return "video";
            case "mp3":
                return "audio";
        }

        return "etc";
	}

	public static String getFileExtType2CodeFromFileName(String fileName) {
		String ext = getFileExtFromFileName(fileName).toLowerCase();

        switch (ext) {
            case "jpeg":
            case "jpg":
                return "jpg";
            case "gif":
                return ext;
            case "png":
                return ext;
            case "mp4":
                return ext;
            case "mov":
                return ext;
            case "avi":
                return ext;
            case "mp3":
                return ext;
        }

        return "etc";
	}
	
	public static String getFileExtFromFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos + 1);

        return ext;
    }
	
	public static boolean isEmpty(Object data) {
        if (data == null) {
            return true;
        }

        if (data instanceof String) {
            String strData = (String) data;

            return strData.trim().length() == 0;
        } else if (data instanceof List) {
            List listData = (List) data;

            return listData.isEmpty();
        } else if (data instanceof Map) {
            Map mapData = (Map) data;

            return mapData.isEmpty();
        }

        return false;
    }
	public static <T> T ifEmpty(T data, T defaultValue) {
        if (isEmpty(data)) {
            return defaultValue;
        }

        return data;
    }

	public static List<Integer> getListDividedBy(String str, String divideBy) {
		return Arrays.asList(str.split(divideBy)).stream().map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
	}

	public static boolean deleteFile(String filePath) {
        java.io.File ioFile = new java.io.File(filePath);
        if (ioFile.exists()) {
            return ioFile.delete();
        }

        return true;
    }

	public static String getNowYearMonthDateStr() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM");

        String dateStr = format1.format(System.currentTimeMillis());

        return dateStr;
    }
}
