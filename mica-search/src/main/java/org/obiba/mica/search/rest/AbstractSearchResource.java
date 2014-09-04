/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.search.rest;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.obiba.mica.micaConfig.MicaConfigService;
import org.obiba.mica.web.model.MicaSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import static org.obiba.mica.web.model.MicaSearch.QueryResultDto;

public abstract class AbstractSearchResource {

  private static final Logger log = LoggerFactory.getLogger(AbstractSearchResource.class);

  @Inject
  private Client client;

  @Inject
  private MicaConfigService micaConfigService;

  @Inject
  private AggregationYamlParser aggregationYamlParser;

  /**
   * Get the description of the available aggregation in YAML format and as a {@link org.springframework.core.io.Resource}.
   *
   * @return
   */
  protected abstract Resource getAggregationsDescription();

  /**
   * Get the ES index name.
   *
   * @return
   */
  protected abstract String getSearchIndex();

  /**
   * Get the ES type that is searched.
   *
   * @return
   */
  protected abstract String getSearchType();

  /**
   * Process the search hits by adding detailed results.
   *
   * @param builder
   * @param detailedQuery
   * @param detailedResult
   * @param hits
   * @throws IOException
   */
  protected abstract void processHits(QueryResultDto.Builder builder, boolean detailedQuery, boolean detailedResult,
      SearchHits hits) throws IOException;

  protected void processAggregations(QueryResultDto.Builder builder, Aggregations aggregations) {
    builder.addAllAggs(EsQueryResultParser.newParser().parseAggregations(aggregations));
  }

  protected QueryResultDto execute(QueryBuilder queryBuilder, int from, int size, boolean detailed) throws IOException {
    return execute(queryBuilder, from, size, detailed, detailed);
  }

  protected QueryResultDto execute(QueryBuilder queryBuilder, int from, int size, boolean detailedQuery,
      boolean detailedResult) throws IOException {
    SearchRequestBuilder requestBuilder = client.prepareSearch(getSearchIndex()) //
        .setTypes(getSearchType()) //
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH) //
        .setQuery(queryBuilder) //
        .setFrom(from) //
        .setSize(size);

    if(!detailedQuery) {
      requestBuilder.setNoFields();
    }

    aggregationYamlParser.setLocales(micaConfigService.getConfig().getLocales());
    aggregationYamlParser.getAggregations(getAggregationsDescription()).forEach(requestBuilder::addAggregation);

    log.info("Request: {}", requestBuilder.toString());
    SearchResponse response = requestBuilder.execute().actionGet();
    log.info("Response: {}", response.toString());
    QueryResultDto.Builder builder = QueryResultDto.newBuilder().setTotalHits((int) response.getHits().getTotalHits());
    processHits(builder, detailedQuery, detailedResult, response.getHits());
    processAggregations(builder, response.getAggregations());

    QueryResultDto dto = builder.build();
    log.info("Response DTO : {}", dto);
    return dto;
  }

}
