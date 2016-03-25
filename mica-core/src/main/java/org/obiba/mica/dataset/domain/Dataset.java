/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.mica.dataset.domain;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.obiba.mica.core.domain.AbstractGitPersistable;
import org.obiba.mica.core.domain.Attribute;
import org.obiba.mica.core.domain.AttributeAware;
import org.obiba.mica.core.domain.Attributes;
import org.obiba.mica.core.domain.Indexable;
import org.obiba.mica.core.domain.LocalizedString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Proxy to Opal tables.
 */
public abstract class Dataset extends AbstractGitPersistable implements AttributeAware, Indexable {

  private static final long serialVersionUID = -3328963766855899217L;

  public static final String DEFAULT_ENTITY_TYPE = "Participant";

  public static final String MAPPING_NAME = "Dataset";

  @NotNull
  private LocalizedString name;

  private LocalizedString acronym;

  private LocalizedString description;

  private String entityType = DEFAULT_ENTITY_TYPE;

  private boolean published = false;

  private Attributes attributes;

  public LocalizedString getName() {
    return name;
  }

  public void setName(LocalizedString name) {
    this.name = name;
  }

  public LocalizedString getAcronym() {
    return acronym;
  }

  public void setAcronym(LocalizedString acronym) {
    this.acronym = acronym;
  }

  public LocalizedString getDescription() {
    return description;
  }

  public void setDescription(LocalizedString description) {
    this.description = description;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType == null ? DEFAULT_ENTITY_TYPE : entityType;
  }

  /**
   * @deprecated kept for backward compatibility.
   * @return
     */
  @JsonIgnore
  @Deprecated
  public boolean isPublished() {
    return published;
  }

  @JsonProperty
  public void setPublished(boolean published) {
    this.published = published;
  }

  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }

  public Attributes getAttributes() {
    return attributes;
  }

  @Override
  public void addAttribute(Attribute attribute) {
    if(attributes == null) attributes = new Attributes();
    attributes.addAttribute(attribute);
  }

  @Override
  public void removeAttribute(Attribute attribute) {
    if(attributes != null) {
      attributes.removeAttribute(attribute);
    }
  }

  @Override
  public void removeAllAttributes() {
    if(attributes != null) attributes.removeAllAttributes();
  }

  @Override
  public boolean hasAttribute(String attName, @Nullable String namespace) {
    return attributes != null && attributes.hasAttribute(attName, namespace);
  }

  @Override
  public String getClassName() {
    return getClass().getSimpleName();
  }

  // for JSON deserial
  public void setClassName(String className) {}

  @Override
  @JsonIgnore
  public String getMappingName() {
    return MAPPING_NAME;
  }

  @Override
  @JsonIgnore
  public String getParentId() {
    return null;
  }

  @Override
  protected MoreObjects.ToStringHelper toStringHelper() {
    return super.toStringHelper().add("name", name);
  }
}
