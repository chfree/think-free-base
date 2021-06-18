package com.tennetcn.free.web.newproject.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.tennetcn.free.core.exception.BizException;
import com.tennetcn.free.web.configuration.ThinkWebConfig;
import com.tennetcn.free.web.newproject.viewmodel.ProjectNewTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.Charset;

@Component
public class NewProjectHelper {
//    private static final String ROOT_PATH="G:\\publish\\java\\idea\\think\\think-code-build\\devsource";
    private static final String PROJECT_TEMPLATE="newproject-template";

    private static final String domainPath = "common";

    @Autowired
    ThinkWebConfig thinkWebConfig;

    String rootPath = null;

    private void loadSetting(){
        if(!StringUtils.isEmpty(this.rootPath)){
            return;
        }
        String templatePath = thinkWebConfig.getTemplatePath();
        if(StringUtils.isEmpty(templatePath)){
            throw new BizException("think.path.template-path未正确配置");
        }
        if((templatePath.length()-1)!=templatePath.lastIndexOf(File.separator)){
            this.rootPath = templatePath + File.separator + domainPath;
        }else{
            this.rootPath = templatePath + domainPath;
        }
    }


    public String createNewProject(ProjectNewTemplate projectNewTemplate){

        // 加载配置
        loadSetting();

        // 创建用户根目录
        createUserPath(projectNewTemplate.getUserPath());

        // 复制文件夹及文件夹
        return copyTemplateProject(projectNewTemplate);
    }

    private String copyTemplateProject(ProjectNewTemplate projectNewTemplate){

        String userPathDisk = getUserPathDisk(projectNewTemplate.getUserPath());
        String srcPath = rootPath + File.separator + PROJECT_TEMPLATE + ".zip";
        if(!FileUtil.exist(srcPath)){
            throw new BizException(srcPath+"模板文件不存在");
        }
        String uuid = IdUtil.fastUUID();
        String unzipPath = rootPath + File.separator + uuid + File.separator;

        ZipUtil.unzip(srcPath, unzipPath);

        // 不能单纯的进行复制，如果先复制了，后面在进行重命名逻辑就比较麻烦
        // 将模板路径对应着用户主目录进行创建，然后路径下有直属根文件的进行复制，并重命名
        loopCopyFile(new File(unzipPath + "${name}"), uuid, userPathDisk,projectNewTemplate);

        FileUtil.del(unzipPath);

        ZipUtil.zip(userPathDisk);

        FileUtil.del(userPathDisk);

        return userPathDisk.substring(0, userPathDisk.lastIndexOf(File.separator)) + ".zip";
    }

    private void createUserPath(String userPath){
        String userPathDisk = getUserPathDisk(userPath);
        File path = new File(userPathDisk);
        if(path.exists()){
            return;
        }
        path.mkdirs();
    }

    private String getUserPathDisk(String userPath){
        return rootPath + File.separator + userPath + File.separator;
    }

    private void loopCopyFile(File srcFile,String tempId,String userPathDisk,ProjectNewTemplate projectNewTemplate){
        if(srcFile==null){
            return;
        }
        String pathName = srcFile.getPath().replace(tempId+File.separator,"");
        if(srcFile.isDirectory()){

            String newPath = convertNewPath(pathName, userPathDisk, projectNewTemplate);
            new File(newPath).mkdirs();

            File[] files = srcFile.listFiles();
            for (File file : files) {
                loopCopyFile(file, tempId, userPathDisk, projectNewTemplate);
            }
        }else if(srcFile.isFile()){
            String newPath = convertNewPath(pathName, userPathDisk, projectNewTemplate);
            newPath = newPath.replace("${Name}", StrUtil.upperFirst(projectNewTemplate.getProjectName()));

            rewriteContent(srcFile, new File(newPath), projectNewTemplate);
        }
    }

    private void rewriteContent(File file, File newFile,ProjectNewTemplate projectNewTemplate){
        String fileContent = FileUtil.readString(file, Charset.defaultCharset());
        if(StrUtil.isEmpty(fileContent)){
            return;
        }
        fileContent = replaceAll(fileContent, "groupId", projectNewTemplate.getGroupId());
        fileContent = replaceAll(fileContent, "artifactId", projectNewTemplate.getArtifactId());
        fileContent = replaceAll(fileContent, "name", projectNewTemplate.getProjectName());
        fileContent = replaceAll(fileContent, "Name", StrUtil.upperFirst(projectNewTemplate.getProjectName()));
        fileContent = replaceAll(fileContent, "email", projectNewTemplate.getEmail());
        fileContent = replaceAll(fileContent, "currentDate", DateUtil.format(projectNewTemplate.getCurrentDate(),"yyyy-MM-dd HH:mm:ss"));
        fileContent = replaceAll(fileContent, "author", projectNewTemplate.getAuthor());
        fileContent = replaceAll(fileContent, "srping-boot-version", projectNewTemplate.getSpringBootVersion());
        fileContent = replaceAll(fileContent, "free-version", projectNewTemplate.getFreeVersion());
        fileContent = replaceAll(fileContent, "version", projectNewTemplate.getVersion());
        fileContent = replaceAll(fileContent, "packageName", projectNewTemplate.getPackageName());
        fileContent = replaceAll(fileContent, "javaVersion", projectNewTemplate.getJavaVersion());
        fileContent = replaceAll(fileContent, "description", projectNewTemplate.getDescription());

        FileUtil.writeString(fileContent, newFile,Charset.defaultCharset());
    }

    private String convertNewPath(String path,String userPathDisk,ProjectNewTemplate projectNewTemplate){
        return path.replace(rootPath, userPathDisk).replace("${name}", projectNewTemplate.getProjectName()).replace("${packageName}", projectNewTemplate.getPackageName());
    }

    private String replaceAll(String content,String key,String value){
        return content.replaceAll("\\$\\{"+key+"\\}", (StrUtil.isEmpty(value)? "": value));
    }
}
