/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * -------------------------------------
 * AbstractElementDefinitionHandler.java
 * -------------------------------------
 * (C)opyright 2003, by Thomas Morgner and Contributors.
 *
 * Original Author:  Kevin Kelley <kelley@ruralnet.net> -
 *                   30718 Rd. 28, La Junta, CO, 81050  USA.                                                         //
 *
 * $Id: Base64.java,v 1.5 2004/01/01 23:59:29 mungady Exp $
 *
 * Changes
 * -------------------------
 * 23.09.2003 : Initial version
 *
 */
package cn.com.infcn.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * pdf
 */
public class PdfClientUtil {
	
	public static void main(String[] args) {
		
		PdfClientUtil u = new PdfClientUtil();
		u.pdftoword();
	}

    public void pdftoword() {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost("http://192.168.10.23:8088/api/PDFApi/convert");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.MAX_VALUE).setConnectTimeout(Integer.MAX_VALUE).build();
        httpPost.setConfig(requestConfig);
        // 创建参数队列
//        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//        formparams.add(new BasicNameValuePair("targetType", "docx"));

        FileBody bin = new FileBody(new File("C:\\Users\\chenlige\\Desktop\\pdf\\1.PDF"));  

        StringBody comment = new StringBody("docx", ContentType.TEXT_PLAIN);  

        HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("targetType", comment).build();  

        
//        UrlEncodedFormEntity entity;
        try {
//            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(reqEntity);

            HttpResponse httpResponse;
            // post请求
            httpResponse = closeableHttpClient.execute(httpPost);

            // getEntity()
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                InputStream is = httpEntity.getContent();
                File f = new File("C:\\Users\\chenlige\\Desktop\\pdf\\1.docx");
                copy(is, f);
               //关闭输入流  
               is.close();  

            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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