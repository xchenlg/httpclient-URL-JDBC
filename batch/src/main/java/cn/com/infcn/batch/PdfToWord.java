package cn.com.infcn.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PdfToWord {

    public static void main(String[] args) throws Exception {
        URL localURL = new URL("http://192.168.10.23:8088/api/PDFApi/convert");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoInput(true);
        // 设置是否向httpUrlConnection输出
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;"); 
        
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        
        try {
            inputStream = httpURLConnection.getInputStream();
            File f = new File("C:\\Users\\chenlige\\Desktop\\pdf\\1.docx");
//            copy(inputStream, f);
            try (InputStream is = inputStream) {
                Files.copy(is, Paths.get("C:\\Users\\chenlige\\Desktop\\pdf\\1.docx"));
            }
//            inputStreamReader = new InputStreamReader(inputStream);
//            reader = new BufferedReader(inputStreamReader);
//            
//            while ((tempLine = reader.readLine()) != null) {
//                resultBuffer.append(tempLine);
//            }
            
        } finally {
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }
        System.out.println(resultBuffer.toString());

    }
    
    
    public static void copy(InputStream in, File f) throws IOException {
        OutputStream out = new FileOutputStream(f);
        byte buf[]=new byte[1024];
        int len;
        while((len=in.read(buf))>0)
            out.write(buf,0,len);
        out.close();
        in.close();
    }
}
