package com.jit.aquaculture.serviceimpl.knowledge;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.util.ImageUtils;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.knowledge.Technology;
import com.jit.aquaculture.mapper.knowledge.TechnologyMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnologyServiceImpl implements TechnologyService {
    @Value("${image.technologyStore.path}")
    private String technologyStore_path;

    @Value("${image.technologyStore.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;
    @Autowired
    private TechnologyMapper technologyMapper;
    @Override
    public Technology insert(MultipartFile image, HttpServletRequest request)throws IOException{
        String name = request.getParameter("name");
        if (name== null || name==""){
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        Technology isExist = technologyMapper.select(name);
        if (isExist!=null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }

        Technology technology = new Technology();
        technology.setName(request.getParameter("name"));
        technology.setCategory(request.getParameter("category"));
        technology.setContent(request.getParameter("content"));
        technology.setSource(request.getParameter("source"));
        technology.setPublish_time(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,technologyStore_path);
            technology.setImage(image_url+fileName);
        }
        int flag = technologyMapper.insert(technology);

        return technology;
    }

    @Override
    public Boolean delete(String ids) {

        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            technologyMapper.deleteTechnologyBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            technologyMapper.delete(id);
        }
        return true;

    }

    /**
     * 更新综合知识
     * @param image
     * @param request
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public Technology update(MultipartFile image, HttpServletRequest request, Integer id)throws IOException {
        Technology technology = technologyMapper.getOne(id);
        if (technology==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        technology.setName(request.getParameter("name"));
        technology.setCategory(request.getParameter("category"));
        technology.setContent(request.getParameter("content"));
        technology.setSource(request.getParameter("source"));
        technology.setPublish_time(new Date());
        if (image != null){
            String fileName = ImageUtils.ImgReceive(image,technologyStore_path);
            technology.setImage(image_url+fileName);
        }

        int flag = technologyMapper.update(technology);
        if (flag>0){
           return technologyMapper.getOne(id);
        }else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
    }

    @Override
    public PageVO<Technology> getByCategory(String category, PageQO pageQO) {
        Page<Technology> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Technology> technologyList = technologyMapper.selectByCategory(category);
        return PageVO.build(page);
    }

    @Override
    public List<String> getCategoryList() {
        List<String> categoryList = technologyMapper.selectCategoryList();
        return categoryList;
    }

    @Override
    public PageVO<Technology> getAll(PageQO pageQO) {
        Page<Technology> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Technology> technologyList = technologyMapper.selectAll();
        return PageVO.build(page);

    }

    @Override
    public Technology getOne(Integer id) {
        Technology technology = technologyMapper.getOne(id);
        return technology;
    }
}
