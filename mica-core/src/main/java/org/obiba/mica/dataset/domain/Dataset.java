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

import org.obiba.mica.domain.AbstractAuditableDocument;
import org.obiba.mica.domain.Attribute;
import org.obiba.mica.domain.AttributeAware;
import org.obiba.mica.domain.Attributes;
import org.obiba.mica.domain.Indexable;
import org.obiba.mica.domain.LocalizedString;

/**
 * Proxy to Opal tables.
 */
public abstract class Dataset extends AbstractAuditableDocument implements AttributeAware, Indexable {

  private static final long serialVersionUID = -3328963766855899217L;

  public static final String MAPPING_NAME = "Dataset";

  @NotNull
  private LocalizedString name;

  private LocalizedString description;

  private String entityType;

  private boolean published = false;

  private Attributes attributes;

  public LocalizedString getName() {
    return name;
  }

  public void setName(LocalizedString name) {
    this.name = name;
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
    this.entityType = entityType;
  }

  public boolean isPublished() {
    return published;
  }

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

  @Override
  public String getMappingName() {
    return MAPPING_NAME;
  }

  @Override
  protected com.google.common.base.Objects.ToStringHelper toStringHelper() {
    return super.toStringHelper().add("name", name);
  }
}