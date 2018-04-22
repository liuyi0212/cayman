package com.medishare.cayman.view;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.medishare.cayman.common.JSONRet;
import com.medishare.cayman.domain.Image;
import com.medishare.cayman.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploadImageController extends BaseController{

	@Value("${link.path}")
	String url;
	
	@Autowired
	ImageService imageService;
	
	@ResponseBody
	@RequestMapping(path = "/upload/img/" ,method = RequestMethod.POST)
	public String uploadImg(HttpServletResponse resp,  @RequestParam("file") MultipartFile file) throws IOException {
		JSONRet res = new JSONRet();
		String imgUrl = uploadImg("大桥糖友会",file);
		Map<String, Object> m = new HashMap<>();
		m.put("imgUrl", imgUrl);
		res.setData(m);
		return res.toString();
	}
	
	/**
	 * 
	 * @param suffix 后缀
	 * @param type 描述
	 * @param file 文件
	 * @return
	 * @throws IOException
	 */
	public String uploadImg(
			String type,
			 MultipartFile file) throws IOException{

		String uuid = UUID.randomUUID().toString();
		String name = uuid  + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		Image img = new Image();
		img.setData(file.getBytes());
		img.setCreated(new Date());
		img.setUri(name);
		img.setDomain(type);
		img.setMemo("upload image");
		
		imageService.saveImage(img);
		String imgUrl = url + "/img/" + name;
		return imgUrl;
	}
}

