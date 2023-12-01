package com.ze.Controller;

import com.ze.Pojo.Result;
import com.ze.Utils.AliOSSUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        //保证文件名是一唯一的，如果文件名一杨会发生覆盖
        String name = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //把文件内容存储到本地磁盘上
      //  file.transferTo(new File("C:\\Users\\ztz\\Desktop\\Fils\\"+name));
        String url = AliOSSUtils.UploadFile(name,file.getInputStream());
        return Result.success(url);
    }
}
