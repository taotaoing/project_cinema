package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBannerTMapper;


import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.api.film.filmService.FilmIndexService;
import com.stylefeng.guns.api.film.filmVo.BannerVo;
import com.stylefeng.guns.api.film.filmVo.FilmIndexVo;
import com.stylefeng.guns.api.film.filmVo.FilmInfo;
import com.stylefeng.guns.api.film.filmVo.FilmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmIndexService.class)
public class FilmIndexServiceImpl implements FilmIndexService {

    @Autowired
    private MtimeBannerTMapper mtimeBannerTMapper;

    @Autowired
    private MtimeFilmTMapper mtimeFilmTMapper;
    //获取banners

    @Override
    public FilmIndexVo getFilmInfo() {
        FilmIndexVo filmIndexVo = new FilmIndexVo();

        List<BannerVo> bannerVos = getBannersBean();
        FilmVo soonFilms = getSoonFilmsBean();
        FilmVo hotFilms = getHotFilmsBean();
        List<FilmInfo> boxRanking = getBoxRankingBean();
        List<FilmInfo> expectRanking = getExpectRankingBean();
        List<FilmInfo> top = getTopBean();

        filmIndexVo.setBanners(bannerVos);
        filmIndexVo.setHotFilms(hotFilms);
        filmIndexVo.setSoonFilms(soonFilms);
        filmIndexVo.setBoxRanking(boxRanking);
        filmIndexVo.setExpectRanking(expectRanking);
        filmIndexVo.setTop100(top);
        return filmIndexVo;
    }

//    private List<BannerVo> do2Banner(List<MtimeBannerT> mtimeBannerTS) {
//        ArrayList<BannerVo> list = new ArrayList<>();
//        for (MtimeBannerT mtimeBannerT : mtimeBannerTS) {
//            BannerVo bannerVo = do2Banner1(mtimeBannerT);
//            list.add(bannerVo);
//        }
//
//        return list;
//    }
//    private BannerVo do2Banner1(MtimeBannerT mtimeBannerT) {
//        BannerVo bannerVo = new BannerVo();
//        bannerVo.setBannerAddress(mtimeBannerT.getBannerAddress());
//        bannerVo.setBannerId(mtimeBannerT.getUuid());
//        bannerVo.setBannerAddress(mtimeBannerT.getBannerAddress());
//        return bannerVo;
//    }

    public List<BannerVo> getBannersBean() {
        List<BannerVo> bannerVos = mtimeBannerTMapper.selectBannersBean();
        return bannerVos;
    }

    //获取热门上映
    public FilmVo getHotFilmsBean() {
        int nums = mtimeFilmTMapper.countHotFilms();
        FilmVo filmVo = new FilmVo();
        filmVo.setFilmNum(nums);
        List<FilmInfo> filmInfos = mtimeFilmTMapper.selectFilms();
        filmVo.setFilmInfo(filmInfos);
        return filmVo;
    }

    //获取即将上映影片（受欢迎程度做排序）
    public FilmVo getSoonFilmsBean() {
        int nums = mtimeFilmTMapper.countHotFilms();
        FilmVo filmVo = new FilmVo();
        filmVo.setFilmNum(nums);
        List<FilmInfo> filmInfos = mtimeFilmTMapper.selectFilms();
        filmVo.setFilmInfo(filmInfos);
        return filmVo;
    }

    //获取票房排行榜
    public List<FilmInfo> getBoxRankingBean() {
//        List<FilmInfo> filmInfos = mtimeFilmTMapper.selectFilms();
//        return filmInfos;
        ArrayList<FilmInfo> list = new ArrayList<>();
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(null);
        for (MtimeFilmT m:mtimeFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(m.getUuid()+"");
            filmInfo.setImgAddress(m.getImgAddress());
            filmInfo.setFilmName(m.getFilmName());
            filmInfo.setBoxNum(m.getFilmBoxOffice());
            list.add(filmInfo);
        }
        return list;
    }

    //获取人气排行榜
    public List<FilmInfo> getExpectRankingBean() {
        List<FilmInfo> filmInfos = mtimeFilmTMapper.selectFilms();
        return filmInfos;
    }

    //获取top100
    public List<FilmInfo> getTopBean() {
        List<FilmInfo> filmInfos = mtimeFilmTMapper.selectFilms();
        return filmInfos;
    }
}
