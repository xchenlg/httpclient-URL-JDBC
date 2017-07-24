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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * Provides encoding of raw bytes to base64-encoded characters, and
 * decoding of base64 characters to raw bytes.
 * date: 06 August 1998
 * modified: 14 February 2000
 * modified: 22 September 2000
 *
 * @author Kevin Kelley (kelley@ruralnet.net)
 * @version 1.3
 */
public class HttpClientUtil1 {
	
	public static void main(String[] args) {
		
	    HttpClientUtil1 u = new HttpClientUtil1();
		u.loginDiscuz();
	}

    public void loginDiscuz() {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost("http://localhost/523/dataProcessController/dataGrid_marc");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
        httpPost.setConfig(requestConfig);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("page", "1"));
        formparams.add(new BasicNameValuePair("rows", "10"));

        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            
            HttpResponse httpResponse;
            // post请求
            httpResponse = closeableHttpClient.execute(httpPost);

            // getEntity()
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
//                JSONObject o = JSONObject.fromObject(EntityUtils.toString(httpEntity, "UTF-8"));
//                FcBean fc = (FcBean) JSONObject.toBean(o, FcBean.class);
//                System.out.println(fc.getName()+fc.getNatureStr());
                String beans = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println("response:" + beans);
                JSONArray ja = JSONArray.fromObject(beans);
                @SuppressWarnings("unchecked")
				List<FcBean> persons = (List<FcBean>)JSONArray.toCollection(ja, FcBean.class);
                System.out.println(persons.size());
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}