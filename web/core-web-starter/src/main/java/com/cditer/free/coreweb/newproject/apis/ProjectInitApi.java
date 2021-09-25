package com.cditer.free.coreweb.newproject.apis;

import cn.hutool.core.io.FileUtil;
import com.cditer.free.coreweb.newproject.helper.NewProjectHelper;
import com.cditer.free.coreweb.newproject.viewmodel.ProjectNewTemplate;
import com.cditer.free.coreweb.webapi.FirstApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Slf4j
@RestController
@RequestMapping(value = "/projectInit/")
public class ProjectInitApi extends FirstApi {

    @Autowired
    NewProjectHelper newProjectHelper;

    @PostMapping(value = "newTemplate")
    public void newTemplate(ProjectNewTemplate newTemplate){
        String zipPath = newProjectHelper.createNewProject(newTemplate);
        try {
            File file = new File(zipPath);
            HttpServletResponse response = getServletResponse();

            String contentType="application/octet-stream";
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(newTemplate.getProjectName() +".zip", "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());

            FileUtil.writeToStream(file, response.getOutputStream());

            file.delete();
        }catch (Exception ex){
            log.error("newTemplate error", ex);
        }
    }
}
