/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.study.search;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.obiba.mica.core.service.PublishedDocumentService;
import org.obiba.mica.search.AbstractPublishedDocumentService;
import org.obiba.mica.study.domain.Study;
import org.obiba.mica.study.service.PublishedStudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Service
public class EsPublishedStudyService extends AbstractPublishedDocumentService<Study> implements PublishedStudyService {

  @Inject
  private ObjectMapper objectMapper;

  @Override
  protected Study processHit(SearchHit hit) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(hit.getSourceAsString().getBytes());
    return objectMapper.readValue(inputStream, Study.class);
  }

  @Override
  protected String getIndexName() {
    return StudyIndexer.PUBLISHED_STUDY_INDEX;
  }

  @Override
  protected String getType() {
    return StudyIndexer.STUDY_TYPE;
  }


}
