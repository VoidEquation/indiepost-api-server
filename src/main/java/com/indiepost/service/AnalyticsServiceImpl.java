package com.indiepost.service;

import com.indiepost.dto.stat.*;
import com.indiepost.enums.Types;
import com.indiepost.enums.Types.ClientType;
import com.indiepost.model.Metadata;
import com.indiepost.repository.MetadataRepository;
import com.indiepost.repository.StatRepository;
import com.indiepost.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by jake on 17. 4. 13.
 */
@Service
@Transactional
public class AnalyticsServiceImpl implements AnalyticsService {

    private final MetadataRepository metadataRepository;

    private final StatRepository statRepository;

    private final VisitorRepository visitorRepository;

    @Autowired
    public AnalyticsServiceImpl(MetadataRepository metadataRepository,
                                StatRepository statRepository,
                                VisitorRepository visitorRepository) {
        this.metadataRepository = metadataRepository;
        this.statRepository = statRepository;
        this.visitorRepository = visitorRepository;
    }

    @Override
    public OverviewStats getOverviewStats(PeriodDto periodDto) {
        LocalDate startDate = periodDto.getStartDate();
        LocalDate endDate = periodDto.getEndDate();
        LocalDateTime since = startDate.atStartOfDay();
        LocalDateTime until = endDate.atTime(23, 59, 59);

        List<TimeDomainStat> pageviewStatResult;
        List<TimeDomainStat> visitorTrendResult;

        Types.TimeDomainDuration duration = periodDto.getDuration();
        pageviewStatResult = statRepository.getPageviewTrend(since, until, duration);
        visitorTrendResult = visitorRepository.getVisitorTrend(since, until, duration);

        Trend pageviewTrend = new Trend();
        Trend visitorTrend = new Trend();
        pageviewTrend.setDuration(duration);
        pageviewTrend.setResult(pageviewStatResult);
        visitorTrend.setDuration(duration);
        visitorTrend.setResult(visitorTrendResult);

        OverviewStats stats = new OverviewStats();
        stats.setPageviewTrend(pageviewTrend);
        stats.setVisitorTrend(visitorTrend);
        stats.setPeriod(periodDto);

        stats.setTotalPageview(statRepository.getTotalPageviews(since, until));
        stats.setTotalUniquePageview(statRepository.getTotalUniquePageviews(since, until));
        stats.setTotalPostview(statRepository.getTotalPostviews(since, until));
        stats.setTotalUniquePostview(statRepository.getTotalUniquePostviews(since, until));
        stats.setTopPagesWebapp(statRepository.getTopPages(since, until, 10L));
        stats.setTopPosts(statRepository.getTopPosts(since, until, 10L));
        stats.setPageviewByAuthor(statRepository.getPageviewsByAuthor(since, until, 10L));
        stats.setPageviewByCategory(statRepository.getPageviewsByCategory(since, until, 10L));

        stats.setTotalVisitor(visitorRepository.getTotalVisitors(since, until));
        stats.setTotalAppVisitor(visitorRepository.getTotalVisitors(since, until, ClientType.INDIEPOST_LEGACY_MOBILE_APP.toString()));
        stats.setTopBrowser(visitorRepository.getTopWebBrowsers(since, until, 10L));
        stats.setTopChannel(visitorRepository.getTopChannel(since, until, 10L));
        stats.setTopReferrer(visitorRepository.getTopReferrers(since, until, 10L));
        stats.setTopOs(visitorRepository.getTopOs(since, until, 10L));

        return stats;
    }

    @Override
    public RecentAndOldPostStats getRecentAndOldPostStats(PeriodDto periodDto) {
        LocalDate startDate = periodDto.getStartDate();
        LocalDate endDate = periodDto.getEndDate();
        LocalDateTime since = startDate.atStartOfDay();
        LocalDateTime until = endDate.atTime(23, 59, 59);
        Types.TimeDomainDuration duration = periodDto.getDuration();

        List<TimeDomainDoubleStat> resultStats = statRepository.getRecentAndOldPageviewTrend(since, until, duration);
        DoubleTrend trend = new DoubleTrend();
        trend.setDuration(duration);
        trend.setResult(resultStats);
        trend.setStatName("Time domain old and new post pageviews trend");

        RecentAndOldPostStats stats = new RecentAndOldPostStats();
        stats.setTrend(trend);
        stats.setPeriod(periodDto);
        stats.setTopRecentPosts(statRepository.getTopRecentPosts(since, until, 10L));
        stats.setTopOldPosts(statRepository.getTopOldPosts(since, until, 10L));
        return stats;
    }

    @Override
    public PostStatsDto getCachedPostStats() {
        List<PostStatDto> statData = statRepository.getCachedPostStats();
        Optional<Metadata> optional = metadataRepository.findById(1L);
        if (optional.isPresent()) {
            Metadata metadata = optional.get();
            LocalDateTime lastUpdated = metadata.getPostStatsLastUpdated();
            return new PostStatsDto(lastUpdated, statData);
        } else {
            return null;
        }

    }

    @Override
    public PostStatsDto getPostStats(PeriodDto periodDto) {
        LocalDate startDate = periodDto.getStartDate();
        LocalDate endDate = periodDto.getEndDate();
        LocalDateTime since = startDate.atStartOfDay();
        LocalDateTime until = endDate.atTime(23, 59, 59);
        List<PostStatDto> postStatDtoList = statRepository.getPostStatsOrderByPageviews(since, until, 3000L);
        return new PostStatsDto(LocalDateTime.now(), postStatDtoList, periodDto);
    }
}
