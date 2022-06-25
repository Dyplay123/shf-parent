package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    private final static String PAGE_UPLOED_SHOW = "house/upload";
    private static final String DETAIL_ACTION = "redirect:/house/";

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseService houseService;

    @GetMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Model model) {
        model.addAttribute("houseId", houseId);
        model.addAttribute("type", type);
        return PAGE_UPLOED_SHOW;
    }


    @ResponseBody
    @PostMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) throws IOException {

        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];
            String originalFilename = file.getOriginalFilename();

            String uuidName = FileUtil.getUUIDName(originalFilename);

            QiniuUtils.upload2Qiniu(file.getBytes(), uuidName);

            String url = QiniuUtils.getUrl(uuidName);

            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setImageName(uuidName);
            houseImage.setImageUrl(url);
            houseImage.setType(type);

            houseImageService.insert(houseImage);
            if (i == 0) {

                House house = houseService.getById(houseId);

                if (house.getDefaultImageUrl() == null || "".equals(house.getDefaultImageUrl())|| "null".equals(house.getDefaultImageUrl()) ) {

                    house.setDefaultImageUrl(url);
                   //更Gai数据库中的房源信息
                    houseService.update(house);
                }
            }
        }

        //2. 返回Result.ok()表示上传成功
        return Result.ok();

    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id,
                         Model model){
        //七牛云删除
        HouseImage houseImage = houseImageService.getById(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        //数据库删除
        houseImageService.delete(id);
        House house = houseService.getById(houseId);
        if (houseImage.getImageUrl().equals(house.getDefaultImageUrl())){
            house.setDefaultImageUrl("null");
            houseService.update(house);
        }
        return  DETAIL_ACTION + houseId;



    }


}


