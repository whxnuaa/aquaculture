package com.jit.aquaculture.serviceimpl.knowledge;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.util.ImageUtils;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.knowledge.Disease;
import com.jit.aquaculture.mapper.knowledge.DiseaseMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Value("${image.diseaseStore.path}")
    private String diseaseStore_path;

    @Value("${image.diseaseStore.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Override
    public Disease insertDisease(MultipartFile image, HttpServletRequest request) throws IOException {
        String name = request.getParameter("diseaseName");
        if (name== null || name==""){
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        Disease isExist = diseaseMapper.getDiseaseByName(name);
        if (isExist!=null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        Disease disease = new Disease();
        disease.setBig_category(request.getParameter("big_category"));
        disease.setSmall_category(request.getParameter("small_category"));
        disease.setDiseaseName(name);
        disease.setSymptom(request.getParameter("symptom"));
        disease.setTreatment(request.getParameter("treatment"));
        disease.setSource(request.getParameter("source"));
        disease.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,diseaseStore_path);
            disease.setImage(image_url+fileName);
        }

        int flag = diseaseMapper.insert(disease);
        if (flag>0){
            return disease;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }



    @Override
    public Boolean deleteDisease(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            diseaseMapper.deleteDiseaseBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            diseaseMapper.delete(id);
        }
        return true;
    }

    @Override
    public Disease updateDisease(MultipartFile image,HttpServletRequest request, Integer id) throws IOException {
        Disease disease = diseaseMapper.getOneDisease(id);
        if (disease == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        disease.setId(id);
        disease.setBig_category(request.getParameter("big_category"));
        disease.setSmall_category(request.getParameter("small_category"));
        disease.setDiseaseName(request.getParameter("diseaseName"));
        disease.setSymptom(request.getParameter("symptom"));
        disease.setTreatment(request.getParameter("treatment"));
        disease.setSource(request.getParameter("source"));
        disease.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,diseaseStore_path);
            disease.setImage(image_url+fileName);
        }

        int flag = diseaseMapper.update(disease);
        if (flag>0){
            Disease disease1 = diseaseMapper.getOneDisease(id);
            return disease1;

        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }

    }

    @Override
    public PageVO<Disease> getAllDiseases(PageQO pageQO) {
        Page<Disease> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Disease> diseaseList = diseaseMapper.getAllDiseases();
        return PageVO.build(page);
    }

    @Override
    public PageVO<Disease> getDiseasesByCategory(String bigCategory, String smallCategory, PageQO pageQO) {
        List<Disease> diseaseList = new ArrayList<>();
        if (smallCategory != null && !"".equals(smallCategory.trim())){
            Page<Disease> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            diseaseList = diseaseMapper.getDiseasesBySmallCategory(bigCategory,smallCategory);
        }else {
            Page<Disease> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            diseaseList = diseaseMapper.getDiseasesByCategory(bigCategory);

        }
        return PageVO.build(diseaseList);
    }

    @Override
    public Disease getDiseaseInfo(Integer id) {
        Disease disease = diseaseMapper.getOneDisease(id);
        if (disease == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        return disease;
    }

}
