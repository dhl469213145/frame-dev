package com.framework.demo;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@Slf4j
public class DownTest {
    static Logger logger = Logger.getLogger(DownTest.class.getName());
    public static final int cache = 10 * 1024;

    public static void main(String[] args) throws Exception {
        FileInputStream inFile = new FileInputStream("d://sojson.com.json");

        InputStreamReader reader = new InputStreamReader(inFile,"UTF-8"); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
        BufferedReader br = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();
        reader.close();
       /* byte[] buf = new byte[1024]; //数据中转站 临时缓冲区
        int length = 0;
        //循环读取文件内容，输入流中将最多buf.length个字节的数据读入一个buf数组中,返回类型是读取到的字节数。
        //当文件读取到结尾时返回 -1,循环结束。

        StringBuilder stringBuilder = new StringBuilder();
        while((length = inFile.read(buf)) != -1){
            stringBuilder.append(new String(buf, 0, length));
        }*/
        String str = StringEscapeUtils.unescapeJava(stringBuilder.toString());
        str = str.replaceAll("\n", "").replaceAll("\t", "");
//        JsonBean strPojo = JSON.parseObject(str, JsonBean.class);
//        List<JsonRootBean> list =JSONObject.parseArray(str, JsonRootBean.class);
        List<JsonRootBean> jsonArray = JSONObject.parseArray(str, JsonRootBean.class);
//        System.out.println(jsonArray.toString());
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(str);
////        JSONArray strJson = JSONObject.parseArray(str);
        List<Map<String, String>> reuslt = new ArrayList<>();
        Set<String> reuslt2 = new HashSet<>();

        DownTest test = new DownTest();
        jsonArray.stream().forEach(jsonRootBean-> {
//            JSONObject dataObj = JSONObject.parseObject(e.toString());
//            Map<String, String> map = new HashMap<>();
//            map.put("id", String.valueOf(jsonRootBean.getId()));
//            map.put("name", jsonRootBean.getName());re

            reuslt2.add(jsonRootBean.getName());
            test.downLoad(String.valueOf(jsonRootBean.getId()), jsonRootBean.getName());
//            reuslt.add(map);
        });
        System.err.println("allSize=" + jsonArray.size());

        System.out.println(reuslt2.size());


//        test.downLoad("", "dd.pdf");
//        test.down2();

    }

    public void downLoad(String id, String localFileName) {
        System.err.println("下载开始...id=" + id+", name=" + localFileName);
        HttpClient client = new HttpClient();
        GetMethod get = null;
        FileOutputStream output = null;

        try {
            get = new GetMethod("https://amos.caac.gov.cn/amos/sfss/space/previewFile?folderId=" + id);
            get.setRequestHeader("Cookie", "SESSION=NTQ1ZjMxNjItYmFiYy00NDIyLThkZDUtYjQ1YjdiZTE5NjZi");

            int i = client.executeMethod(get);

            if (200 == i) {
                System.out.println("The response value of token:" + get.getResponseHeader("token"));

                File storeFile = new File(localFileName);
                output = new FileOutputStream(storeFile);

                // 得到网络资源的字节数组,并写入文件
                output.write(get.getResponseBody());
            } else {
                System.out.println("DownLoad file occurs exception, the error code is :" + i);
            }
        } catch (Exception e) {
//            logger.
//            e.printStackTrace();
        } finally {
            try {
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            get.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        System.err.println("下载end...id=" + id+", name=" + localFileName);
    }

   /* public void down2() throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("https://amos.caac.gov.cn/amos/sfss/space/previewFile?folderId=1244802");
        httpget.setHeader("Cookie", "SESSION=N2FjZjk3YTAtYzJhNS00ZGU5LWJlMmYtYmMyM2QxYjNlZWY2");
        HttpResponse response = client.execute(httpget);

        HttpEntity entity = response.getEntity();
        File storeFile = new File("~/test.pdf");


        FileOutputStream fileout = new FileOutputStream(storeFile);

        FileOutputStream output = new FileOutputStream(storeFile);

        // 得到网络资源的字节数组,并写入文件
//        output.write(entity);
//        InputStream is = entity.getContent();
//        if (filepath == null){
//            filepath = getFilePath(response);
//        }
    }*/

//    public void upload(String localFile){
//        File file = new File(localFile);
//        MultipartFile file = localFile;
//
//
//        PostMethod filePost = new PostMethod(URL_STR);
//        HttpClient client = new HttpClient();
//
//        try {
//            // 通过以下方法可以模拟页面参数提交
////            filePost.setParameter("userName", userName);
////            filePost.setParameter("passwd", passwd);
//
//            Part[] parts = { new FilePart(file.getName(), file) };
//            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
//
//            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//
//            int status = client.executeMethod(filePost);
//            if (status == HttpStatus.SC_OK) {
//                System.out.println("上传成功");
//            } else {
//                System.out.println("上传失败");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            filePost.releaseConnection();
//        }
//    }

    public void upload() {
        File file = new File("/Users/dhl/Downloads/中华人民共和国行政许可法 (1).pdf");
//        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
    }
}
