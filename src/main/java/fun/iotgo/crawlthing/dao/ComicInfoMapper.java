package fun.iotgo.crawlthing.dao;

import fun.iotgo.crawlthing.entity.ComicInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComicInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComicInfo record);

    int insertSelective(ComicInfo record);

    ComicInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComicInfo record);

    int updateByPrimaryKey(ComicInfo record);
}