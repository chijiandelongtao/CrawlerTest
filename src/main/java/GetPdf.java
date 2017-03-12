import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhuqiuhui on 2017/3/5.
 */
public class GetPdf {

    public void downLoadPdf(String urlStr, String fileName, String savePath) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
//        conn.setRequestProperty("","");

        inputStream = conn.getInputStream();
        byte[] getData = readInputStream(inputStream);

        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }


        System.out.println("info:" + url + " download success！");
    }

    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public String getFileName(String s){
        int index1 = s.lastIndexOf('/');
        String subStr1 = s.substring(index1);
        int index2 = subStr1.indexOf('.');
        String subStr2 = subStr1.substring(1,index2);
        return subStr2;
    }

//    public static void main(String[] args) {
//        String urlStr = "http://wwwconference.org/www2008/papers/pdf/p247-zhengA.pdf";
//        String fileName = "transportation.pdf";
//        String savePath = "/Users/zhuqiuhui/zqh/Project/pdf";
//        try {
//            downLoadPdf(urlStr, fileName, savePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
