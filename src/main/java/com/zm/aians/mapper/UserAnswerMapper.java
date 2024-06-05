package com.zm.aians.mapper;

import com.zm.aians.model.dto.statistic.AppAnswerCountDTO;
import com.zm.aians.model.dto.statistic.AppAnswerResultCountDTO;
import com.zm.aians.model.entity.UserAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 29524
* @description 针对表【user_answer(用户答题记录)】的数据库操作Mapper
* @createDate 2024-05-29 14:21:04
* @Entity com.zm.aians.model.entity.UserAnswer
*/
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {


    @Select("select appId,count(userId) as answerCount from user_answer group by appId order by answerCount desc limit 10")
    List<AppAnswerCountDTO> doAppAnswerCount();

    @Select("select resultName,count(resultName) as resultCount from user_answer where appId=#{appId} group by resultName order by resultCount desc;")
    List<AppAnswerResultCountDTO> doAppAnswerResultCount(Long appId);
}




