package com.kspt.orange.frameworks.api.twitter.sources;

import com.kspt.orange.application.TraversedSource;
import com.kspt.orange.core.Storage;
import com.kspt.orange.core.entities.DataCollection;
import com.kspt.orange.frameworks.api.twitter.endpoints.home.TwitterHomeDataQuery;
import com.kspt.orange.frameworks.api.twitter.entities.common.TwitterData;
import java.util.List;

public class TraversedTwitterSource
    extends TraversedSource<TwitterHomeDataQuery, TwitterData, TwitterData> {
  public TraversedTwitterSource(final Storage<TwitterHomeDataQuery, TwitterData> storage) {
    super(storage);
  }

  @Override
  public List<TwitterData> filter(final DataCollection<TwitterData> dataColection) {
    /*final List<TwitterData> filtered = Lists.newLinkedList();
    for (final TwitterData td : dataColection.dataList()) {
      if (td.coordinates() != null) {
        filtered.add(td);
      }
    }
    return filtered;*/
    return dataColection.dataList();
  }

  @Override
  public TwitterHomeDataQuery nextPortionQuery(
      final TwitterHomeDataQuery previousQuery,
      final DataCollection<TwitterData> answerToPreviousQuery) {
    final List<TwitterData> dataList = answerToPreviousQuery.dataList();
    if (dataList.isEmpty()) {
      return previousQuery;
    } else {
      long minimalIdInPreviousAnswer = computeMinimalId(dataList);
      return new TwitterHomeDataQuery(
          previousQuery.limit(),
          minimalIdInPreviousAnswer - 1,
          previousQuery.trimUser(),
          previousQuery.excludeReplies(),
          previousQuery.contributorsDetails(),
          previousQuery.includeRts());
    }
  }

  private long computeMinimalId(final List<TwitterData> dataList) {
    long minimumIdInPreviousAnswer = dataList.get(0).id();
    for (int i = 2; i < dataList.size(); ++i) {
      final long id = dataList.get(i).id();
      if (id < minimumIdInPreviousAnswer) {
        minimumIdInPreviousAnswer = id;
      }
    }
    return minimumIdInPreviousAnswer;
  }
}
