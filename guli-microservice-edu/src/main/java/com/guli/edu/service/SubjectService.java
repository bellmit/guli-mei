package com.guli.edu.service;

import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
public interface SubjectService extends IService<Subject> {

    /**
     *文件批量导入
     * @param file
     * @return
     */
    List<String> batchImport(MultipartFile file);

    /**
     * 获取课程类树结构
     * @return
     */
    List<SubjectVo> getNestedTreeList();

    /**
     * 添加一级分类
     * @param subject
     * @return
     */
    boolean saveSubjectLevelOne(Subject subject);

    /**
     * 删除分类
     * @param id
     * @return
     */
    boolean removeById(String id);

    /**
     * 添加二级分类
     * @param subject
     * @return
     */
    boolean saveSubjectLevelTwo(Subject subject);
}
