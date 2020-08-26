package org.blockchain.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.blockchain.wallet.base.BaseResponse;
import org.blockchain.wallet.base.ResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;

/**
 * @author hxy
 */
@RestController
@RequestMapping(value = "/update")
@RequiredArgsConstructor
public class AppUpdateController {

    @Value("${app.update.xml}")
    String xmlFilePath;

    @Value("${app.update.apk}")
    String apkFilePath;

    @RequestMapping(value = "/xml")
    public ResponseEntity<FileSystemResource> getUpdateXml() throws Exception {
        File file = new File(xmlFilePath);
        return export(file);
    }

    @RequestMapping(value = "/version")
    public BaseResponse<String> getVersion() throws Exception{

        return new ResultResponse<>(readVersion());
    }

    @RequestMapping(value = "/apk")
    public ResponseEntity<FileSystemResource> getUpdateApk() throws Exception{
        File file = new File(apkFilePath);
        return export(file);
    }

    public ResponseEntity<FileSystemResource> export(File file) throws Exception {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + "hd-wallet_v_" + readVersion().replace('.', '_') + ".apk");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

    private String readVersion() throws Exception{
        File file = new File(xmlFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        String version = doc.getElementsByTagName("version").item(0).getFirstChild().getNodeValue();
        String result = "";
        for(int i=0;i<6;i+=2) {
            result += Integer.valueOf(version.substring(i, i+2));
            if (i != 4) {
                result +=".";
            }
        }
        return result;
    }
}
