/*
 * Copyright (c) 2017 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.micaConfig.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.obiba.mica.spi.search.ConfigurationProvider;
import org.obiba.opal.core.domain.taxonomy.Taxonomy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class MicaConfigurationProvider implements ConfigurationProvider {

  @Inject
  private MicaConfigService micaConfigService;

  @Inject
  @Lazy
  private TaxonomyService taxonomyService;

  @Override
  public List<String> getLocales() {
    return micaConfigService.getLocales();
  }

  @Override
  public List<String> getRoles() {
    return micaConfigService.getRoles();
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return micaConfigService.getObjectMapper();
  }

  @Override
  public Taxonomy getNetworkTaxonomy() {
    return taxonomyService.getNetworkTaxonomy();
  }

  @Override
  public Taxonomy getStudyTaxonomy() {
    return taxonomyService.getStudyTaxonomy();
  }

  @Override
  public Taxonomy getVariableTaxonomy() {
    return taxonomyService.getVariableTaxonomy();
  }

  @Override
  public Taxonomy getDatasetTaxonomy() {
    return taxonomyService.getDatasetTaxonomy();
  }

  @Override
  public List<Taxonomy> getVariableTaxonomies() {
    return taxonomyService.getVariableTaxonomies();
  }
}
