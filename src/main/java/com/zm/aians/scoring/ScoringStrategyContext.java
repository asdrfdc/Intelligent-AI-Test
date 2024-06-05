package com.zm.aians.scoring;


import com.zm.aians.common.ErrorCode;
import com.zm.aians.exception.BusinessException;
import com.zm.aians.model.entity.App;
import com.zm.aians.model.entity.UserAnswer;
import com.zm.aians.model.enums.AppScoringStrategyEnum;
import com.zm.aians.model.enums.AppTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Deprecated
public class ScoringStrategyContext {

    @Resource
    private CustomScoreScoringStrategy customScoreScoringStrategy;

    @Resource
    private CustomTestScoringStrategy customTestScoringStrategy;


    public UserAnswer doScore(List<String> choice, App app) throws Exception{
        AppTypeEnum appTypeEnum=AppTypeEnum.getEnumByValue(app.getAppType());
        AppScoringStrategyEnum appScoringStrategyEnum=AppScoringStrategyEnum.getEnumByValue(app.getScoringStrategy());
        if(appTypeEnum == null || appScoringStrategyEnum == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误，未找到匹配的策略");
        }
        //根据不同的应用类别和评分策略，选择对应的策略执行
        switch (appTypeEnum){
            case SCORE:
                switch (appScoringStrategyEnum){
                    case CUSTOM:
                        return customScoreScoringStrategy.doScore(choice,app);
                    case AI:
                        break;
                    default:
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误，未找到匹配的策略");
                }
                break;
            case TEST:
                switch (appScoringStrategyEnum){
                    case CUSTOM:
                        return customTestScoringStrategy.doScore(choice,app);
                    case AI:
                        break;
                    default:
                       throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误，未找到匹配的策略");
                   }
                   break;
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"应用配置有误，未找到匹配的策略");
    }
}
