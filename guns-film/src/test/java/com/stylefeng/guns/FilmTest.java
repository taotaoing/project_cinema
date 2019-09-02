package com.stylefeng.guns;

import com.stylefeng.guns.rest.GunsFilmApplication;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.film.filmService.FilmConditionService;
import com.stylefeng.guns.rest.film.filmService.FilmIndexService;
import com.stylefeng.guns.rest.film.filmVo.FilmConditionVo;
import com.stylefeng.guns.rest.film.filmVo.FilmIndexVo;
import com.stylefeng.guns.rest.modular.film.FilmIndexServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MapperScan(basePackages = "com.stylefeng.guns.rest.common.persistence.dao")
@SpringBootTest(classes = {GunsFilmApplication.class, FilmIndexServiceImpl.class})
public class FilmTest {
    @Autowired
    private FilmIndexService filmService;
    @Autowired
    private FilmConditionService filmConditionService;

    @Autowired
    MtimeBannerTMapper mtimeBannerTMapper;
    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

//    @Test
//    public void test(){
//        FilmIndexVo filmIndexVo = new FilmIndexVo();
//        filmIndexVo.setBanners(filmService.getBanners());
//        filmIndexVo.setHotFilms(filmService.getHotFilms());
//        filmIndexVo.setSoonFilms(filmService.getSoonFilms());
//        filmIndexVo.setBoxRanking(filmService.getBoxRanking());
//        filmIndexVo.setExpectRanking(filmService.getExpectRanking());
//        filmIndexVo.setTop100(filmService.getTop());
//        System.out.println(filmIndexVo);
//    }

    @Test
    public void test1(){
//        List<BannerVo> bannerVos = mtimeBannerTMapper.selectBannersBean("XXX");
//        int i = mtimeFilmTMapper.countHotFilms();
//        List<MtimeBannerT> mtimeBannerTS = mtimeBannerTMapper.selectList(null);
        FilmIndexVo filmInfo = filmService.getFilmInfo();
        System.out.println(filmInfo);
    }

    @Test
    public void test2(){
        FilmConditionVo filmCondition = filmConditionService.getFilmCondition(1, 1, 1);
        System.out.println(filmCondition);
    }
}
