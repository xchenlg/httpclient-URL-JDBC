package cn.com.infcn.batch;

import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.sender.FilePostSender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PdfToWordNuts {

    private static final String API_CONVERT = "http://192.168.10.23:8088/api/PDFApi/convert";

    public static void main(String[] args) throws IOException {
        String input = "C:\\Users\\chenlige\\Desktop\\pdf\\2.pdf";
        String output = "C:\\Users\\chenlige\\Desktop\\pdf\\2.docx";

        Request req = Request.create(API_CONVERT, Request.METHOD.POST);
        req.getParams().put("file", new File(input));

        // PDF转WORD
        req.getParams().put("targetType", "docx");

        /*// PDF转HTML
        req.getParams().put("targetType", "html");
        req.getParams().put("isZip", "true");*/

        Response res = new FilePostSender(req).setTimeout(60 * 60 * 1000).send();
        if (res.isOK()) {
            try (InputStream is = res.getStream()) {
                Files.copy(is, Paths.get(output));
            }
        } else {
            // 转换失败
            System.out.println(res.getContent());
        }
    }
}
