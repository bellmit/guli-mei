package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.util.ExcelImportUtil;
import com.guli.edu.entity.Subject;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.SubjectService;
import com.guli.edu.vo.SubSubjectVo;
import com.guli.edu.vo.SubjectVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-02-23
 */
@Service
@Slf4j
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private CourseService courseService;

    @Transactional
    @Override
    public List<String> batchImport(MultipartFile file) {
        List<String> msg = new ArrayList<>();
        try {
            ExcelImportUtil excelImportUtil = new ExcelImportUtil(file.getInputStream());
            Sheet sheet = excelImportUtil.getSheet();
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            if (numberOfRows <=1){
                msg.add("请填写数据");
                return msg;
            }
            for (int rowNum = 1; rowNum < numberOfRows; rowNum++) {
                Row rowData = sheet.getRow(rowNum);
                if (rowData != null) {
                    String levelOneValue = "";
                    Cell levelOneCell = rowData.getCell(0);
                    if (levelOneCell != null) {
                      levelOneValue = excelImportUtil.getCellValue(levelOneCell);
                        if (StringUtils.isEmpty(levelOneValue)) {
                            msg.add("第"+rowNum+"行一级分类为空");
                            continue;
                        }
                    }
              Subject subject = this.getByTitle(levelOneValue);
                    Subject subjectLevelOne = null;
                    String parentId = null;
                    if (subject == null) {
                        subjectLevelOne = new Subject();
                        subjectLevelOne.setTitle(levelOneValue);
                        subjectLevelOne.setSort(0);
                        baseMapper.insert(subjectLevelOne);
                        parentId = subjectLevelOne.getId();
                    }else{
                        parentId = subject.getId();
                    }

                    String levelTwoValue = "";
                    Cell levelTwoCell = rowData.getCell(1);
                    if (levelTwoCell != null) {
                       levelTwoValue = excelImportUtil.getCellValue(levelTwoCell);
                        if (StringUtils.isEmpty(levelTwoValue)){
                            msg.add("第"+rowNum+"行二级分类为空");
                            continue;
                        }
                    }
                    Subject subjectSub = this.getSunByTitle(levelTwoValue,parentId);
                    Subject subjectLevelTwo = null;
                    if (subjectSub == null) {
                        subjectLevelTwo = new Subject();
                        subjectLevelTwo.setTitle(levelTwoValue);
                        subjectLevelTwo.setParentId(parentId);
                        subjectLevelTwo.setSort(0);
                        baseMapper.insert(subjectLevelTwo);
                    }
                }
            }
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
        return msg;
    }

    @Override
    public List<SubjectVo> getNestedTreeList() {
        List<SubjectVo> subjectVos = new ArrayList<>();
        QueryWrapper<Subject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id",0);
        queryWrapper1.orderByAsc("sort");
        List<Subject> subjectList = baseMapper.selectList(queryWrapper1);

        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id",0);
        queryWrapper2.orderByAsc("sort");
        List<Subject> subSubjectList = baseMapper.selectList(queryWrapper2);

        for (Subject subject:subjectList) {
            SubjectVo subjectVo = new SubjectVo();
            BeanUtils.copyProperties(subject,subjectVo);
            List<SubSubjectVo> children = subjectVo.getChildren();
            for (Subject subSubject: subSubjectList) {
                SubSubjectVo subSubjectVo = new SubSubjectVo();
                BeanUtils.copyProperties(subSubject,subSubjectVo);
                if (subject.getId().equals(subSubject.getParentId())) {
                    children.add(subSubjectVo);
                }
            }
            subjectVos.add(subjectVo);
        }
        return subjectVos;
    }


    @Transactional
    @Override
    public boolean saveSubjectLevelOne(Subject subject) {
        Subject subjectLevelOne = this.getByTitle(subject.getTitle());
        if (subjectLevelOne == null) {
           return super.save(subject);
        }
        throw new GuliException(21002,"该类名已存在!");
    }

    @Transactional
    @Override
    public boolean removeById(String id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count >0){
            throw new GuliException(20002,"存在其他子分类,请先删除子分类!");
         }
        if(courseService.getCourseBySubjectId(id)){
            throw new GuliException(20003,"该分类下存在课程,请先删除课程!");
        }
        Integer deleteById = baseMapper.deleteById(id);
        return null !=deleteById && deleteById>0;
    }

    @Transactional
    @Override
    public boolean saveSubjectLevelTwo(Subject subject) {
        Subject sunByTitle = this.getSunByTitle(subject.getTitle(),subject.getParentId());
        if (sunByTitle == null){
            return super.save(subject);
        }
        throw new GuliException(21002,"该类名已存在!");
    }

    /**
     * 根据分类名称和父Id查询这个二级分类中是否存在
     * @param title
     * @param parentId
     * @return
     */
    private Subject getSunByTitle(String title, String parentId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id",parentId);
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 根据分类名称查询这个一级分类中是否存在
     * @param title
     * @return
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id","0");
        return baseMapper.selectOne(queryWrapper);
    }
}
