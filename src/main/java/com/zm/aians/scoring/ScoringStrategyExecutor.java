package com.zm.aians.scoring;

import com.zm.aians.common.ErrorCode;
import com.zm.aians.exception.BusinessException;
import com.zm.aians.model.entity.App;
import com.zm.aians.model.entity.UserAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评分策略执行器
 */
@Service
public class ScoringStrategyExecutor {

    //策略列表
    @Resource
    private List<ScoringStrategy> scoringStrategyList;

    public UserAnswer doScore(List<String> choice, App app) throws Exception {
        Integer appType = app.getAppType();
        Integer appScoringStrategy = app.getScoringStrategy();
        if(appType == null || appScoringStrategy == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误，未找到匹配的策略");
        }
        //根据注解获取策略
        for(ScoringStrategy strategy:scoringStrategyList){
            if(strategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)){
                ScoringStrategyConfig scoringStrategyConfig=strategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                if(scoringStrategyConfig.appType() == appType && scoringStrategyConfig.scoringStrategy() == appScoringStrategy){
                    return strategy.doScore(choice,app);
                }
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误，未找到匹配的策略");
    }
}
