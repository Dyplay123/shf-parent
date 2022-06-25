package com.atguigu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class TestFile {

  @Test
  public void test01(){

    //构造一个带指定 Region 对象的配置类
    Configuration cfg = new Configuration(Zone.zone2());
    //...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);
    //...生成上传凭证，然后准备上传
    String accessKey = "kzIVsUeVkR2HjP5h2HAXwfB39TGwSSrLK3sv48zq";
    String secretKey = "uQVLONnpuElZpbTV4mbdN8t_fvy9ToIMvaFaijR8";
    String bucket = "dyplay";
    //如果是Windows情况下，格式是 D:\\qiniu\\test.png
    String localFilePath = "C:\\Users\\dypiay\\Desktop\\图片\\111.jpg";
    //默认不指定key的情况下，以文件内容的hash值作为文件名
    String key = null;
    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);


        try {
        //真正执行文件上传，并且获取到响应结果
        Response response = uploadManager.put(localFilePath, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        //打印上传文件在七牛云中的名字
        System.out.println(putRet.key);
        //打印上传文件的hash值
        System.out.println(putRet.hash);
    } catch (QiniuException ex) {
        //如果出现异常，打印异常信息
        Response r = ex.response;
        System.err.println(r.toString());
        try {
            System.err.println(r.bodyString());
        } catch (QiniuException ex2) {
            //ignore
        }
    }
  }

  @Test
  public void test02(){
      //构造一个带指定 Zone 对象的配置类
      Configuration cfg = new Configuration(Zone.zone2());
      String accessKey = "kzIVsUeVkR2HjP5h2HAXwfB39TGwSSrLK3sv48zq";
      String secretKey = "uQVLONnpuElZpbTV4mbdN8t_fvy9ToIMvaFaijR8";
      String bucket = "dyplay";

      //要删除的那个文件在七牛云中的路径(名字)
      String key = "cedd0c08115a70a94f7a768495594af.jpg";

      //鉴权对象
      Auth auth = Auth.create(accessKey, secretKey);
      //创建控件管理器对象
      BucketManager bucketManager = new BucketManager(auth, cfg);
      try {
          //删除空间中的文件
          bucketManager.delete(bucket, key);
      } catch (QiniuException ex) {
          //如果遇到异常，说明删除失败
          System.err.println(ex.code());
          System.err.println(ex.response.toString());
      }
  }
}
